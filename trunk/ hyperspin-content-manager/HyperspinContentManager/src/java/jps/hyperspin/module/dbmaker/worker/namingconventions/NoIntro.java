package jps.hyperspin.module.dbmaker.worker.namingconventions;

import jps.hyperspin.module.dbmaker.model.DbMakerRegionEnum;

/**
 * 
 * @author jps
 * 
 */
public class NoIntro extends AbstractNamingConvention {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isBelongingToType(String rom, DbMakerRegionEnum type) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isCandidate(String rom, String canditate, DbMakerRegionEnum type) {
		// TODO Auto-generated method stub
		return false;
	}

}
