package jps.hyperspin.common.presentation;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import jps.hyperspin.common.log.Logger;

/**
 * 
 * @author JPS
 * 
 */
public class PanelLogger extends JPanel implements Logger, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum Level {
		INFO("Info", 0), TRACE("Trace", 1), ERROR("Error", -1);

		private int index;
		private String name;

		private Level(String name, int index) {
			this.index = index;
			this.name = name;
		}

		/**
		 * @return the index
		 */
		public int getIndex() {
			return index;
		}

		/**
		 * @param index
		 *            the index to set
		 */
		public void setIndex(int index) {
			this.index = index;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name
		 *            the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

	}

	/**
	 * 
	 */
	private JComboBox levelBox;

	/**
	 *  
	 */
	private JButton clearButton;

	/**
	 * 
	 */
	private JTextArea textArea = new JTextArea();

	/**
	 * 	
	 */
	public PanelLogger() {

		setLayout(LayoutUtilities.newLayout());
		GridBagConstraints c = LayoutUtilities.newConstraint(0, 0);

		// Text area
		JLabel label = new JLabel("Logs");
		label.setFont(LayoutUtilities.h2());
		c.gridwidth = 2;
		add(label, c);

		JScrollPane scroll = new JScrollPane(textArea);
		scroll.setPreferredSize(new Dimension(800, 300));
		scroll.setMinimumSize(scroll.getPreferredSize());
		this.setSize(scroll.getSize());
		c.gridy++;
		c.gridwidth = 2;
		add(scroll, c);

		// Level
		c.gridy++;
		Level[] levels = { Level.INFO, Level.TRACE };
		c.weightx = 0;
		c.gridwidth = 1;

		c.anchor = GridBagConstraints.FIRST_LINE_END;
		levelBox = new JComboBox(levels);
		levelBox.setSelectedIndex(1);
		levelBox.addActionListener(this);
		add(levelBox, c);

		// Clear button
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		clearButton = new JButton("Clear");
		c.gridx++;
		c.gridheight = 1;
		add(clearButton, c);
		clearButton.addActionListener(this);

	}

	public final void clear() {
		textArea.setText("");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void info(final String message) {
		Level level = (Level) levelBox.getSelectedItem();
		if (level.getIndex() >= Level.INFO.getIndex()) {
			textArea.setText(textArea.getText().concat("\n" + message + "\n"));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void trace(final String message) {
		Level level = (Level) levelBox.getSelectedItem();
		if (level.getIndex() >= Level.TRACE.getIndex()) {
			textArea.setText(textArea.getText().concat("\n" + message + "\n"));
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void error(final String message) {
		Level level = (Level) levelBox.getSelectedItem();
		if (level.getIndex() >= Level.ERROR.getIndex()) {
			textArea.setText(textArea.getText().concat("\n" + message + "\n"));
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void actionPerformed(final ActionEvent e) {
		if (e.getSource() == clearButton) {
			clear();
		}
	}

}
