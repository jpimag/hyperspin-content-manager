package jps.hyperspin.common.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import jps.hyperspin.common.log.LoggerLevel;
import jps.hyperspin.main.controller.CommonLogger;

public class PanelLogger extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextArea jTextArea = null;
	private JButton clearButton = null;
	private JScrollPane jScrollPane = null; // @jve:decl-index=0:visual-constraint="636,39"

	/**
	 * This is the default constructor
	 */
	public PanelLogger() {
		super();
		initialize();
		CommonLogger.instance.setPanel(this);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.gridwidth = 1;
		gridBagConstraints1.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints1.weightx = 5.0;
		gridBagConstraints1.anchor = GridBagConstraints.EAST;
		gridBagConstraints1.gridy = 1;
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.gridx = 0;
		this.setSize(600, 400);
		this.setLayout(new GridBagLayout());
		this.add(getJScrollPane(), gridBagConstraints);
		this.add(getClearButton(), gridBagConstraints1);
	}

	/**
	 * This method initializes jTextArea
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
			jTextArea.setLineWrap(true);
		}
		return jTextArea;
	}

	/**
	 * This method initializes clearButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getClearButton() {
		if (clearButton == null) {
			clearButton = new JButton();
			clearButton.setText("Clear");
			clearButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("actionPerformed()"); // TODO
																// Auto-generated
																// Event stub
																// actionPerformed()
					jTextArea.setText("");
				}
			});
		}
		return clearButton;
	}

	public void append(String s, LoggerLevel level) {
		if (level.getIndex() >= level.getIndex()) {
			getJTextArea().setText(getJTextArea().getText().concat("\n" + s + "\n"));
		}
	}

	public void clear() {
		getJTextArea().setText("");
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTextArea());
		}
		return jScrollPane;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
