package jps.hyperspin.main.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;

import jps.hyperspin.common.presentation.LayoutUtilities;
import jps.hyperspin.main.model.Systems;
import jps.hyperspin.main.model.VersionStatut;

/**
 * The JList of the systems available.
 * 
 * @author jps
 * 
 */
public class SystemListPanel extends JPanel {

	private JList list = new JList(new DefaultListModel());

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param arg0
	 */
	public SystemListPanel() {
		super();
		// Label
		setLayout(new BorderLayout());
		JLabel systemListLabel = new JLabel("System selection");
		systemListLabel.setFont(LayoutUtilities.h2());
		add(systemListLabel, BorderLayout.PAGE_START);

		// list
		list.setCellRenderer(new SystemListCellRenderer());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane listScroller = new JScrollPane(list);
		add(listScroller, BorderLayout.CENTER);

	}

	public void addElement(String system) {
		((DefaultListModel) list.getModel()).addElement(system);
	}

	public void removeAllElements() {
		((DefaultListModel) list.getModel()).removeAllElements();
	}

	public String getSelection() {
		return (String) list.getSelectedValue();
	}

	public void addListSelectionListener(ListSelectionListener listener) {
		list.addListSelectionListener(listener);
	}

	private class SystemListCellRenderer extends DefaultListCellRenderer {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			Component c = super.getListCellRendererComponent(list, value,
					index, isSelected, cellHasFocus);
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
}
