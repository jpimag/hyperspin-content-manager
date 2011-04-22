package jps.hyperspin.module.dbdownloader.presentation;


/**
 * Options disponibles de l'onglet Database
 * 
 * @author jps
 * 
 */
public interface IDatabaseDetail {

	String getMediaRepository();

	String getUserDatabaseDir();

	String getIniSelected();

	SystemIniProperties getIniProp();
}
