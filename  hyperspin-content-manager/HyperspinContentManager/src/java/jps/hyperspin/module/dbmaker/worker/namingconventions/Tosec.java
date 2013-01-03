package jps.hyperspin.module.dbmaker.worker.namingconventions;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jps.hyperspin.module.dbmaker.model.DbMakerRegionEnum;

/**
 * 
 * @author jps
 * 
 */
public class Tosec extends AbstractNamingConvention {

	private final static String REGION_SECTION_PATTERN = "\\((PAL|NTSC)\\)";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isBelongingToType(String rom, DbMakerRegionEnum region) {
		Set<String> regions = getRegions(rom);

		if (regions.contains(getRegionAsString(region).toLowerCase())) {
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isCandidate(String rom, String canditate, DbMakerRegionEnum type) {
		String c = cleanSections(canditate);
		String r = cleanSections(rom);
		if (clean(c).equals(clean(r))) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param region
	 * @return
	 */
	private String getRegionAsString(DbMakerRegionEnum region) {
		switch (region) {
		case EUROPE:
			return "PAL";
		default:
			return "not implemented";
		}
	}

	/**
	 * Get the list of region of a rom name according to nointro convention.
	 * 
	 * @param rom
	 * @return
	 */
	private Set<String> getRegions(String rom) {
		Set<String> regions = new HashSet<String>();
		Matcher matcher = Pattern.compile(REGION_SECTION_PATTERN).matcher(rom);
		if (matcher.find()) {
			String regionSection = matcher.group();
			regionSection = regionSection.substring(1, regionSection.length() - 1);
			String[] regionsArray = regionSection.split(",");
			for (String string : regionsArray) {
				regions.add(string.trim().toLowerCase());
			}
		}
		return regions;
	}

	/**
	 * 
	 * @param rom
	 * @return
	 */
	private String cleanSections(String rom) {
		String result = rom;
		result = removeRegion(result);
		return result;
	}

	/**
	 * Extract the region section.
	 * 
	 * @param rom
	 * @return
	 */
	private String removeRegion(String rom) {
		String result = rom;
		Matcher matcher = Pattern.compile(REGION_SECTION_PATTERN).matcher(rom);
		if (matcher.find()) {
			String regionSection = matcher.group();
			result = rom.replace(regionSection, "");
		}
		return result;
	}

}
