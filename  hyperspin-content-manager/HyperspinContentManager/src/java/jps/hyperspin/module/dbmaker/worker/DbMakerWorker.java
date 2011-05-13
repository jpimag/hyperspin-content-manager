package jps.hyperspin.module.dbmaker.worker;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jps.hyperspin.common.DatabaseUtilities;
import jps.hyperspin.common.DeltaFileUtilities;
import jps.hyperspin.common.file.FileFilterExtension;
import jps.hyperspin.common.file.FileUtilities;
import jps.hyperspin.common.worker.CommonWorker;
import jps.hyperspin.common.xml.XmlBinding;
import jps.hyperspin.main.controller.CommonLogger;
import jps.hyperspin.module.dbdownloader.model.generated.menu.GameType;
import jps.hyperspin.module.dbdownloader.model.generated.menu.MenuType;
import jps.hyperspin.module.dbmaker.model.DbMakerOption;
import jps.hyperspin.module.dbmaker.model.Delta;
import jps.hyperspin.module.dbmaker.model.MenuTypeWrapper;

/**
 * 
 * @author jps
 * 
 */
public class DbMakerWorker extends CommonWorker {

	private String system;
	private DbMakerOption option;

	private Map<String, Delta> countryDelta;
	private Map<String, Delta> regionDelta;
	private Set<Delta> replacedGames;

	public DbMakerWorker(String system, DbMakerOption option) {
		super();
		this.system = system;
		this.option = option;

	}

	@Override
	protected void executeInternal() throws Exception {

		// Deltas
		if (option.useRegionPreference) {
			countryDelta = DeltaFileUtilities.loadDeltaFileIndexedByOriginalName(DatabaseUtilities.getDeltaPath(system,
					option.country));

			regionDelta = DeltaFileUtilities.loadDeltaFileIndexedByOriginalName(DatabaseUtilities.getDeltaPath(system,
					option.region));
		}

		// Delete existing database
		FileUtilities.deleteAllFiles(DatabaseUtilities.getUserDatabaseDir(system), "xml");

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

		// Parse all games
		for (MenuTypeWrapper menu : menus) {
			Iterator<GameType> it = menu.getMenu().getGame().iterator();
			while (it.hasNext()) {
				GameType game = it.next();

				// Check Replacement
				checkReplacement(game, countryDelta);
				checkReplacement(game, regionDelta);

				// Check if exist
				// TODO
			}
		}

		// exclude clones
		// TODO

		// Genre file
		MenuTypeWrapper genreMenu = new MenuTypeWrapper(new MenuType(), "Genre.xml");
		menus.add(genreMenu);
		// TODO

		// Write files
		// TODO

		// Move replaced roms
		// TODO

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

	private void checkReplacement(GameType game, Map<String, Delta> deltas) throws IOException {
		if (deltas == null) {
			return;
		}
		// Try to replace name
		Delta delta = deltas.get(game.getName());
		if (delta != null) {
			applyReplacement(game, delta);
		}

		// Try to replace cloneof section
		delta = deltas.get(game.getClass());
		if (delta != null) {
			game.setCloneof(delta.replacementName);
		}
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
