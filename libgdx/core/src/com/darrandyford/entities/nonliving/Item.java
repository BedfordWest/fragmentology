package com.darrandyford.entities.nonliving;

import com.darrandyford.assets.Assets;

public class Item extends NonlivingEntity {
	public Item(float positionX, float positionY, float dimensionX, float dimensionY,
	            Assets.AssetGenericNonLiving asset, String region) {
		super(positionX, positionY, dimensionX, dimensionY, asset, region);
		this.entityType = "item";
	}
}
