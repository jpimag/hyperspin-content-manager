package jps.hyperspin.main.view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import jps.hyperspin.main.model.Systems;
import jps.hyperspin.main.model.VersionStatut;

public class SystemListCellRenderer extends DefaultListCellRenderer {

	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		Component c = super.getListCellRendererComponent(list, value, index,
				isSelected, cellHasFocus);
		VersionStatut status = Systems.instance.getStatut(value.toString());
		setText(value.toString());
		if (isSelected) {
			setBackground(Color.BLUE);
			setForeground(Color.WHITE);
		} else if (status != null) {
			switch (status) {
			case SYSTEM_NOT_AVAILABLE:
			case SYSTEM_NOT_VERSIONNED:
			case OUT_DATED_DOWNLOADED_DB:
				setBackground(Color.RED);
				break;
			case OUT_DATED_USER_DB:
				setBackground(Color.ORANGE);
				break;
			case UP_TO_DATE:
				setBackground(Color.GREEN);
				break;
			}
			setForeground(Color.WHITE);
		} else {
			setBackground(Color.WHITE);
		}
		return c;
	}

}