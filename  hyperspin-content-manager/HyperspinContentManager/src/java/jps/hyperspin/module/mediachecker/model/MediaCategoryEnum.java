package jps.hyperspin.module.mediachecker.model;

public enum MediaCategoryEnum {

	WHEEL("Images\\Wheel", "png"),

	VIDEO("Video", "flv", "mp4"),

	ARTWORK1("Images\\Artwork1", "png"),

	ARTWORK2("Images\\Artwork2", "png"),

	ARTWORK3("Images\\Artwork3", "png"),

	ARTWORK4("Images\\Artwork4", "png"),

	THEME("Atari 2600\\Themes", "zip");

	/**
	 * 
	 */
	private String path;

	/**
	 * 
	 */
	private String[] extension;

	private MediaCategoryEnum(String path, String... extension) {
		this.path = path;
		this.extension = extension;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @return the extension
	 */
	public String[] getExtension() {
		return extension;
	}

}
