package com.darrandyford.world;


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
		initLevel();
		initPhysics();
	}

	/**
	 * Update the game world on each loop
	 * @param deltaTime the amount of time passed between loop cycles in ms
	 */
	public void update (float deltaTime) {
		updatePhysics(deltaTime);
		doPhysicsStep(deltaTime);
	}

	/**
	 * Initialize the game level - what we consider a level to be is TBD
	 */
	private void initLevel() {
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

	// Setters
	public void setInitRenderState(boolean state) {
		this.initRenderState = state;
	}
}
