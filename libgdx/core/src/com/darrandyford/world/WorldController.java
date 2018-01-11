package com.darrandyford.world;


import com.darrandyford.entities.living.characters.Player;
import com.darrandyford.input.InputController;
import com.darrandyford.utils.CameraHelper;
import com.darrandyford.zones.Zone;

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
		player = new Player();
		cameraHelper = new CameraHelper();
		initLevel();
		initPhysics();
		inputController = new InputController(this);
		cameraHelper.setTarget(player);
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
	}

	/**
	 * Initialize the game level - what we consider a level to be is TBD
	 */
	private void initLevel() {
		this.zone = new Zone(this);
	}

	/**
	 * Initialize the physics system
	 */
	private void initPhysics() {
	}

	/**
	 * Update the physics world on each cycle
	 * @param deltaTime time passed in ms between cycles
	 */
	private void updatePhysics(float deltaTime) {
	}

	/**
	 * Only execute physics interactions at specified intervals. This method dictates what happens when physics
	 * interactions are to be executed.
	 * @param deltaTime time passed between cycles in ms
	 */
	private void doPhysicsStep(float deltaTime) {
		// fixed time step
		// max frame time to avoid spiral of death (on slow devices)
		double frameTime = Math.min(deltaTime, 0.25f);
		accumulator += frameTime;

	}

	// Getters
	public boolean getInitRenderState() { return this.initRenderState; }
	public CameraHelper getCameraHelper() { return this.cameraHelper; }
	public Zone getZone() {
		return this.zone;
	}
	public Player getPlayer() { return this.player; }

	// Setters
	public void setInitRenderState(boolean state) {
		this.initRenderState = state;
	}
}
