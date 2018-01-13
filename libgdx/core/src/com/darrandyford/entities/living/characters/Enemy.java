package com.darrandyford.entities.living.characters;

import box2dLight.ConeLight;
import com.darrandyford.entities.living.LivingEntity;

import java.util.ArrayList;

public class Enemy extends LivingEntity {
	private ArrayList<ConeLight> coneLights;
	private int numConelights = 4;

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
