package jps.hyperspin.common.i18n;

import java.util.ResourceBundle;

/**
 * 
 * @author jps
 * 
 */
public class Message {

	private static ResourceBundle bundle = ResourceBundle.getBundle("message");

	public static String getMessage(String key) {
		return bundle.getString(key);
	}

}
