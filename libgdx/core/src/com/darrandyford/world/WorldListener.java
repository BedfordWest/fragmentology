package com.darrandyford.world;

import com.badlogic.gdx.physics.box2d.*;
import com.darrandyford.entities.living.characters.Enemy;
import com.darrandyford.entities.living.characters.Player;

/**
 * Handle events when two physics bodies collide. This is useful for any collision detection and corresponding game
 * adjustments.
 */
public class WorldListener implements ContactListener {
	private WorldController worldController;

	public WorldListener(WorldController worldController) {
		this.worldController = worldController;
	}

	/** Called when two fixtures begin to touch. */
	@Override
	public void beginContact(Contact contact) {

		Body a = contact.getFixtureA().getBody();
		Body b = contact.getFixtureB().getBody();

		// Handle special class-based collisions to act on things like damage
		Class aClassType = a.getUserData().getClass();
		Class bClassType = b.getUserData().getClass();

		if (aClassType.isAssignableFrom(Player.class)) {
			if (bClassType.isAssignableFrom(Enemy.class)) {
				worldController.reset();
			}

		}

		else if (aClassType.isAssignableFrom(Enemy.class)) {
			if (bClassType.isAssignableFrom(Player.class)) {
				worldController.reset();
			}

		}

	}

	/** Called when two fixtures cease to touch. */
	@Override
	public void endContact(Contact contact) {

	}

	/*
	 * This is called after a contact is updated. This allows you to inspect a
	 * contact before it goes to the solver. If you are careful, you can
	 * modify the contact manifold (e.g. disable contact). A copy of the old
	 * manifold is provided so that you can detect changes. Note: this is
	 * called only for awake bodies. Note: this is called even when the number
	 * of contact points is zero. Note: this is not called for sensors. Note:
	 * if you set the number of contact points to zero, you will not get an
	 * EndContact callback. However, you may get a BeginContact callback the
	 * next step.
	 */
	@Override
	public void preSolve (Contact contact, Manifold oldManifold) {
		Body a = contact.getFixtureA().getBody();
		Body b = contact.getFixtureB().getBody();

	}

	/*
	 * This lets you inspect a contact after the solver is finished. This is
	 * useful for inspecting impulses. Note: the contact manifold does not
	 * include time of impact impulses, which can be arbitrarily large if the
	 * sub-step is small. Hence the impulse is provided explicitly in a
	 * separate data structure. Note: this is only called for contacts that
	 * are touching, solid, and awake.
	 */
	public void postSolve (Contact contact, ContactImpulse impulse) {


	}
}
