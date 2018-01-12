package com.darrandyford.world;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.darrandyford.utils.Constants;

public class PhysicsController {
	private World b2world;
	private WorldController worldController;
	private static final String TAG = PhysicsController.class.getName();

	public PhysicsController(WorldController worldController) {
		init(worldController);
	}

	private void init(WorldController worldController) {
		if (b2world != null) { b2world.dispose(); }
		b2world = new World(new Vector2(0, Constants.NORMAL_GRAVITY), true);
		this.worldController = worldController;
	}

	public void updatePhysics(float deltaTime) {
	}

	// Getters
	public World getB2World() { return this.b2world; }
}
