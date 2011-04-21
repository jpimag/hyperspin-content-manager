package jps.hyperspin.module.dbdownloader.presentation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.jps.hyperspin.common.properties.IniProperties;
import java.jps.hyperspin.exception.HCMDBadFileException;
import java.jps.hyperspin.exception.HCMDatabaseException;


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

		} catch (HCMDatabaseException e) {

		}

	}

	public String getSystemName() throws HCMDatabaseException {
		return inFileName;

	}

	public String getRomExtension() throws HCMDatabaseException {
		String romExtension = iniProp.getProperty("[exe info]", "romextension");
		if (romExtension == null) {
			throw new HCMDBadFileException(null,
					"romExtension not found in ini file");
		}
		// Workaround for MAME. With MAME we have to include sub dir as rom.
		if (inFileName.equals("MAME")) {
			romExtension = "";
		}
		return romExtension;

	}

	public String getRomPath() throws HCMDatabaseException {
		String romPath = iniProp.getProperty("[exe info]", "rompath");
		if (romPath == null) {
			throw new HCMDBadFileException(null, "romPath not found in ini file");
		}
		return romPath;

	}

}
