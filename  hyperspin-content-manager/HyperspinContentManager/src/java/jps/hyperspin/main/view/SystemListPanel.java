package jps.hyperspin.main.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionListener;

import jps.hyperspin.common.i18n.Message;
import jps.hyperspin.main.model.Systems;
import jps.hyperspin.main.model.VersionStatut;

public class SystemListPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton checkButton = null;
	private JScrollPane jScrollPane = null;
	private JList list = null;
	private JLabel title = null;

	/**
	 * This method initializes
	 * 
	 */
	public SystemListPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.gridx = 0;
		gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints2.anchor = GridBagConstraints.CENTER;
		gridBagConstraints2.gridy = 1;
		title = new JLabel();
		title.setText(Message.getMessage("dbdownloader.system.selection.title"));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setHorizontalTextPosition(SwingConstants.CENTER);
		title.setBackground(new Color(188, 247, 191));
		title.setFont(new Font("Dialog", Font.BOLD, 14));
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.fill = GridBagConstraints.BOTH;
		gridBagConstraints1.gridy = 2;
		gridBagConstraints1.weightx = 1.0;
		gridBagConstraints1.weighty = 1.0;
		gridBagConstraints1.gridx = 0;
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		this.setLayout(new GridBagLayout());
		this.setSize(new Dimension(330, 630));

		this.add(getCheckButton(), gridBagConstraints);
		this.add(getJScrollPane(), gridBagConstraints1);
		this.add(title, gridBagConstraints2);
	}

	/**
	 * This method initializes checkButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getCheckButton() {
		if (checkButton == null) {
			checkButton = new JButton();
			checkButton.setText(Message
					.getMessage("dbdownloader.system.check.label"));
		}
		return checkButton;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getList());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes list
	 * 
	 * @return javax.swing.JList
	 */
	private JList getList() {
		if (list == null) {
			list = new JList();
			list.setModel(new DefaultListModel());
			list.setCellRenderer(new DefaultListCellRenderer() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public Component getListCellRendererComponent(JList list,
						Object value, int index, boolean isSelected,
						boolean cellHasFocus) {
					Component c = super.getListCellRendererComponent(list,
							value, index, isSelected, cellHasFocus);
					VersionStatut status = Systems.instance.getStatut(value
							.toString());
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
			});
		}

		return list;
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
} // @jve:decl-index=0:visual-constraint="10,10"
