package jps.hyperspin.module.dbmaker.presentation;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JButton;

import jps.hyperspin.main.controller.CommonLogger;
import jps.hyperspin.module.dbdownloader.controller.DbDownLoaderController;
import jps.hyperspin.module.dbmaker.worker.MediaProcessor;

/**
 * Panel des actions pour la categorie media
 * 
 * @author jps
 * 
 */
public class MediaActionPanel extends CommonDatabaseActionPanel {
	/**
	 *  
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *  
	 */
	private JButton process = new JButton("Process");

	/**
	 *  
	 */
	private JButton purge = new JButton("Purge");

	/**
	 * Constructeur.
	 */
	public MediaActionPanel(DatabaseActionTab databaseActionTab) {
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

		// Purge button
		c = new GridBagConstraints();
		c.insets = new Insets(10, 0, 0, 0);
		c.gridx = 0;
		c.gridy = 3;
		this.add(purge, c);
		purge.addActionListener(this);

		// Display frame

		this.setVisible(true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		MediaProcessor mediaProcessor = new MediaProcessor(DbDownLoaderController.instance.getDbDetail(), getOption());
		try {
			if (e.getSource() == process) {
				CommonLogger.instance.clear();

				switch (getAction()) {

				case ARTWORK1:
					mediaProcessor.processMedia("Images/Artwork1");
					break;
				case ARTWORK2:
					mediaProcessor.processMedia("Images/Artwork2");
					break;
				case ARTWORK3:
					mediaProcessor.processMedia("Images/Artwork3");
					break;
				case ARTWORK4:
					mediaProcessor.processMedia("Images/Artwork4");
					break;
				case VIDEOS:
					mediaProcessor.processMedia("Video");
					break;
				case WHEEL:
					mediaProcessor.processMedia("Images/Wheel");
					break;
				}

			} else if (e.getSource() == purge) {
				CommonLogger.instance.clear();
				switch (getAction()) {

				case ARTWORK1:
					mediaProcessor.purgeMedia("Images/Artwork1");
					break;
				case ARTWORK2:
					mediaProcessor.purgeMedia("Images/Artwork2");
					break;
				case ARTWORK3:
					mediaProcessor.purgeMedia("Images/Artwork3");
					break;
				case ARTWORK4:
					mediaProcessor.purgeMedia("Images/Artwork4");
					break;

				case VIDEOS:
					mediaProcessor.purgeMedia("Video");
					break;
				case WHEEL:
					mediaProcessor.purgeMedia("Images/Wheel");
					break;
				}
			}
		} catch (Exception ex) {
			CommonLogger.instance.info(ex.toString());
		}
	}
}
