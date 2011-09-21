package jps.hyperspin.module.mediachecker.model;

import jps.hyperspin.common.model.CommonPeristable;

/**
 * 
 * @author jps
 * 
 */
public class MediaCheckerOption extends CommonPeristable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MediaCheckerOption() {
		super();
	}

	public boolean manualResolving = false;

	public boolean moveNotUsed = false;

	public MediaCategoryEnum category;

	/**
	 * load a CommonOption instance from disk. Create a new one if no instance
	 * are found on disk.
	 * 
	 * @param system
	 */
	public static MediaCheckerOption load(String system) {
		try {
			return (MediaCheckerOption) CommonPeristable.load(MediaCheckerOption.class, system);
		} catch (Exception e2) {
			throw new IllegalArgumentException();
		}
	}
}
