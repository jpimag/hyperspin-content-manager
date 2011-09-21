package jps.hyperspin.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jps.hyperspin.module.dbmaker.model.Delta;

public class DeltaFileUtilities {
	/**
	 * Write a delta file.
	 * 
	 * @param deltas
	 * @param writer
	 */
	public static void writeDeltaFile(List<Delta> deltas, FileWriter writer) throws IOException {
		for (Delta delta : deltas) {
			writer.write(delta.name + "->" + delta.replacementName + "\n");
		}
		writer.close();
	}

	/**
	 * Load a delta file
	 * 
	 * @param datas
	 * @param type
	 */
	public static Map<String, Delta> loadAllDeltaFileIndexedByReplacementName(String path) throws IOException {
		Map<String, Delta> result = new HashMap<String, Delta>();
		File dir = new File(path);
		if (dir.exists()) {
			for (File file : dir.listFiles()) {
				if (file.getName().endsWith("delta")) {
					result.putAll(loadDeltaFileIndexedByReplacementName(file.getAbsolutePath()));
				}
			}
		}
		return result;
	}

	/**
	 * Load a delta file
	 * 
	 * @param datas
	 * @param type
	 */
	public static Map<String, Delta> loadDeltaFileIndexedByReplacementName(String path) throws IOException {
		return loadDeltaFile(path, 1);
	}

	/**
	 * Load a delta file
	 * 
	 * @param datas
	 * @param type
	 */
	public static Map<String, Delta> loadDeltaFileIndexedByOriginalName(String path) throws IOException {
		return loadDeltaFile(path, 0);
	}

	private static Map<String, Delta> loadDeltaFile(String path, int index) throws IOException {
		if (path == null) {
			return null;
		}
		Map<String, Delta> deltas = new HashMap<String, Delta>();
		InputStream ips = new FileInputStream(path);
		InputStreamReader ipsr = new InputStreamReader(ips);
		BufferedReader br = new BufferedReader(ipsr);
		String ligne;
		while ((ligne = br.readLine()) != null) {
			String[] a = ligne.split("->");
			if (a != null && a.length == 2) {
				deltas.put(a[index], new Delta(a[0], a[1]));
			}
		}
		br.close();

		return deltas;
	}
}
