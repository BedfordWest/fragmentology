package com.darrandyford.zones;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.darrandyford.assets.Assets;
import com.darrandyford.entities.living.LivingEntity;
import com.darrandyford.entities.living.characters.Enemy;
import com.darrandyford.entities.nonliving.NonlivingEntity;
import com.darrandyford.entities.terrain.TerrainTile;
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
	private static final int OBJECT_TOTAL = 3;
	private ArrayList<NonlivingEntity> walls = new ArrayList<NonlivingEntity>();
	private ArrayList<NonlivingEntity> objects = new ArrayList<NonlivingEntity>();
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
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
				TerrainTile ttile = new TerrainTile((x * Constants.TILE_WIDTH) -
					Constants.TILE_WIDTH/2,
					(y * Constants.TILE_HEIGHT) -
						Constants.TILE_HEIGHT/2, 2.0f, 2.0f, Assets.instance.ground);
				ttile.setCellPosition(x,y);
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
		createWalls();
		createEnemies(ENEMY_TOTAL);
		createObjects(OBJECT_TOTAL);
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
						(Assets.instance.ground.getRegions().get("dirtrocks")));
				} else if (tile.isSolid())
					cell.setTile(new StaticTiledMapTile
						(Assets.instance.wall.getRegions().get("wall")));
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

	private void createWalls() {
		for(int i = 0; i < Constants.ZONE_X_TILES; i++) {
			NonlivingEntity topWall = new NonlivingEntity(i * Constants.TILE_WIDTH + Constants.TILE_WIDTH/2,
				Constants.ZONE_Y_TILES * Constants.TILE_HEIGHT - Constants.TILE_HEIGHT/2,
				Constants.TILE_WIDTH, Constants.TILE_HEIGHT, Assets.instance.wall, "wall");
			NonlivingEntity bottomWall = new NonlivingEntity(i * Constants.TILE_WIDTH + Constants.TILE_WIDTH/2,
				Constants.TILE_HEIGHT/2, Constants.TILE_WIDTH, Constants.TILE_HEIGHT, Assets.instance.wall, "wall");
			walls.add(topWall);
			walls.add(bottomWall);
		}

		for(int i = 1; i < Constants.ZONE_Y_TILES - 1; i++) {
			NonlivingEntity rightWall =
				new NonlivingEntity(Constants.ZONE_X_TILES * Constants.TILE_WIDTH - Constants.TILE_WIDTH/2,
				i * Constants.TILE_HEIGHT + Constants.TILE_HEIGHT/2, Constants.TILE_WIDTH,
					Constants.TILE_HEIGHT, Assets.instance.wall, "wall");
			NonlivingEntity leftWall = new NonlivingEntity(Constants.TILE_WIDTH/2,
				i * Constants.TILE_HEIGHT + Constants.TILE_HEIGHT/2, Constants.TILE_WIDTH,
				Constants.TILE_HEIGHT, Assets.instance.wall, "wall");
			walls.add(rightWall);
			walls.add(leftWall);
		}

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
				for(NonlivingEntity wall:walls) {
					if(enemyRect.overlaps(wall.getBounds())) {
						i--;
						enemyOverlap = true;
						break;
					}
				}

				if(!enemyOverlap) {
					for (LivingEntity anotherEnemy : enemies) {
						if (enemyRect.overlaps(anotherEnemy.getBounds())) {
							i--;
							enemyOverlap = true;
							break;
						}
					}
				}
			}
			if(!enemyOverlap) {
				Enemy enemy = new Enemy(newX, newY, 1.0f, 1.0f, Assets.instance.enemy);
				enemies.add(enemy);
			}
		}
	}

	private void createObjects(int numObjects) {
		Random rand = new Random();
		for(int i = 0; i < numObjects; i++) {
			boolean overlap = false;
			float newX = rand.nextInt((Constants.ZONE_X_TILES * (int)Constants.TILE_WIDTH) -
				(int)Constants.TILE_WIDTH/2) + Constants.TILE_WIDTH/2;
			float newY = rand.nextInt((Constants.ZONE_Y_TILES * (int)Constants.TILE_HEIGHT) -
				(int)Constants.TILE_HEIGHT/2) + Constants.TILE_HEIGHT/2;
			Rectangle objectRect = new Rectangle(newX - Constants.TILE_WIDTH/2, newY - Constants.TILE_HEIGHT/2,
				2, 2);

			if(objectRect.overlaps(worldController.getPlayer().getBounds())) {
				i--;
				overlap = true;
			}
			else {
				for(NonlivingEntity wall:walls) {
					if(objectRect.overlaps(wall.getBounds())) {
						i--;
						overlap = true;
						break;
					}
				}

				if(!overlap) {
					for (LivingEntity anotherEnemy : enemies) {
						if (objectRect.overlaps(anotherEnemy.getBounds())) {
							i--;
							overlap = true;
							break;
						}
					}
				}

				if(!overlap) {
					for (NonlivingEntity anotherObject : objects) {
						if (objectRect.overlaps(anotherObject.getBounds())) {
							i--;
							overlap = true;
							break;
						}
					}
				}

			}
			if(!overlap) {
				NonlivingEntity object = new NonlivingEntity(newX, newY, 1.0f, 1.0f,
					Assets.instance.object, "object");
				objects.add(object);
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

	public ArrayList<NonlivingEntity> getWalls() {
		return walls;
	}

	public ArrayList<NonlivingEntity> getObjects() {
		return objects;
	}

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}
}
