package jps.hyperspin.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import jps.hyperspin.MainClass;
import jps.hyperspin.common.log.Logger;
import jps.hyperspin.common.xml.XmlBinding;
import jps.hyperspin.exception.HCMDatabaseException;
import jps.hyperspin.module.dbdownloader.model.MenuType;

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
	public static MenuType loadDatabase(String databaseFullPath,
			final Logger logger) throws HCMDatabaseException,
			FileNotFoundException {
		File database = new File(databaseFullPath);
		logger.info("Database to be parsed : " + database.getName());
		FileReader reader = new FileReader(database);
		MenuType menu = (MenuType) XmlBinding.getInstance().xml2java(
				MenuType.class, reader);
		return menu;
	}

	/**
	 * Get the main directory path of the reference directory of the selected
	 * system.
	 * 
	 * @return
	 */
	public static String getDownloadedDatabaseDir() {
		String path = System.getProperty("user.dir") + File.separator
				+ "database";
		path += File.separator + MainClass.mainFrame.getSystemSelected();

		return path;
	}

	/**
	 * Get the main database full path of the reference directory of the
	 * selected system.
	 * 
	 * @return
	 */
	public static String getDownloadedDatabasePath() {
		return getDownloadedDatabaseDir() + File.separator
				+ MainClass.mainFrame.getSystemSelected() + ".xml";
	}

	public static String getDownloadedGenreDatabasePath(String genre) {
		return getDownloadedDatabaseDir() + File.separator + genre + ".xml";
	}

	/**
	 * Get the main directory path of the reference directory of the selected
	 * system.
	 * 
	 * @return
	 */
	public static String getUserDatabasePath() {
		String path = MainClass.mainFrame.getHyperSpinPath() + File.separator
				+ "Databases" + File.separator
				+ MainClass.mainFrame.getSystemSelected() + File.separator
				+ MainClass.mainFrame.getSystemSelected() + ".xml";
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
			MainClass.mainFrame.getLogger().info(
					"Database File writed : " + path);
		} catch (IOException e) {
			MainClass.mainFrame.getLogger().error(e.getMessage());
		} catch (HCMDatabaseException e) {
			MainClass.mainFrame.getLogger().error(e.getMessage());
		}
	}

}
