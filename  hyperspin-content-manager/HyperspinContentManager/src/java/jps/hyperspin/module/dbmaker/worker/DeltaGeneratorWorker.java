package jps.hyperspin.module.dbmaker.worker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jps.hyperspin.common.DatabaseUtilities;
import jps.hyperspin.common.RomUtilities;
import jps.hyperspin.common.file.FileUtilities;
import jps.hyperspin.common.worker.CommonWorker;
import jps.hyperspin.exception.HCMDatabaseException;
import jps.hyperspin.main.controller.CommonLogger;
import jps.hyperspin.main.controller.MainController;
import jps.hyperspin.module.dbdownloader.model.DatabaseDetail;
import jps.hyperspin.module.dbdownloader.model.generated.menu.MenuType;
import jps.hyperspin.module.dbmaker.model.DbMakerOption;
import jps.hyperspin.module.dbmaker.model.DbMakerRegionEnum;
import jps.hyperspin.module.dbmaker.worker.namingconventions.AbstractNamingConvention;

/**
 * 
 * @author jps
 * 
 */
public class DeltaGeneratorWorker extends CommonWorker {

	private String system;
	private DbMakerOption option;

	public class Delta {
		public String name;
		public String replacementName;
	}

	public class DeltaResult {
		public List<Delta> deltas;
		public List<String> unknowns;
	}

	public DeltaGeneratorWorker(String system, DbMakerOption option) {
		super();
		this.system = system;
		this.option = option;
	}

	@Override
	protected void executeInternal() throws Exception {
		// Delete existing region and country delta files
		FileUtilities.deleteAllFiles(DatabaseUtilities.getGeneratedDeltaDir(system), "delta");
		// Load main database
		MenuType database = DatabaseUtilities.loadDatabase(DatabaseUtilities.getDownloadedDatabasePath(system));

		if (option.useRegionPreference) {
			if (option.region != DbMakerRegionEnum.NONE) {
				// Find region matching roms
				DeltaResult regionResult = computeDelta(option.country, database);

				// Save delta region file
				writeDeltaFile(regionResult.deltas, option.region.toString());
				writeUnknwonRegionFile(regionResult.unknowns, option.region.toString());

			}
			if (option.country != DbMakerRegionEnum.NONE) {
				// Find country roms
				DeltaResult countryResult = computeDelta(option.region, database);

				// Save delta country file
				writeDeltaFile(countryResult.deltas, option.country.toString());
				writeUnknwonRegionFile(countryResult.unknowns, option.country.toString());
			}
		}
	}

	private DeltaResult computeDelta(DbMakerRegionEnum type, MenuType database) throws HCMDatabaseException,
			IOException {
		DatabaseDetail detail = MainController.instance.getDbDetail();
		// Result
		DeltaResult result = new DeltaResult();
		result.deltas = new ArrayList<Delta>();
		result.unknowns = new ArrayList<String>();
		// Stat
		int nbReplacement = 0;

		// The naming convention class
		AbstractNamingConvention convention = option.namingConventions.getNamingConvention();

		// Get the rom list
		Map<String, String> romMap = RomUtilities.listRoms(system, detail);
		// Candidate to replace
		Map<String, String> candidtateRomMap = new HashMap<String, String>(romMap);
		// Matching type roms
		Map<String, String> matchingRomMap = new HashMap<String, String>();

		// Browse all roms
		for (String rom : romMap.keySet()) {
			if (convention.isBelongingToType(rom, type)) {
				candidtateRomMap.remove(rom);
				matchingRomMap.put(rom, romMap.get(rom));
			}

		}

		// We browse all matching roms to find a good candidate for
		// replacement.
		for (String rom : matchingRomMap.keySet()) {
			for (String candidate : candidtateRomMap.keySet()) {
				// TODO is it already in db ?

				// Is there a correct candidate
				if (convention.isCandidate(rom, candidate, type)) {
					Delta delta = new Delta();
					delta.name = romMap.get(candidate);
					delta.replacementName = romMap.get(rom);
					result.deltas.add(delta);
					candidtateRomMap.remove(candidate);
					nbReplacement++;
					break;
				}
			}
		}
		CommonLogger.instance.info(matchingRomMap.size() + " rom found for type " + type.name());
		CommonLogger.instance.info(nbReplacement + " rom replaced for type " + type.name() + "\n");
		return result;

	}

	/**
	 * 
	 * @param datas
	 * @param type
	 */
	private void writeDeltaFile(Collection<Delta> deltas, String region) throws IOException {
		FileWriter writer = new FileWriter(DatabaseUtilities.getGeneratedDeltaPath(system, region));
		for (Delta delta : deltas) {
			writer.write(delta.name + "->" + delta.replacementName + "\n");
		}
		writer.close();
	}

	/**
	 * Write all non replaced region file.
	 * 
	 * @param datas
	 * @param type
	 */
	private void writeUnknwonRegionFile(Collection<String> unknowns, String region) throws IOException {
		String unknwonFilePath = DatabaseUtilities.getDeltaDir(system) + File.separator + region + ".unknown";
		FileWriter writer = new FileWriter(unknwonFilePath, true);
		for (String s : unknowns) {
			writer.write(s + "\n");
		}
		writer.close();
	}

}
