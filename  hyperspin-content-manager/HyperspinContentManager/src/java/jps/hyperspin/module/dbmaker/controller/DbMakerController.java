package jps.hyperspin.module.dbmaker.controller;

import jps.hyperspin.MainClass;
import jps.hyperspin.common.view.BasicProgressDialog;
import jps.hyperspin.main.controller.MainController;
import jps.hyperspin.module.dbdownloader.controller.DbDownLoaderController;
import jps.hyperspin.module.dbmaker.model.DbMakerOption;
import jps.hyperspin.module.dbmaker.view.DbMakerOptionPanel;
import jps.hyperspin.module.dbmaker.worker.DbMakerWorker;

public class DbMakerController {

	public static DbMakerController instance = new DbMakerController();

	public void process() {
		String system = MainClass.mainFrame.getSystemSelected();

		// Convert options choosen to DbMakerOption instance
		DbMakerOption option = panelToModel();

		// Save the model into a file
		option.save(system);

		// DbMaker
		DbMakerWorker worker = new DbMakerWorker(system, option, MainController.instance.getDbDetail(system));
		new BasicProgressDialog(worker);

		// Update detail
		DbDownLoaderController.instance.updateDetails();
	}

	/**
	 * Load preferences.
	 */
	public void load(String system) {

		// Load preference into DbMakerOption instance

		DbMakerOption option = DbMakerOption.load(system);

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
		option.moveNotUsedRoms = getOptionPanel().getRemoveNotUsedRoms().isSelected();

		return option;
	}

	/**
	 * Set panel components from a model instance.
	 * 
	 * @return
	 */
	private void modelToPanel(DbMakerOption option) {
		getOptionPanel().getNoClones().setSelected(option.noClone);
		getOptionPanel().getRemoveNotUsedRoms().setSelected(option.moveNotUsedRoms);
	}

}
