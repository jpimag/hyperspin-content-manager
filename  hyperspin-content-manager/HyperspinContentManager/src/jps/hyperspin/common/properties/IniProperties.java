package jps.hyperspin.common.properties;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import jps.hyperspin.exception.HCMInternalException;
import jps.hyperspin.exception.HCMDatabaseException;

/**
 * 
 * @author JPS
 * 
 */
public final class IniProperties {

	/**
	 * 
	 */
	private Map<String, IniCategoryProperties> categories;

	/**
	 * 
	 */
	public IniProperties() {
		categories = new HashMap<String, IniCategoryProperties>();
	}

	/**
	 * 
	 * @param reader
	 *            reader
	 * @throws HCMDatabaseException
	 *             exception
	 */
	public void load(final Reader reader) throws HCMDatabaseException {
		try {
			BufferedReader br = new BufferedReader(reader);
			String strLine;
			String categorie = null;
			while ((strLine = br.readLine()) != null) {
				strLine = strLine.trim();
				if (strLine.startsWith("[")) {
					// Categorie
					categorie = strLine;
					categories.put(categorie.trim(),
							new IniCategoryProperties());
				} else if (categorie != null) {

					if (strLine.contains("=")) {
						// entry
						String[] result = strLine.split("=", 2);
						if (result.length == 2) {
							categories.get(categorie).getProperties().put(
									result[0].trim(), result[1].trim());
						}
					}
				}
			}
		} catch (IOException e) {
			throw new HCMInternalException(e, "error loading ini file");
		}
	}

	/**
	 * @param categorie
	 *            categorie
	 * @param key
	 *            key
	 * @return the categories
	 */
	public String getProperty(final String categorie, final String key) {
		IniCategoryProperties cat = categories.get(categorie);
		if (cat != null) {
			return cat.getProperty(key);
		}
		return null;
	}

	/**
	 * @return the categories
	 */
	public Map<String, IniCategoryProperties> getCategories() {
		return categories;
	}

	/**
	 * @return the categories
	 */
	public String toString() {
		String result = "";
		for (String catKey : getCategories().keySet()) {
			result += "----------" + catKey + "---------\n";
			for (String key : getCategories().get(catKey).getProperties()
					.keySet()) {
				result += key + ":"
						+ getCategories().get(catKey).getProperty(key) + "\n";
			}
		}
		return result;
	}
}
