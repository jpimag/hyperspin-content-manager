package jps.hyperspin.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import jps.hyperspin.MainClass;
import jps.hyperspin.common.xml.XmlBinding;
import jps.hyperspin.exception.HCMDatabaseException;
import jps.hyperspin.main.controller.CommonLogger;
import jps.hyperspin.module.dbdownloader.model.generated.menu.MenuType;

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
		CommonLogger.instance.info("Database to be parsed : " + database.getName());
		FileReader reader = new FileReader(database);
		MenuType menu = (MenuType) XmlBinding.getInstance().xml2java(MenuType.class, reader);
		return menu;
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
		String path = MainClass.HYPERSPIN_PATH + File.separator + "Databases" + File.separator + system
				+ File.separator + system + ".xml";
		return path;
	}

	/**
	 * Get the directory of generated delta files.
	 * 
	 * @param system
	 * 
	 * @return
	 */
	public static String getGeneratedDeltaDir(String system) {
		String path = getDeltaDir(system) + File.separator + "generated";
		return path;
	}

	/**
	 * Get the main directory path of the reference directory of a system.
	 * 
	 * @param system
	 * 
	 * @return
	 */
	public static String getGeneratedDeltaPath(String system, String type) {
		String path = getGeneratedDeltaDir(system) + File.separator + "generated_" + type + ".delta";
		return path;
	}

	/**
	 * Get the directory of delta files.
	 * 
	 * @param system
	 * 
	 * @return
	 */
	public static String getDeltaDir(String system) {
		String path = getDownloadedDatabaseDir(system) + File.separator + "delta";
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
			CommonLogger.instance.info("Database File writed : " + path);
		} catch (IOException e) {
			CommonLogger.instance.error(e.getMessage());
		} catch (HCMDatabaseException e) {
			CommonLogger.instance.error(e.getMessage());
		}
	}

}
