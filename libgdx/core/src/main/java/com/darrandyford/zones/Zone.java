package main.java.com.darrandyford.zones;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import main.java.com.darrandyford.assets.Assets;
import main.java.com.darrandyford.entities.living.LivingEntity;
import main.java.com.darrandyford.entities.living.characters.Enemy;
import main.java.com.darrandyford.entities.nonliving.Item;
import main.java.com.darrandyford.entities.nonliving.Cover;
import main.java.com.darrandyford.entities.nonliving.NonlivingEntity;
import main.java.com.darrandyford.entities.terrain.TerrainTile;
import main.java.com.darrandyford.utils.Constants;
import main.java.com.darrandyford.world.WorldController;

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
	private static final int SOFT_COVER_TOTAL = 2;
	private static final int CHANCE_ROCK1_TILE = 1;
	private static final int CHANCE_ROCK2_TILE = 6;
	private static final int CHANCE_ROCK3_TILE = 1;
	private static final int CHANCE_ASSORTED_TILE = 2;
	private static final int CHANCE_SPECKS_TILE = 25;
	private ArrayList<NonlivingEntity> walls = new ArrayList<NonlivingEntity>();
	private ArrayList<Item> objects = new ArrayList<Item>();
	private ArrayList<Cover> coverObjects = new ArrayList<Cover>();
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

		TiledMapTileLayer losLayer = (TiledMapTileLayer)map.getLayers().get(0);

		for (TerrainTile tile : zoneTiles) {

			if(true)
			{
				TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
				if (!tile.isSolid())
				{
					Random rand = new Random();
					if(rand.nextInt(199) + 1 <= CHANCE_ROCK1_TILE) {
						cell.setTile(new StaticTiledMapTile
							(Assets.instance.ground.getRegions().get("dirtrocks")));
					}
					else if(rand.nextInt(199) + 1 <= CHANCE_ROCK2_TILE) {
						cell.setTile(new StaticTiledMapTile
							(Assets.instance.ground.getRegions().get("dirtrocks2")));
					}
					else if(rand.nextInt(199) + 1 <= CHANCE_ROCK3_TILE) {
						cell.setTile(new StaticTiledMapTile
							(Assets.instance.ground.getRegions().get("dirtrocks3")));
					}
					else if(rand.nextInt(99) + 1 <= CHANCE_ASSORTED_TILE) {
						cell.setTile(new StaticTiledMapTile
							(Assets.instance.ground.getRegions().get("assorted")));
					}
					else if(rand.nextInt(99) + 1 <= CHANCE_SPECKS_TILE) {
						cell.setTile(new StaticTiledMapTile
							(Assets.instance.ground.getRegions().get("specks")));
					}
					else {
						cell.setTile(new StaticTiledMapTile
							(Assets.instance.ground.getRegions().get("empty")));
					}
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
		createWalls();
		createEnemies(ENEMY_TOTAL);
		createObjects(OBJECT_TOTAL);
		createSoftCover(SOFT_COVER_TOTAL);
		updateZoneState();
	}

	/**
	 * Update the zone state as necessary. This will later contain logic for player line-of-sight, etc.
	 */
	public void updateZoneState() {
	}

	private boolean inRange(float numToCheck, Vector2 vec) {

		return false;
	}

	private void createWalls() {
		// Top left corner
		NonlivingEntity topLeftWall = new NonlivingEntity(Constants.TILE_WIDTH/2,
			Constants.ZONE_Y_TILES * Constants.TILE_HEIGHT - Constants.TILE_HEIGHT/2,
			Constants.TILE_WIDTH, Constants.TILE_HEIGHT, Assets.instance.wall, "cornerwall");
		// Top right corner
		NonlivingEntity topRightWall = new NonlivingEntity(Constants.ZONE_X_TILES * Constants.TILE_WIDTH - Constants.TILE_WIDTH/2,
			Constants.ZONE_Y_TILES * Constants.TILE_HEIGHT - Constants.TILE_HEIGHT/2,
			Constants.TILE_WIDTH, Constants.TILE_HEIGHT, Assets.instance.wall, "cornerwall");
		topRightWall.getSprite().rotate90(true);
		// Bottom left corner
		NonlivingEntity bottomLeftWall = new NonlivingEntity(Constants.TILE_WIDTH/2, Constants.TILE_HEIGHT/2, Constants.TILE_WIDTH,
			Constants.TILE_HEIGHT, Assets.instance.wall, "cornerwall");
		bottomLeftWall.getSprite().rotate(90);
		// Bottom right corner
		NonlivingEntity bottomRightWall = new NonlivingEntity(Constants.ZONE_X_TILES * Constants.TILE_WIDTH - Constants.TILE_WIDTH/2, Constants.TILE_HEIGHT/2, Constants.TILE_WIDTH,
			Constants.TILE_HEIGHT, Assets.instance.wall, "cornerwall");
		bottomRightWall.getSprite().rotate(180);

		walls.add(topLeftWall);
		walls.add(topRightWall);
		walls.add(bottomLeftWall);
		walls.add(bottomRightWall);


		for(int i = 1; i < Constants.ZONE_X_TILES - 1; i++) {
			NonlivingEntity topWall = new NonlivingEntity(i * Constants.TILE_WIDTH + Constants.TILE_WIDTH/2,
				Constants.ZONE_Y_TILES * Constants.TILE_HEIGHT - Constants.TILE_HEIGHT/2,
				Constants.TILE_WIDTH, Constants.TILE_HEIGHT, Assets.instance.wall, "wall");
			NonlivingEntity bottomWall = new NonlivingEntity(i * Constants.TILE_WIDTH + Constants.TILE_WIDTH/2,
				Constants.TILE_HEIGHT/2, Constants.TILE_WIDTH, Constants.TILE_HEIGHT, Assets.instance.wall, "wall");
			bottomWall.getSprite().flip(false, true);
			bottomWall.getSprite().setFlip(false, true);
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
			rightWall.getSprite().rotate90(true);
			leftWall.getSprite().rotate(90);
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
				enemy.setWorldController(worldController);
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
					for (Item anotherObject : objects) {
						if (objectRect.overlaps(anotherObject.getBounds())) {
							i--;
							overlap = true;
							break;
						}
					}
				}

			}
			if(!overlap) {
				Item object = new Item(newX, newY, 1.0f, 1.0f,
					Assets.instance.object, "stick");
				objects.add(object);
			}
		}
	}

	private void createSoftCover(int numCoverObjects) {
		Random rand = new Random();
		for(int i = 0; i < numCoverObjects; i++) {
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
					for (Item anotherObject : objects) {
						if (objectRect.overlaps(anotherObject.getBounds())) {
							i--;
							overlap = true;
							break;
						}
					}
				}

				if(!overlap) {
					for (Cover anotherCoverObject : coverObjects) {
						if (objectRect.overlaps(anotherCoverObject.getBounds())) {
							i--;
							overlap = true;
							break;
						}
					}
				}

			}
			if(!overlap) {
				Cover coverObject = new Cover(newX, newY, 2.0f, 2.0f,
					Assets.instance.object, "stickbundle");
				coverObjects.add(coverObject);
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

	public ArrayList<Item> getObjects() {
		return objects;
	}

	public ArrayList<Cover> getCoverObjects() {
		return coverObjects;
	}

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}
}
