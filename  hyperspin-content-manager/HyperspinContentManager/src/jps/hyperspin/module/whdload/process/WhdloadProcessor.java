package jps.hyperspin.module.whdload.process;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jps.hyperspin.common.file.FileUtilities;
import jps.hyperspin.common.presentation.PanelLogger;
import jps.hyperspin.common.xml.XmlBinding;
import jps.hyperspin.exception.HCMDatabaseException;
import jps.hyperspin.module.AbstractProcessor;
import jps.hyperspin.module.dbdownloader.presentation.SystemIniProperties;
import jps.hyperspin.module.whdload.model.GameType;
import jps.hyperspin.module.whdload.model.WhdloadType;
import jps.hyperspin.module.whdload.presentation.WhdloadFormPanel;

/**
 * 
 * @author JPS
 * 
 */
public class WhdloadProcessor extends AbstractProcessor {

	public WhdloadProcessor(SystemIniProperties ini) {
		super(ini);
	}

	private Map<String, String> loadWhdLoadGameList(
			final WhdloadFormPanel form, final PanelLogger logger)
			throws HCMDatabaseException, IOException {

		Map<String, String> whdLoadGames = new HashMap<String, String>();
		File whdload = new File(form.getWhdLoadXml());
		FileReader reader = new FileReader(whdload);
		WhdloadType whdloadType = (WhdloadType) XmlBinding.getInstance()
				.xml2java(WhdloadType.class, reader);
		for (GameType game : whdloadType.getGame()) {
			String filename = game.getFileName()
					.replaceFirst("WHDLoad\\\\", "");
			whdLoadGames.put(filename.substring(0, filename.lastIndexOf('.')),
					filename);

		}
		logger.info("Whdload list performed. (" + whdLoadGames.size()
				+ " games)");
		return whdLoadGames;
	}

	/**
	 * 
	 * @param request
	 *            request
	 * @param logger
	 *            logger
	 * @throws HCMDatabaseException
	 *             exception
	 */
	public final void processMissingFiles(final WhdloadFormPanel form,
			final PanelLogger logger) throws HCMDatabaseException,
			IOException {

		logger.info("----------------------------------");
		// Build whdlload list
		Map<String, String> whdLoadGames = loadWhdLoadGameList(form, logger);

		// Load roms list
		Map<String, String> romList = listRecursiveFilesWithFilter(new File(
				getIni().getRomPath()), false, "zip");
		logger.info("Roms list performed. (" + romList.size() + " files)");

		// search missing roms
		int missing = 0;
		for (String whdLoadGame : whdLoadGames.keySet()) {
			if (romList.get(whdLoadGame) == null) {
				missing++;
				logger.info(whdLoadGames.get(whdLoadGame) + " not found");
			}
		}
		logger.info("A total of " + missing + " game not found.");
		logger.info("FINISHED.");

	}

	/**
	 * 
	 * @param request
	 *            request
	 * @param logger
	 *            logger
	 * @throws HCMDatabaseException
	 *             exception
	 */
	public final void processNotUsedFiles(final WhdloadFormPanel form,
			final PanelLogger logger) throws HCMDatabaseException,
			IOException {

		logger.info("----------------------------------");
		// Build whdlload list
		Map<String, String> whdLoadGames = loadWhdLoadGameList(form, logger);

		// Load roms list
		Map<String, String> romList = listRecursiveFilesWithFilter(new File(
				getIni().getRomPath()), false, "zip");
		logger.info("Roms list performed. (" + romList.size() + " files)");

		// search not used
		int notused = 0;
		File notuseddir = new File(getIni().getRomPath() + "/notused");
		if (!notuseddir.exists()) {
			notuseddir.mkdir();
		}
		for (String rom : romList.keySet()) {
			if (whdLoadGames.get(rom) == null) {
				notused++;
				logger.info(romList.get(rom) + " not used");
				FileUtilities.moveFile(getIni().getRomPath(), notuseddir
						.getAbsolutePath(), romList.get(rom), romList.get(rom));
			}
		}
		logger.info("A total of " + notused + " not used game not found.");
		logger.info("They have been moved into 'notused' directory");

		logger.info("FINISHED.");

	}
}
