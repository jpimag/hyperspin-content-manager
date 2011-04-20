package jps.hyperspin.module.whdload.presentation;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import jps.hyperspin.common.presentation.PanelLogger;
import jps.hyperspin.module.whdload.process.WhdloadProcessor;

public class WhdloadTab extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *  
	 */
	private JButton missingFiles = new JButton("List Missing Files");

	/**
	 *  
	 */
	private JButton notuseFiles = new JButton("List Not Use Files");

	private WhdloadFormPanel formPanel = new WhdloadFormPanel();

	/**
	 * 
	 */
	private PanelLogger loggerPanel = new PanelLogger();

	public WhdloadTab() {
		super();
		init();
	}

	private void init() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints c;

		// Panel file selection
		c = new GridBagConstraints();
		c.insets = new Insets(10, 0, 0, 0);
		c.gridx = 0;
		c.gridy = 0;
		this.add(formPanel, c);

		// Process button
		c = new GridBagConstraints();
		c.insets = new Insets(10, 0, 0, 0);
		c.gridx = 0;
		c.gridy = 1;
		this.add(missingFiles, c);
		missingFiles.addActionListener(this);

		// Process button
		c = new GridBagConstraints();
		c.insets = new Insets(10, 0, 0, 0);
		c.gridx = 0;
		c.gridy = 2;
		this.add(notuseFiles, c);
		notuseFiles.addActionListener(this);

		// Panel logger
		c = new GridBagConstraints();
		c.insets = new Insets(10, 0, 0, 0);
		c.gridx = 0;
		c.gridy = 3;
		c.ipady = 0;
		this.add(loggerPanel, c);

		// Display frame

		this.setVisible(true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void actionPerformed(final ActionEvent e) {
		try {

			WhdloadProcessor processor = new WhdloadProcessor(formPanel
					.getIniProp());

			if (e.getSource() == notuseFiles) {
				loggerPanel.clear();
				processor.processNotUsedFiles(formPanel, loggerPanel);

			} else if (e.getSource() == missingFiles) {
				loggerPanel.clear();
				processor.processMissingFiles(formPanel, loggerPanel);
			}
		} catch (Exception ex) {
			loggerPanel.info(ex.toString());
		}

	}
}
