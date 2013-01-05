package jps.hyperspin.common;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import jps.hyperspin.common.file.FileFilterDirectory;
import jps.hyperspin.common.file.FileFilterExtension;
import jps.hyperspin.exception.HCMDatabaseException;
import jps.hyperspin.module.dbdownloader.model.DatabaseDetail;

public class RomUtilities {
	/**
	 * 
	 * @param dir
	 *            the dir
	 * @param extension
	 *            the extension
	 * @return A map of rom names (the key is the normalized name).
	 * 
	 */
	public static Map<String, String> listRoms(String system, DatabaseDetail detail) throws HCMDatabaseException {
		File dir = new File(detail.systemIniProperties.getRomPath());
		return listRomsRec(system, detail, dir);

	}

	/**
	 * 
	 * @param romFileName
	 * @param system
	 * @param detail
	 * @throws HCMDatabaseException
	 */
	public static void moveRomToNotUsedFolder(String romFileName, String system, DatabaseDetail detail)
			throws HCMDatabaseException {
		moveRomToFolder(romFileName, system, detail, "unused");
	}

	/**
	 * 
	 * @param romFileName
	 * @param system
	 * @param detail
	 * @throws HCMDatabaseException
	 */
	private static void moveRomToFolder(String romFileName, String system, DatabaseDetail detail, String dirname)
			throws HCMDatabaseException {
		File destDir = new File(detail.systemIniProperties.getRomPath() + File.separator + dirname);
		destDir.mkdirs();

		// Is it a simple file rom ?
		File file = new File(detail.systemIniProperties.getRomPath(), romFileName);
		if (file.exists() && file.isFile()) {
			file.renameTo(new File(destDir, romFileName));
		} else {
			// Is the rom in a dedicated directory ?
			String romNameWithoutExtension = romFileName;
			// Is there a file extension
			int endIndex = romFileName.lastIndexOf(".");
			if (endIndex != -1) {
				romNameWithoutExtension = romFileName.substring(0, endIndex);
			}

			file = new File(detail.systemIniProperties.getRomPath() + File.separator + romNameWithoutExtension);
			if (file.exists() && file.isDirectory()) {
				file.renameTo(new File(destDir, romNameWithoutExtension));
			}
		}

	}

	/**
	 * Private recursif method for listRoms.
	 * 
	 * @param system
	 * @param detail
	 * @param dir
	 * @return
	 * @throws HCMDatabaseException
	 */
	private static Map<String, String> listRomsRec(String system, DatabaseDetail detail, File dir)
			throws HCMDatabaseException {
		Map<String, String> romList = new HashMap<String, String>();
		String[] extensions = detail.systemIniProperties.getRomExtensions();
		if (dir.isDirectory()) {
			File[] files;
			if (extensions != null && !(extensions.length == 0) && !extensions[0].equals("")) {
				files = dir.listFiles(new FileFilterExtension(extensions));
			} else {
				files = dir.listFiles();
			}
			for (File file : files) {
				String name = file.getName();
				int index = name.lastIndexOf('.');
				if (index != -1) {
					name = name.substring(0, index);
				}
				romList.put(name, file.getName());

			}
			for (File file : dir.listFiles(new FileFilterDirectory())) {
				romList.putAll(listRomsRec(system, detail, file));

			}
		}
		return romList;
	}

	/**
	 * Normalize rom name
	 * 
	 * @param s
	 * @return
	 */
	public static String normalize(String s) {
		return s.toLowerCase();
	}
}
