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

		if (this.direction == Constants.DIRECTION_RIGHT)
		{
			sideSprite.setPosition(position.x, position.y);
			sideSprite.setCenter(position.x, position.y);
			sideSprite.setOriginCenter();
			sideSprite.flip(false, true);
			sideSprite.setFlip(false, true);
			//sideSprite.setScale(1/Constants.WORLD_SCALE);
			sideSprite.draw(batch);
		}

		else if (this.direction == Constants.DIRECTION_LEFT)
		{
			sideSprite.setPosition(position.x, position.y);
			sideSprite.setCenter(position.x, position.y);
			sideSprite.setOriginCenter();
			sideSprite.flip(false, false);
			sideSprite.setFlip(false, false);
			//sideSprite.setScale(1/Constants.WORLD_SCALE);
			sideSprite.draw(batch);
		}

		else if (this.direction == Constants.DIRECTION_UP) {
			backSprite.setPosition(position.x, position.y);
			backSprite.setCenter(position.x, position.y);
			backSprite.setOriginCenter();
			//backSprite.setScale(1/Constants.WORLD_SCALE);
			backSprite.draw(batch);
		}

		else if (this.direction == Constants.DIRECTION_DOWN) {
			frontSprite.setPosition(position.x, position.y);
			frontSprite.setCenter(position.x, position.y);
			frontSprite.setOriginCenter();
			//frontSprite.setScale(1/Constants.WORLD_SCALE);
			frontSprite.draw(batch);
		}

		else {
			Gdx.app.debug(TAG, "Problem rendering the living entity!");
		}
	}

	// Setters
	public void setSideSprite(TextureRegion reg) {
		sideSprite = new Sprite(reg);
		sideSprite.setScale(1/Constants.WORLD_SCALE);
	}

	public void setFrontSprite(TextureRegion reg) {
		frontSprite = new Sprite(reg);
		frontSprite.setScale(1/Constants.WORLD_SCALE);
	}

	public void setBackSprite(TextureRegion reg) {
		backSprite = new Sprite(reg);
		backSprite.setScale(1/Constants.WORLD_SCALE);
	}

	// Getters
}
