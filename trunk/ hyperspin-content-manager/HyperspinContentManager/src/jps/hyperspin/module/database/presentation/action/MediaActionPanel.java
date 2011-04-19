package jps.hyperspin.module.database.presentation.action;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JButton;

import jps.hyperspin.MainClass;
import jps.hyperspin.module.database.process.MediaProcessor;

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

		MediaProcessor mediaProcessor = new MediaProcessor(getDetail(),
				getOption());
		try {
			if (e.getSource() == process) {
				MainClass.mainFrame.getLogger().clear();

				switch (getAction()) {

				case ARTWORK1:
					mediaProcessor.processMedia("Images/Artwork1",
							MainClass.mainFrame.getLogger());
					break;
				case ARTWORK2:
					mediaProcessor.processMedia("Images/Artwork2",
							MainClass.mainFrame.getLogger());
					break;
				case ARTWORK3:
					mediaProcessor.processMedia("Images/Artwork3",
							MainClass.mainFrame.getLogger());
					break;
				case ARTWORK4:
					mediaProcessor.processMedia("Images/Artwork4",
							MainClass.mainFrame.getLogger());
					break;
				case VIDEOS:
					mediaProcessor.processMedia("Video",
							MainClass.mainFrame.getLogger());
					break;
				case WHEEL:
					mediaProcessor.processMedia("Images/Wheel",
							MainClass.mainFrame.getLogger());
					break;
				}

			} else if (e.getSource() == purge) {
				MainClass.mainFrame.getLogger().clear();
				switch (getAction()) {

				case ARTWORK1:
					mediaProcessor.purgeMedia("Images/Artwork1",
							MainClass.mainFrame.getLogger());
					break;
				case ARTWORK2:
					mediaProcessor.purgeMedia("Images/Artwork2",
							MainClass.mainFrame.getLogger());
					break;
				case ARTWORK3:
					mediaProcessor.purgeMedia("Images/Artwork3",
							MainClass.mainFrame.getLogger());
					break;
				case ARTWORK4:
					mediaProcessor.purgeMedia("Images/Artwork4",
							MainClass.mainFrame.getLogger());
					break;

				case VIDEOS:
					mediaProcessor.purgeMedia("Video",
							MainClass.mainFrame.getLogger());
					break;
				case WHEEL:
					mediaProcessor.purgeMedia("Images/Wheel",
							MainClass.mainFrame.getLogger());
					break;
				}
			}
		} catch (Exception ex) {
			MainClass.mainFrame.getLogger().info(ex.toString());
		}
	}
}
