package jps.hyperspin.module.dbmaker.worker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jps.hyperspin.common.DatabaseUtilities;
import jps.hyperspin.common.DeltaFileUtilities;
import jps.hyperspin.common.RomUtilities;
import jps.hyperspin.common.file.FileUtilities;
import jps.hyperspin.common.worker.CommonWorker;
import jps.hyperspin.exception.HCMDatabaseException;
import jps.hyperspin.main.controller.CommonLogger;
import jps.hyperspin.main.controller.MainController;
import jps.hyperspin.module.dbdownloader.model.DatabaseDetail;
import jps.hyperspin.module.dbdownloader.model.generated.menu.GameType;
import jps.hyperspin.module.dbdownloader.model.generated.menu.MenuType;
import jps.hyperspin.module.dbmaker.model.DbMakerOption;
import jps.hyperspin.module.dbmaker.model.DbMakerRegionEnum;
import jps.hyperspin.module.dbmaker.model.Delta;
import jps.hyperspin.module.dbmaker.worker.namingconventions.AbstractNamingConvention;

/**
 * 
 * @author jps
 * 
 */
public class DeltaGeneratorWorker extends CommonWorker {

	private String system;
	private DbMakerOption option;
	private MenuType database;

	public class DeltaResult {
		// Rom replaced
		public List<Delta> deltas = new ArrayList<Delta>();
		// Matching region roms not use
		public List<String> unknowns = new ArrayList<String>();
		// No matching region keep in database
		public List<String> originalKept = new ArrayList<String>();
		// Rom fromcustom traduction file not found
		public List<String> traductionNotFound = new ArrayList<String>();

	}

	public DeltaGeneratorWorker(String system, DbMakerOption option, MenuType mainDownloadedDatabase) {
		super();
		this.system = system;
		this.option = option;
		this.database = mainDownloadedDatabase;
	}

	@Override
	protected void executeInternal() throws Exception {
		// Original rom kept which not belongs to preferred region
		List<String> originalKept = null;

		// Delete existing region and country delta files
		FileUtilities.deleteAllFiles(DatabaseUtilities.getLogsDir(system));
		// Load main database
		Map<String, GameType> games = DatabaseUtilities.getAsMap(database);
		if (option.useRegionPreference) {
			if (option.region != DbMakerRegionEnum.NONE) {
				// Find region matching roms
				DeltaResult regionResult = computeDelta(option.region, games);

				// Save delta region file
				writeDeltaFile(regionResult.deltas, option.region);
				writeUnknwonRegionFile(regionResult.unknowns, option.region);
				writeTraductionUnknwonRegionFile(new ArrayList<String>(regionResult.traductionNotFound), option.region);

				// OriginalKept
				originalKept = regionResult.originalKept;

			}
			setProgress(50);
			if (option.country != DbMakerRegionEnum.NONE) {
				// Find country roms
				DeltaResult countryResult = computeDelta(option.country, games);

				// Save delta country file
				writeDeltaFile(countryResult.deltas, option.country);
				writeUnknwonRegionFile(countryResult.unknowns, option.country);
				writeTraductionUnknwonRegionFile(new ArrayList<String>(countryResult.traductionNotFound),
						option.country);

				// Original kept
				if (originalKept == null) {
					originalKept = countryResult.originalKept;
				} else {
					// We kept only those not file neither in region nor country
					Iterator<String> it = originalKept.iterator();
					while (it.hasNext()) {
						if (!countryResult.originalKept.contains(it.next())) {
							it.remove();
						}
					}
				}
			}
			if (originalKept != null) {
				CommonLogger.instance.info(originalKept.size()
						+ " rom remaning in the database wich are not from preferred region");
				// Write original kept file
				writeOriginalKeptRegionFile(originalKept);

			}
		}
	}

	private DeltaResult computeDelta(DbMakerRegionEnum type, Map<String, GameType> games) throws HCMDatabaseException,
			IOException {
		DatabaseDetail detail = MainController.instance.getDbDetail();
		// Result
		DeltaResult result = new DeltaResult();
		result.originalKept.addAll(games.keySet());
		// Stat
		int nbReplacement = 0;

		// The naming convention class
		AbstractNamingConvention convention = option.namingConventions.getNamingConvention();

		// Get the rom list
		Map<String, String> romMap = RomUtilities.listRoms(system, detail);
		// Candidate to replace
		// ---------------------
		// Candidate are all roms not belonging to region in database.
		Map<String, String> candidtateRomMap = new HashMap<String, String>();
		for (String name : games.keySet()) {
			candidtateRomMap.put(name, name);
		}
		// Matching type roms
		// -------------------
		// Matching roms are all roms belonging region not in database
		Map<String, String> matchingRomMap = new HashMap<String, String>();

		// Browse all roms to determinate "candidate" and "matching"
		for (String rom : romMap.keySet()) {
			if (convention.isBelongingToType(rom, type)) {
				candidtateRomMap.remove(rom);
				if (!games.containsKey(rom)) {
					// The game is not in db
					matchingRomMap.put(rom, romMap.get(rom));
				} else {
					// this original rom belongs to preferred region
					result.originalKept.remove(rom);
				}
			}
		}

		// Custom traductions file
		Map<String, Delta> traductionsMap = null;
		try {
			traductionsMap = DeltaFileUtilities.loadDeltaFileIndexedByReplacementName(DatabaseUtilities
					.getTraductionPath(system, type.name()));
		} catch (IOException e) {
			CommonLogger.instance.info("No traduction file for the region : " + type.name());
		}

		// We browse all matching roms to find a good candidate for
		// replacement.
		for (String rom : matchingRomMap.keySet()) {
			// 1 - Search in traduction map
			if (traductionsMap != null && traductionsMap.containsKey(rom)
					&& candidtateRomMap.containsKey(traductionsMap.get(rom).name)) {
				String candidate = traductionsMap.get(rom).name;
				Delta delta = new Delta(candidate, romMap.get(rom));
				result.deltas.add(delta);
				candidtateRomMap.remove(candidate);
				nbReplacement++;
				result.originalKept.remove(candidate);
				traductionsMap.remove(rom);
			} else {
				// 2 - Search in candidate
				boolean found = false;
				for (String candidate : candidtateRomMap.keySet()) {

					// Is it a correct candidate
					if (convention.isCandidate(rom, candidate, type)) {
						Delta delta = new Delta(candidate, romMap.get(rom));
						result.deltas.add(delta);
						candidtateRomMap.remove(candidate);
						nbReplacement++;
						found = true;
						result.originalKept.remove(candidate);
						break;
					}
				}
				// No candidate found
				if (!found) {
					result.unknowns.add(rom);
				}
			}
		}

		// traductions not found
		if (traductionsMap != null && traductionsMap.size() > 0) {
			result.traductionNotFound.addAll(traductionsMap.keySet());
			CommonLogger.instance.info(traductionsMap.size() + " traduction unknowns for type " + type.name() + "\n");
		}

		// Logs
		CommonLogger.instance.info(matchingRomMap.size() + " rom found for type " + type.name());
		CommonLogger.instance.info(nbReplacement + " rom replaced for type " + type.name() + "\n");
		return result;

	}

	/**
	 * 
	 * @param datas
	 * @param type
	 */
	private void writeDeltaFile(List<Delta> deltas, DbMakerRegionEnum region) throws IOException {
		Collections.sort(deltas);
		File file = new File(DatabaseUtilities.getLogsDir(system));
		file.mkdirs();
		FileWriter writer = new FileWriter(DatabaseUtilities.getDeltaPath(system, region));
		DeltaFileUtilities.writeDeltaFile(deltas, writer);
	}

	/**
	 * Write all non replaced region file.
	 * 
	 * @param datas
	 * @param type
	 */
	private void writeUnknwonRegionFile(List<String> unknowns, DbMakerRegionEnum region) throws IOException {
		writeRegionFile(unknowns, region.name() + ".unknwon");
	}

	/**
	 * Write all rom kept from the original database which are not in region.
	 * 
	 * @param datas
	 * @param type
	 */
	private void writeOriginalKeptRegionFile(List<String> originals) throws IOException {
		writeRegionFile(originals, "ORIGINAL.kept");
	}

	/**
	 * Write all rom kept from the custom translation file which are not found.
	 * 
	 * @param datas
	 * @param type
	 */
	private void writeTraductionUnknwonRegionFile(List<String> unknowns, DbMakerRegionEnum region) throws IOException {
		writeRegionFile(unknowns, region.name() + ".traduction.notfound");
	}

	/**
	 * Write all non replaced region file.
	 * 
	 * @param datas
	 * @param type
	 */
	private void writeRegionFile(List<String> datas, String fileName) throws IOException {
		Collections.sort(datas);
		String unknwonFilePath = DatabaseUtilities.getLogsDir(system);
		File file = new File(unknwonFilePath);
		file.mkdirs();
		FileWriter writer = new FileWriter(unknwonFilePath + File.separator + fileName);
		for (String s : datas) {
			writer.write(s + "\n");
		}
		writer.close();
	}

}
