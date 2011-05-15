package jps.hyperspin.module.dbdownloader.worker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import jps.hyperspin.common.worker.CommonProcessor;
import jps.hyperspin.common.worker.CommonWorker;
import jps.hyperspin.common.xml.XmlBinding;
import jps.hyperspin.exception.HCMBindingException;
import jps.hyperspin.exception.HCMDatabaseException;
import jps.hyperspin.main.controller.CommonLogger;
import jps.hyperspin.module.dbdownloader.model.generated.menu.MenuType;

/**
 * 
 * @author JPS
 * 
 */
public abstract class AbstractDbDownloaderProcessor extends CommonProcessor {

	protected static final String HYPERLIST_URL = "http://hyperlist.hyperspin-fe.com/";

	protected class DatabaseUrls {
		public String urlDb;
		public String urlGenre;

	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	public AbstractDbDownloaderProcessor(CommonWorker worker, int untilProgress) {
		super(worker, untilProgress);
	}

	/**
	 * Retourne la l'url de la derniere version du systeme selectionné dans le
	 * tableau HyperList sur le site officiel d'Hyperspin.
	 */
	protected DatabaseUrls getLastAvailableDbUrls(String system) {
		BufferedReader in = null;
		HttpURLConnection urlConn = null;

		DatabaseUrls databaseUrls = new DatabaseUrls();
		try {

			URL url = new URL(HYPERLIST_URL);
			urlConn = (HttpURLConnection) url.openConnection();

			// Lire la reponse
			in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
			boolean found = false;
			String sLine;
			// On recherche l'URL du fichier DB
			while (!found && (sLine = in.readLine()) != null) {
				if (sLine.contains("System Name")) {
					while (!found && (sLine = in.readLine()) != null) {
						if (sLine.contains(system)) {
							while (!found && (sLine = in.readLine()) != null) {
								if (sLine.contains("Download XML")) {
									databaseUrls.urlDb = sLine.split("href='")[1];
									databaseUrls.urlDb = databaseUrls.urlDb.split("'")[0];
									databaseUrls.urlDb = HYPERLIST_URL + databaseUrls.urlDb;
								}

								if (sLine.contains("Download Genre XML")) {
									databaseUrls.urlGenre = sLine.split("href='")[2];
									databaseUrls.urlGenre = databaseUrls.urlGenre.split("'")[0];
									databaseUrls.urlGenre = HYPERLIST_URL

									+ databaseUrls.urlGenre;
									found = true;
								}

							}
						}
					}
				}
			}
		} catch (MalformedURLException e) {
			CommonLogger.instance.error(e.getMessage());
		} catch (IOException e) {
			CommonLogger.instance.error(e.getMessage());
		} finally {
			try {
				// deconnection
				in.close();
				urlConn.disconnect();
			} catch (Exception e) {

			}
		}
		return databaseUrls;
	}

	/**
	 * Retourne la derniere version de la database principale du systeme
	 * selectionné dans le tableau HyperList sur le site officiel d'Hyperspin.
	 */
	public MenuType getLastAvailableDb(String system) throws HCMBindingException, HCMDatabaseException {
		HttpURLConnection urlConn = null;
		MenuType main = null;
		try {
			DatabaseUrls dbUrl = getLastAvailableDbUrls(system);

			// Main database
			// -------------
			URL url = new URL(dbUrl.urlDb);
			urlConn = (HttpURLConnection) url.openConnection();

			BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

			main = (MenuType) XmlBinding.getInstance().xml2java(MenuType.class, in);

		} catch (MalformedURLException e) {
			CommonLogger.instance.error(e.getMessage());
		} catch (IOException e) {
			CommonLogger.instance.error(e.getMessage());

		} finally {
			if (urlConn != null) {
				urlConn.disconnect();
			}
		}
		return main;

	}

}
