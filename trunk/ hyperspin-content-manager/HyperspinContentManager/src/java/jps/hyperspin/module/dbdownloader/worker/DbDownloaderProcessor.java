package jps.hyperspin.module.dbdownloader.worker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import jps.hyperspin.MainClass;
import jps.hyperspin.common.DatabaseUtilities;
import jps.hyperspin.common.file.FileUtilities;
import jps.hyperspin.common.worker.CommonWorker;
import jps.hyperspin.common.xml.XmlBinding;
import jps.hyperspin.module.dbdownloader.model.generated.menu.MenuType;

public class DbDownloaderProcessor extends AbstractDbDownloaderProcessor {

	/**
	 * 
	 * @param worker
	 * @param untilProgress
	 */
	public DbDownloaderProcessor(CommonWorker worker, int untilProgress) {
		super(worker, untilProgress);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Get last databases version of selected system from hyperlist official
	 * website.
	 * 
	 */
	@Override
	public void execute() throws Exception {

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
						getLastAvailableDb(MainClass.mainFrame.getSystemSelected()));
				setProgress(10);

				// Genre databases
				// ---------------
				URL url = new URL(getLastAvailableDbUrls(MainClass.mainFrame.getSystemSelected()).urlGenre);
				urlConnGenre = (HttpURLConnection) url.openConnection();
				BufferedReader in = new BufferedReader(new InputStreamReader(urlConnGenre.getInputStream()));
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
					BufferedReader inGenre = new BufferedReader(new InputStreamReader(urlConnTmp.getInputStream()));
					MenuType genre = (MenuType) XmlBinding.getInstance().xml2java(MenuType.class, inGenre);
					String[] tab = urlGenre.split("=");
					map.put(tab[tab.length - 1], genre);
					urlConnTmp.disconnect();

				}
				setProgress(50);

				// Delete all existing files
				// ---------------
				FileUtilities.deleteAllFiles(DatabaseUtilities.getDownloadedDatabaseDir(system), "xml");
				setProgress(60);

				// Write databases from HyperList site
				// ---------------
				int i = 2;
				for (String fileName : map.keySet()) {
					DatabaseUtilities.writeDatabase(map.get(fileName),
							DatabaseUtilities.getDownloadedDatabaseDir(system), fileName + ".xml");
					i++;
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
