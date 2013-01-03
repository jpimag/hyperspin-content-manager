package jps.hyperspin.module.dbmaker.model;

public enum DbMakerRegionEnum {

	NONE(null), EUROPE(DbMakerRegionTypeEnum.Region), FRANCE(DbMakerRegionTypeEnum.Country);

	public enum DbMakerRegionTypeEnum {
		Region, Country;
	}

	private DbMakerRegionTypeEnum type;

	private DbMakerRegionEnum(DbMakerRegionTypeEnum type) {
		this.type = type;
	}

}
