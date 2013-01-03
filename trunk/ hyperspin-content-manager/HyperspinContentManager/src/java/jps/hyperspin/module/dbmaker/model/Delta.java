package jps.hyperspin.module.dbmaker.model;


/**
 * 
 * @author jps
 * 
 */
public class Delta implements Comparable<Delta> {
	public String name;
	public String replacementName;

	public Delta(String name, String replacementName) {
		super();
		this.name = name;
		this.replacementName = replacementName;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(Delta o) {
		return this.name.compareTo(o.name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Delta other = (Delta) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
