package jps.hyperspin.module.dbmaker.processor;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jps.hyperspin.MainClass;
import jps.hyperspin.common.DatabaseUtilities;
import jps.hyperspin.common.DeltaFileUtilities;
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
import jps.hyperspin.module.dbmaker.model.Delta;
import jps.hyperspin.module.dbmaker.model.MenuTypeWrapper;

public class DbMakerProcessor extends CommonProcessor {
	private String system;
	private DbMakerOption option;
	private DatabaseDetail detail;

	private Map<String, Delta> countryDelta;
	private Map<String, Delta> regionDelta;
	private Set<Delta> replacedGames = new HashSet<Delta>();
	private dbMakerResult result = new dbMakerResult();

	public class dbMakerResult {
		public long dbSize = 0;
		public long nbMissing = 0;
		public long nbReplaced = 0;
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

		// Deltas
		countryDelta = DeltaFileUtilities.loadDeltaFileIndexedByOriginalName(DatabaseUtilities.getDeltaPath(system,
				option.country));

		regionDelta = DeltaFileUtilities.loadDeltaFileIndexedByOriginalName(DatabaseUtilities.getDeltaPath(system,
				option.region));

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

				// Check Replacement
				String originalName = game.getName();
				boolean replaced = checkReplacement(game, countryDelta);
				if (!replaced) {
					replaced = checkReplacement(game, regionDelta);
				}

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
						if (found) {
							CommonLogger.instance.trace("Clone excluded : " + game.getName());
							it.remove();
						}
						result.nbClones++;
					}
				}

				if (menu.getFileName().startsWith(MainClass.mainFrame.getSystemSelected())) {
					// Main database
					result.dbSize++;
					if (replaced) {
						result.nbReplaced++;
						if (found && option.moveReplacedRoms) {
							// Move replaced roms
							RomUtilities.moveRomToReplaceFolder(originalName, system, detail);
						}
					}
					if (!found) {
						result.nbMissing++;
					}
				}

			}
		}

		setProgress(70);

		// Genre file
		MenuTypeWrapper genreMenu = new MenuTypeWrapper(new MenuType(), "Genre.xml");
		for (MenuTypeWrapper menu : menus) {
			if (!menu.getFileName().startsWith(MainClass.mainFrame.getSystemSelected())) {
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
		CommonLogger.instance.info("\nTotal of " + (result.dbSize - result.nbMissing) + "/" + result.dbSize
				+ " rom found (" + result.nbMissing + " roms missing)");
		CommonLogger.instance.info("Total of " + (result.nbReplaced)
				+ " roms replaced according to region preferences.");
		CommonLogger.instance.info("-----------------------------------------------------");

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

	private boolean checkReplacement(GameType game, Map<String, Delta> deltas) throws IOException {
		if (deltas == null) {
			return false;
		}

		boolean replaced = false;
		// Try to replace name
		Delta delta = deltas.get(game.getName());
		if (delta != null) {
			applyReplacement(game, delta);
			replaced = true;
		}

		// Try to replace cloneof section
		delta = deltas.get(game.getClass());
		if (delta != null) {
			game.setCloneof(delta.replacementName);
		}
		return replaced;
	}

	/**
	 * 
	 * @param game
	 * @param delta
	 */
	private void applyReplacement(GameType game, Delta delta) {
		game.setName(delta.replacementName);
		game.setCrc("");
		game.setDescription(game.getName());
		replacedGames.add(delta);
	}
}
