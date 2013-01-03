package jps.hyperspin.module.dbmaker.worker.namingconventions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jps.hyperspin.module.dbmaker.model.DbMakerRegionEnum;

/**
 * 
 * @author jps
 * 
 */
public class RedumpOrg extends AbstractNamingConvention {

	/**
	 * Pattern : <name> (<region1>,...,<regionN>) (<lang1>, ..., <langN>)
	 * (<other1) .. (<otherN)
	 */
	private final static String SECTION_PATTERN = "\\((\\w|\\s|,)*\\)";

	/**
	 * 
	 */
	private final static String VERSION_SECTION_PATTERN = "\\(v\\d\\.\\d\\)";

	/**
	 * 
	 */
	private final static String DISC_SECTION_PATTERN = "\\(disc\\s\\d\\)";

	/**
	 * List off languages use in redumpOrg convention.
	 */
	private static List<String> languages = new ArrayList<String>();

	static {
		languages.add("En");
		languages.add("Fr");
		languages.add("De");
		languages.add("Es");
		languages.add("It");
		languages.add("Nl");
		languages.add("Da");

	}

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
		return region.name();
	}

	/**
	 * Get the list of region of a rom name according to nointro convention.
	 * 
	 * @param rom
	 * @return
	 */
	private Set<String> getRegions(String rom) {
		Set<String> regions = new HashSet<String>();
		// Certaines roms ont le disc avant la r�gion (non standard)
		rom = removeDisc(rom);
		Matcher matcher = Pattern.compile(SECTION_PATTERN).matcher(rom);
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
		result = removeDisc(result);
		result = removeRegion(result);
		result = removeLanguage(result);
		result = removeVersion(result);
		return result;
	}

	/**
	 * Extract the disc section because the word 'disc' is not always used
	 * accordig regions.
	 * 
	 * @param rom
	 * @return
	 */
	private String removeDisc(String rom) {
		String result = rom;
		Matcher matcher = Pattern.compile(DISC_SECTION_PATTERN, Pattern.CASE_INSENSITIVE).matcher(rom);
		while (matcher.find()) {
			String section = matcher.group();
			result = result.replace(section, "");
		}
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
		Matcher matcher = Pattern.compile(SECTION_PATTERN).matcher(rom);
		if (matcher.find()) {
			String regionSection = matcher.group();
			result = rom.replace(regionSection, "");
		}
		return result;
	}

	/**
	 * Extract the version section.
	 * 
	 * @param rom
	 * @return
	 */
	private String removeVersion(String rom) {
		String result = rom;
		Matcher matcher = Pattern.compile(VERSION_SECTION_PATTERN).matcher(rom);
		if (matcher.find()) {
			String section = matcher.group();
			result = rom.replace(section, "");
		}
		return result;
	}

	/**
	 * Remove the langage section
	 * 
	 * @param rom
	 * @return
	 */
	private String removeLanguage(String rom) {
		String result = rom;
		Matcher matcher = Pattern.compile(SECTION_PATTERN).matcher(rom);
		while (matcher.find()) {
			boolean found = false;
			String section = matcher.group();
			String sectionIn = section.substring(1, section.length() - 1);
			if (languages.contains(sectionIn)) {
				found = true;
			} else {
				String[] langs = sectionIn.split(",");
				for (String lang : langs) {
					if (languages.contains(lang)) {
						found = true;
					}
				}
				if (found) {
					result = rom.replace(section, "");
				}
			}
		}
		return result;
	}

	/**
	 * @param args
	 *            main args
	 */
	public static void main(final String[] args) {
		RedumpOrg n = new RedumpOrg();

		String rom = "Alone in the Dark - The New Nightmare (Disc 1) (France)";
		rom = n.cleanSections(rom);
		System.out.println(rom);
	}
}
