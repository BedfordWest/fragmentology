package com.darrandyford.entities.living.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.darrandyford.entities.AbstractGameEntity;
import com.darrandyford.utils.Constants;

/**
 * Class to represent a player in the game. Will be living and have any player-specific attributes necessary.
 */
public class Player extends AbstractGameEntity {

	// Set the TAG for logging purposes
	private static final String TAG = Player.class.getName();

	public Player() {
		init();
	}

	/**
	 * Initialize the player. Should be facing right by default. The origin should be in the middle of the player.
	 */
	private void init () {
		this.dimension.set(1.0f, 1.75f);
		this.rotation = 0.0f;
		this.direction = Constants.DIRECTION_RIGHT;
		this.origin.set(dimension.x/2, dimension.y/2);
	}

	/**
	 * Render the player.
	 * @param batch the Sprite Batch used to render the entity
	 */
	@Override
	public void render (SpriteBatch batch) {

		if (this.direction == Constants.DIRECTION_RIGHT)
		{
		}

		else if (this.direction == Constants.DIRECTION_LEFT)
		{
		}

		else if (this.direction == Constants.DIRECTION_UP) {
		}

		else if (this.direction == Constants.DIRECTION_DOWN) {
		}

		else {
			Gdx.app.debug(TAG, "Problem rendering the player!");
		}
	}
}
