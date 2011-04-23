package jps.hyperspin.main.model;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 * The model of the systems.
 * 
 * @author jps
 * 
 */
public class Systems {

	public static Systems instance = new Systems();

	private Map<String, VersionStatut> elements = new TreeMap<String, VersionStatut>();

	public void put(String system, VersionStatut statut) {
		elements.put(system, statut);
	}

	public VersionStatut getStatut(String system) {
		return elements.get(system);
	}

	public boolean exist(String system) {
		return elements.containsKey(system);
	}

	public Collection<String> list() {
		return elements.keySet();
	}
}
