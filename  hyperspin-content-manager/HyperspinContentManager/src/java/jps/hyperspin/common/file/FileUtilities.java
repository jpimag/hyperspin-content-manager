package jps.hyperspin.common.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileUtilities {

	public static String getExtension(File f) {
		return getExtension(f.getName());
	}

	public static String getExtension(String s) {
		int index = s.lastIndexOf(".");
		if (index == -1) {
			return "";
		} else {
			return s.substring(index).toLowerCase();
		}
	}

	public static String getNameWithoutExtension(File f) {
		int index = f.getName().lastIndexOf(".");
		if (index == -1) {
			return f.getName();
		} else {
			return f.getName().substring(0, index);
		}
	}

	public static String getNameWithoutExtension(String f) {
		int index = f.lastIndexOf(".");
		if (index == -1) {
			return f;
		} else {
			return f.substring(0, index);
		}
	}

	public static void moveFile(String directorySource, String directoryDest, String media, String game) {
		File d = new File(directoryDest);
		if (!d.exists()) {
			d.mkdirs();
		}
		File dest = new File(directoryDest + File.separator + game);
		if (dest.exists()) {
			dest.delete();
		}
		File file = new File(directorySource + File.separator + media);
		file.renameTo(dest);
	}

	public static void deleteFile(String directorySource, String media) {
		File file = new File(directorySource + File.separator + media);
		file.delete();
	}

	public static void deleteAllFiles(String directorySource, String extension) {
		File file = new File(directorySource);
		if (file.exists() && file.isDirectory()) {
			for (File child : file.listFiles(new FileFilterExtension(extension))) {
				child.delete();
			}
		}
	}

	public static void deleteAllFiles(String directorySource) {
		File file = new File(directorySource);
		if (file.exists() && file.isDirectory()) {
			for (File child : file.listFiles()) {
				child.delete();
			}
		}
	}

	public static void copyFile(String directory, String media, String game) throws FileNotFoundException, IOException {
		// Delete destination if needed
		deleteFile(directory, game);

		// Copy
		FileInputStream fis = new FileInputStream(directory + "/" + media);
		FileOutputStream fos = new FileOutputStream(directory + "/" + game);
		FileChannel channelSrc = fis.getChannel();
		FileChannel channelDest = fos.getChannel();
		channelSrc.transferTo(0, channelSrc.size(), channelDest);
		fis.close();
		fos.close();

	}

}
