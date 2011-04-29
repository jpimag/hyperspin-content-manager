package jps.hyperspin.module.dbmaker.worker;

import jps.hyperspin.module.dbdownloader.model.generated.menu.GameType;
import jps.hyperspin.module.dbdownloader.model.generated.menu.MenuType;

import java.util.ArrayList;
import java.util.List;


public class MenuTypeWrapper {

	private MenuType menu;

	private String fileName;

	private List<GameType> notFoundGame = new ArrayList<GameType>();

	private List<GameType> rejectedGame = new ArrayList<GameType>();

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

	/**
	 * @param notFoundGame
	 *            the notFoundGame to set
	 */
	public void setNotFoundGame(List<GameType> notFoundGame) {
		this.notFoundGame = notFoundGame;
	}

	/**
	 * @return the rejectedGame
	 */
	public List<GameType> getRejectedGame() {
		if (rejectedGame == null) {
			rejectedGame = new ArrayList<GameType>();
		}
		return rejectedGame;
	}

	/**
	 * @param rejectedGame
	 *            the rejectedGame to set
	 */
	public void setRejectedGame(List<GameType> rejectedGame) {
		this.rejectedGame = rejectedGame;
	}

}
