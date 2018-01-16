package com.darrandyford.entities.living.characters;

import box2dLight.ConeLight;
import com.badlogic.gdx.math.Vector2;
import com.darrandyford.assets.Assets;
import com.darrandyford.entities.living.LivingEntity;
import com.darrandyford.utils.Constants;

import java.util.ArrayList;
import java.util.Random;

public class Enemy extends LivingEntity {
	private ArrayList<ConeLight> coneLights;
	private int numConelights = 4;
	private enum AlertState { STATIONARY, PATROLLING, ALERT, CHASING }
	private AlertState alertState;
	private float patrolDuration, patrolCurrent;

	// Set the TAG for logging purposes
	private static final String TAG = Enemy.class.getName();

	public Enemy(float positionX, float positionY, float dimensionX, float dimensionY,
	                    Assets.AssetGenericLiving asset) {
		super(positionX, positionY, dimensionX, dimensionY, asset);
		init();
	}

	/**
	 * Initialize the player. Should be facing right by default. The origin should be in the middle of the player.
	 */
	private void init () {
		coneLights = new ArrayList<ConeLight>();
		alertState = AlertState.PATROLLING;
		patrolDuration = 3.0f;
		patrolCurrent = 3.0f;
		moveRate = 2.0f;
	}

	public void update(float deltaTime) {
		switch (alertState) {
			case ALERT:
				break;
			case STATIONARY:
				break;
			case PATROLLING:
				patrolCurrent += deltaTime;
				if (patrolCurrent >= patrolDuration)
				{
					patrolCurrent %= patrolDuration;
					executePatrol();
				}
				break;
			case CHASING:
				break;
			default:
				break;
		}
	}

	/**
	 * Patrolling is done based on the following algorithm:
	 * Generate a random point in the zone. The enemy will move toward that point for 3 seconds, then move
	 * toward a newly generated random point.
	 */
	private void executePatrol() {
		Random rand = new Random();
		float xLocation = rand.nextInt(Constants.ZONE_X_TILES);
		float yLocation = rand.nextInt(Constants.ZONE_Y_TILES);
		Vector2 randomLocation = new Vector2(xLocation, yLocation);
		Vector2 normalizedDirection = new Vector2(randomLocation);
		normalizedDirection.sub(position).nor();
		float angle = (360.0f - normalizedDirection.angle()) + 90.0f;
		if(angle >= 360.0f) {
			angle -= 360.0f;
		}
		setDirection(angle);
		float newSpeedX = normalizedDirection.x * moveRate;
		float newSpeedY = normalizedDirection.y * moveRate;
		body.setLinearVelocity(newSpeedX, newSpeedY);
		setMoveSpeed(newSpeedX, newSpeedY);

	}

	//Getters
	public ArrayList<ConeLight> getConelights() {
		return coneLights;
	}
	public int getNumConelights() {
		return numConelights;
	}

	//Setters
	public void addConelight(ConeLight light) {
		coneLights.add(light);
	}
}
