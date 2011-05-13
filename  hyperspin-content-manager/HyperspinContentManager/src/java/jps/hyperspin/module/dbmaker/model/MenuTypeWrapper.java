package jps.hyperspin.module.dbmaker.model;

import jps.hyperspin.module.dbdownloader.model.generated.menu.MenuType;

public class MenuTypeWrapper {

	private MenuType menu;

	private String fileName;

	public MenuTypeWrapper(MenuType menu, String fileName) {
		super();
		this.menu = menu;
		this.fileName = fileName;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @return the menu
	 */
	public MenuType getMenu() {
		return menu;
	}

}
