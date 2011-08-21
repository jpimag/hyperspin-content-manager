package jps.hyperspin.module.reportmaker.controller;

import jps.hyperspin.common.view.BasicProgressDialog;
import jps.hyperspin.module.dbdownloader.controller.DbDownLoaderController;
import jps.hyperspin.module.reportmaker.worker.ReportMakerWorker;

public class ReportMakerController {

	public static ReportMakerController instance = new ReportMakerController();

	public void process() {

		ReportMakerWorker worker = new ReportMakerWorker();
		new BasicProgressDialog(worker);

		// Update detail
		DbDownLoaderController.instance.updateDetails();
	}

}
