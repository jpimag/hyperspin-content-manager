package jps.hyperspin.module.dbdownloader.presentation;

import jps.hyperspin.module.dbdownloader.process.SystemIniProperties;

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
