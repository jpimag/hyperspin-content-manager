package jps.hyperspin.module.database.presentation.main;

import jps.hyperspin.module.database.process.SystemIniProperties;

/**
 * Options disponibles de l'onglet Database
 * 
 * @author jps
 * 
 */
public interface IDatabaseDetail {

	String getMediaRepository();

	String getGeneratedDatabaseDir();

	String getIniSelected();

	SystemIniProperties getIniProp();
}
