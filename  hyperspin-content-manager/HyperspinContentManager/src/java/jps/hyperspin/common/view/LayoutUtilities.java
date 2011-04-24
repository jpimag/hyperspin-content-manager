package jps.hyperspin.common.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public final class LayoutUtilities {

	public static GridBagLayout newLayout() {
		return new GridBagLayout();
	}

	/**
	 * 
	 * @param parent
	 * @param o
	 * @param positionX
	 * @param positionY
	 */
	public static GridBagConstraints newConstraint(int positionX, int positionY) {
		GridBagConstraints c = new GridBagConstraints();
		c.insets = defaultInsets();
		c.gridx = positionX;
		c.gridy = positionY;
		return c;
	}

	public static Insets defaultInsets() {
		return new Insets(0, 30, 0, 30);
	}

	public static void fixSize(Component c, Component ref) {
		c.setPreferredSize(ref.getPreferredSize());
		c.setMinimumSize(ref.getMinimumSize());
	}

	public static void fixSize(Component c, int x, int y) {
		c.setSize(new Dimension(x, y));
		c.setPreferredSize(c.getSize());
		c.setMinimumSize(c.getSize());
	}

	public static JSeparator newSeparator(GridBagConstraints c) {
		// Separator
		c.gridx = 0;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = defaultInsets();
		JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
		return sep;
	}

	public static Font h2() {
		return new Font(Font.SANS_SERIF, Font.BOLD, 24);
	}

}
