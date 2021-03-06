package main.java.com.darrandyford.world;


import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import main.java.com.darrandyford.assets.Assets;
import main.java.com.darrandyford.entities.AbstractGameEntity;
import main.java.com.darrandyford.entities.living.characters.Enemy;
import main.java.com.darrandyford.entities.living.characters.Player;
import main.java.com.darrandyford.entities.nonliving.Item;
import main.java.com.darrandyford.input.InputController;
import main.java.com.darrandyford.utils.CameraHelper;
import main.java.com.darrandyford.utils.Constants;
import main.java.com.darrandyford.world.physics.PhysicsController;
import main.java.com.darrandyford.zones.Zone;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Controls the primary loop of the game world. This includes initialization, updates, and destruction.
 * The primary groupings of control are:
 * Camera
 * Renderer
 * Entities (player, enemies, maps, etc.)
 * Physics bodies and calculations
 */
public class WorldController {

	// Set the TAG for logging purposes
	private static final String TAG = WorldController.class.getName();

	private CameraHelper cameraHelper;
	private InputController inputController;
	private Zone zone;
	private Player player;
	private PhysicsController physicsController;
	private Array<Body> bodies = new Array<Body>();
	private ArrayList<AbstractGameEntity> scheduledForRemoval = new ArrayList<AbstractGameEntity>();

	private float accumulator = 0; // keeps track of physics accumulated time between steps
	private boolean initRenderState = false;

	public WorldController () {
		init();
	}

	/**
	 * Reset the world to the default/beginning state
	 */
	public void reset() {
		init();
	}

	/**
	 * 	This init is used whenever the object needs to be reset without
	 * 	completely deleting and re-instantiating it.
	 */
	private void init () {
		player = new Player((Constants.ZONE_X_TILES * Constants.TILE_WIDTH)/2,
			(Constants.ZONE_Y_TILES * Constants.TILE_HEIGHT)/2,
			.95f, 1.95f, Assets.instance.player);
		cameraHelper = new CameraHelper();
		initZone();
		initPhysics();
		inputController = new InputController(this);
		cameraHelper.setTarget(player);
		Assets.instance.playMusic("cave");
	}

	/**
	 * Update the game world on each loop
	 * @param deltaTime the amount of time passed between loop cycles in ms
	 */
	public void update (float deltaTime) {
		inputController.handleDebugInput(deltaTime);
		updatePhysics(deltaTime);
		doPhysicsStep(deltaTime);
		cameraHelper.update(deltaTime);
		removeScheduledEntities();
	}

	/**
	 * Initialize the game level - what we consider a level to be is TBD
	 */
	private void initZone() {
		this.zone = new Zone(this);
	}

	/**
	 * Initialize the physics system
	 */
	private void initPhysics() {
		this.physicsController = new PhysicsController(this);
	}

	/**
	 * Update the physics world on each cycle
	 * @param deltaTime time passed in ms between cycles
	 */
	private void updatePhysics(float deltaTime) {
		physicsController.updatePhysics(deltaTime);
	}

	/**
	 * Only execute physics interactions at specified intervals. This method dictates what happens when physics
	 * interactions are to be executed.
	 * @param deltaTime time passed between cycles in ms
	 */
	private void doPhysicsStep(float deltaTime) {
		// max frame time to avoid spiral of death (on slow devices)
		double frameTime = Math.min(deltaTime, 0.25f);
		accumulator += frameTime;

		this.getPhysicsController().getB2World().getBodies(bodies);

		while (accumulator >= Constants.TIME_STEP) {
			this.physicsController.getB2World().step(
				Constants.TIME_STEP,
				Constants.VELOCITY_ITERATIONS,
				Constants.POSITION_ITERATIONS
			);
			accumulator -= Constants.TIME_STEP;

			for (Body b : bodies) {
				// Get the body's user data - in this example, our user data
				//   is an instance of the Entity class
				if (b.getUserData() instanceof AbstractGameEntity) {
					AbstractGameEntity e = (AbstractGameEntity) b.getUserData();

					if (e != null) {
						e.update(Constants.TIME_STEP);
					}
				}
			}
		}

		for (Body b : bodies) {
			// Get the body's user data - in this example, our user data
			//   is an instance of the Entity class
			if (b.getUserData() instanceof AbstractGameEntity) {
				AbstractGameEntity e = (AbstractGameEntity) b.getUserData();

				if (e != null) {
					e.interpolate(accumulator / Constants.TIME_STEP);
				}
			}
		}
	}

	/**
	 * Handle the events that occur when a player is spotted by an enemy.
	 */
	public void playerSpotted(Enemy enemy) {
		if(!player.getCover()) {
			Assets.instance.playSound("alert");
			enemy.setAlertState(Enemy.AlertState.CHASING);
		}
	}

	public void playerOutOfLOS(Enemy enemy) {
		enemy.setAlertState(Enemy.AlertState.ALERT);
		enemy.setAlertCurrent(0.0f);
	}

	/**
	 * Handle the events that occur when a player acquires an item
	 */
	public void acquireItem(Item item) {
		Assets.instance.playSound("item");
		scheduledForRemoval.add(item);
	}

	private void removeScheduledEntities() {
		for(Iterator<AbstractGameEntity> it = scheduledForRemoval.iterator(); it.hasNext(); ) {
			AbstractGameEntity entity = it.next();
			getPhysicsController().getB2World().destroyBody(entity.getBody());
			getZone().getObjects().remove(entity);
			it.remove();
		}
	}

	public void dispose() {
		getPhysicsController().dispose();
	}


	// Getters
	public boolean getInitRenderState() { return this.initRenderState; }
	public CameraHelper getCameraHelper() { return this.cameraHelper; }
	public Zone getZone() {
		return this.zone;
	}
	public Player getPlayer() { return this.player; }
	public PhysicsController getPhysicsController() {
		return this.physicsController;
	}

	// Setters
	public void setInitRenderState(boolean state) {
		this.initRenderState = state;
	}
}
