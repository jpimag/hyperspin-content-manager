package jps.hyperspin.module.mediachecker.processor;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import jps.hyperspin.common.DatabaseUtilities;
import jps.hyperspin.common.file.FileFilterExtension;
import jps.hyperspin.common.file.FileUtilities;
import jps.hyperspin.common.processor.CommonProcessor;
import jps.hyperspin.common.worker.CommonWorker;
import jps.hyperspin.main.controller.CommonLogger;
import jps.hyperspin.module.dbdownloader.model.DatabaseDetail;
import jps.hyperspin.module.dbdownloader.model.generated.menu.GameType;
import jps.hyperspin.module.dbdownloader.model.generated.menu.MenuType;
import jps.hyperspin.module.mediachecker.model.MediaCheckerOption;

public class MediaCheckerProcessor extends CommonProcessor {
	private String system;
	private MediaCheckerOption option;
	private DatabaseDetail detail;

	private DbCheckerResult result = new DbCheckerResult();

	public class DbCheckerResult {
		int nbMediaFound = 0;
		int nbMediaMissing = 0;

	}

	public MediaCheckerProcessor(String system, MediaCheckerOption option, DatabaseDetail detail, CommonWorker worker,
			int untilProgress) {
		super(worker, untilProgress);
		this.system = system;
		this.option = option;
		this.detail = detail;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute() throws Exception {
		// Load main database
		// -------------------
		MenuType menu = DatabaseUtilities.loadDatabase(DatabaseUtilities.getUserDatabasePath(system));
		setProgress(20);

		// Load medias
		// ------------
		Map<String, String> medias = new HashMap<String, String>();
		// Path
		String mediaDir = detail.mediaDir + File.separator;
		mediaDir += option.category.getPath();
		// Files
		FileFilterExtension filter = new FileFilterExtension(option.category.getExtension());
		File dir = new File(mediaDir);
		File[] files = dir.listFiles(filter);
		for (File file : files) {
			medias.put(FileUtilities.getNameWithoutExtension(file), FileUtilities.getExtension(file));
		}
		// Unused
		Map<String, String> unused = new HashMap<String, String>();
		unused.putAll(medias);

		// Browse games
		// -------------
		for (GameType game : menu.getGame()) {
			// TODO
		}

		// Logs

		CommonLogger.instance.info("\nTotal of " + result.nbMediaFound + "/" + menu.getGame().size() + " found ("
				+ result.nbMediaMissing + "medias/video missing)");
	}

}
