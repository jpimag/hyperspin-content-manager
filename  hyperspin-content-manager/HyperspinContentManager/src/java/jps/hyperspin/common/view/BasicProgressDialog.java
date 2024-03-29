package jps.hyperspin.common.view;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import jps.hyperspin.common.worker.CommonWorker;

/**
 * 
 * @author jps
 * 
 */
public class BasicProgressDialog extends JDialog implements
		PropertyChangeListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private JProgressBar progressBar;

	public BasicProgressDialog(CommonWorker worker) {
		super();

		// Dialog component
		progressBar = new JProgressBar(0, CommonWorker.MAX_PROGRESS_VALUE);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		progressBar.setString("In Progress");
		JPanel panel = new JPanel();
		panel.add(progressBar);
		add(panel, BorderLayout.PAGE_START);

		// Listener
		worker.addPropertyChangeListener(this);

		// Launch worker
		worker.execute();

		// Dialog preferences
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(getParent());
		setModal(true);
		pack();
		setVisible(true);

	}

	/**
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress" == evt.getPropertyName()) {
			int progress = (Integer) evt.getNewValue();
			progressBar.setValue(progress);

			if (progress == CommonWorker.MAX_PROGRESS_VALUE) {
				this.dispose();
			}
		}
	}

	/**
	 * @return the progressBar
	 */
	public JProgressBar getProgressBar() {
		return progressBar;
	}

}
