package jps.hyperspin.module.reportmaker.view;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ReportTableCellRenderer extends DefaultTableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@Override
	public Component getTableCellRendererComponent(JTable jtable, Object value, boolean isSelected,
			boolean cellHasFocus, int row, int column) {
		Component c = super.getTableCellRendererComponent(jtable, value, isSelected, cellHasFocus, row, column);
		return c;
	}

}