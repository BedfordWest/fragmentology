package com.darrandyford.entities.living;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.darrandyford.assets.Assets;
import com.darrandyford.entities.AbstractGameEntity;
import com.darrandyford.entities.living.characters.Player;
import com.darrandyford.utils.Constants;

public class LivingEntity extends AbstractGameEntity {

	// Set the TAG for logging purposes
	private static final String TAG = LivingEntity.class.getName();
	protected TextureRegion regSide, regBack, regFront;

	public LivingEntity() {
		init();
	}

	/**
	 * Initialize the player. Should be facing right by default. The origin should be in the middle of the player.
	 */
	private void init () {
		this.dimension.set(1.0f, 1.0f);
		this.rotation = 0.0f;
		this.direction = Constants.DIRECTION_LEFT;
		this.origin.set(dimension.x/2, dimension.y/2);
		this.setPosition(0,0);
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
			Gdx.app.debug(TAG, "Problem rendering the living entity!");
		}
	}

	// Setters
	public void setSideTexture(TextureRegion reg) {
		this.regSide = reg;
	}

	public void setFrontTexture(TextureRegion reg) {
		this.regFront = reg;
	}

	public void setBackTexture(TextureRegion reg) {
		this.regBack = reg;
	}

	// Getters
	public TextureRegion getRegSide() {
		return regSide;
	}

	public TextureRegion getRegFront() {
		return regFront;
	}

	public TextureRegion getRegBack() {
		return regBack;
	}
}
