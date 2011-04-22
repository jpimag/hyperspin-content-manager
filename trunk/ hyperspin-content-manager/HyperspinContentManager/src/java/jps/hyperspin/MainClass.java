package jps.hyperspin;

import jps.hyperspin.main.controller.MainController;
import jps.hyperspin.main.view.MainFrame;

/**
 * 
 * @author JPS
 * 
 */
public final class MainClass {

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
		mainFrame = new MainFrame();
		// Update list
		MainController.instance.computeSystems();
	}

}
