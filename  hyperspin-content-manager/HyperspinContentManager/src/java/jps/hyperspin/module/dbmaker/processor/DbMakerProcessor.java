package jps.hyperspin.module.dbmaker.processor;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jps.hyperspin.common.DatabaseUtilities;
import jps.hyperspin.common.RomUtilities;
import jps.hyperspin.common.file.FileFilterExtension;
import jps.hyperspin.common.file.FileUtilities;
import jps.hyperspin.common.processor.CommonProcessor;
import jps.hyperspin.common.worker.CommonWorker;
import jps.hyperspin.common.xml.XmlBinding;
import jps.hyperspin.main.controller.CommonLogger;
import jps.hyperspin.module.dbdownloader.model.DatabaseDetail;
import jps.hyperspin.module.dbdownloader.model.generated.menu.GameType;
import jps.hyperspin.module.dbdownloader.model.generated.menu.MenuType;
import jps.hyperspin.module.dbmaker.model.DbMakerOption;
import jps.hyperspin.module.dbmaker.model.MenuTypeWrapper;

public class DbMakerProcessor extends CommonProcessor {
	private String system;
	private DbMakerOption option;
	private DatabaseDetail detail;

	private DbMakerResult result = new DbMakerResult();
	private List<String> missings = new ArrayList<String>();
	private Set<String> used = new HashSet<String>();

	public static class DbMakerResult implements Serializable {
		public long dbSize = 0;
		public long nbMissing = 0;
		public long nbClones = 0;

	}

	public DbMakerProcessor(String system, DbMakerOption option, DatabaseDetail detail, CommonWorker worker,
			int untilProgress) {
		super(worker, untilProgress);
		this.system = system;
		this.option = option;
		this.detail = detail;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute() throws Exception {

		// Delete existing database
		FileUtilities.deleteAllFiles(DatabaseUtilities.getUserDatabaseDir(system), "xml");
		setProgress(10);

		// Load all database
		List<MenuTypeWrapper> menus = new ArrayList<MenuTypeWrapper>();
		File[] databases = loadDatabases();
		for (File file : databases) {
			// Parse database
			CommonLogger.instance.info("File parsed : " + file.getName());
			FileReader reader = new FileReader(file);
			MenuType menu = (MenuType) XmlBinding.getInstance().xml2java(MenuType.class, reader);
			MenuTypeWrapper wrapper = new MenuTypeWrapper(menu, file.getName());
			menus.add(wrapper);
			CommonLogger.instance.info(menu.getGame().size() + " games");
		}
		setProgress(20);

		// Load roms
		// Get the rom list
		Map<String, String> romMap = RomUtilities.listRoms(system, detail);
		setProgress(30);

		// Parse all games
		double stepProgress = (double) 40 / (double) menus.size();
		double progress = 30;
		for (MenuTypeWrapper menu : menus) {
			progress += stepProgress;
			setProgress((int) progress);
			Iterator<GameType> it = menu.getMenu().getGame().iterator();
			while (it.hasNext()) {
				GameType game = it.next();

				// Check if exist
				boolean found = true;
				if (!romMap.containsKey(game.getName())) {
					CommonLogger.instance.trace("Rom not found : " + game.getName());
					it.remove();
					found = false;
				}

				// exclude clones
				if (option.noClone) {
					if (game.getCloneof() != null && !"".equals(game.getCloneof().trim())) {
						if (menu.getFileName().startsWith(system)) {
							result.nbClones++;
						}
						if (found) {
							CommonLogger.instance.trace("Clone excluded : " + game.getName());
							it.remove();
							continue;
						}
					}
				}

				if (menu.getFileName().startsWith(system)) {
					// Main database
					result.dbSize++;

					if (!found) {
						result.nbMissing++;
						missings.add(game.getName());
					} else {
						used.add(game.getName());
					}
				}

			}
		}

		setProgress(70);

		if (option.moveNotUsedRoms) {
			// We move all not used roms
			for (String rom : romMap.keySet()) {
				if (!used.contains(rom)) {
					RomUtilities.moveRomToNotUsedFolder(romMap.get(rom), system, detail);
				}
			}
		}

		// Genre file
		MenuTypeWrapper genreMenu = new MenuTypeWrapper(new MenuType(), "Genre.xml");
		for (MenuTypeWrapper menu : menus) {
			if (!menu.getFileName().startsWith(system)) {
				GameType category = new GameType();
				category.setName(FileUtilities.getNameWithoutExtension(menu.getFileName()));
				genreMenu.getMenu().getGame().add(category);
			}
		}
		menus.add(genreMenu);
		setProgress(80);

		// Write files
		for (MenuTypeWrapper menu : menus) {
			if (menu.getMenu().getGame().size() > 0) {
				FileWriter writer = new FileWriter(detail.userDatabaseDir + File.separator + menu.getFileName());
				XmlBinding.getInstance().java2xml(menu.getMenu(), writer);
				writer.close();
				CommonLogger.instance.info("Database File writed : " + menu.getFileName());
			}
		}

		// Logs
		writeMissingsFile(missings);
		Collection<String> unused = romMap.keySet();
		unused.removeAll(used);
		writUnusedFile(new ArrayList<String>(unused));
		CommonLogger.instance.info("\nTotal of " + (result.dbSize - result.nbMissing) + "/" + result.dbSize
				+ " rom found (" + result.nbMissing + " roms missing)");
		CommonLogger.instance.info("-----------------------------------------------------");

	}

	/**
	 * @return the result
	 */
	public DbMakerResult getResult() {
		return result;
	}

	/**
	 * 
	 * @return
	 */
	private File[] loadDatabases() {
		File dir = new File(DatabaseUtilities.getDownloadedDatabaseDir(system));
		File[] databases = dir.listFiles(new FileFilterExtension("xml"));
		return databases;
	}

	/**
	 * Write all non replaced region file.
	 * 
	 * @param datas
	 * @param type
	 */
	private void writeMissingsFile(List<String> datas) throws IOException {
		Collections.sort(datas);
		String path = DatabaseUtilities.getLogsDir(system);
		File file = new File(path);
		file.mkdirs();
		FileWriter writer = new FileWriter(path + File.separator + "missings.txt");
		for (String s : datas) {
			writer.write(s + "\n");
		}
		writer.close();
	}

	/**
	 * Write all non replaced region file.
	 * 
	 * @param datas
	 * @param type
	 */
	private void writUnusedFile(List<String> datas) throws IOException {
		Collections.sort(datas);
		String path = DatabaseUtilities.getLogsDir(system);
		File file = new File(path);
		file.mkdirs();
		FileWriter writer = new FileWriter(path + File.separator + "unused.txt");
		for (String s : datas) {
			writer.write(s + "\n");
		}
		writer.close();
	}
}
