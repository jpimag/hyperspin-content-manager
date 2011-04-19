package jps.hyperspin.process.file;

import java.io.File;
import java.io.FileFilter;

/**
 * 
 * @author JPS
 * 
 */
public class FileFilterDirectory implements FileFilter {

	/**
	 * {@inheritDoc}
	 */
	public final boolean accept(final File pathname) {
		return pathname.isDirectory();
	}

}
