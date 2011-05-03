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
public class NoIntro extends AbstractNamingConvention {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isBelongingToType(String rom, DbMakerRegionEnum region) {
		Set<String> regions = getRegions(rom);
		if (regions.contains(getRegionAsString(region))) {
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isCandidate(String rom, String canditate, DbMakerRegionEnum type) {
		// TODO
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
		Matcher matcher = Pattern.compile("\\((\\w|\\s|,)*\\)").matcher(rom);
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
	 * @param args
	 *            main args
	 */
	public static void main(final String[] args) {
		NoIntro n = new NoIntro();
		Set<String> s = n.getRegions("Princess Tomato in Salad Kingdom (USA, World)(Unl)");
		for (String string : s) {
			System.out.println(string);
		}
	}
}
