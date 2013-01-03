package jps.hyperspin.module.reportmaker.view;

import javax.swing.table.AbstractTableModel;

import jps.hyperspin.common.i18n.Message;
import jps.hyperspin.module.dbmaker.model.DbMakerOption;
import jps.hyperspin.module.mediachecker.model.MediaCategoryEnum;
import jps.hyperspin.module.mediachecker.processor.MediaCheckerProcessor.MediaCheckerResult;
import jps.hyperspin.module.reportmaker.model.Report;
import jps.hyperspin.module.reportmaker.model.ReportRow;

/**
 * 
 * @author jps
 * 
 */
public class ReportTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Report report;

	public ReportTableModel(Report report) {
		super();
		this.report = report;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getColumnCount() {
		return 15;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getRowCount() {
		return report.rows.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getColumnName(int col) {
		return Message.getMessage("reportmaker.table." + col + ".label");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getValueAt(int i, int j) {
		ReportRow row = report.rows.get(i);
		String result;
		switch (j) {
		case 0:
			result = row.system;
			break;
		case 1:
			result = row.version;
			break;
		case 2:
			result = row.versionStatut.name();
			break;
		case 3:
			result = getMessage(row.dbMakerOption);
			break;
		case 4:
			result = (row.dbMakerResult.dbSize - row.dbMakerResult.nbMissing) + "/" + row.dbMakerResult.dbSize + "roms";
			break;
		case 5:
			result = String.valueOf(row.dbMakerResult.nbMissing);
			break;
		case 6:
			result = row.dbMakerResult.nbReplaced + " replaced";
			break;
		case 7:
			result = row.dbMakerResult.nbClones + " clones";
			break;
		case 8:
			result = getMessage(row.mediaCheckerResults.get(MediaCategoryEnum.WHEEL));
			break;
		case 9:
			result = getMessage(row.mediaCheckerResults.get(MediaCategoryEnum.VIDEO));
			break;
		case 10:
			result = getMessage(row.mediaCheckerResults.get(MediaCategoryEnum.ARTWORK1));
			break;
		case 11:
			result = getMessage(row.mediaCheckerResults.get(MediaCategoryEnum.ARTWORK2));
			break;
		case 12:
			result = getMessage(row.mediaCheckerResults.get(MediaCategoryEnum.ARTWORK3));
			break;
		case 13:
			result = getMessage(row.mediaCheckerResults.get(MediaCategoryEnum.ARTWORK4));
			break;
		case 14:
			result = getMessage(row.mediaCheckerResults.get(MediaCategoryEnum.THEME));
			break;

		default:
			result = "";
			break;
		}
		return result;
	}

	private String getMessage(MediaCheckerResult result) {
		if (result.nbMediaFound == 0) {
			return "";
		} else {
			return String.valueOf(result.nbMediaMissing);
		}
	}

	private String getMessage(DbMakerOption option) {
		String result = "";
		if (option != null) {
			if (option.namingConventions != null) {
				result += option.namingConventions;
			}
			if (option.noClone) {
				result += " - no clones";
			}
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<?> getColumnClass(int i) {
		return String.class;
	}

}