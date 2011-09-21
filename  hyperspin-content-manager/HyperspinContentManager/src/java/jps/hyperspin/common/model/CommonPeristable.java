package jps.hyperspin.common.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import jps.hyperspin.common.DatabaseUtilities;
import jps.hyperspin.main.controller.CommonLogger;

/**
 * 
 * @author jps
 * 
 */
public abstract class CommonPeristable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected CommonPeristable() {
	}

	/**
	 * Save the object on the disk.
	 */
	public void save(String category) {
		try {
			String sDir = getSaveFileDir(getClass(), category);
			File dir = new File(sDir);
			if (!dir.exists()) {
				dir.mkdir();
			}
			File file = new File(getSaveFilePath(getClass(), category));
			if (file.exists()) {
				file.delete();
			}
			FileOutputStream fichier = new FileOutputStream(getSaveFilePath(getClass(), category));
			ObjectOutputStream oos = new ObjectOutputStream(fichier);
			oos.writeObject(this);
			oos.flush();
			oos.close();
		} catch (java.io.IOException e) {
			CommonLogger.instance.error("Error serializing preferences : " + e.getMessage());
		}
	}

	/**
	 * load a CommonOption instance from disk. Create a new one if no instance
	 * are found on disk.
	 */
	protected static CommonPeristable load(Class<? extends CommonPeristable> classe, String category)
			throws IllegalAccessException, InstantiationException {
		try {
			FileInputStream fichier = new FileInputStream(getSaveFilePath(classe, category));
			ObjectInputStream ois = new ObjectInputStream(fichier);
			return (CommonPeristable) ois.readObject();
		} catch (Exception e) {
			if (!(e instanceof FileNotFoundException)) {
				CommonLogger.instance.error("Error deserializing preferences : " + e.getMessage());
			}

			return (CommonPeristable) classe.newInstance();

		}

	}

	private static String getSaveFileDir(Class<? extends CommonPeristable> classe, String category) {
		return DatabaseUtilities.getDownloadedDatabaseDir(category);

	}

	/**
	 * 
	 * @return
	 */
	private static String getSaveFilePath(Class<? extends CommonPeristable> classe, String category) {
		return getSaveFileDir(classe, category) + File.separator + classe.getSimpleName() + ".ser";

	}
}
