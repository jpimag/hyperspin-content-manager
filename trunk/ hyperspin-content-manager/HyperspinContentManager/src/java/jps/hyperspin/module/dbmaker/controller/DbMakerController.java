package jps.hyperspin.module.dbmaker.controller;

import javax.swing.ButtonGroup;

import jps.hyperspin.MainClass;
import jps.hyperspin.module.dbmaker.view.DbMakerOptionPanel;

public class DbMakerController {

	public static DbMakerController instance = new DbMakerController();

	/**
	 * Called when userRegion combobox is toggled.
	 * 
	 * @param selected
	 */
	public void toggleUserRegion(boolean selected) {
		getOptionPanel().getRegionPreferencePanel().getNoIntro().setEnabled(selected);
		getOptionPanel().getRegionPreferencePanel().getRedumpOrg().setEnabled(selected);
		getOptionPanel().getRegionPreferencePanel().getPreferredRegion().setEnabled(selected);
		getOptionPanel().getRegionPreferencePanel().getPreferredCountry().setEnabled(selected);
	}

	/**
	 * Called when delteFile combobox is toggled.
	 * 
	 * @param selected
	 */
	public void toggleDeltaFile(boolean selected) {
		getOptionPanel().getDeltaFilesList().setEnabled(selected);
	}

	public void process() {

		// Convert options choosen to DbMakerOption instance
		// TODO

		// Save the model into a file
		// TODO

		// Process
		// TODO

	}

	/**
	 * Load preferences.
	 */
	public void load() {
		// Intitialise some comonent
		ButtonGroup group = new ButtonGroup();
		group.add(getOptionPanel().getRegionPreferencePanel().getNoIntro());
		group.add(getOptionPanel().getRegionPreferencePanel().getRedumpOrg());

		getOptionPanel().getRegionPreferencePanel().getPreferredRegion().addItem("EUROPE");
		getOptionPanel().getRegionPreferencePanel().getPreferredRegion().addItem("NO");
		getOptionPanel().getRegionPreferencePanel().getPreferredCountry().addItem("FRANCE");
		getOptionPanel().getRegionPreferencePanel().getPreferredCountry().addItem("NO");

		// Load preference into DbMakerOption instance
		// TODO

		// Update view according to preference
		// TODO

	}

	private DbMakerOptionPanel getOptionPanel() {
		return MainClass.mainFrame.getMainTabbedPane().getDbMakerTab().getDbMakerOptionPanel();
	}
}
