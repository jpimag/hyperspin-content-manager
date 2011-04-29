package jps.hyperspin.module.dbmaker.worker;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import jps.hyperspin.MainClass;
import jps.hyperspin.common.DatabaseUtilities;
import jps.hyperspin.common.file.FileFilterExtension;
import jps.hyperspin.common.file.FileUtilities;
import jps.hyperspin.common.log.Logger;
import jps.hyperspin.common.view.ChoiceDialog;
import jps.hyperspin.common.xml.XmlBinding;
import jps.hyperspin.exception.HCMDatabaseException;
import jps.hyperspin.main.controller.CommonLogger;
import jps.hyperspin.module.AbstractProcessor;
import jps.hyperspin.module.dbdownloader.model.DatabaseDetail;
import jps.hyperspin.module.dbdownloader.model.generated.menu.GameType;
import jps.hyperspin.module.dbdownloader.model.generated.menu.MenuType;
import jps.hyperspin.module.dbmaker.presentation.IDatabaseOption;

/**
 * 
 * @author JPS
 * 
 */
public class DatabaseProcessor extends AbstractProcessor {

	public DatabaseProcessor(DatabaseDetail databaseDetail,
			IDatabaseOption databaseOption) {
		super(databaseDetail, databaseOption);
	}

	private enum ProcessingOption {
		ALL, REMOVE_DUPLICATE_REGION, REMOVE_CLONES
	}

	private enum PreProcessingOption {
		NONE, FORCE_EUROPE_IF_EXIST
	}

	/**
	 * 
	 * @param request
	 *            request
	 * @param logger
	 *            logger
	 * @throws HCMDatabaseException
	 *             exception
	 */
	public final void processDatabase() throws HCMDatabaseException,
			IOException {
		CommonLogger logger = CommonLogger.instance;
		// Pre process option
		List<PreProcessingOption> preOptions = new ArrayList<PreProcessingOption>();
		preOptions.add(PreProcessingOption.NONE);
		preOptions.add(PreProcessingOption.FORCE_EUROPE_IF_EXIST);
		PreProcessingOption preOption = (PreProcessingOption) JOptionPane
				.showInputDialog(null, "What database pre process choose ? "
						+ preOptions.size(), "Option",
						JOptionPane.WARNING_MESSAGE, null,
						preOptions.toArray(), preOptions.get(0));

		// option
		List<ProcessingOption> processingOptions = new ArrayList<ProcessingOption>();
		processingOptions.add(ProcessingOption.ALL);
		processingOptions.add(ProcessingOption.REMOVE_DUPLICATE_REGION);
		processingOptions.add(ProcessingOption.REMOVE_CLONES);
		ProcessingOption processingOption = (ProcessingOption) JOptionPane
				.showInputDialog(null,
						"What policy of database generation do you choose ? "
								+ processingOptions.size(), "Option",
						JOptionPane.WARNING_MESSAGE, null,
						processingOptions.toArray(), processingOptions.get(0));

		// Step 4 : Parse roms
		Map<String, String> romList = listRecursiveFilesWithFilter(new File(
				getIni().getRomPath()), false, getIni().getRomExtension());
		logger.info("Roms list performed. (" + romList.size() + " files)");

		// Step 5 : Load all databases
		List<MenuTypeWrapper> menus = new ArrayList<MenuTypeWrapper>();
		MenuTypeWrapper genreMenu = new MenuTypeWrapper(new MenuType(),
				"Genre.xml");
		menus.add(genreMenu);

		// Parse database
		Map<String, String> databaseGame = null;
		File dir = new File(
				DatabaseUtilities.getDownloadedDatabaseDir(MainClass.mainFrame
						.getSystemSelected()));
		File[] databases = dir.listFiles(new FileFilterExtension("xml"));
		for (File file : databases) {

			logger.info("Database to be parsed : " + file.getName());
			FileReader reader = new FileReader(file);
			MenuType menu = (MenuType) XmlBinding.getInstance().xml2java(
					MenuType.class, reader);
			normalizeMenu(menu);
			MenuTypeWrapper wrapper = new MenuTypeWrapper(menu, file.getName());
			menus.add(wrapper);
			logger.info(menu.getGame().size() + " games");

			if (!file.getName().startsWith(
					MainClass.mainFrame.getSystemSelected())) {
				// Add the category to genre
				GameType category = new GameType();
				category.setName(FileUtilities.getNameWithoutExtension(file));
				genreMenu.getMenu().getGame().add(category);
			} else {
				// Main database
				// Store main game list in a map
				databaseGame = new HashMap<String, String>();
				for (GameType game : menu.getGame()) {
					databaseGame.put(normalize(game.getName()), game.getName());
				}
			}

		}

		// PreProcess
		int nbForcedEurope = 0;
		int nbForcedFrance = 0;
		if (preOption == PreProcessingOption.FORCE_EUROPE_IF_EXIST) {
			for (MenuTypeWrapper menu : menus) {
				if (!menu.getFileName().equals("Genre.xml"))
					for (GameType game : menu.getMenu().getGame()) {

						// Game to test
						String initialRomNormalized = normalize(game.getName());
						String initialRomShortName = initialRomNormalized
								.split("\\(")[0];
						String revision = initialRomNormalized
								.substring(initialRomShortName.length());

						// Filter revision
						// some games have some name under dirrerent revision
						// We exclude revision with manufacturor
						boolean ok = true;
						String[] revisionArray = revision.split("\\)");
						for (String string : revisionArray) {
							String s = normalize(string);
							if (!s.contains("usa") && !s.contains("japan")
									&& !s.contains("unl") && !s.contains("rev")
									&& !s.contains("en")) {
								ok = false;
							}
						}

						//

						if (ok && !revision.contains("europe")) {

							for (String rom : romList.keySet()) {
								String romNormalized = normalize(rom);
								String romShortName = romNormalized
										.split("\\(")[0];
								String romRevision = romNormalized
										.substring(romShortName.length());
								if (romShortName.equals(initialRomShortName)
										&& romRevision.contains("europe")) {
									// Check that game does not exist already in
									// db
									if (databaseGame.get(rom) == null) {
										databaseGame.put(romNormalized, rom);
										databaseGame
												.remove(initialRomNormalized);
										game.setName(rom);
										game.setDescription(rom);
										nbForcedEurope++;
										break;
									}
								}
							}
						}

						//
						if (ok && !revision.contains("france")) {

							for (String rom : romList.keySet()) {
								String romNormalized = normalize(rom);
								String romShortName = romNormalized
										.split("\\(")[0];
								String romRevision = romNormalized
										.substring(romShortName.length());
								if (romShortName.equals(initialRomShortName)
										&& romRevision.contains("france")) {
									// Check that game does not exist already in
									// db
									if (databaseGame.get(rom) == null) {
										databaseGame.put(romNormalized, rom);
										databaseGame
												.remove(initialRomNormalized);
										game.setName(rom);
										game.setDescription(rom);
										nbForcedFrance++;
										break;
									}
								}
							}
						}

					}

			}

		}

		// Process main database
		logger.info("Process main database");
		int totalGames = 0;
		MenuTypeWrapper mainMenu = null;
		for (MenuTypeWrapper menu : menus) {

			if (menu.getFileName().startsWith(
					MainClass.mainFrame.getSystemSelected())) {
				mainMenu = menu;
				// Games map
				totalGames = menu.getMenu().getGame().size();

				// Step 6 : Parse current roms to find games
				for (GameType game : menu.getMenu().getGame()) {
					// filter game
					String fullName = romList.get(game.getName());
					if (fullName == null) {
						// Roms not found
						menu.getNotFoundGame().add(game);
					} else {
						romList.remove(game.getName());
					}
				}

				// Setp 7 : Parse notfound game to search matching roms
				if (databaseOption.isDeepMatchSelected()) {

					// Not found games copy
					List<GameType> notFound = new ArrayList<GameType>(
							menu.getNotFoundGame());

					int i = 0;
					out: for (GameType game : notFound) {
						i++;
						List<DistanceString> bests = bestMatch(game.getName(),
								romList);

						// Dialog
						String message = "Matching roms have been found. \nOriginal normalized name : "
								+ game.getName()
								+ "\nDo you want to accept and rename this rom ?";
						ChoiceDialog d = new ChoiceDialog(null,
								"Confirm dialog " + i + "/" + notFound.size(),
								message, bests.toArray());
						Object choix = d.getSelection();

						if (d.isCancelled()) {
							// Break
							break out;
						} else if (d.isOk()) {
							DistanceString ds = (DistanceString) choix;

							// Write log
							String rom = romList.get(ds.string);
							FileUtilities.moveFile(getIni().getRomPath(),
									getIni().getRomPath(), rom, game.getName()
											+ "." + getIni().getRomExtension());
							menu.getNotFoundGame().remove(game);
							romList.remove(ds.string);

						}
					}
				}

				// Step 8 :Remove not found games
				for (GameType game : menu.getNotFoundGame()) {
					menu.getMenu().getGame().remove(game);
				}

				// Reject some games
				reject(processingOption, menu, logger);
			}

		}

		// Category Files
		HashMap<String, GameType> mainMenuMap = new HashMap<String, GameType>();
		for (GameType game : mainMenu.getMenu().getGame()) {
			mainMenuMap.put(game.getName(), game);

		}
		for (MenuTypeWrapper menu : menus) {
			if (!menu.getFileName().startsWith(
					MainClass.mainFrame.getSystemSelected())
					&& !menu.getFileName().equals("Genre.xml")) {
				// Category file
				List<GameType> notfoundGames = new ArrayList<GameType>();
				for (GameType game : menu.getMenu().getGame()) {
					if (!mainMenuMap.containsKey(game.getName())) {
						notfoundGames.add(game);
					}
				}
				for (GameType game : notfoundGames) {
					menu.getMenu().getGame().remove(game);
				}
			}
		}

		// Write database file
		for (MenuTypeWrapper menu : menus) {
			if (menu.getMenu().getGame().size() > 0) {
				FileWriter writer = new FileWriter(
						databaseDetail.userDatabaseDir + File.separator
								+ menu.getFileName());
				XmlBinding.getInstance().java2xml(menu.getMenu(), writer);
				writer.close();
				logger.info("Database File writed : " + menu.getFileName());
			}
		}

		// stats
		for (MenuTypeWrapper menu : menus) {
			if (menu.getFileName().startsWith(
					MainClass.mainFrame.getSystemSelected())) {
				// Stats
				logStats(menu, totalGames, true, logger);
			}
		}

		logger.info("----------------------------------");

		if (preOption == PreProcessingOption.FORCE_EUROPE_IF_EXIST) {
			logger.info(nbForcedEurope + " roms forced to Europe");
			logger.info(nbForcedFrance + " roms forced to france");
		}
		logger.info("FINISHED.");

	}

	private final void reject(ProcessingOption option, MenuTypeWrapper menu,
			Logger logger) {
		List<GameType> rejected = new ArrayList<GameType>();

		if (option == ProcessingOption.REMOVE_DUPLICATE_REGION) {
			// Reject some games
			for (GameType game : menu.getMenu().getGame()) {
				if (rejectGame(game, menu.getMenu(), logger)) {
					rejected.add(game);
				}
			}
		} else if (option == ProcessingOption.REMOVE_CLONES) {
			// Reject some games
			for (GameType game : menu.getMenu().getGame()) {
				if (!game.getCloneof().trim().equals("")) {
					rejected.add(game);
				}
			}

		}
		menu.setRejectedGame(rejected);
		for (GameType game : rejected) {
			menu.getMenu().getGame().remove(game);
		}
	}

	protected final void logStats(MenuTypeWrapper menu, int totalGames,
			boolean detailed, final Logger logger) {
		int menuRomNotFound = 0;
		logger.info("-------------------------------");
		logger.info("Database : " + menu.getFileName());
		// Roms
		for (GameType game : menu.getNotFoundGame()) {
			menuRomNotFound++;
			if (detailed) {
				logger.trace("Rom : " + game.getName() + " not found.");
			}
		}

		// Stats
		int menuRomFound = menu.getMenu().getGame().size();
		int menuRom = menuRomFound + menuRomNotFound;
		logger.info("\nTotal of " + menuRomFound + "/" + menuRom + " found ("
				+ menuRomNotFound + " roms missing)");
		logger.info("\nTotal of " + menuRomFound + "/" + menuRom + " found ("
				+ menuRomNotFound + " roms missing)");
		logger.info("\nTotal of " + (totalGames - menuRom)
				+ " rejected because of region/rev duplicate or clones.");

	}
}
