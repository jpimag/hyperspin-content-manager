package jps.hyperspin.module.dbmaker.model;

import jps.hyperspin.common.model.CommonOption;

/**
 * 
 * @author jps
 * 
 */
public class DbMakerOption extends CommonOption {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DbMakerOption() {
		super();
	}

	public enum NamingConventions {
		NO_INTRO, REDUMP_ORG
	}

	public boolean noClone;

	public boolean removeReplacedRoms;

	public boolean useRegionPreference;

	public boolean useDeltaFiles;

	public NamingConventions namingConventions;

	public DbMakerRegionEnum region;

	public DbMakerRegionEnum country;

	/**
	 * load a CommonOption instance from disk. Create a new one if no instance
	 * are found on disk.
	 */
	public static DbMakerOption load() {
		try {
			return (DbMakerOption) CommonOption.load(DbMakerOption.class);
		} catch (Exception e2) {
			throw new IllegalArgumentException();
		}
	}
}
