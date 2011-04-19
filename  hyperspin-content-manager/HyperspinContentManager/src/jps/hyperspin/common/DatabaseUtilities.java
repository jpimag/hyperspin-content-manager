package jps.hyperspin.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import jps.hyperspin.MainClass;
import jps.hyperspin.exception.HCMDatabaseException;
import jps.hyperspin.log.Logger;
import jps.hyperspin.module.database.model.MenuType;
import jps.hyperspin.process.xml.XmlBinding;

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
	public static String getReferenceDatabaseDir() {
		String path = System.getProperty("user.dir") + File.separator
				+ "database";
		File file = new File(path);
		if (!file.exists()) {
			file.mkdir();
		}
		path += File.separator + MainClass.mainFrame.getSystemSelected();
		file = new File(path);
		if (!file.exists()) {
			file.mkdir();
		}
		return path;
	}

	/**
	 * Get the main database full path of the reference directory of the
	 * selected system.
	 * 
	 * @return
	 */
	public static String getReferenceDatabasePath() {
		return getReferenceDatabaseDir() + File.separator
				+ MainClass.mainFrame.getSystemSelected() + ".xml";
	}

	public static String getGenreDatabasePath(String genre) {
		return getReferenceDatabaseDir() + File.separator + genre + ".xml";
	}

	/**
	 * Retourne la derniere version de la database principal du systeme
	 * selectionné dans le tableau HyperList sur le site officiel d'Hyperspin.
	 */
	public static void writeDatabase(MenuType db, String path) {
		try {

			FileWriter writer = new FileWriter(path);
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
