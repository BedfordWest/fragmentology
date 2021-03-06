package main.java.com.darrandyford.world.physics;

import box2dLight.ConeLight;
import box2dLight.Light;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import main.java.com.darrandyford.entities.living.LivingEntity;
import main.java.com.darrandyford.entities.living.characters.Enemy;
import main.java.com.darrandyford.entities.living.characters.Player;
import main.java.com.darrandyford.entities.nonliving.Item;
import main.java.com.darrandyford.entities.nonliving.Cover;
import main.java.com.darrandyford.entities.nonliving.NonlivingEntity;
import main.java.com.darrandyford.utils.Constants;
import main.java.com.darrandyford.world.WorldController;
import main.java.com.darrandyford.world.WorldListener;

import java.util.ArrayList;

/**
 * Controls the physics system for the game.
 * This is where physics bodies will be linked between the entities and the Box2d World.
 */
public class PhysicsController {
	private World b2world;
	private WorldController worldController;
	private Player player;
	private RayHandler rayHandler;
	private WorldListener worldListener;
	private static final String TAG = PhysicsController.class.getName();
	private ArrayList<PhysicsEntity> physicsEntities = new ArrayList<PhysicsEntity>();

	/**
	 * Constructor
	 * @param worldController handle to the world controller
	 */
	public PhysicsController(WorldController worldController) {
		init(worldController);
	}

	/**
	 * Initialize the physics world
	 * @param worldController handle to the world controller
	 */
	private void init(WorldController worldController) {
		if (b2world != null) { b2world.dispose(); }
		b2world = new World(new Vector2(0.0f, 0.0f), true);
		this.worldController = worldController;
		worldListener = new WorldListener(this.worldController);
		b2world.setContactListener(worldListener);
		initPlayerPhysics();
		initEnemyPhysics();
		initObjectPhysics();
		initLights();
		initWallPhysics();
		initCoverPhysics();
	}

	/**
	 * Initialize player physics
	 */
	private void initPlayerPhysics() {
		PlayerPhysicsEntity playerPhysicsEntity = new PlayerPhysicsEntity(this, worldController.getPlayer());
		playerPhysicsEntity.initPhysics();
	}

	/**
	 * Initialize enemy physics
	 */
	private void initEnemyPhysics() {
		ArrayList<Enemy> enemies = worldController.getZone().getEnemies();
		for(LivingEntity enemy:enemies) {
			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyDef.BodyType.DynamicBody;
			bodyDef.position.set(enemy.getPosition());
			enemy.setBody(b2world.createBody(bodyDef));
			PolygonShape polygonShape = new PolygonShape();
			polygonShape.setAsBox(
				enemy.getDimension().x / 2.0f,
				enemy.getDimension().y / 2.0f,
				new Vector2(0.0f,0.0f),
				0
			);
			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.shape = polygonShape;
			fixtureDef.restitution = 0f;
			fixtureDef.filter.categoryBits = Constants.CATEGORY_ENEMY;
			fixtureDef.filter.maskBits = Constants.MASK_ENEMY;
			enemy.getBody().createFixture(fixtureDef);
			enemy.getBody().setUserData(enemy);
			polygonShape.dispose();
		}
	}

	/**
	 * Initialize wall physics
	 */
	private void initWallPhysics() {
		ArrayList<NonlivingEntity> walls = worldController.getZone().getWalls();
		for(NonlivingEntity wall:walls) {
			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyDef.BodyType.StaticBody;
			bodyDef.position.set(wall.getOrigin());
			wall.setBody(b2world.createBody(bodyDef));
			PolygonShape polygonShape = new PolygonShape();
			polygonShape.setAsBox(
				wall.getDimension().x / 2.0f,
				wall.getDimension().y / 2.0f,
				new Vector2(0.0f,0.0f),
				0
			);
			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.shape = polygonShape;
			fixtureDef.restitution = 0f;
			fixtureDef.filter.categoryBits = Constants.CATEGORY_HARDOBJECT;
			fixtureDef.filter.maskBits = Constants.MASK_HARDOBJECT;
			wall.getBody().createFixture(fixtureDef);
			wall.getBody().setUserData(wall);
			polygonShape.dispose();
		}
	}

	/**
	 * Initialize cover physics
	 */
	private void initCoverPhysics() {
		ArrayList<Cover> coverObjects = worldController.getZone().getCoverObjects();
		for(Cover coverObject:coverObjects) {
			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyDef.BodyType.StaticBody;
			bodyDef.position.set(coverObject.getOrigin());
			coverObject.setBody(b2world.createBody(bodyDef));
			PolygonShape polygonShape = new PolygonShape();
			polygonShape.setAsBox(
				coverObject.getDimension().x / 2.0f,
				coverObject.getDimension().y / 2.0f,
				new Vector2(0.0f,0.0f),
				0
			);
			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.shape = polygonShape;
			fixtureDef.restitution = 0f;
			fixtureDef.filter.categoryBits = Constants.CATEGORY_SOFTCOVER;
			fixtureDef.filter.maskBits = Constants.MASK_SOFTCOVER;
			fixtureDef.isSensor = true;
			coverObject.getBody().createFixture(fixtureDef);
			coverObject.getBody().setUserData(coverObject);
			polygonShape.dispose();
		}
	}

	/**
	 * Initialize object physics
	 */
	private void initObjectPhysics() {
		ArrayList<Item> objects = worldController.getZone().getObjects();
		for (Item object : objects) {
			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyDef.BodyType.DynamicBody;
			bodyDef.position.set(object.getPosition());
			object.setBody(b2world.createBody(bodyDef));
			PolygonShape polygonShape = new PolygonShape();
			polygonShape.setAsBox(
					object.getDimension().x / 2.0f,
					object.getDimension().y / 2.0f,
					new Vector2(0.0f, 0.0f),
					0
			);
			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.shape = polygonShape;
			fixtureDef.restitution = 0f;
			fixtureDef.filter.categoryBits = Constants.CATEGORY_ITEM;
			fixtureDef.filter.maskBits = Constants.MASK_ITEM;
			object.getBody().createFixture(fixtureDef);
			object.getBody().setUserData(object);
			polygonShape.dispose();
		}
	}

	/**
	 * Initialize the LOS lights
	 */
	private void initLights() {
		rayHandler = new RayHandler(b2world);
		rayHandler.useDiffuseLight(true);
		rayHandler.setAmbientLight(0.7f, 0.7f, 0.7f, 1f);
		rayHandler.setBlurNum(3);
		rayHandler.setShadows(true);

		ArrayList<Enemy> enemies = worldController.getZone().getEnemies();
		for(Enemy enemy:enemies) {

			for(int i = 0; i < enemy.getNumConelights(); i++) {
				enemy.addConelight(new ConeLight(rayHandler, 3,
					new Color(1.0f, 0.03f, 0.3f, 1.0f),
					12.0f, enemy.getPosition().x, enemy.getPosition().y,
					enemy.getDirection(), 20.0f));
				enemy.getConelights().get(i).setContactFilter(Constants.CATEGORY_ENEMY_LIGHT, (short) 0,
					Constants.MASK_ENEMY_LIGHT);
				if(!enemy.coneLightBodySet()) {
					BodyDef bodyDef = new BodyDef();
					bodyDef.type = BodyDef.BodyType.DynamicBody;
					bodyDef.position.set(enemy.getPosition());
					enemy.setLightBody(b2world.createBody(bodyDef));
					PolygonShape coneLightBody = new PolygonShape();
					float coneWidth = 20.0f * MathUtils.degRad;
					Vector2 leftVecRaw = new Vector2((float)Math.cos(-coneWidth),
						(float)Math.sin(-coneWidth));
					Vector2 rightVecRaw = new Vector2((float)Math.cos(coneWidth),
						(float)Math.sin(coneWidth));
					Vector2 leftVec = new Vector2(leftVecRaw.nor());
					Vector2 rightVec = new Vector2(rightVecRaw.nor());
					Vector2 enemyPosLeft = new Vector2((leftVec.scl(10.f)));
					Vector2 enemyPosRight = new Vector2(rightVec.scl(10.f));
					Vector2[] vertices = {new Vector2(0.0f, 0.0f),
						enemyPosLeft, enemyPosRight};
					coneLightBody.set(vertices);
					FixtureDef fixtureDef = new FixtureDef();
					fixtureDef.isSensor = true;
					fixtureDef.shape = coneLightBody;
					fixtureDef.filter.categoryBits = Constants.CATEGORY_ENEMY_LIGHT;
					fixtureDef.filter.maskBits = Constants.MASK_ENEMY_LIGHT;
					fixtureDef.restitution = 0f;
					enemy.getLightBody().createFixture(fixtureDef);
					enemy.getLightBody().setUserData(enemy.getEnemyLight());
					enemy.getLightBody().setSleepingAllowed(false);
					coneLightBody.dispose();
					enemy.setConeLightBodySetState(true);
				}
			}
		}

	}

	/**
	 * Update the physics world
	 * @param deltaTime time elapsed since last cycle (in ms)
	 */
	public void updatePhysics(float deltaTime) {
		for(PhysicsEntity physicsEntity:physicsEntities) {
			physicsEntity.updatePhysics(deltaTime);
		}
		updateEnemyPhysics(deltaTime);
	}


	/**
	 * Update enemy physics
	 * @param deltaTime time elapsed since last cycle (in ms)
	 */
	public void updateEnemyPhysics(float deltaTime) {
		boolean playerSpotted = false;
		ArrayList<Enemy> enemies = worldController.getZone().getEnemies();
		for(Enemy enemy:enemies) {
			Vector2 moveSpeed = enemy.getMoveSpeed();
			enemy.getBody()
				.setLinearVelocity(moveSpeed);
			enemy.getLightBody().setTransform(enemy.getPosition(), (enemy.getDirection()) * MathUtils.degRad);
			if (!(moveSpeed.x == 0.0f && moveSpeed.y == 0.0f)) {
				enemy.setMoving(true);
			} else enemy.setMoving(false);
			for(ConeLight conelight:enemy.getConelights()) {
				conelight.setDirection(enemy.getDirection());
				conelight.setPosition(enemy.getPosition());
				switch(enemy.getAlertState()) {
					case PATROLLING:
						conelight.setColor(255.0f/255.0f, 224.0f/255.0f, 58.0f/255.0f, 1.0f);
						break;
					case ALERT:
					case CHASING:
						conelight.setColor(1.0f, 0.03f, 0.3f, 1.0f);
						break;
				}
				conelight.update();
			}
		}
	}

	/**
	 * Check if the player is in a given light source
	 */
	private boolean playerInLight(Light light) {
		ArrayList<Vector2> contactPoints = player.getContactPoints();
		for(Vector2 point:contactPoints) {
			if(light.contains(point.x, point.y)) return true;
		}
		return false;
	}


	public void dispose() {
		rayHandler.dispose();
	}

	// Getters
	public World getB2World() { return this.b2world; }

	public RayHandler getRayHandler() {
		return rayHandler;
	}


	// Setters
	public void addPhysicsEntity(PhysicsEntity entity) {
		this.physicsEntities.add(entity);
	}
}
