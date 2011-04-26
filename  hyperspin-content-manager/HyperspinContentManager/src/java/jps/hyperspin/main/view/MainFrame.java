package jps.hyperspin.main.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.SystemColor;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import jps.hyperspin.common.view.PanelLogger;
import jps.hyperspin.module.dbdownloader.controller.DbDownLoaderController;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel jPanel = null; // @jve:decl-index=0:visual-constraint="10,10"
	private SystemTablePanel systemListPanel = null;
	private PanelLogger panelLogger = null;
	private MainTabbedPane mainTabbedPane = null;

	/**
	 * This method initializes
	 * 
	 */
	public MainFrame() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(1266, 950));

		this.setContentPane(getJPanel());
		systemListPanel.addListSelectionListener(new ListSelectionListener() {
			private String lastSelection;

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				try {

					if (lastSelection != getSystemSelected()) {

						lastSelection = getSystemSelected();
						DbDownLoaderController.instance.updateDetails();

					}

				} catch (Exception e) {
				}
			}
		});
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints4.gridy = 0;
			gridBagConstraints4.weightx = 15.0;
			gridBagConstraints4.weighty = 0.0;
			gridBagConstraints4.anchor = GridBagConstraints.WEST;
			gridBagConstraints4.gridx = 1;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 1;
			gridBagConstraints3.weightx = 0.0;
			gridBagConstraints3.fill = GridBagConstraints.BOTH;
			gridBagConstraints3.weighty = 0.5;
			gridBagConstraints3.gridy = 1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridheight = 3;
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints.weightx = 0.5;
			gridBagConstraints.weighty = 0.0;
			gridBagConstraints.gridy = 0;
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.setSize(new Dimension(898, 559));
			jPanel.add(getSystemListPanel(), gridBagConstraints);
			jPanel.add(getPanelLogger(), gridBagConstraints3);
			jPanel.add(getMainTabbedPane(), gridBagConstraints4);
		}
		return jPanel;
	}

	/**
	 * This method initializes systemListPanel
	 * 
	 * @return jps.hyperspin.main.view.SystemListPanel
	 */
	public SystemTablePanel getSystemListPanel() {
		if (systemListPanel == null) {
			systemListPanel = new SystemTablePanel();

			systemListPanel.setBorder(BorderFactory.createLineBorder(
					SystemColor.activeCaption, 5));
		}
		return systemListPanel;
	}

	/**
	 * This method initializes panelLogger
	 * 
	 * @return jps.hyperspin.common.presentation.PanelLogger
	 */
	private PanelLogger getPanelLogger() {
		if (panelLogger == null) {
			panelLogger = new PanelLogger();
			panelLogger.setBorder(BorderFactory.createLineBorder(
					SystemColor.activeCaption, 5));
		}
		return panelLogger;
	}

	/**
	 * This method initializes mainTabbedPane
	 * 
	 * @return jps.hyperspin.main.view.MainTabbedPane
	 */
	public MainTabbedPane getMainTabbedPane() {
		if (mainTabbedPane == null) {
			mainTabbedPane = new MainTabbedPane();
		}
		return mainTabbedPane;
	}

	public String getSystemSelected() {
		return systemListPanel.getSelection();
	}

} // @jve:decl-index=0:visual-constraint="10,10"
