package jps.hyperspin.module.dbmaker.controller;

import javax.swing.ButtonGroup;

import jps.hyperspin.MainClass;
import jps.hyperspin.common.view.BasicProgressDialog;
import jps.hyperspin.main.controller.MainController;
import jps.hyperspin.module.dbdownloader.controller.DbDownLoaderController;
import jps.hyperspin.module.dbmaker.model.DbMakerOption;
import jps.hyperspin.module.dbmaker.model.DbMakerOption.NamingConventions;
import jps.hyperspin.module.dbmaker.model.DbMakerRegionEnum;
import jps.hyperspin.module.dbmaker.view.DbMakerOptionPanel;
import jps.hyperspin.module.dbmaker.worker.DbMakerWorker;

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

	public void process() {
		String system = MainClass.mainFrame.getSystemSelected();

		// Convert options choosen to DbMakerOption instance
		DbMakerOption option = panelToModel();

		// Save the model into a file
		option.save();

		// DbMaker
		DbMakerWorker worker = new DbMakerWorker(system, option, MainController.instance.getDbDetail());
		new BasicProgressDialog(worker);

		// Update detail
		DbDownLoaderController.instance.updateDetails();
	}

	/**
	 * Load preferences.
	 */
	public void load() {
		// Intitialise some comonent
		ButtonGroup group = new ButtonGroup();
		group.add(getOptionPanel().getRegionPreferencePanel().getNoIntro());
		group.add(getOptionPanel().getRegionPreferencePanel().getRedumpOrg());

		getOptionPanel().getRegionPreferencePanel().getPreferredRegion().removeAllItems();
		getOptionPanel().getRegionPreferencePanel().getPreferredRegion().addItem(DbMakerRegionEnum.EUROPE);
		getOptionPanel().getRegionPreferencePanel().getPreferredRegion().addItem(DbMakerRegionEnum.NONE);
		getOptionPanel().getRegionPreferencePanel().getPreferredCountry().removeAllItems();
		getOptionPanel().getRegionPreferencePanel().getPreferredCountry().addItem(DbMakerRegionEnum.FRANCE);
		getOptionPanel().getRegionPreferencePanel().getPreferredCountry().addItem(DbMakerRegionEnum.NONE);

		// Load preference into DbMakerOption instance

		DbMakerOption option = DbMakerOption.load();

		// Update view according to preference
		modelToPanel(option);

	}

	private DbMakerOptionPanel getOptionPanel() {
		return MainClass.mainFrame.getMainTabbedPane().getDbMakerTab().getDbMakerOptionPanel();
	}

	/**
	 * Build an dbMakerOption from the current panel state.
	 * 
	 * @return
	 */
	private DbMakerOption panelToModel() {
		DbMakerOption option = new DbMakerOption();
		option.noClone = getOptionPanel().getNoClones().isSelected();
		option.moveReplacedRoms = getOptionPanel().getRemoveReplacedRoms().isSelected();
		option.useRegionPreference = getOptionPanel().getUseRegionPreference().isSelected();
		if (option.useRegionPreference) {
			option.country = (DbMakerRegionEnum) getOptionPanel().getRegionPreferencePanel().getPreferredCountry()
					.getSelectedItem();
			option.region = (DbMakerRegionEnum) getOptionPanel().getRegionPreferencePanel().getPreferredRegion()
					.getSelectedItem();

			if (getOptionPanel().getRegionPreferencePanel().getNoIntro().isSelected()) {
				option.namingConventions = NamingConventions.NO_INTRO;
			} else {
				option.namingConventions = NamingConventions.REDUMP_ORG;
			}
		}
		return option;
	}

	/**
	 * Set panel components from a model instance.
	 * 
	 * @return
	 */
	private void modelToPanel(DbMakerOption option) {
		getOptionPanel().getNoClones().setSelected(option.noClone);
		getOptionPanel().getRemoveReplacedRoms().setSelected(option.moveReplacedRoms);
		getOptionPanel().getUseRegionPreference().setSelected(option.useRegionPreference);
		if (option.useRegionPreference) {
			getOptionPanel().getRegionPreferencePanel().getPreferredRegion().setSelectedItem(option.region);
			getOptionPanel().getRegionPreferencePanel().getPreferredCountry().setSelectedItem(option.country);
			getOptionPanel().getRegionPreferencePanel().getNoIntro().setSelected(false);
			getOptionPanel().getRegionPreferencePanel().getRedumpOrg().setSelected(false);
			switch (option.namingConventions) {
			case NO_INTRO:
				getOptionPanel().getRegionPreferencePanel().getNoIntro().setSelected(true);
				break;
			case REDUMP_ORG:
				getOptionPanel().getRegionPreferencePanel().getRedumpOrg().setSelected(true);
				break;
			}
		}

	}

}
