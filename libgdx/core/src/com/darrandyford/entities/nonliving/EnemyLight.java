package com.darrandyford.entities.nonliving;

import com.darrandyford.entities.living.characters.Enemy;

public class EnemyLight {
	Enemy enemy;
	public EnemyLight(Enemy enemy) {
		this.enemy = enemy;
	}

	public Enemy getEnemy() { return enemy; }
}
