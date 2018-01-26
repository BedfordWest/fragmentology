package com.darrandyford.entities.nonliving;

import com.darrandyford.assets.Assets;

public class Cover extends NonlivingEntity {
	public Cover(float positionX, float positionY, float dimensionX, float dimensionY,
	             Assets.AssetGenericNonLiving asset, String region) {
		super(positionX, positionY, dimensionX, dimensionY, asset, region);
		this.entityType = "cover";
	}
}
