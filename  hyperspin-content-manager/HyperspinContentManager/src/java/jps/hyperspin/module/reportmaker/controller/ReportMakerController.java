package jps.hyperspin.module.reportmaker.controller;

import jps.hyperspin.MainClass;
import jps.hyperspin.common.view.BasicProgressDialog;
import jps.hyperspin.module.reportmaker.view.ReportMakerTab;
import jps.hyperspin.module.reportmaker.worker.ReportMakerWorker;

public class ReportMakerController {

	public static ReportMakerController instance = new ReportMakerController();

	public void process() {

		final ReportMakerTab tab = MainClass.mainFrame.getMainTabbedPane().getReportMakerTab();

		ReportMakerWorker worker = new ReportMakerWorker() {
			@Override
			public void done() {
				tab.displayReport();
				super.done();
			}
		};
		new BasicProgressDialog(worker);

	}

}
