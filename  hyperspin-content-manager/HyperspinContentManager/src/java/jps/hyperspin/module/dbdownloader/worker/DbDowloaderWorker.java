package jps.hyperspin.module.dbdownloader.worker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import jps.hyperspin.MainClass;
import jps.hyperspin.common.DatabaseUtilities;
import jps.hyperspin.common.file.FileUtilities;
import jps.hyperspin.common.xml.XmlBinding;
import jps.hyperspin.module.dbdownloader.model.MenuType;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


/**
 * This worker in in charge to check database version for the selected system.
 * 
 * @author jps
 * 
 */
public class DbDowloaderWorker extends AbstractDbDownloaderWorker {

	public DbDowloaderWorker() {
		super();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Get last databases version of selected system from hyperlist official
	 * website.
	 * 
	 */
	protected void executeInternal() throws Exception {

		setProgress(1);

		String system = MainClass.mainFrame.getSystemSelected();
		if (system != null) {

			Map<String, MenuType> map = new HashMap<String, MenuType>();
			HttpURLConnection urlConnGenre = null;
			HttpURLConnection urlConnTmp = null;
			try {

				// Main database
				// -------------
				map.put(MainClass.mainFrame.getSystemSelected(),
						getLastAvailableDb());
				setProgress(10);

				// Genre databases
				// ---------------
				URL url = new URL(getLastAvailableDbUrls().urlGenre);
				urlConnGenre = (HttpURLConnection) url.openConnection();
				BufferedReader in = new BufferedReader(new InputStreamReader(
						urlConnGenre.getInputStream()));
				String sLine;
				String fileContent = "";
				while ((sLine = in.readLine()) != null) {
					fileContent += sLine;
				}
				setProgress(20);

				String[] options = fileContent.split("<option");
				for (int i = 2; i < options.length; i++) {
					String urlGenre = options[i].split("value='")[1];
					urlGenre = urlGenre.split("'")[0];
					urlGenre = urlGenre.replaceAll("%20", " ");
					url = new URL(HYPERLIST_URL + urlGenre);
					urlConnTmp = (HttpURLConnection) url.openConnection();
					BufferedReader inGenre = new BufferedReader(
							new InputStreamReader(urlConnTmp.getInputStream()));
					MenuType genre = (MenuType) XmlBinding.getInstance()
							.xml2java(MenuType.class, inGenre);
					String[] tab = urlGenre.split("=");
					map.put(tab[tab.length - 1], genre);
					urlConnTmp.disconnect();

					computeProgressFromAchievedStep(i, options.length, 50);
				}

				// Delete all existing files
				// ---------------
				FileUtilities.deleteAllFiles(
						DatabaseUtilities.getReferenceDatabaseDir(), "xml");
				setProgress(60);

				// Write databases from HyperList site
				// ---------------
				int i = 2;
				for (String genre : map.keySet()) {
					DatabaseUtilities.writeDatabase(map.get(genre),
							DatabaseUtilities.getGenreDatabasePath(genre));
					i++;
					computeProgressFromAchievedStep(i, options.length, 99);
				}

			} finally {

				if (urlConnGenre != null) {
					urlConnGenre.disconnect();
				}
				if (urlConnTmp != null) {
					urlConnTmp.disconnect();
				}
				setProgress(100);

			}

		}

	}
}
