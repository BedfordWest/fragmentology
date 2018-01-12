package com.darrandyford.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.darrandyford.entities.living.characters.Player;
import com.darrandyford.utils.Constants;

/**
 * Controls the physics system for the game.
 * This is where physics bodies will be linked between the entities and the Box2d World.
 */
public class PhysicsController {
	private World b2world;
	private WorldController worldController;
	private Player player;
	private static final String TAG = PhysicsController.class.getName();

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
		b2world = new World(new Vector2(0, Constants.NORMAL_GRAVITY), true);
		this.worldController = worldController;
		initPlayerPhysics();
	}

	/**
	 * Initialize player physics
	 */
	private void initPlayerPhysics() {
		player = worldController.getPlayer();
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.KinematicBody;
		bodyDef.position.set(player.getPosition());
		player.setBody(b2world.createBody(bodyDef));
		player.getBody().setUserData(player);
	}

	/**
	 * Update the physics world
	 * @param deltaTime time elapsed since last cycle (in ms)
	 */
	public void updatePhysics(float deltaTime) {
		updatePlayerPhysics(deltaTime);
	}

	/**
	 * Update player physics
	 * @param deltaTime time elapsed since last cycle (in ms)
	 */
	public void updatePlayerPhysics(float deltaTime) {
	}

	// Getters
	public World getB2World() { return this.b2world; }
}
