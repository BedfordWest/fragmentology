package com.darrandyford.entities.living.characters;

import box2dLight.ConeLight;
import com.badlogic.gdx.math.MathUtils;
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
	private float patrolDuration, patrolCurrent, alertDuration, alertCurrent;
	private Body lightBody;
	boolean coneLightBodySet = false;
	private EnemyLight enemyLight;
	private float chaseRate;

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
		patrolDuration = 1.0f;
		patrolCurrent = 1.0f;
		alertDuration = 3.0f;
		alertCurrent = 0.0f;
		moveRate = 2.0f;
		chaseRate = 6.0f;
		this.enemyLight = new EnemyLight(this);
		this.entityType = "enemy";
	}

	public void update(float deltaTime) {
		switch (alertState) {
			case ALERT:
				alertCurrent += deltaTime;
				if(alertCurrent >= alertDuration) {
					alertCurrent = 0.0f;
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
			case CHASING:
				executeChase();
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
		float randomDirection = new Vector2(randomLocation).sub(getPosition()).angle() * MathUtils.degRad;
		float currentDirection = getDirection() * MathUtils.degRad;

		float newDirection = 0.0f;

		// Faster to get to random point by going clockwise
		if((randomDirection - currentDirection) >= Math.PI) {
			newDirection = ((float)(Math.PI * 2 - randomDirection) + currentDirection)/4.0f + currentDirection;
		}
		// Faster to get to random point by going counter-clockwise
		else if((randomDirection - currentDirection) < Math.PI && (randomDirection - currentDirection) >= 0) {
			newDirection = (randomDirection - currentDirection)/4.0f + currentDirection;
		}
		// Faster to get to point by going counter-clockwise
		else if((randomDirection - currentDirection) < 0 && (randomDirection - currentDirection) <= -Math.PI) {
			newDirection = (randomDirection + (float)(2 * Math.PI - currentDirection))/4.0f + currentDirection;
		}
		// Faster to get to point by going clockwise
		else  {
			newDirection = (randomDirection - currentDirection)/4.0f + currentDirection;
		}

		Vector2 newDirectionVecRaw = new Vector2((float)Math.cos(newDirection),
			(float)Math.sin(newDirection));

		setDirection(newDirection * MathUtils.radDeg);
		float newSpeedX = newDirectionVecRaw.x * moveRate;
		float newSpeedY = newDirectionVecRaw.y * moveRate;
		body.setLinearVelocity(newSpeedX, newSpeedY);
		setMoveSpeed(newSpeedX, newSpeedY);

		// Play sound when enemy moves
		float toPlayer = getPosition().sub(worldController.getPlayer().getPosition()).len();
		if(toPlayer < 1.0f) toPlayer = 1.0f;
		Assets.instance.playSound("slime", 1.0f/toPlayer);

	}

	/**
	 * Chase after the player for as long as they are in the cone of sight.
	 */
	private void executeChase() {
		Vector2 toPlayer = new Vector2(worldController.getPlayer().getPosition().sub(getPosition()));
		toPlayer.nor();
		moveSpeed.x = toPlayer.x * chaseRate;
		moveSpeed.y = toPlayer.y * chaseRate;
		direction = toPlayer.angle();
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

	public float getAlertCurrent() {
		return alertCurrent;
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
	public void setAlertCurrent(float current) {
		alertCurrent = current;
	}
}
