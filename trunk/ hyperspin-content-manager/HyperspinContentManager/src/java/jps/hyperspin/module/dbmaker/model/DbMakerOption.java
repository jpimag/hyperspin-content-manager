package jps.hyperspin.module.dbmaker.model;

/**
 * 
 * @author jps
 * 
 */
public class DbMakerOption {
	public enum NamingConventions {
		NO_INTRO, REDUMP_ORG
	}

	public enum Region {
		NONE, EUROPE
	}

	public enum Country {
		NONE, FRANCE,
	}

	public boolean noClone;

	public boolean removeReplacedRoms;

	public boolean useRegionPreference;

	public boolean useDeltaFiles;

	public NamingConventions namingConventions;

	public Region region;

	public Country country;

	public void save() {
		// TODO
	}

	public void load() {
		// TODO
	}

}
