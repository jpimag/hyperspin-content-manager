package jps.hyperspin.module.dbmaker.model;

import jps.hyperspin.common.model.CommonPeristable;
import jps.hyperspin.module.dbmaker.worker.namingconventions.AbstractNamingConvention;
import jps.hyperspin.module.dbmaker.worker.namingconventions.NoIntro;
import jps.hyperspin.module.dbmaker.worker.namingconventions.RedumpOrg;
import jps.hyperspin.module.dbmaker.worker.namingconventions.Tosec;

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
		region = DbMakerRegionEnum.NONE;
		country = DbMakerRegionEnum.NONE;
		namingConventions = NamingConventions.OTHER;

	}

	public enum NamingConventions {
		NO_INTRO, REDUMP_ORG, TOSEC, OTHER;

		/**
		 * 
		 * @return the NamingConvention class.
		 */
		public AbstractNamingConvention getNamingConvention() {
			switch (this) {
			case NO_INTRO:
				return new NoIntro();
			case REDUMP_ORG:
				return new RedumpOrg();
			case TOSEC:
				return new Tosec();
			}
			return null;
		}
	}

	public boolean noClone;

	public boolean moveReplacedRoms;

	public boolean moveNotUsedRoms;

	public boolean useDeltaFiles;

	public NamingConventions namingConventions;

	public DbMakerRegionEnum region;

	public DbMakerRegionEnum country;

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
