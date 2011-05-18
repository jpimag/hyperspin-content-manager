package jps.hyperspin.module.mediachecker.controller;

import jps.hyperspin.MainClass;
import jps.hyperspin.module.mediachecker.model.MediaCategoryEnum;
import jps.hyperspin.module.mediachecker.model.MediaCheckerOption;
import jps.hyperspin.module.mediachecker.view.MediaCheckerOptionPanel;

public class MediaCheckerController {

	public static MediaCheckerController instance = new MediaCheckerController();

	public void process() {
		String system = MainClass.mainFrame.getSystemSelected();

		// Convert options choosen to Option instance
		MediaCheckerOption option = panelToModel();

		// Save the model into a file
		option.save();

		// Process
		// TODO
	}

	/**
	 * Load preferences.
	 */
	public void load() {
		// Intitialise some comonent
		for (MediaCategoryEnum category : MediaCategoryEnum.values()) {
			getOptionPanel().getJComboBox().addItem(category);
		}

		// Load preference option
		MediaCheckerOption option = MediaCheckerOption.load();

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
		return option;
	}

	/**
	 * Set panel components from a model instance.
	 * 
	 * @return
	 */
	private void modelToPanel(MediaCheckerOption option) {
		getOptionPanel().getManualCheckBox().setSelected(option.manualResolving);

	}

}
