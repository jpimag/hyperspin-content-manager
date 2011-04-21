package jps.hyperspin.module;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.jps.hyperspin.common.file.FileFilterDirectory;
import java.jps.hyperspin.common.file.FileFilterExtension;
import java.jps.hyperspin.common.log.Logger;
import java.jps.hyperspin.common.xml.XmlBinding;
import java.jps.hyperspin.exception.HCMDatabaseException;
import java.jps.hyperspin.module.dbdownloader.model.GameType;
import java.jps.hyperspin.module.dbdownloader.model.MenuType;
import java.jps.hyperspin.module.dbdownloader.presentation.IDatabaseDetail;
import java.jps.hyperspin.module.dbdownloader.presentation.SystemIniProperties;
import java.jps.hyperspin.module.dbmaker.presentation.IDatabaseOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author JPS
 * 
 */
public abstract class AbstractProcessor {

	private static final int NB_BEST = 3000;

	protected SystemIniProperties ini;
	protected IDatabaseDetail databaseDetail;
	protected IDatabaseOption databaseOption;

	protected class DistanceString {
		public int distance;
		public String string;
		public Object info;

		public DistanceString(String s, int d) {
			distance = d;
			string = s;
			info = null;
		}

		public String toString() {

			String s = string + " - " + distance;
			if (info != null) {
				s += " - " + info;
			}
			return s;
		}
	}

	/**
	 * @return the ini
	 */
	public SystemIniProperties getIni() {
		return ini;
	}

	public AbstractProcessor(final IDatabaseDetail databaseDetail,
			final IDatabaseOption databaseOption) {
		super();
		this.databaseDetail = databaseDetail;
		this.databaseOption = databaseOption;
		this.ini = databaseDetail.getIniProp();
	}

	public AbstractProcessor(SystemIniProperties ini) {
		super();
		this.ini = ini;
	}

	protected Map<String, String> loadDatabase(String databaseFullPath,
			final Logger logger) throws HCMDatabaseException,
			FileNotFoundException {
		// Step 5 : Load main database
		File database = new File(databaseFullPath);

		logger.info("Database to be parsed : " + database.getName());
		FileReader reader = new FileReader(database);
		MenuType menu = (MenuType) XmlBinding.getInstance().xml2java(
				MenuType.class, reader);
		normalizeMenu(menu);
		logger.info(menu.getGame().size() + " games");
		Map<String, String> games = new HashMap<String, String>();
		for (GameType game : menu.getGame()) {
			games.put(game.getName(), game.getName());
		}
		return games;
	}

	protected boolean rejectGame(GameType game, MenuType menu, Logger logger) {
		String normalized = normalize(game.getName());
		if (normalized.contains(" (beta)")) {

			// Reject Beta games
			String toSearch = normalized.replace(" (beta)", "");
			for (GameType current : menu.getGame()) {
				if (!current.getName().equals(game.getName())) {
					if (normalize(current.getName()).startsWith(toSearch)) {
						logger.info("Game " + normalized
								+ " rejected because a rom with name "
								+ normalize(current.getName())
								+ " already exist.");
						return true;
					}
				}
			}
		}
		// Reject multi region
		String toSearch = normalized.split("\\(")[0];
		String revision = normalized.substring(toSearch.length());
		for (GameType current : menu.getGame()) {
			if (!current.getName().equals(game.getName())) {
				String currentNormalized = normalize(current.getName());
				String currentToSearch = currentNormalized.split("\\(")[0];
				String currentRevision = currentNormalized
						.substring(currentToSearch.length());
				if (toSearch.equals(currentToSearch)) {
					// Keep only best revision
					boolean rejected = false;

					// only one by region
					if (!revision.contains("europe")) {
						if (currentRevision.contains("europe")) {
							rejected = true;
						} else if (!revision.contains("usa")) {
							if (currentRevision.contains("usa")) {
								rejected = true;
							}
						}
					}

					// only first alphabetical ref
					if (revision.contains("europe")) {
						if (currentRevision.contains("europe")) {
							if (revision.compareTo(currentRevision) > 0) {
								rejected = true;
							}
						}
					} else if (revision.contains("usa")) {
						if (currentRevision.contains("usa")) {
							if (revision.compareTo(currentRevision) > 0) {
								rejected = true;
							}
						}
					}
					// Only one by revision
					if (!rejected
							&& (revision.contains("rev a")
									|| revision.contains("rev b")
									|| revision.contains("rev c")
									|| revision.contains("rev d")
									|| revision.contains("rev e") || revision
									.contains("rev f"))) {
						if (revision.replaceFirst("\\(rev .\\)", "").trim()
								.equals(currentRevision)) {
							return true;
						}

					}
					if (rejected) {
						logger.info("Game " + normalized
								+ " rejected because a rom with name "
								+ normalize(current.getName()) + " was found.");
						return true;
					}

				}
			}
		}

		return false;
	}

	protected final List<DistanceString> bestMatch(String game,
			Map<String, String> romsMap) {
		List<DistanceString> bests = new ArrayList<DistanceString>();
		Set<String> roms = romsMap.keySet();
		String cutgame = game.split("\\(")[0];
		cutgame = game.split("_")[0];
		for (String rom : roms) {
			String cutrom = rom.split("\\(")[0];
			int distance;
			if (cutgame.startsWith(cutrom) || cutrom.startsWith(cutgame)) {
				distance = 1;
			} else {
				distance = StringUtils.getLevenshteinDistance(cutgame, cutrom);
			}

			boolean added = false;
			for (int i = 0; i < bests.size(); i++) {
				if (distance < bests.get(i).distance) {
					added = true;
					DistanceString ds = new DistanceString(rom, distance);
					bests.add(i, ds);
					break;
				}
			}
			if (bests.size() < NB_BEST && !added) {
				added = true;
				DistanceString ds = new DistanceString(rom, distance);
				bests.add(bests.size(), ds);
			}
			if (bests.size() > NB_BEST) {
				bests.remove(NB_BEST);
			}
		}

		return bests;
	}

	/**
	 * 
	 * @param dir
	 *            he dir
	 * @param extension
	 *            the extension
	 * @return the list of rom names
	 */
	protected Map<String, String> listRecursiveFilesWithFilter(final File dir,
			boolean normalized, final String extension) {
		Map<String, String> romList = new HashMap<String, String>();
		if (dir.isDirectory()) {
			File[] files;
			if (extension != null && !extension.equals("")) {
				files = dir.listFiles(new FileFilterExtension(extension));
			} else {
				files = dir.listFiles();
			}
			for (File file : files) {
				String name = file.getName();
				if (!name.equals("RenameLogs.txt")) {
					int index = name.lastIndexOf('.');
					String nameNormalized = name;
					if (index != -1) {
						nameNormalized = nameNormalized.substring(0, index);
					}

					if (normalized) {
						nameNormalized = normalize(nameNormalized);
					}
					romList.put(nameNormalized, name);
				}
			}
			for (File file : dir.listFiles(new FileFilterDirectory())) {
				romList.putAll(listRecursiveFilesWithFilter(file, normalized,
						extension));
			}
		}
		return romList;

	}

	protected void normalizeMenu(MenuType menu) {
		for (GameType game : menu.getGame()) {
			game.setName(game.getName().replaceAll("&apos;", "'"));
			game.setDescription(game.getDescription().replaceAll("&apos;", "'"));

		}
	}

	protected String normalize(String s) {
		return s.toLowerCase();
	}

}
