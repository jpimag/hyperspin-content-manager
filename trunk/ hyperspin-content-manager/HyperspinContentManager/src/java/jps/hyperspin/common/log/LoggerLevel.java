package jps.hyperspin.common.log;

public enum LoggerLevel {
	INFO("Info", 0), TRACE("Trace", 1), ERROR("Error", -1);

	private int index;
	private String name;

	private LoggerLevel(String name, int index) {
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