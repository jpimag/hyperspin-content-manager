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
		group.add(getOptionPanel().getNamingConventionPanel().getNoIntro());
		group.add(getOptionPanel().getNamingConventionPanel().getRedumpOrg());
		group.add(getOptionPanel().getNamingConventionPanel().getOther());

		getOptionPanel().getNamingConventionPanel().getPreferredRegion().removeAllItems();
		getOptionPanel().getNamingConventionPanel().getPreferredRegion().addItem(DbMakerRegionEnum.EUROPE);
		getOptionPanel().getNamingConventionPanel().getPreferredRegion().addItem(DbMakerRegionEnum.NONE);
		getOptionPanel().getNamingConventionPanel().getPreferredRegion().setSelectedItem(DbMakerRegionEnum.NONE);
		getOptionPanel().getNamingConventionPanel().getPreferredCountry().removeAllItems();
		getOptionPanel().getNamingConventionPanel().getPreferredCountry().addItem(DbMakerRegionEnum.FRANCE);
		getOptionPanel().getNamingConventionPanel().getPreferredCountry().addItem(DbMakerRegionEnum.NONE);
		getOptionPanel().getNamingConventionPanel().getPreferredCountry().setSelectedItem(DbMakerRegionEnum.NONE);

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
		option.country = (DbMakerRegionEnum) getOptionPanel().getNamingConventionPanel().getPreferredCountry()
				.getSelectedItem();
		option.region = (DbMakerRegionEnum) getOptionPanel().getNamingConventionPanel().getPreferredRegion()
				.getSelectedItem();

		if (getOptionPanel().getNamingConventionPanel().getNoIntro().isSelected()) {
			option.namingConventions = NamingConventions.NO_INTRO;
		} else {
			option.namingConventions = NamingConventions.REDUMP_ORG;
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
		getOptionPanel().getNamingConventionPanel().getPreferredRegion().setSelectedItem(option.region);
		getOptionPanel().getNamingConventionPanel().getPreferredCountry().setSelectedItem(option.country);
		getOptionPanel().getNamingConventionPanel().getNoIntro().setSelected(false);
		getOptionPanel().getNamingConventionPanel().getRedumpOrg().setSelected(false);
		switch (option.namingConventions) {
		case NO_INTRO:
			getOptionPanel().getNamingConventionPanel().getNoIntro().setSelected(true);
			break;
		case REDUMP_ORG:
			getOptionPanel().getNamingConventionPanel().getRedumpOrg().setSelected(true);
			break;
		}

	}

}
