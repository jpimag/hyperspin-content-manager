package jps.hyperspin.common.file;

import java.io.File;
import java.io.FileFilter;

/**
 * 
 * @author JPS
 * 
 */
public class FileFilterExtension implements FileFilter {

	/**
	 * 
	 */
	private String[] extensions;

	/**
	 * 
	 * @param extension
	 *            extension
	 */
	public FileFilterExtension(final String... extensions) {
		this.extensions = extensions;
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean accept(final File pathname) {
		for (String extension : extensions) {
			if (pathname.isFile() && pathname.getName().toLowerCase().endsWith(extension)) {
				return true;
			}
		}
		return false;
	}

}
