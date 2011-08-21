package jps.hyperspin.module.mediachecker.model;

import jps.hyperspin.common.model.CommonOption;

/**
 * 
 * @author jps
 * 
 */
public class MediaCheckerOption extends CommonOption {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MediaCheckerOption() {
		super();
	}

	public boolean manualResolving;

	public boolean moveNotUsed;

	public MediaCategoryEnum category;

	/**
	 * load a CommonOption instance from disk. Create a new one if no instance
	 * are found on disk.
	 * 
	 * @param system
	 */
	public static MediaCheckerOption load(String system) {
		try {
			return (MediaCheckerOption) CommonOption.load(MediaCheckerOption.class, system);
		} catch (Exception e2) {
			throw new IllegalArgumentException();
		}
	}
}
