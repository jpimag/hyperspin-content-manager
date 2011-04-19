package jps.hyperspin.process.properties;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author JPS
 * 
 */
public final class IniCategoryProperties {

	/**
	 * 
	 */
	private Map<String, String> properties;

	/**
	 * 
	 */
	public IniCategoryProperties() {
		properties = new HashMap<String, String>();
	}

	/**
	 * @param key
	 *            key
	 * @return the properties
	 */
	public String getProperty(final String key) {
		return properties.get(key);
	}

	/**
	 * @return the properties
	 */
	public Map<String, String> getProperties() {
		return properties;
	}

	/**
	 * @param properties
	 *            the properties to set
	 */
	public void setProperties(final Map<String, String> properties) {
		this.properties = properties;
	}
}
