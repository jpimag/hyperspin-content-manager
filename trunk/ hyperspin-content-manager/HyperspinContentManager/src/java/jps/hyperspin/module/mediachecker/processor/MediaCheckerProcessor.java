package jps.hyperspin.module.mediachecker.processor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jps.hyperspin.common.DatabaseUtilities;
import jps.hyperspin.common.DeltaFileUtilities;
import jps.hyperspin.common.file.FileFilterExtension;
import jps.hyperspin.common.file.FileUtilities;
import jps.hyperspin.common.processor.CommonProcessor;
import jps.hyperspin.common.view.ChoiceDialog;
import jps.hyperspin.common.worker.CommonWorker;
import jps.hyperspin.main.controller.CommonLogger;
import jps.hyperspin.module.dbdownloader.model.DatabaseDetail;
import jps.hyperspin.module.dbdownloader.model.generated.menu.GameType;
import jps.hyperspin.module.dbdownloader.model.generated.menu.MenuType;
import jps.hyperspin.module.dbmaker.model.Delta;
import jps.hyperspin.module.mediachecker.model.MediaCheckerOption;

import org.apache.commons.lang.StringUtils;

public class MediaCheckerProcessor extends CommonProcessor {

	private static final int NB_BEST = 3000;
	private String system;
	private MediaCheckerOption option;
	private DatabaseDetail detail;

	private MediaCheckerResult result = new MediaCheckerResult();

	public class MediaCheckerResult {
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
		// Not found
		List<String> notfound = new ArrayList<String>();

		// Used
		Set<String> used = new HashSet<String>();

		// Load delta file
		// ------------
		Map<String, Delta> deltas = DeltaFileUtilities.loadAllDeltaFileIndexedByReplacementName(DatabaseUtilities
				.getLogsDir(system));

		// Browse games
		// -------------
		for (GameType game : menu.getGame()) {
			boolean found = false;
			// Search if media exist
			if (medias.containsKey(game.getName())) {
				unused.remove(game.getName());
				used.add(game.getName());
				found = true;
			} else {
				// Search in delta file
				if (!found && deltas.containsKey(game.getName())) {
					Delta delta = deltas.get(game.getName());
					if (medias.containsKey(delta.name)) {
						String extension = medias.get(delta.name);
						FileUtilities.moveFile(mediaDir, mediaDir, delta.name + extension, delta.replacementName
								+ extension);
						unused.remove(game.getName());
						used.add(game.getName());
						found = true;
						CommonLogger.instance.info("Media found from delta file. File " + delta.name + extension
								+ "renamed into " + delta.replacementName + extension);
					}
				}
			}
			if (!found) {
				notfound.add(game.getName());
				CommonLogger.instance.info("Media not found for game : " + game.getName());
			}
		}

		if (option.manualResolving && notfound.size() > 0) {
			List<String> l = new ArrayList<String>(notfound);
			for (int i = 0; i < l.size(); i++) {
				// Distance
				String game = l.get(i);
				List<DistanceString> bests;
				bests = bestMatch(game, medias);
				for (DistanceString distanceString : bests) {
					if (used.contains(distanceString.string)) {
						distanceString.info = "used";
					}
				}

				// Dialog
				String message = "Matching medias have been found. \nOriginal normalized name : " + game
						+ "\nDo you want to accept and rename this media ?";

				ChoiceDialog d = new ChoiceDialog(null, "Confirm dialog " + i + "/" + unused.size(), message,
						bests.toArray());
				Object choix = d.getSelection();

				if (d.isCancelled()) {
					// Break
					break;
				} else {

					if (d.isOk()) {
						DistanceString ds = (DistanceString) choix;
						if (ds.info == null || !ds.info.equals("used")) {
							String extension = medias.get(ds.string);
							// Move media
							FileUtilities.moveFile(mediaDir, mediaDir, ds.string + extension, game + extension);
							// update media available
							medias.remove(ds.string);
							unused.remove(ds.string);
							medias.put(game, extension);
						} else {
							String extension = medias.get(ds.string);
							// Move media
							FileUtilities.copyFile(mediaDir, ds.string + extension, game + extension);
							medias.put(game, extension);
							unused.remove(game);
						}
						notfound.remove(game);
						used.add(game);
						CommonLogger.instance.info("Media/video :" + game + " manually found.");
					}
				}
			}
		}

		if (option.moveNotUsed) {
			for (String game : unused.keySet()) {
				String fileName = game + medias.get(game);
				FileUtilities.moveFile(mediaDir, mediaDir + File.separator + "notused" + File.separator, fileName,
						fileName);
			}
		}

		// Logs
		result.nbMediaFound = used.size();
		result.nbMediaMissing = notfound.size();
		CommonLogger.instance.info("\nTotal of " + result.nbMediaFound + "/" + menu.getGame().size() + " found ("
				+ result.nbMediaMissing + "medias/video missing)");
	}

	protected List<DistanceString> bestMatch(String game, Map<String, String> romsMap) {
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

}
