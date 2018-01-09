package com.darrandyford.entities.living.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.darrandyford.entities.AbstractGameEntity;
import com.darrandyford.utils.Constants;

public class Player extends AbstractGameEntity {

	// Set the TAG for logging purposes
	private static final String TAG = Player.class.getName();

	public Player() {
		init();
	}

	private void init () {
		this.dimension.set(1.0f, 1.75f);
		this.rotation = 0.0f;
		this.direction = Constants.DIRECTION_RIGHT;
		this.origin.set(dimension.x/2, dimension.y/2);
	}

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
