package jps.hyperspin.module.dbdownloader.presentation;


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
