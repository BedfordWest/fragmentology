package com.darrandyford.entities.living.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.darrandyford.assets.Assets;
import com.darrandyford.entities.AbstractGameEntity;
import com.darrandyford.utils.Constants;

/**
 * Class to represent a player in the game. Will be living and have any player-specific attributes necessary.
 */
public class Player extends AbstractGameEntity {

	// Set the TAG for logging purposes
	private static final String TAG = Player.class.getName();
	private TextureRegion regSide, regBack, regFront;

	public Player() {
		init();
	}

	/**
	 * Initialize the player. Should be facing right by default. The origin should be in the middle of the player.
	 */
	private void init () {
		this.dimension.set(1.0f, 1.75f);
		this.rotation = 0.0f;
		this.direction = Constants.DIRECTION_LEFT;
		this.origin.set(dimension.x/2, dimension.y/2);
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
		TextureRegion reg;

		if (this.direction == Constants.DIRECTION_RIGHT)
		{
			reg = regSide;
			batch.draw(reg.getTexture(), position.x, position.y,
				origin.x, origin.y, dimension.x, dimension.y, scale.x,
				scale.y, rotation, reg.getRegionX(), reg.getRegionY(),
				reg.getRegionWidth(), reg.getRegionHeight(), true, false);
		}

		else if (this.direction == Constants.DIRECTION_LEFT)
		{
			reg = regSide;
			batch.draw(reg.getTexture(), position.x, position.y,
				origin.x, origin.y, dimension.x, dimension.y, scale.x,
				scale.y, rotation, reg.getRegionX(), reg.getRegionY(),
				reg.getRegionWidth(), reg.getRegionHeight(), false, false);
		}

		else if (this.direction == Constants.DIRECTION_UP) {
			reg = regBack;
			batch.draw(reg.getTexture(), position.x, position.y,
				origin.x, origin.y, dimension.x, dimension.y, scale.x,
				scale.y, rotation, reg.getRegionX(), reg.getRegionY(),
				reg.getRegionWidth(), reg.getRegionHeight(), false, false);
		}

		else if (this.direction == Constants.DIRECTION_DOWN) {
			reg = regFront;
			batch.draw(reg.getTexture(), position.x, position.y,
				origin.x, origin.y, dimension.x, dimension.y, scale.x,
				scale.y, rotation, reg.getRegionX(), reg.getRegionY(),
				reg.getRegionWidth(), reg.getRegionHeight(), false, false);
		}

		else {
			Gdx.app.debug(TAG, "Problem rendering the player!");
		}
	}
}
