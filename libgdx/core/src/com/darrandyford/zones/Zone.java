package com.darrandyford.zones;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.darrandyford.entities.terrain.TerrainTile;
import com.darrandyford.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a zone the player is currently active within. A zone is a generic term for an area of the world
 * that is currently being shown.
 */
public class Zone {
	public static final String TAG = Zone.class.getName();
	private TiledMap map;
	private List<TerrainTile> zoneTiles = new ArrayList<TerrainTile>();

	public Zone () {
		Gdx.app.debug(TAG, "Initializing the zone");
		init();
	}

	/**
	 * Create the tile objects that the currently existing zone consists of.
	 */
	private void createTileObjects() {
		for (int x = 0; x < Constants.ZONE_X_TILES; x++) {
			for (int y = 0; y < Constants.ZONE_Y_TILES; y++) {
				TerrainTile ttile = new TerrainTile();
				ttile.setCellPosition(x,y);
				ttile.setPosition(
					(x * Constants.TILE_WIDTH/Constants.WORLD_SCALE) +
						ttile.getDimension().x/2,
					y * Constants.TILE_HEIGHT/Constants.WORLD_SCALE +
						ttile.getDimension().y/2
				);
				ttile.setSolid(false);
				zoneTiles.add(ttile);
			}
		}
	}

	/**
	 * Initialize the zone, splitting it out into necessary map layers.
	 */
	private void init () {
		this.createTileObjects();
		map = new TiledMap();
		map.getLayers().add(new TiledMapTileLayer(
			Constants.ZONE_X_TILES,
			Constants.ZONE_Y_TILES,
			Constants.TILE_WIDTH,
			Constants.TILE_HEIGHT));

		for ( TerrainTile tile : zoneTiles) {
			if (!tile.isSolid()) {
				}
				else {
					}
		}
		updateZoneState();
	}

	/**
	 * Update the zone state as necessary. This will later contain logic for player line-of-sight, etc.
	 */
	public void updateZoneState() {
		TiledMapTileLayer losLayer = (TiledMapTileLayer)map.getLayers().get(0);

		for (TerrainTile tile : zoneTiles) {

			if(true)
			{
				TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
				if (!tile.isSolid())
				{
				} else if (tile.isSolid())
				{
				}
				losLayer.setCell(
					tile.getCellXPosition(),
					tile.getCellYPosition(),
					cell
				);
			}
			else {
				losLayer.setCell(
					tile.getCellXPosition(),
					tile.getCellYPosition(),
					null
				);
			}
		}

	}

	// Getters
	public TiledMap getTiledMap() {
		return this.map;
	}
	public List<TerrainTile> getZoneTiles() { return this.zoneTiles; }
	public TerrainTile getTileAtPosition(Vector2 position) {
		int index;
		index = (int) (((position.x/Constants.TILE_WIDTH)
			* Constants.ZONE_Y_TILES) + position.y/Constants.TILE_HEIGHT);
		return zoneTiles.get(index);
	}

}
