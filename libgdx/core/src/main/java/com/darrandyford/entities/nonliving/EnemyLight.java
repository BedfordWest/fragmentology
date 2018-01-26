package main.java.com.darrandyford.entities.nonliving;

import main.java.com.darrandyford.entities.living.characters.Enemy;

public class EnemyLight {
	Enemy enemy;
	public EnemyLight(Enemy enemy) {
		this.enemy = enemy;
	}

	public Enemy getEnemy() { return enemy; }
}
