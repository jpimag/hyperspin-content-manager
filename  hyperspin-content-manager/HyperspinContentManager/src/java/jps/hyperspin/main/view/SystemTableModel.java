package jps.hyperspin.main.view;

import javax.swing.table.AbstractTableModel;

import jps.hyperspin.common.i18n.Message;
import jps.hyperspin.main.model.Systems;

/**
 * 
 * @author jps
 * 
 */
public class SystemTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getColumnCount() {
		return 2;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getRowCount() {
		return Systems.instance.list().size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getColumnName(int col) {
		return Message
				.getMessage("dbdownloader.system.table." + col + ".label");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getValueAt(int i, int j) {
		String system = Systems.instance.get(i);
		if (j == 0) {
			return system;
		} else {
			if (Systems.instance.getStatut(system) == null) {
				return "";
			}
			return Systems.instance.getStatut(system).toString();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<?> getColumnClass(int i) {
		return String.class;
	}

}