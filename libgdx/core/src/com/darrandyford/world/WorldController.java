package com.darrandyford.world;

public class WorldController {

	// Set the TAG for logging purposes
	private static final String TAG = WorldController.class.getName();

	private float accumulator = 0;
	private boolean initRenderState = false;

	public WorldController () {
		init();
	}

	public void reset() {
		init();
	}

	// This init is used whenever the object needs to be reset without
	//   completely deleting and re-instantiating it.
	private void init () {
		initLevel();
		initPhysics();
	}

	public void update (float deltaTime) {
		updatePhysics(deltaTime);
		doPhysicsStep(deltaTime);
	}

	private void initLevel() {
	}

	private void initPhysics() {
	}

	private void updatePhysics(float deltaTime) {
	}

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
