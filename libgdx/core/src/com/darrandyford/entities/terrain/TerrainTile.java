package com.darrandyford.entities.terrain;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.darrandyford.assets.Assets;
import com.darrandyford.entities.AbstractGameEntity;
import com.darrandyford.utils.Constants;

/**
 * Represents a tile of terrain - floor, ground, wall, etc.
 */
public class TerrainTile extends AbstractGameEntity {
	private boolean isSolid;
	private Constants.TileType tileType;
	private int cellXPosition, cellYPosition;

	public TerrainTile (float positionX, float positionY, float dimensionX, float dimensionY,
	                    Assets.AssetGenericNonLiving asset) {
		super(positionX, positionY, dimensionX, dimensionY);
		isSolid = false;
		this.entityType = "terraintile";
	}

	public void update (float deltaTime) {
		origin.set(
			position.x - (dimension.x/2.0f),
			position.y - (dimension.y/2.0f)
		);
	}

	public void render (SpriteBatch batch) {}
	public void render () {}

	// Getters
	public boolean isSolid() { return isSolid; }
	public Constants.TileType getTileType() { return tileType; }
	public int getCellXPosition() { return cellXPosition; }
	public int getCellYPosition() { return cellYPosition; }

	// Setters
	public void setSolid(boolean solidState) { this.isSolid = solidState; }
	public void setTileType(Constants.TileType type) { this.tileType = type; }
	public void setCellPosition(int x, int y) {
		cellXPosition = x;
		cellYPosition = y;
	}
}
