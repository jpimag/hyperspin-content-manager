package jps.hyperspin.module.mediachecker.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class MediaCheckerTab extends JPanel {

	private static final long serialVersionUID = 1L;
	private MediaCheckerOptionPanel mediaCheckerOptionPanel = null;

	/**
	 * This is the default constructor
	 */
	public MediaCheckerTab() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		this.setSize(800, 400);
		this.setLayout(new GridBagLayout());
		this.setPreferredSize(new Dimension(800, 400));
		this.setBackground(Color.white);
		this.add(getMediaCheckerOptionPanel(), gridBagConstraints);
	}

	/**
	 * This method initializes mediaCheckerOptionPanel
	 * 
	 * @return jps.hyperspin.module.mediachecker.view.MediaCheckerOptionPanel
	 */
	public MediaCheckerOptionPanel getMediaCheckerOptionPanel() {
		if (mediaCheckerOptionPanel == null) {
			mediaCheckerOptionPanel = new MediaCheckerOptionPanel();
		}
		return mediaCheckerOptionPanel;
	}

}
