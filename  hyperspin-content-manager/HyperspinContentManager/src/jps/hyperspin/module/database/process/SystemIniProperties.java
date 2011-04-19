package jps.hyperspin.module.database.process;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import jps.hyperspin.exception.HSDBadFileException;
import jps.hyperspin.exception.HyperSpinDatabaseException;
import jps.hyperspin.process.properties.IniProperties;

public class SystemIniProperties {
	private IniProperties iniProp = new IniProperties();

	private String inFileName;

	public SystemIniProperties(String hyperSpinPath, String inFileName) {

		File iniFile = new File(hyperSpinPath + "/settings/" + inFileName
				+ ".ini");
		this.inFileName = inFileName;
		try {
			iniProp.load(new FileReader(iniFile));
		} catch (FileNotFoundException e) {

		} catch (HyperSpinDatabaseException e) {

		}

	}

	public String getSystemName() throws HyperSpinDatabaseException {
		return inFileName;

	}

	public String getRomExtension() throws HyperSpinDatabaseException {
		String romExtension = iniProp.getProperty("[exe info]", "romextension");
		if (romExtension == null) {
			throw new HSDBadFileException(null,
					"romExtension not found in ini file");
		}
		// Workaround for MAME. With MAME we have to include sub dir as rom.
		if (inFileName.equals("MAME")) {
			romExtension = "";
		}
		return romExtension;

	}

	public String getRomPath() throws HyperSpinDatabaseException {
		String romPath = iniProp.getProperty("[exe info]", "rompath");
		if (romPath == null) {
			throw new HSDBadFileException(null, "romPath not found in ini file");
		}
		return romPath;

	}

}
