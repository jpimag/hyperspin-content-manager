package jps.hyperspin.module.whdload.presentation;

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jps.hyperspin.exception.HCMDatabaseException;
import jps.hyperspin.module.dbdownloader.worker.SystemIniProperties;

/**
 * 
 * @author JPS
 * 
 */
public class WhdloadFormPanel extends JPanel {

	/**
	 * 
 	*/
	private JLabel hyperSpinLabel = new JLabel("HyperSpin path");

	/**
	 * 
 	*/
	private JTextField hyperSpinField = new JTextField();

	/**
	 * 
 	*/
	private JLabel iniFileLabel = new JLabel("ini file");

	/**
	 * 
 	*/
	private JComboBox iniFileCombo = new JComboBox();

	/**
	 *  
	 */
	private JLabel romsPathLabel = new JLabel("Roms path ");

	/**
	 *  
	 */
	private JTextField romsPathField = new JTextField();

	/**
	 *  
	 */
	private JLabel whdloadXmlLabel = new JLabel("WHDLoad.xml path ");

	/**
	 *  
	 */
	private JTextField whdloadXmlField = new JTextField();

	/**
	 *  
	 */
	private SystemIniProperties iniProp;

	/**
	 * 
	 */
	public WhdloadFormPanel() {

		// Layout
		this.setLayout(new GridLayout(10, 2));

		this.add(hyperSpinLabel);
		this.add(hyperSpinField);
		hyperSpinField.setText("S:/HyperSpin");

		this.add(iniFileLabel);
		this.add(iniFileCombo);
		iniFileCombo.addItem("Amiga");

		this.add(romsPathLabel);
		this.add(romsPathField);
		romsPathField.setEnabled(false);

		this.add(whdloadXmlLabel);
		this.add(whdloadXmlField);
		romsPathField.setEnabled(false);

		// Update Fields
		try {
			iniProp = new SystemIniProperties(this.getHyperSpinPath(),
					getIniFile());
			romsPathField.setText(getIniProp().getRomPath());
			whdloadXmlField.setText(getHyperSpinPath()
					+ "/Emulators/WinUAELoader/Data/WHDLoad.xml");

		} catch (HCMDatabaseException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final String getIniSelected() {
		return iniFileCombo.getSelectedItem().toString();
	}

	public final String getWhdLoadXml() {
		return whdloadXmlField.getText();
	}

	public final String getHyperSpinPath() {
		return hyperSpinField.getText();
	}

	public final String getIniFile() {
		return (String) iniFileCombo.getSelectedItem();
	}

	public final SystemIniProperties getIniProp() {
		return iniProp;
	}

}
