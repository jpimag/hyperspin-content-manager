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
	private String extension;

	/**
	 * 
	 * @param extension
	 *            extension
	 */
	public FileFilterExtension(final String extension) {
		this.extension = extension;
	}

	/**
	 * {@inheritDoc}
	 */
	public final boolean accept(final File pathname) {
		return pathname.isFile() && pathname.getName().endsWith(extension);
	}

}
