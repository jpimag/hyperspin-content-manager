package jps.hyperspin.module.dbmaker.presentation;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import jps.hyperspin.module.database.process.SystemIniProperties;

/**
 * 
 * @author JPS
 * 
 */
public class DatabaseOptionPanel extends JPanel implements ActionListener,
		IDatabaseOption {

	/**
	 *  
	 */
	private JCheckBox deepMatchBox = new JCheckBox("Deep match search");

	/**
	 *  
	 */
	private JCheckBox onlyUnused = new JCheckBox("Only unused search");

	/**
	 *  
	 */
	private JCheckBox regionMatching = new JCheckBox("region matching");

	/**
	 *  
	 */
	private SystemIniProperties iniProp;

	/**
	 * 
	 */
	public DatabaseOptionPanel() {

		// Layout
		this.setLayout(new GridLayout(0, 2));

		deepMatchBox.setSelected(false);
		this.add(deepMatchBox);

		onlyUnused.setSelected(false);
		this.add(onlyUnused);

		regionMatching.setSelected(false);
		this.add(regionMatching);

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final boolean isDeepMatchSelected() {
		return deepMatchBox.isSelected();
	}

	public final boolean isOnlyUnused() {
		return onlyUnused.isSelected();
	}

	public final boolean isRegionMatching() {
		return regionMatching.isSelected();
	}

	public final SystemIniProperties getIniProp() {
		return iniProp;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void actionPerformed(ActionEvent actionevent) {

	}

}
