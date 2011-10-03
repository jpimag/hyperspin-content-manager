package jps.hyperspin.module.mediachecker.controller;

import jps.hyperspin.MainClass;
import jps.hyperspin.common.view.BasicProgressDialog;
import jps.hyperspin.main.controller.MainController;
import jps.hyperspin.module.dbdownloader.controller.DbDownLoaderController;
import jps.hyperspin.module.mediachecker.model.MediaCategoryEnum;
import jps.hyperspin.module.mediachecker.model.MediaCheckerOption;
import jps.hyperspin.module.mediachecker.view.MediaCheckerOptionPanel;
import jps.hyperspin.module.mediachecker.worker.MediaCheckerWorker;

public class MediaCheckerController {

	public static MediaCheckerController instance = new MediaCheckerController();

	public void process() {
		String system = MainClass.mainFrame.getSystemSelected();

		// Convert options choosen to Option instance
		MediaCheckerOption option = panelToModel();

		// MediaChecker
		MediaCheckerWorker worker = new MediaCheckerWorker(system, option, MainController.instance.getDbDetail(system));
		new BasicProgressDialog(worker);

		// Update detail
		DbDownLoaderController.instance.updateDetails();
	}

	/**
	 * Load preferences.
	 * 
	 * @param system
	 */
	public void load(String system) {
		// Intitialise some comonent
		getOptionPanel().getJComboBox().removeAllItems();
		for (MediaCategoryEnum category : MediaCategoryEnum.values()) {
			getOptionPanel().getJComboBox().addItem(category);
		}

		// Load preference option
		MediaCheckerOption option = MediaCheckerOption.load(system);

		// Update view according to preference
		modelToPanel(option);

	}

	private MediaCheckerOptionPanel getOptionPanel() {
		return MainClass.mainFrame.getMainTabbedPane().getMediaCheckerTab().getMediaCheckerOptionPanel();
	}

	/**
	 * Build an mediaCheckerPanel from the current panel state.
	 * 
	 * @return
	 */
	private MediaCheckerOption panelToModel() {
		MediaCheckerOption option = new MediaCheckerOption();
		option.manualResolving = getOptionPanel().getManualCheckBox().isSelected();
		option.moveNotUsed = getOptionPanel().getPurgeCheckBox().isSelected();
		option.category = (MediaCategoryEnum) getOptionPanel().getJComboBox().getSelectedItem();
		return option;
	}

	/**
	 * Set panel components from a model instance.
	 * 
	 * @return
	 */
	private void modelToPanel(MediaCheckerOption option) {
		getOptionPanel().getManualCheckBox().setSelected(option.manualResolving);
		getOptionPanel().getPurgeCheckBox().setSelected(option.moveNotUsed);
		if (option.category != null) {
			getOptionPanel().getJComboBox().setSelectedItem(option.category);
		} else {
			getOptionPanel().getJComboBox().setSelectedItem(MediaCategoryEnum.WHEEL);
		}
	}

}
