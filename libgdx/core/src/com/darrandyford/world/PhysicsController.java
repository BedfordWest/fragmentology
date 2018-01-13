package com.darrandyford.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.darrandyford.entities.living.LivingEntity;
import com.darrandyford.entities.living.characters.Player;
import com.darrandyford.utils.Constants;

import java.util.ArrayList;

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
		initEnemyPhysics();
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
		PolygonShape polygonShape = new PolygonShape();
		polygonShape.setAsBox(
			player.getDimension().x / 2.0f,
			player.getDimension().y / 2.0f,
			player.getOrigin(),
			0
		);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = polygonShape;
		fixtureDef.restitution = 0f;
		player.getBody().createFixture(fixtureDef);
		player.getBody().setUserData(player);
		polygonShape.dispose();
	}

	/**
	 * Initialize enemy physics
	 */
	private void initEnemyPhysics() {
		ArrayList<LivingEntity> enemies = worldController.getZone().getEnemies();
		for(LivingEntity enemy:enemies) {
			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyDef.BodyType.KinematicBody;
			bodyDef.position.set(enemy.getPosition());
			enemy.setBody(b2world.createBody(bodyDef));
			PolygonShape polygonShape = new PolygonShape();
			polygonShape.setAsBox(
				enemy.getDimension().x / 2.0f,
				enemy.getDimension().y / 2.0f,
				enemy.getOrigin(),
				0
			);
			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.shape = polygonShape;
			fixtureDef.restitution = 0f;
			enemy.getBody().createFixture(fixtureDef);
			enemy.getBody().setUserData(enemy);
			polygonShape.dispose();
		}
	}

	/**
	 * Update the physics world
	 * @param deltaTime time elapsed since last cycle (in ms)
	 */
	public void updatePhysics(float deltaTime) {
		updatePlayerPhysics(deltaTime);
		updateEnemyPhysics(deltaTime);
	}

	/**
	 * Update player physics
	 * @param deltaTime time elapsed since last cycle (in ms)
	 */
	public void updatePlayerPhysics(float deltaTime) {
		Vector2 moveSpeed = player.getMoveSpeed();
		player.getBody()
			.setLinearVelocity(moveSpeed);
		if(!(moveSpeed.x == 0.0f && moveSpeed.y == 0.0f)) {
			player.setMoving(true);
		}
		else player.setMoving(false);
	}

	/**
	 * Update enemy physics
	 * @param deltaTime time elapsed since last cycle (in ms)
	 */
	public void updateEnemyPhysics(float deltaTime) {
		ArrayList<LivingEntity> enemies = worldController.getZone().getEnemies();
		for(LivingEntity enemy:enemies) {
			Vector2 moveSpeed = enemy.getMoveSpeed();
			enemy.getBody()
				.setLinearVelocity(moveSpeed);
			if (!(moveSpeed.x == 0.0f && moveSpeed.y == 0.0f)) {
				enemy.setMoving(true);
			} else enemy.setMoving(false);
		}
	}

	// Getters
	public World getB2World() { return this.b2world; }
}
