package jps.hyperspin.module.dbmaker.processor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import jps.hyperspin.common.DatabaseUtilities;
import jps.hyperspin.common.file.FileUtilities;
import jps.hyperspin.common.log.Logger;
import jps.hyperspin.exception.HCMDatabaseException;
import jps.hyperspin.module.AbstractProcessor;
import jps.hyperspin.module.dbdownloader.presentation.ChoiceDialog;
import jps.hyperspin.module.dbdownloader.presentation.IDatabaseDetail;
import jps.hyperspin.module.dbmaker.presentation.IDatabaseOption;

/**
 * 
 * @author JPS
 * 
 */
public class MediaProcessor extends AbstractProcessor {

	private enum ProcessingOption {
		DATABASE, REFERENCE_DATABASE, ROMS
	}

	public MediaProcessor(final IDatabaseDetail databaseDetail,
			final IDatabaseOption databaseOption) {
		super(databaseDetail, databaseOption);

	}

	public void processMedia(String relativePath, final Logger logger)
			throws HCMDatabaseException, IOException {

		// Database or roms
		List<ProcessingOption> options = new ArrayList<ProcessingOption>();
		options.add(ProcessingOption.DATABASE);
		options.add(ProcessingOption.REFERENCE_DATABASE);
		options.add(ProcessingOption.ROMS);
		ProcessingOption processingOption = (ProcessingOption) JOptionPane
				.showInputDialog(null, "Whic list of roms do you want check ? "
						+ options.size(), "Option",
						JOptionPane.WARNING_MESSAGE, null, options.toArray(),
						options.get(0));

		String mediaDir = databaseDetail.getMediaRepository() + "/"
				+ relativePath;

		Map<String, String> games = null;
		if (processingOption == ProcessingOption.DATABASE) {
			games = loadDatabase(databaseDetail.getGeneratedDatabaseDir() + "/"
					+ databaseDetail.getIniSelected() + ".xml", logger);
		} else if (processingOption == ProcessingOption.REFERENCE_DATABASE) {
			games = loadDatabase(DatabaseUtilities.getReferenceDatabasePath(),
					logger);

		} else if (processingOption == ProcessingOption.ROMS) {
			// Step 4 : Parse roms
			games = listRecursiveFilesWithFilter(
					new File(getIni().getRomPath()), false, getIni()
							.getRomExtension());
		}

		// Medias
		int menuMediaNotFound = 0;
		File dir = new File(mediaDir);
		File[] files = dir.listFiles();
		Map<String, String> medias = new HashMap<String, String>();
		for (File file : files) {
			String extension = FileUtilities.getExtension(file);
			if (extension.equalsIgnoreCase(".flv")
					|| extension.equalsIgnoreCase(".avi")
					|| extension.equalsIgnoreCase(".png")
					|| extension.equalsIgnoreCase(".swf")) {
				medias.put(FileUtilities.getNameWithoutExtension(file),
						FileUtilities.getExtension(file));
			}
		}

		// Unused
		List<String> unused = new ArrayList<String>();
		for (String media : medias.keySet()) {
			if (games.get(media) == null) {
				unused.add(media);
			}
		}

		// Parse all games
		int i = 0;
		boolean continueDeep = true;
		for (String gameSearched : games.keySet()) {
			i++;
			if (medias.get(gameSearched) == null) {
				menuMediaNotFound++;

				logger.trace("Media :" + gameSearched + " not found.");

				boolean found = false;
				if (databaseOption.isRegionMatching()) {
					// Propose automatic research by region

					String extension = "";
					String gameWithoutRegion = gameSearched.split("\\(")[0];
					List<String> removed = new ArrayList<String>();
					for (String media : medias.keySet()) {
						String mediaWithoutRegion = media.split("\\(")[0];
						if (mediaWithoutRegion.equals(gameWithoutRegion)) {
							logger.trace("A media from another region have been for game "
									+ gameSearched + ". A copy have been made.");
							found = true;
							extension = medias.get(media);
							if (games.get(media) == null) {
								// We made a copy
								// Move media
								FileUtilities.moveFile(mediaDir, mediaDir,
										media + extension, gameSearched
												+ extension);
								removed.add(media);

							} else {
								FileUtilities.copyFile(mediaDir, media
										+ extension, gameSearched + extension);

							}
						}
					}
					for (String string : removed) {
						medias.remove(string);
					}
					if (found) {
						medias.put(gameSearched, extension);
						menuMediaNotFound--;
					}
				}

				// Propose matching medias
				if (!found && continueDeep
						&& databaseOption.isDeepMatchSelected()) {

					List<DistanceString> bests;
					if (databaseOption.isOnlyUnused()) {
						bests = new ArrayList<DistanceString>();
						for (String media : unused) {
							bests.add(new DistanceString(media, 1));
						}
					} else {
						bests = bestMatch(gameSearched, medias);
						for (DistanceString distanceString : bests) {
							if (games.get(distanceString.string) != null) {
								distanceString.info = "used";
							}
						}
					}

					// Dialog
					String message = "Matching medias have been found. \nOriginal normalized name : "
							+ gameSearched
							+ "\nDo you want to accept and rename this media ?";

					ChoiceDialog d = new ChoiceDialog(null, "Confirm dialog "
							+ i + "/" + games.size(), message, bests.toArray());
					Object choix = d.getSelection();

					if (d.isCancelled()) {
						// Break
						continueDeep = false;
					} else {

						if (d.isOk()) {
							DistanceString ds = (DistanceString) choix;
							if (ds.info == null || !ds.info.equals("used")) {
								String extension = medias.get(ds.string);
								// Move media
								FileUtilities.moveFile(mediaDir, mediaDir,
										ds.string + extension, gameSearched
												+ extension);
								// update media available
								medias.remove(ds.string);
								unused.remove(ds.string);
								medias.put(gameSearched, extension);

							} else {
								String extension = medias.get(ds.string);
								// Move media
								FileUtilities.copyFile(mediaDir, ds.string
										+ extension, gameSearched + extension);
								medias.put(gameSearched, extension);
							}
							logger.trace("Media/video :" + gameSearched
									+ " manually found.");
							menuMediaNotFound--;
						}
					}
				}

			}

		}

		int menuMediaFound = games.size() - menuMediaNotFound;
		logger.info("\nTotal of " + menuMediaFound + "/" + games.size()
				+ " found (" + menuMediaNotFound + "medias/video missing)");

		int choix = JOptionPane.showConfirmDialog(null,
				"Do you want to manually check unused medias ?", "",
				JOptionPane.YES_NO_OPTION);
		if (choix == 0) {
			reverseProcessMedia(relativePath, medias, games, logger);
		}

	}

	public final void reverseProcessMedia(String relativePath,
			Map<String, String> medias, Map<String, String> games,
			final Logger logger) throws FileNotFoundException,
			HCMDatabaseException {

		String mediaDir = databaseDetail.getMediaRepository() + "/"
				+ relativePath;

		int menuMediaNotFound = 0;

		// Unused
		List<String> unused = new ArrayList<String>();
		for (String media : medias.keySet()) {
			if (games.get(media) == null) {
				unused.add(media);
			}
		}

		if (unused.size() == 0) {
			JOptionPane.showMessageDialog(null, "No media not used.");
		} else {
			// Show non used videos
			String message = unused.size() + " unused medias :\n\n";

			JOptionPane.showInputDialog(null, message, unused.size()
					+ " unused medias ", JOptionPane.PLAIN_MESSAGE, null,
					unused.toArray(), unused.get(0));

			// Parse all games
			int i = 0;
			boolean continueDeep = true;
			for (String unusedMedia : unused) {
				i++;
				if (games.get(unusedMedia) == null) {
					menuMediaNotFound++;

					logger.trace("Media :" + unusedMedia + " not found.");

					// Propose matching medias
					if (continueDeep) {

						List<DistanceString> bests;

						if (databaseOption.isOnlyUnused()) {
							bests = new ArrayList<DistanceString>();
							for (String g : games.keySet()) {
								if (medias.get(g) == null) {
									bests.add(new DistanceString(g, 1));
								}
							}
						} else {
							bests = bestMatch(unusedMedia, games);
							for (DistanceString distanceString : bests) {
								if (medias.get(distanceString.string) != null) {
									distanceString.info = "have media";
								}
							}
						}

						// Dialog
						message = "Matching games have been found. \nOriginal normalized media name : '"
								+ unusedMedia
								+ "' Do you want to accept and rename this media ?";

						ChoiceDialog d = new ChoiceDialog(null,
								"Confirm dialog " + i + "/" + games.size(),
								message, bests.toArray());
						Object choix = d.getSelection();

						if (d.isCancelled()) {
							// Break
							continueDeep = false;
						} else {
							String extension = medias.get(unusedMedia);
							DistanceString ds = (DistanceString) choix;
							if (d.isRemove()) {
								FileUtilities.deleteFile(mediaDir, unusedMedia
										+ extension);
							}
							if (d.isOk()) {
								if (ds.info == null
										|| !ds.info.equals("have media")) {

									// Move media
									FileUtilities.moveFile(mediaDir, mediaDir,
											unusedMedia + extension, ds.string
													+ extension);
									// update media available
									medias.remove(unusedMedia);
									medias.put(ds.string, extension);

									logger.trace("Media/video :" + ds.string
											+ " manually found.");
									menuMediaNotFound--;
								}
							}
						}
					}

				}
			}
		}

		logger.info("\nTotal of " + menuMediaNotFound + "unused.");

	}

	public final void purgeMedia(String relativePath, final Logger logger)
			throws FileNotFoundException, HCMDatabaseException {

		String mediaDir = databaseDetail.getMediaRepository() + "/"
				+ relativePath;
		Map<String, String> games = loadDatabase(

		DatabaseUtilities.getReferenceDatabasePath(), logger);

		// Medias
		File dir = new File(mediaDir);
		File[] files = dir.listFiles();
		Map<String, String> medias = new HashMap<String, String>();
		for (File file : files) {
			String extension = FileUtilities.getExtension(file);
			if (extension.equals(".flv") || extension.equals(".png")) {
				medias.put(FileUtilities.getNameWithoutExtension(file),
						FileUtilities.getExtension(file));
			}
		}

		// Unused
		List<String> unused = new ArrayList<String>();
		for (String media : medias.keySet()) {
			if (games.get(media) == null) {
				unused.add(media);
			}
		}

		// Show non used videos
		JOptionPane.showInputDialog(null, "Your are going to suppress "
				+ unused.size() + " unused medias. ", "Warning",
				JOptionPane.WARNING_MESSAGE, null, unused.toArray(),
				unused.get(0));

		int choix = JOptionPane.showConfirmDialog(null,
				"Are you sure you want to delete these " + unused.size()
						+ " unused medias. ", "Warning",
				JOptionPane.YES_NO_OPTION);

		if (choix == 0) {
			for (String string : unused) {
				File toDelete = new File(mediaDir + "/" + string
						+ medias.get(string));
				toDelete.delete();
			}
			logger.info("\nTotal of " + unused.size()
					+ " unused medias deleted.");
		}

	}
}
