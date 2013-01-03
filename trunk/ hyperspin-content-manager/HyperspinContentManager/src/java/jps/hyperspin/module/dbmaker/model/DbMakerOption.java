package jps.hyperspin.module.dbmaker.model;

import jps.hyperspin.common.model.CommonPeristable;

/**
 * 
 * @author jps
 * 
 */
public class DbMakerOption extends CommonPeristable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DbMakerOption() {
		super();
	}

	public boolean noClone;

	public boolean moveNotUsedRoms;

	public boolean useDeltaFiles;

	/**
	 * load a CommonOption instance from disk. Create a new one if no instance
	 * are found on disk.
	 */
	public static DbMakerOption load(String system) {
		try {
			return (DbMakerOption) CommonPeristable.load(DbMakerOption.class, system);
		} catch (Exception e2) {
			throw new IllegalArgumentException();
		}
	}
}
