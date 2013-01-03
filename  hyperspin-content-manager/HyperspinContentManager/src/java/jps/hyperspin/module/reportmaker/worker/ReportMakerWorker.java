package jps.hyperspin.module.reportmaker.worker;

import java.io.File;
import java.io.FileReader;
import java.util.Date;

import jps.hyperspin.common.DatabaseUtilities;
import jps.hyperspin.common.log.LoggerLevel;
import jps.hyperspin.common.worker.CommonWorker;
import jps.hyperspin.common.xml.XmlBinding;
import jps.hyperspin.main.controller.CommonLogger;
import jps.hyperspin.main.controller.MainController;
import jps.hyperspin.main.model.Systems;
import jps.hyperspin.main.model.VersionStatut;
import jps.hyperspin.module.dbdownloader.model.DatabaseDetail;
import jps.hyperspin.module.dbdownloader.model.generated.menu.MenuType;
import jps.hyperspin.module.dbdownloader.processor.CheckDatabaseVersionProcessor;
import jps.hyperspin.module.dbmaker.model.DbMakerOption;
import jps.hyperspin.module.dbmaker.processor.DbMakerProcessor;
import jps.hyperspin.module.dbmaker.processor.DbMakerProcessor.DbMakerResult;
import jps.hyperspin.module.dbmaker.processor.DeltaGeneratorProcessor;
import jps.hyperspin.module.mediachecker.model.MediaCategoryEnum;
import jps.hyperspin.module.mediachecker.model.MediaCheckerOption;
import jps.hyperspin.module.mediachecker.processor.MediaCheckerProcessor;
import jps.hyperspin.module.reportmaker.model.Report;
import jps.hyperspin.module.reportmaker.model.ReportRow;

/**
 * 
 * @author jps
 * 
 */
public class ReportMakerWorker extends CommonWorker {

	public ReportMakerWorker() {
		super();

	}

	@Override
	protected void executeInternal() throws Exception {
		try {
			CommonLogger.instance.setForceLevel(LoggerLevel.NOTIF);
			Report report = new Report();
			report.date = new Date();

			// progess count
			double progressUnit = (double) MAX_PROGRESS_VALUE / (double) (Systems.instance.list().size() * 11);
			double progress = 0;

			// system loop
			int i = 0;
			for (String system : Systems.instance.list()) {
				try {
					// if (i == 2) {
					// break;
					// }
					i++;
					ReportRow row = new ReportRow();
					row.system = system;
					DatabaseDetail detail = MainController.instance.getDbDetail(system);

					// Step 0 - check version
					progress += progressUnit;
					CheckDatabaseVersionProcessor checkDbProcessor = new CheckDatabaseVersionProcessor(system, this,
							(int) progressUnit);
					checkDbProcessor.execute();
					VersionStatut status = checkDbProcessor.getVersionStatut();

					if (status == VersionStatut.UNOFFICIAL_DB || status == VersionStatut.SYSTEM_NOT_AVAILABLE) {
						// Step 1 - no db downloaded, we only read current db
						File file = new File(DatabaseUtilities.getUserDatabasePath(system));
						int size;
						try {
							FileReader reader = new FileReader(file);
							MenuType menu = (MenuType) XmlBinding.getInstance().xml2java(MenuType.class, reader);
							size = menu.getGame().size();
						} catch (Exception e) {
							size = 0;
						}
						row.dbMakerResult = new DbMakerResult();
						row.dbMakerResult.dbSize = size;
					} else {
						// Step 1 - We make the db
						// Load preference into DbMakerOption instance
						DbMakerOption dbMakerOption = DbMakerOption.load(system);
						DeltaGeneratorProcessor deltaProcessor = new DeltaGeneratorProcessor(system, dbMakerOption,
								detail, this, (int) progress);
						DbMakerProcessor dbMakerProcessor = new DbMakerProcessor(system, dbMakerOption, detail, this,
								(int) progress);
						progress += progressUnit;
						deltaProcessor.execute();
						progress += progressUnit;
						dbMakerProcessor.execute();
						row.dbMakerResult = dbMakerProcessor.getResult();
						row.dbMakerOption = dbMakerOption;
					}
					// Step 2 - Medias
					for (MediaCategoryEnum category : MediaCategoryEnum.values()) {
						progress += progressUnit;
						MediaCheckerOption mediaCheckerOption = new MediaCheckerOption();
						mediaCheckerOption.deleteNotUsed = true;
						mediaCheckerOption.category = category;
						MediaCheckerProcessor mediaCheckerProcessor = new MediaCheckerProcessor(system,
								mediaCheckerOption, detail, this, (int) progress);
						mediaCheckerProcessor.execute();
						row.mediaCheckerResults.put(category, mediaCheckerProcessor.getResult());
					}

					// Step 3 - check version
					progress += progressUnit;
					checkDbProcessor = new CheckDatabaseVersionProcessor(system, this, (int) progressUnit);
					checkDbProcessor.execute();
					row.versionStatut = checkDbProcessor.getVersionStatut();
					row.version = checkDbProcessor.getListversion();

					// Step 4 : Add row report
					CommonLogger.instance.notif("Report row finished for system : " + system);
					report.rows.add(row);
				} catch (Throwable t) {
					CommonLogger.instance.notif("Exception occured for system " + system + ": " + t);
				}
			}

			// Store report
			report.save();
		} finally {
			CommonLogger.instance.setForceLevel(LoggerLevel.TRACE);
		}

	}
}
