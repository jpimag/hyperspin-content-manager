package jps.hyperspin.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jps.hyperspin.MainClass;
import jps.hyperspin.common.xml.XmlBinding;
import jps.hyperspin.exception.HCMDatabaseException;
import jps.hyperspin.main.controller.CommonLogger;
import jps.hyperspin.module.dbdownloader.model.generated.menu.GameType;
import jps.hyperspin.module.dbdownloader.model.generated.menu.MenuType;
import jps.hyperspin.module.dbmaker.model.DbMakerRegionEnum;

public class DatabaseUtilities {

	/**
	 * Load a database file
	 * 
	 * @param databaseFullPath
	 * @param logger
	 * @return
	 * @throws HCMDatabaseException
	 * @throws FileNotFoundException
	 */
	public static MenuType loadDatabase(String databaseFullPath) throws HCMDatabaseException, FileNotFoundException {
		File database = new File(databaseFullPath);
		// CommonLogger.instance.info("Database parsed : " + databaseFullPath);
		FileReader reader = new FileReader(database);
		MenuType menu = (MenuType) XmlBinding.getInstance().xml2java(MenuType.class, reader);
		// CommonLogger.instance.info("Total number of roms : " +
		// menu.getGame().size());
		return menu;
	}

	/**
	 * 
	 * @param database
	 * @return
	 */
	public static Map<String, GameType> getAsMap(MenuType database) {
		Map<String, GameType> result = new HashMap<String, GameType>();
		for (GameType game : database.getGame()) {
			result.put(game.getName(), game);
		}
		return result;
	}

	/**
	 * Get the main directory path of the reference directory of a system.
	 * 
	 * @param system
	 * 
	 * @return
	 */
	public static String getDownloadedDatabaseDir(String system) {
		String path = System.getProperty("user.dir") + File.separator + "database";
		path += File.separator + system;

		return path;
	}

	/**
	 * Get the main database full path of the reference directory a system.
	 * 
	 * @param system
	 * @return
	 */
	public static String getDownloadedDatabasePath(String system) {
		return getDownloadedDatabaseDir(system) + File.separator + system + ".xml";
	}

	/**
	 * 
	 * @param system
	 * @param genre
	 * @return
	 */
	public static String getDownloadedGenreDatabasePath(String system, String genre) {
		return getDownloadedDatabaseDir(system) + File.separator + genre + ".xml";
	}

	/**
	 * 
	 * @param genre
	 * @return
	 */
	public static String getDownloadedGenreDatabasePath(String genre) {
		return getDownloadedGenreDatabasePath(MainClass.mainFrame.getSystemSelected());
	}

	/**
	 * Get the main directory path of the reference directory of the selected
	 * system.
	 * 
	 * @return
	 */
	public static String getUserDatabasePath(String system) {
		String path = getUserDatabaseDir(system) + File.separator + system + ".xml";
		return path;
	}

	/**
	 * Get the main directory path of the reference directory of the selected
	 * system.
	 * 
	 * @return
	 */
	public static String getUserDatabaseDir(String system) {
		String path = MainClass.HYPERSPIN_PATH + File.separator + "Databases" + File.separator + system;
		return path;
	}

	/**
	 * 
	 * @param system
	 * 
	 * @return
	 */
	public static String getDeltaPath(String system, DbMakerRegionEnum region) {
		if (region == DbMakerRegionEnum.NONE || region == null) {
			return null;
		}
		String path = getLogsDir(system) + File.separator + region.name() + ".delta";
		return path;
	}

	/**
	 * 
	 * @param system
	 * 
	 * @return
	 */
	public static String getTraductionPath(String system, String type) {
		String path = getDownloadedDatabaseDir(system) + File.separator + "traduction" + File.separator + type
				+ ".traduction";
		return path;
	}

	/**
	 * Get the directory of delta files.
	 * 
	 * @param system
	 * 
	 * @return
	 */
	public static String getLogsDir(String system) {
		String path = getDownloadedDatabaseDir(system) + File.separator + "logs";
		return path;
	}

	/**
	 * Retourne la derniere version de la database principal du systeme
	 * selectionné dans le tableau HyperList sur le site officiel d'Hyperspin.
	 */
	public static void writeDatabase(MenuType db, String path, String fileName) {
		try {
			File file = new File(path);
			file.mkdirs();
			FileWriter writer = new FileWriter(path + File.separator + fileName);
			XmlBinding.getInstance().java2xml(db, writer);
			writer.close();
			CommonLogger.instance.info("Database File writed : " + path + File.separator + fileName);
		} catch (IOException e) {
			CommonLogger.instance.error(e.getMessage());
		} catch (HCMDatabaseException e) {
			CommonLogger.instance.error(e.getMessage());
		}
	}

}
