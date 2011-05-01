package jps.hyperspin.module.dbmaker.presentation;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JButton;

import jps.hyperspin.main.controller.CommonLogger;
import jps.hyperspin.main.controller.MainController;
import jps.hyperspin.module.dbmaker.worker.DatabaseProcessor;

/**
 * Panel des actions pour la categorie database
 * 
 * @author jps
 * 
 */
public class DatabaseActionPanel extends CommonDatabaseActionPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *  
	 */
	private JButton process = new JButton("Process");

	/**
	 * Constructeur.
	 */
	public DatabaseActionPanel(DatabaseActionTab databaseActionTab) {
		super(databaseActionTab);
		init();
	}

	private void init() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints c;

		// Process button
		c = new GridBagConstraints();
		c.insets = new Insets(10, 0, 0, 0);
		c.gridx = 0;
		c.gridy = 2;
		this.add(process, c);
		process.addActionListener(this);

		// Display frame

		this.setVisible(true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		DatabaseProcessor databaseProcessor = new DatabaseProcessor(MainController.instance.getDbDetail(), getOption());
		try {
			if (e.getSource() == process) {
				CommonLogger.instance.clear();

				switch (getAction()) {
				case DATABASE:
					databaseProcessor.processDatabase();
					break;

				}
			}
		} catch (Exception ex) {
			CommonLogger.instance.info(ex.toString());
		}
	}
}
