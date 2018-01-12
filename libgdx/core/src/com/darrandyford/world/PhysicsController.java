package com.darrandyford.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.darrandyford.entities.living.characters.Player;
import com.darrandyford.utils.Constants;

public class PhysicsController {
	private World b2world;
	private WorldController worldController;
	private Player player;
	private static final String TAG = PhysicsController.class.getName();

	public PhysicsController(WorldController worldController) {
		init(worldController);
	}

	private void init(WorldController worldController) {
		if (b2world != null) { b2world.dispose(); }
		b2world = new World(new Vector2(0, Constants.NORMAL_GRAVITY), true);
		this.worldController = worldController;
		initPlayerPhysics();
	}

	// Initialize the player's physics
	private void initPlayerPhysics() {
		player = worldController.getPlayer();
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.KinematicBody;
		bodyDef.position.set(player.getPosition());
		player.setBody(b2world.createBody(bodyDef));
		player.getBody().setUserData(player);
	}

	public void updatePhysics(float deltaTime) {
		updatePlayerPhysics(deltaTime);
	}

	// Update the player's physics based on any external influences
	public void updatePlayerPhysics(float deltaTime) {
	}

	// Getters
	public World getB2World() { return this.b2world; }
}
