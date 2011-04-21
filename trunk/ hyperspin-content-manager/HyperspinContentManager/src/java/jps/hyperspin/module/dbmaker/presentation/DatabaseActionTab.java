package jps.hyperspin.module.dbmaker.presentation;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import jps.hyperspin.MainClass;
import jps.hyperspin.common.presentation.LayoutUtilities;
import jps.hyperspin.module.dbdownloader.presentation.DatabaseTab;
import jps.hyperspin.module.dbdownloader.presentation.IDatabaseDetail;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;


public class DatabaseActionTab extends JPanel implements ActionListener {

	public enum Action {
		DATABASE, VIDEOS, WHEEL, ARTWORK1, ARTWORK2, ARTWORK3, ARTWORK4

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
 	*/
	private JComboBox actionBox = new JComboBox();

	/**
	 * 
	 */
	private CommonDatabaseActionPanel actionPanel;

	/**
	 * 
	 */
	private DatabaseOptionPanel formPanel = new DatabaseOptionPanel();

	/**
	 * 
	 */
	private DatabaseTab databaseTab;

	public DatabaseActionTab(DatabaseTab databaseTab) {
		super();
		this.databaseTab = databaseTab;
		init();
	}

	private void init() {
		this.setLayout(LayoutUtilities.newLayout());

		// Panel file selection
		GridBagConstraints c = LayoutUtilities.newConstraint(0, 0);
		add(formPanel, c);

		// Action choice list
		for (Action action : Action.values()) {
			actionBox.addItem(action);

		}
		actionBox.addActionListener(this);
		c = LayoutUtilities.newConstraint(0, 1);
		add(new JSeparator(SwingConstants.VERTICAL), c);

		c = LayoutUtilities.newConstraint(0, 2);
		add(actionBox, c);

		c = LayoutUtilities.newConstraint(0, 3);
		add(new JSeparator(SwingConstants.VERTICAL), c);

		// Process button
		c = LayoutUtilities.newConstraint(0, 4);
		actionPanel = new DatabaseActionPanel(this);
		add(actionPanel, c);

		// Display frame
		this.setVisible(true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void actionPerformed(final ActionEvent e) {

		if (e.getSource() == actionBox) {
			remove(actionPanel);
			if (getSelectedAction() == Action.DATABASE) {
				// database action selected
				actionPanel = new DatabaseActionPanel(this);
			} else {
				// media action selected
				actionPanel = new MediaActionPanel(this);
			}
			GridBagConstraints c = LayoutUtilities.newConstraint(0, 4);
			add(actionPanel, c);
			MainClass.mainFrame.pack();
			validate();
		}
	}

	/**
	 * 
	 * @return
	 */
	public Action getSelectedAction() {
		return (Action) actionBox.getSelectedItem();
	}

	/**
	 * 
	 * @return
	 */
	public IDatabaseOption getOption() {
		return getOption();
	}

	/**
	 * 
	 * @return
	 */
	public IDatabaseDetail getDetail() {
		return databaseTab.getDetail();
	}

}
