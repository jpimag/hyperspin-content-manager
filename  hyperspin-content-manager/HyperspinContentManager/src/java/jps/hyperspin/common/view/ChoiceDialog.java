package jps.hyperspin.common.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class ChoiceDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Object[] selections;
	private JList list;
	private JButton buttonOk;
	private JButton buttonNoMatching;
	private JButton buttonRemove;

	private JButton buttonCancel;
	private JButton buttonFilter;
	private JTextField pattern;
	private boolean isCancelled = false;
	private boolean isOk = false;
	private boolean isRemoved = false;

	public ChoiceDialog(JFrame parent, String title, String message, Object[] selections) {

		super(parent, title, true);
		this.selections = selections;

		// Message
		JPanel messagePane = new JPanel();
		messagePane.add(new JLabel(message));
		getContentPane().add(messagePane, BorderLayout.NORTH);

		// Button;
		JPanel buttonPane = new JPanel();
		buttonOk = new JButton("OK");
		buttonPane.add(buttonOk);
		buttonOk.addActionListener(this);
		buttonNoMatching = new JButton("No Matching");
		buttonPane.add(buttonNoMatching);
		buttonNoMatching.addActionListener(this);
		buttonRemove = new JButton("Remove");
		buttonPane.add(buttonRemove);
		buttonRemove.addActionListener(this);
		buttonCancel = new JButton("Cancel");
		buttonPane.add(buttonCancel);
		buttonCancel.addActionListener(this);
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		// Options
		JPanel listPane = new JPanel();
		list = new JList(selections);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(500, 600));
		listPane.add(listScroller, BorderLayout.NORTH);
		getContentPane().add(listPane, BorderLayout.CENTER);
		list.setMinimumSize(new Dimension(200, 600));

		// filter
		JPanel filterPane = new JPanel();
		pattern = new JTextField(20);
		pattern.setText(".*");
		filterPane.add(pattern, BorderLayout.WEST);
		buttonFilter = new JButton("Filter");
		filterPane.add(buttonFilter, BorderLayout.EAST);
		buttonFilter.addActionListener(this);
		listPane.add(filterPane);

		// display
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setModal(true);
		setVisible(true);

	}

	public Object getSelection() {

		return list.getSelectedValue();
	}

	public boolean isCancelled() {
		return isCancelled;
	}

	public boolean isOk() {
		return isOk;
	}

	public boolean isRemove() {
		return isRemoved;
	}

	private void filter() {
		String p = pattern.getText();
		List<Object> array = new ArrayList<Object>();
		for (Object object : selections) {
			if (object.toString().toLowerCase().matches(p.toLowerCase())) {
				array.add(object);
			}
		}
		list.setListData(array.toArray());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttonFilter) {
			filter();
		} else {
			if (e.getSource() == buttonCancel) {
				isCancelled = true;
			} else if (e.getSource() == buttonOk) {
				isOk = true;
			} else if (e.getSource() == buttonRemove) {
				isRemoved = true;
			}

			setVisible(false);
			dispose();
		}
	}
}
