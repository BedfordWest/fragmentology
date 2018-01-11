package com.darrandyford.entities.living;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.darrandyford.entities.AbstractGameEntity;
import com.darrandyford.utils.Constants;

public class LivingEntity extends AbstractGameEntity {

	// Set the TAG for logging purposes
	private static final String TAG = LivingEntity.class.getName();
	protected Sprite sideSprite, frontSprite, backSprite;


	public LivingEntity() {
		init();
	}

	/**
	 * Initialize the player. Should be facing right by default. The origin should be in the middle of the player.
	 */
	private void init () {
	}

	/**
	 * Render the player.
	 * @param batch the Sprite Batch used to render the entity
	 */
	@Override
	public void render (SpriteBatch batch) {

		if (this.direction == Constants.DIRECTION_RIGHT)
		{
			sideSprite.setPosition(position.x, position.y);
			sideSprite.setCenter(position.x + dimension.x/2, position.y + dimension.y/2);
			sideSprite.setOriginCenter();
			sideSprite.flip(true, false);
			sideSprite.setFlip(true, false);
			sideSprite.draw(batch);
		}

		else if (this.direction == Constants.DIRECTION_LEFT)
		{
			sideSprite.setPosition(position.x, position.y);
			sideSprite.setCenter(position.x, position.y);
			sideSprite.setOriginCenter();
			sideSprite.flip(false, false);
			sideSprite.setFlip(false, false);
			sideSprite.draw(batch);
		}

		else if (this.direction == Constants.DIRECTION_UP) {
			backSprite.setPosition(position.x, position.y);
			backSprite.setCenter(position.x, position.y);
			backSprite.setOriginCenter();
			backSprite.draw(batch);
		}

		else if (this.direction == Constants.DIRECTION_DOWN) {
			frontSprite.setPosition(position.x, position.y);
			frontSprite.setCenter(position.x, position.y);
			frontSprite.setOriginCenter();
			frontSprite.draw(batch);
		}

		else {
			Gdx.app.debug(TAG, "Problem rendering the living entity!");
		}
	}

	// Setters
	public void setSideSprite(TextureRegion reg) {
		sideSprite = new Sprite(reg);
		sideSprite.setSize(dimension.x, dimension.y);
	}

	public void setFrontSprite(TextureRegion reg) {
		frontSprite = new Sprite(reg);
		frontSprite.setSize(dimension.x, dimension.y);
	}

	public void setBackSprite(TextureRegion reg) {
		backSprite = new Sprite(reg);
		backSprite.setSize(dimension.x, dimension.y);
	}

	// Getters
}
