package jps.hyperspin.module.dbmaker.presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.jps.hyperspin.module.dbdownloader.presentation.IDatabaseDetail;
import java.jps.hyperspin.module.dbmaker.presentation.DatabaseActionTab.Action;

import javax.swing.JPanel;


/**
 * Panel commun � toutes les categories d'action.
 * 
 * @author jps
 * 
 */
public abstract class CommonDatabaseActionPanel extends JPanel implements
		ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Tab parent
	 */
	private DatabaseActionTab databaseActionTab;

	/**
	 * 
	 */
	public CommonDatabaseActionPanel(DatabaseActionTab databaseActionTab) {
		super();
		this.databaseActionTab = databaseActionTab;

	}

	/**
	 * 
	 */
	@Override
	public abstract void actionPerformed(ActionEvent arg0);

	public IDatabaseOption getOption() {
		return databaseActionTab.getOption();
	}

	public IDatabaseDetail getDetail() {
		return databaseActionTab.getDetail();
	}

	public Action getAction() {
		return databaseActionTab.getSelectedAction();
	}
}
