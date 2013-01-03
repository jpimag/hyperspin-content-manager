package jps.hyperspin.module.reportmaker.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jps.hyperspin.common.model.CommonPeristable;

public class Report extends CommonPeristable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public List<ReportRow> rows = new ArrayList<ReportRow>();
	public Date date;

	public void save() {
		super.save("report");
	}

	/**
	 * load a CommonOption instance from disk. Create a new one if no instance
	 * are found on disk.
	 */
	public static Report load() {
		try {
			return (Report) CommonPeristable.load(Report.class, "report");
		} catch (Exception e2) {
			throw new IllegalArgumentException();
		}
	}
}