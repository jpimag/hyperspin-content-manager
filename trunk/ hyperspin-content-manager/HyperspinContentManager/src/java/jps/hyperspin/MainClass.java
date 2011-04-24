package jps.hyperspin;

import javax.swing.JOptionPane;

import jps.hyperspin.common.i18n.Message;
import jps.hyperspin.main.controller.MainController;
import jps.hyperspin.main.model.Systems;
import jps.hyperspin.main.view.MainFrame;

/**
 * 
 * @author JPS
 * 
 */
public final class MainClass {

	public static String HYPERSPIN_PATH = "S:/HyperSpin";

	/**
	 * 
	 */
	private MainClass() {
		// Nothing
	}

	public static MainFrame mainFrame;

	/**
	 * @param args
	 *            main args
	 */
	public static void main(final String[] args) {
		try {
			/*UIManager
					.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");*/
			// UIManager.setLookAndFeel(new InfoNodeLookAndFeel());
			// UIManager.setLookAndFeel(new NimbusLookAndFeel());
			// UIManager.setLookAndFeel(new PgsLookAndFeel());

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (args != null && args.length > 0) {
			HYPERSPIN_PATH = args[0];
		}
		mainFrame = new MainFrame();
		mainFrame.setVisible(true);
		// Update list
		int i = MainController.instance.computeSystems();
		if (i == 0) {
			// Is the hyperspin path correct ?
			if (Systems.instance.list().size() == 0) {
				JOptionPane.showMessageDialog(null,
						Message.getMessage("hyperspin_load_error"));
				mainFrame.dispose();
				System.exit(1);
			}

		}
	}

}
