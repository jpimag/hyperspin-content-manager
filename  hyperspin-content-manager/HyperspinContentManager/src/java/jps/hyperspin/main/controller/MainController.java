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
	public void putSystem(String system, VersionStatut statut) {
		if (!Systems.instance.exist(system)) {
			MainClass.mainFrame.getSystemListPanel().addElement(system);
		} else {
			MainClass.mainFrame.getSystemListPanel().repaint();
		}
		Systems.instance.put(system, statut);

	}

	/**
	 * Compute the system list.
	 */
	public void computeSystems() {

		System.out.println("Hyper spin field  edited");
		// Update JList
		MainClass.mainFrame.getSystemListPanel().removeAllElements();

		File file = new File(MainClass.mainFrame.getHyperSpinPath());
		if (file.isDirectory()) {
			File settings = new File(MainClass.mainFrame.getHyperSpinPath()
					+ "/settings");
			if (settings.isDirectory()) {
				for (String s : settings.list()) {
					if (s.endsWith(".ini")) {
						String ini = s.substring(0, s.length() - 4);
						if (!ini.equals("Main Menu") && !ini.equals("Settings")) {
							MainController.instance.putSystem(ini, null);
						}
					}
				}
			}
		}
	}
}
