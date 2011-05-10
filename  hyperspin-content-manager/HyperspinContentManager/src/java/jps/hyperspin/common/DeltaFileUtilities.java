package jps.hyperspin.common;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jps.hyperspin.module.dbmaker.worker.DeltaGeneratorWorker.Delta;

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
	public static Map<String, Delta> loadDeltaFile(String path) throws IOException {
		Map<String, Delta> deltas = new HashMap<String, Delta>();
		InputStream ips = new FileInputStream(path);
		InputStreamReader ipsr = new InputStreamReader(ips);
		BufferedReader br = new BufferedReader(ipsr);
		String ligne;
		while ((ligne = br.readLine()) != null) {
			String[] a = ligne.split("->");
			if (a != null && a.length == 2) {
				deltas.put(a[1], new Delta(a[0], a[1]));
			}
		}
		br.close();

		return deltas;
	}
}
