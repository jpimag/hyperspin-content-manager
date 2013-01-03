package jps.hyperspin.module.dbmaker.worker.namingconventions;

import jps.hyperspin.module.dbmaker.model.DbMakerRegionEnum;

/**
 * 
 * @author jps
 * 
 */
public abstract class AbstractNamingConvention {

	/**
	 * 
	 * @param type
	 * @param rom
	 * @return true if the rom name belong to the selected type
	 */
	public abstract boolean isBelongingToType(String rom, DbMakerRegionEnum type);

	/**
	 * 
	 * @param rom
	 * @param canditate
	 * @param type
	 * @return true if the rom replace the candidate according to type.
	 */
	public abstract boolean isCandidate(String rom, String canditate, DbMakerRegionEnum type);

	protected String clean(String s) {
		String[] removeCharsTab = new String[] { " ", "'", "-", "_", "\\." };
		String result = s;
		for (String string : removeCharsTab) {
			result = result.replaceAll(string, "");
		}
		return result.trim().toLowerCase();
	}

}
