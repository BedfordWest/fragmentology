package com.darrandyford.entities.living.characters;

import box2dLight.ConeLight;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.darrandyford.assets.Assets;
import com.darrandyford.entities.living.LivingEntity;
import com.darrandyford.entities.nonliving.EnemyLight;
import com.darrandyford.utils.Constants;

import java.util.ArrayList;
import java.util.Random;

public class Enemy extends LivingEntity {
	private ArrayList<ConeLight> coneLights;
	private int numConelights = 4;
	public enum AlertState { STATIONARY, PATROLLING, ALERT, CHASING }
	private AlertState alertState;
	private float patrolDuration, patrolCurrent, chasingDuration, chasingCurrent;
	private Body lightBody;
	boolean coneLightBodySet = false;
	private EnemyLight enemyLight;

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
		chasingDuration = 3.0f;
		chasingCurrent = 0.0f;
		moveRate = 2.0f;
		this.enemyLight = new EnemyLight(this);
	}

	public void update(float deltaTime) {
		switch (alertState) {
			case CHASING:
				chasingCurrent += deltaTime;
				if(chasingCurrent >= chasingDuration) {
					chasingCurrent = 0.0f;
					alertState = AlertState.PATROLLING;
				}
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
			case ALERT:
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
		float xLocation = rand.nextInt(Constants.ZONE_X_TILES * 2);
		float yLocation = rand.nextInt(Constants.ZONE_Y_TILES * 2);
		Vector2 randomLocation = new Vector2(xLocation, yLocation);
		Vector2 normalizedDirection = new Vector2(randomLocation);
		normalizedDirection.sub(position).nor();
		float angle = normalizedDirection.angle();
		setDirection(angle);
		float newSpeedX = normalizedDirection.x * moveRate;
		float newSpeedY = normalizedDirection.y * moveRate;
		body.setLinearVelocity(newSpeedX, newSpeedY);
		setMoveSpeed(newSpeedX, newSpeedY);

	}

	public boolean coneLightBodySet() {
		return coneLightBodySet;
	}

	public void setConeLightBodySetState(boolean setState) {
		coneLightBodySet = setState;
	}

	//Getters
	public ArrayList<ConeLight> getConelights() {
		return coneLights;
	}
	public int getNumConelights() {
		return numConelights;
	}
	public Body getLightBody() { return this.lightBody; }

	public EnemyLight getEnemyLight() {
		return enemyLight;
	}

	public AlertState getAlertState() {
		return alertState;
	}

	public float getChasingCurrent() {
		return chasingCurrent;
	}

	//Setters
	public void addConelight(ConeLight light) {
		coneLights.add(light);
	}
	public void setLightBody(Body body) {
		this.lightBody = body;
	}

	public void setAlertState(AlertState alertState) {
		this.alertState = alertState;
	}
	public void setChasingCurrent(float current) {
		chasingCurrent = current;
	}
}
