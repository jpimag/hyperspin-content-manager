package jps.hyperspin.module.database.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import jps.hyperspin.MainClass;
import jps.hyperspin.module.AbstractProcessor;
import jps.hyperspin.module.database.model.MenuType;
import jps.hyperspin.module.database.presentation.downloader.IDatabaseDetail;
import jps.hyperspin.process.xml.XmlBinding;

/**
 * 
 * @author JPS
 * 
 */
public class DownloadProcessor extends AbstractProcessor {

	private static final String HYPERLIST_URL = "http://hyperlist.hyperspin-fe.com/";

	public DownloadProcessor(IDatabaseDetail databaseDetail) {
		super(databaseDetail, null);
	}

	private class DatabaseUrls {
		public String urlDb;
		public String urlGenre;

	}

	/**
	 * Retourne la l'url de la derniere version du systeme selectionné dans le
	 * tableau HyperList sur le site officiel d'Hyperspin.
	 */
	private DatabaseUrls getLastAvailableDbUrls() {
		DatabaseUrls databaseUrls = new DatabaseUrls();
		try {
			URL url = new URL(HYPERLIST_URL);
			HttpURLConnection urlConn = (HttpURLConnection) url
					.openConnection();

			// Lire la reponse
			BufferedReader in = new BufferedReader(new InputStreamReader(
					urlConn.getInputStream()));
			boolean found = false;
			String sLine;
			// On recherche l'URL du fichier DB
			while (!found && (sLine = in.readLine()) != null) {
				if (sLine.contains("System Name")) {
					while (!found && (sLine = in.readLine()) != null) {
						if (sLine.contains(MainClass.mainFrame
								.getSystemSelected())) {
							while (!found && (sLine = in.readLine()) != null) {
								if (sLine.contains("Download XML")) {
									databaseUrls.urlDb = sLine.split("href='")[1];
									databaseUrls.urlDb = databaseUrls.urlDb
											.split("'")[0];
									databaseUrls.urlDb = HYPERLIST_URL
											+ databaseUrls.urlDb;
								}

								if (sLine.contains("Download Genre XML")) {
									databaseUrls.urlGenre = sLine
											.split("href='")[2];
									databaseUrls.urlGenre = databaseUrls.urlGenre
											.split("'")[0];
									databaseUrls.urlGenre = HYPERLIST_URL

									+ databaseUrls.urlGenre;
									found = true;
								}

							}
						}
					}
				}
			}
			// deconnection
			in.close();
			urlConn.disconnect();
		} catch (MalformedURLException e) {
			MainClass.mainFrame.getLogger().error(e.getMessage());
		} catch (IOException e) {
			MainClass.mainFrame.getLogger().error(e.getMessage());
		}
		return databaseUrls;
	}

	/**
	 * Retourne la derniere version des databases du systeme selectionné dans le
	 * tableau HyperList sur le site officiel d'Hyperspin.
	 */
	public Map<String, MenuType> getLastAvailableDbs(boolean all) {
		Map<String, MenuType> map = new HashMap<String, MenuType>();
		HttpURLConnection urlConn = null;
		HttpURLConnection urlConnGenre = null;
		HttpURLConnection urlConnTmp = null;
		try {
			DatabaseUrls dbUrl = getLastAvailableDbUrls();

			// Main database
			// -------------
			URL url = new URL(dbUrl.urlDb);
			urlConn = (HttpURLConnection) url.openConnection();

			BufferedReader in = new BufferedReader(new InputStreamReader(
					urlConn.getInputStream()));

			MenuType main = (MenuType) XmlBinding.getInstance().xml2java(
					MenuType.class, in);
			map.put(MainClass.mainFrame.getSystemSelected(), main);

			// Genre databases
			// ---------------
			if (all) {
				url = new URL(getLastAvailableDbUrls().urlGenre);
				urlConnGenre = (HttpURLConnection) url.openConnection();
				in = new BufferedReader(new InputStreamReader(
						urlConnGenre.getInputStream()));
				String sLine;
				String fileContent = "";
				while ((sLine = in.readLine()) != null) {
					fileContent += sLine;
				}

				String[] options = fileContent.split("<option");
				for (int i = 2; i <= options.length; i++) {
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
				}

			}

		} catch (Exception e) {
			MainClass.mainFrame.getLogger().error(e.getMessage());
		} finally {
			if (urlConn != null) {
				urlConn.disconnect();
			}
			if (urlConnGenre != null) {
				urlConnGenre.disconnect();
			}
			if (urlConnTmp != null) {
				urlConnTmp.disconnect();
			}
		}

		return map;
	}
}
