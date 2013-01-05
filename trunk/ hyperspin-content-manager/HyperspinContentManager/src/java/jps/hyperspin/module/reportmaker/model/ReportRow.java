package jps.hyperspin.module.reportmaker.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import jps.hyperspin.main.model.VersionStatut;
import jps.hyperspin.module.dbmaker.model.DbMakerOption;
import jps.hyperspin.module.dbmaker.processor.DbMakerProcessor.DbMakerResult;
import jps.hyperspin.module.mediachecker.model.MediaCategoryEnum;
import jps.hyperspin.module.mediachecker.processor.MediaCheckerProcessor.MediaCheckerResult;

public class ReportRow implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String system;
	public VersionStatut versionStatut;
	public String version;
	public DbMakerResult dbMakerResult;
	public DbMakerOption dbMakerOption;
	public Map<MediaCategoryEnum, MediaCheckerResult> mediaCheckerResults = new HashMap<MediaCategoryEnum, MediaCheckerResult>();

}