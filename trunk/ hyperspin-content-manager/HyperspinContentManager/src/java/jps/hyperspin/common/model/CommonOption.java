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
public abstract class CommonOption implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected CommonOption() {
	}

	/**
	 * Save the object on the disk.
	 */
	public void save(String system) {
		try {
			FileOutputStream fichier = new FileOutputStream(getSaveFilePath(getClass(), system));
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
	protected static CommonOption load(Class<? extends CommonOption> classe, String system)
			throws IllegalAccessException, InstantiationException {
		try {
			FileInputStream fichier = new FileInputStream(getSaveFilePath(classe, system));
			ObjectInputStream ois = new ObjectInputStream(fichier);
			return (CommonOption) ois.readObject();
		} catch (Exception e) {
			if (!(e instanceof FileNotFoundException)) {
				CommonLogger.instance.error("Error deserializing preferences : " + e.getMessage());
			}

			return (CommonOption) classe.newInstance();

		}

	}

	/**
	 * 
	 * @return
	 */
	private static String getSaveFilePath(Class<? extends CommonOption> classe, String system) {
		return DatabaseUtilities.getDownloadedDatabaseDir(system) + File.separator + classe.getSimpleName() + ".ser";

	}
}
