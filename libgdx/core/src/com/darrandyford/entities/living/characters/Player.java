package com.darrandyford.entities.living.characters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.darrandyford.assets.Assets;
import com.darrandyford.entities.living.LivingEntity;
import com.darrandyford.utils.Constants;

/**
 * Class to represent a player in the game. Will be living and have any player-specific attributes necessary.
 */
public class Player extends LivingEntity {

	// Set the TAG for logging purposes
	private static final String TAG = Player.class.getName();

	public Player() {
		init();
	}

	/**
	 * Initialize the player. Should be facing right by default. The origin should be in the middle of the player.
	 */
	private void init () {
		this.regSide = Assets.instance.player.left;
		this.regBack = Assets.instance.player.up;
		this.regFront = Assets.instance.player.down;
		this.setPosition((Constants.ZONE_X_TILES * Constants.TILE_WIDTH)/(2 * Constants.WORLD_SCALE),
			(Constants.ZONE_Y_TILES * Constants.TILE_HEIGHT)/(2 * Constants.WORLD_SCALE));
	}

	/**
	 * Render the player.
	 * @param batch the Sprite Batch used to render the entity
	 */
	@Override
	public void render (SpriteBatch batch) {
		super.render(batch);
	}
}
