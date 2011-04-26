package jps.hyperspin.main.controller;

import java.io.File;

import jps.hyperspin.MainClass;
import jps.hyperspin.main.model.Systems;
import jps.hyperspin.main.model.VersionStatut;

public class MainController {

	public static MainController instance = new MainController();

	/**
	 * Put a new system.
	 * 
	 * @param system
	 * @param statut
	 */
	public void changeSystemStatut(String system, VersionStatut statut) {
		Systems.instance.put(system, statut);
		MainClass.mainFrame.getSystemListPanel().repaint();

	}

	/**
	 * Compute the system list.
	 * 
	 * @return th number of system foudn in user settings.
	 */
	public int computeSystems() {

		System.out.println("Hyper spin field  edited");
		// Update JList

		File file = new File(MainClass.HYPERSPIN_PATH);
		if (file.isDirectory()) {
			File settings = new File(MainClass.HYPERSPIN_PATH + "/settings");
			if (settings.isDirectory()) {
				for (String s : settings.list()) {
					if (s.endsWith(".ini")) {
						String ini = s.substring(0, s.length() - 4);
						if (!ini.equals("Main Menu") && !ini.equals("Settings")) {
							Systems.instance.put(ini, null);
						}
					}
				}
			}
		}

		return Systems.instance.list().size();
	}

}
