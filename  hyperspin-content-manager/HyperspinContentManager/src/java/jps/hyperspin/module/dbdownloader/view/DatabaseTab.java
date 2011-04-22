package jps.hyperspin.module.dbdownloader.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import jps.hyperspin.MainClass;
import jps.hyperspin.common.presentation.LayoutUtilities;

import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class DatabaseTab extends JPanel implements ListSelectionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private DatabaseDetailPanel formPanel = new DatabaseDetailPanel(this);

	private String lastSelection;

	public DatabaseTab() {
		super();
		init();
	}

	private void init() {

		GridBagConstraints c = LayoutUtilities.newConstraint(0, 0);
		this.setLayout(LayoutUtilities.newLayout());

		// Panel file selection
		c.fill = GridBagConstraints.NONE;
		c.gridx++;
		c.gridheight = 1;
		c.gridy = 0;
		c.insets = new Insets(30, 30, 30, 30);
		add(formPanel, c);

		this.setVisible(true);

		setBackground(Color.white);

	}

	/**
	 * 
	 * @return
	 */
	public IDatabaseDetail getDetail() {
		return formPanel;
	}

	public String getSystemSelected() {
		return (String) MainClass.mainFrame.getSystemSelected();
	}

	@Override
	public void valueChanged(ListSelectionEvent listselectionevent) {
		try {

			if (lastSelection != getSystemSelected()) {

				lastSelection = getSystemSelected();
				formPanel.updateFields();

			}

		} catch (Exception e) {
		}
	}
}
