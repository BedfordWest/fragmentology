package com.darrandyford.zones;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.darrandyford.assets.Assets;
import com.darrandyford.entities.living.LivingEntity;
import com.darrandyford.entities.nonliving.terrain.TerrainTile;
import com.darrandyford.utils.Constants;
import com.darrandyford.world.WorldController;

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
	private static final int ENEMY_TOTAL = 4;
	private ArrayList<LivingEntity> enemies = new ArrayList<LivingEntity>();
	private WorldController worldController;

	public Zone (WorldController newWorldController) {
		Gdx.app.debug(TAG, "Initializing the zone");
		init(newWorldController);
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
					(x * Constants.TILE_WIDTH) -
						ttile.getDimension().x/2,
					(y * Constants.TILE_HEIGHT) -
						ttile.getDimension().y/2);
				ttile.setSolid(false);
				zoneTiles.add(ttile);
			}
		}
	}

	/**
	 * Initialize the zone, splitting it out into necessary map layers.
	 */
	private void init (WorldController newWorldController) {
		this.worldController = newWorldController;
		createTileObjects();
		map = new TiledMap();
		map.getLayers().add(new TiledMapTileLayer(
			Constants.ZONE_X_TILES,
			Constants.ZONE_Y_TILES,
			(int) Constants.TILE_WIDTH * (int) Constants.WORLD_SCALE,
			(int) Constants.TILE_HEIGHT * (int) Constants.WORLD_SCALE));

		for ( TerrainTile tile : zoneTiles) {
			if (!tile.isSolid()) {
				}
				else {
					}
		}
		createEnemies(ENEMY_TOTAL);
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
					cell.setTile(new StaticTiledMapTile
						(Assets.instance.ground.terrain));
				} else if (tile.isSolid())
					cell.setTile(new StaticTiledMapTile
						(Assets.instance.wall.wall));
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

	private boolean inRange(float numToCheck, Vector2 vec) {

		return false;
	}

	private void createEnemies(int numEnemies) {
		Random rand = new Random();
		for(int i = 0; i < numEnemies; i++) {
			boolean enemyOverlap = false;
			float newX = rand.nextInt((Constants.ZONE_X_TILES * (int)Constants.TILE_WIDTH) -
				(int)Constants.TILE_WIDTH/2) + Constants.TILE_WIDTH/2;
			float newY = rand.nextInt((Constants.ZONE_Y_TILES * (int)Constants.TILE_HEIGHT) -
				(int)Constants.TILE_HEIGHT/2) + Constants.TILE_HEIGHT/2;
			Rectangle enemyRect = new Rectangle(newX - Constants.TILE_WIDTH/2, newY - Constants.TILE_HEIGHT/2,
				2, 2);

			if(enemyRect.overlaps(worldController.getPlayer().getBounds())) {
				i--;
				enemyOverlap = true;
			}
			else {
				for(LivingEntity anotherEnemy:enemies) {
					if(enemyRect.overlaps(anotherEnemy.getBounds())) {
						i--;
						enemyOverlap = true;
						break;
					}
				}
			}
			if(!enemyOverlap) {
				LivingEntity enemy = new LivingEntity();
				enemy.setPosition(newX, newY);
				enemy.setSideSprite(Assets.instance.enemy.left);
				enemy.setFrontSprite(Assets.instance.enemy.down);
				enemy.setBackSprite(Assets.instance.enemy.up);
				enemies.add(enemy);
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

	public ArrayList<LivingEntity> getEnemies() {
		return enemies;
	}
}
