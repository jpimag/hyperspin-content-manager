package jps.hyperspin.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import jps.hyperspin.common.log.Logger;
import jps.hyperspin.common.presentation.LayoutUtilities;
import jps.hyperspin.common.presentation.PanelLogger;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;


/**
 * Main frame. Display the system list to the right and the tabs to the left .
 * 
 * @author JPS
 * 
 */
public class MainFrame extends JFrame implements ComponentListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private MainTabbedPane tabs = new MainTabbedPane();

	/**
	 * 
 	*/
	private JList systemList = new JList();

	/**
	 * 
	 */
	private PanelLogger loggerPanel = new PanelLogger();

	/**
	 * 
	 */
	public MainFrame() {
		init();
	}

	/**
	 * 
	 */
	private void init() {
		GridBagConstraints c = LayoutUtilities.newConstraint(0, 0);
		this.setLayout(LayoutUtilities.newLayout());
		this.setResizable(false);

		// Frame properties
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// System List
		JPanel systemPanel = new JPanel();
		systemPanel.setMinimumSize(new Dimension(350, 900));
		systemPanel.setPreferredSize(systemPanel.getMinimumSize());
		systemPanel.setMaximumSize(systemPanel.getMinimumSize());
		c.gridx = 0;
		c.fill = GridBagConstraints.NONE;
		c.weighty = 1.0;
		c.weightx = 1.0;
		c.gridheight = 10;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.LINE_START;
		this.add(systemPanel, c);

		systemPanel.setLayout(new BorderLayout());
		DefaultListModel listModel = new DefaultListModel();
		systemList.setModel(listModel);
		JLabel systemListLabel = new JLabel("System selection");
		systemListLabel.setFont(LayoutUtilities.h2());
		systemPanel.add(systemListLabel, BorderLayout.PAGE_START);
		systemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane listScroller = new JScrollPane(systemList);
		systemPanel.add(listScroller, BorderLayout.CENTER);
		systemList.addListSelectionListener(tabs.getDatabaseTab());

		// Right panel
		c.insets = new Insets(30, 30, 30, 30);
		c.gridy = 0;
		c.gridx = 2;
		c.weighty = 0;
		c.gridheight = 1;
		JPanel rightPanel = new JPanel();
		add(rightPanel, c);
		rightPanel.setLayout(LayoutUtilities.newLayout());

		// Tabs
		GridBagConstraints c2 = LayoutUtilities.newConstraint(0, 0);
		tabs.setMinimumSize(new Dimension(800, 400));
		tabs.setPreferredSize(tabs.getMinimumSize());
		tabs.setMaximumSize(tabs.getMinimumSize());
		rightPanel.add(tabs, c2);
		tabs.init();

		// Panel logger
		c2.gridy++;
		c2.insets = new Insets(50, 30, 50, 30);
		rightPanel.add(loggerPanel, c2);

		// Update list
		updateSystemList();

		this.pack();
		this.setVisible(true);
		this.addComponentListener(this);

	}

	public void componentHidden(ComponentEvent e) {
		this.repaint();
	}

	public void componentMoved(ComponentEvent e) {
		this.repaint();
	}

	public void componentResized(ComponentEvent e) {
		this.repaint();
	}

	public void componentShown(ComponentEvent e) {
		this.repaint();

	}

	/**
	 * 
	 */
	private void updateSystemList() {

		System.out.println("Hyper spin field  edited");
		((DefaultListModel) systemList.getModel()).removeAllElements();
		File file = new File(getHyperSpinPath());
		if (file.isDirectory()) {
			File settings = new File(getHyperSpinPath() + "/settings");
			if (settings.isDirectory()) {
				for (String s : settings.list()) {
					if (s.endsWith(".ini")) {
						String ini = s.substring(0, s.length() - 4);
						((DefaultListModel) systemList.getModel())
								.addElement(ini);
					}
				}
			}
		}

	}

	public String getSystemSelected() {
		return (String) systemList.getSelectedValue();
	}

	public String getHyperSpinPath() {
		return "S:/HyperSpin";
	}

	/**
	 * 
	 * @return
	 */
	public Logger getLogger() {
		return loggerPanel;
	}

}
