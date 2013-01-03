package jps.hyperspin.module.dbmaker.model;

import java.util.ArrayList;
import java.util.List;

import jps.hyperspin.module.dbdownloader.model.generated.menu.GameType;
import jps.hyperspin.module.dbdownloader.model.generated.menu.MenuType;

public class MenuTypeWrapper {

	private MenuType menu;

	private String fileName;
	private List<GameType> notFoundGame = new ArrayList<GameType>();

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

	/**
	 * @return the notFoundGame
	 */
	public List<GameType> getNotFoundGame() {
		return notFoundGame;
	}

}
