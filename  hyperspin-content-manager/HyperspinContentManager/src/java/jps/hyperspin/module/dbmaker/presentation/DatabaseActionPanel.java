package jps.hyperspin.module.dbmaker.presentation;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import jps.hyperspin.MainClass;
import jps.hyperspin.module.dbmaker.processor.DatabaseProcessor;

import javax.swing.JButton;


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

		DatabaseProcessor databaseProcessor = new DatabaseProcessor(
				getDetail(), getOption());
		try {
			if (e.getSource() == process) {
				MainClass.mainFrame.getLogger().clear();

				switch (getAction()) {
				case DATABASE:
					databaseProcessor.processDatabase(MainClass.mainFrame
							.getLogger());
					break;

				}
			}
		} catch (Exception ex) {
			MainClass.mainFrame.getLogger().info(ex.toString());
		}
	}
}
