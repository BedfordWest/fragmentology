package com.darrandyford.entities.living.characters;

import box2dLight.ConeLight;
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

	public Enemy() {
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

	private void executePatrol() {
		Random rand = new Random();
		int direction = rand.nextInt(4);
		switch(direction) {
			case 0: //North
				setDirection(Constants.DIRECTION_UP);
				body.setLinearVelocity(0.0f, moveRate);
				break;
			case 1: //East
				setDirection(Constants.DIRECTION_RIGHT);
				body.setLinearVelocity(moveRate, 0.0f);
				break;
			case 2: //South
				setDirection(Constants.DIRECTION_DOWN);
				body.setLinearVelocity(0.0f, -moveRate);
				break;
			case 3: //West
				setDirection(Constants.DIRECTION_LEFT);
				body.setLinearVelocity(-moveRate, 0.0f);
				break;
			default:
				break;
		}
		setMoveSpeed(body.getLinearVelocity());

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
