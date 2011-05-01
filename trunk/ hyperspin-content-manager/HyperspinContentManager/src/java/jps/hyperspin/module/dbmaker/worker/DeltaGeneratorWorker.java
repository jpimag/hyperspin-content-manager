package jps.hyperspin.module.dbmaker.worker;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import jps.hyperspin.common.DatabaseUtilities;
import jps.hyperspin.common.file.FileUtilities;
import jps.hyperspin.common.worker.CommonWorker;
import jps.hyperspin.module.dbdownloader.model.generated.menu.MenuType;
import jps.hyperspin.module.dbmaker.model.DbMakerOption;
import jps.hyperspin.module.dbmaker.model.DbMakerRegionEnum;

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
				Map<String, Delta> regionDatas = computeDelta(option.country, database);

				// Save delta region file
				writeDeltaFile(regionDatas.values(), option.region.toString());
			}
			if (option.country != DbMakerRegionEnum.NONE) {
				// Find country roms
				Map<String, Delta> countryDatas = computeDelta(option.region, database);

				// Save delta country file
				writeDeltaFile(countryDatas.values(), option.country.toString());
			}
		}
	}

	private Map<String, Delta> computeDelta(DbMakerRegionEnum type, MenuType database) {
		Map<String, Delta> deltas = new HashMap<String, DeltaGeneratorWorker.Delta>();
		switch (option.namingConventions) {
		case NO_INTRO:
			// TODO
			break;

		case REDUMP_ORG:
			// TODO
			break;
		}

		return deltas;
	}

	/**
	 * 
	 * @param datas
	 * @param type
	 */
	private void writeDeltaFile(Collection<Delta> deltas, String type) throws IOException {
		FileWriter writer = new FileWriter(DatabaseUtilities.getGeneratedDeltaPath(system, type));
		for (Delta delta : deltas) {
			writer.write(delta.name + "->" + delta.replacementName + "\n");
		}
		writer.close();
	}
}
