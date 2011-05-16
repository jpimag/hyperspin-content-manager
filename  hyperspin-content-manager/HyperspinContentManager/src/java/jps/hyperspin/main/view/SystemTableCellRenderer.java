package jps.hyperspin.main.view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import jps.hyperspin.main.model.Systems;
import jps.hyperspin.main.model.VersionStatut;

public class SystemTableCellRenderer extends DefaultTableCellRenderer {

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
		VersionStatut status;
		try {
			status = VersionStatut.valueOf(value.toString());
		} catch (Exception e) {
			status = Systems.instance.getStatut(value.toString());
		}

		setText(value.toString());
		if (isSelected) {
			setBackground(Color.LIGHT_GRAY);
		} else if (status != null) {
			switch (status) {
			case SYSTEM_NOT_AVAILABLE:
			case SYSTEM_NOT_VERSIONNED:
				setBackground(Color.RED);
				setForeground(Color.WHITE);
				break;
			case OUT_DATED_USER_DB:
			case OUT_DATED_DOWNLOADED_DB:
				setBackground(Color.ORANGE);
				setForeground(Color.WHITE);
				break;
			case UP_TO_DATE:
				setBackground(new Color(51200)); // Green
				setForeground(Color.WHITE);
				break;
			}

		} else {
			setBackground(Color.WHITE);
			setForeground(Color.BLACK);
		}
		return c;
	}

}