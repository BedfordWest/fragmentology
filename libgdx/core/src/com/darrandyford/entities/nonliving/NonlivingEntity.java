package com.darrandyford.entities.nonliving;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.darrandyford.entities.AbstractGameEntity;

public class NonlivingEntity extends AbstractGameEntity {
	// Set the TAG for logging purposes
	private static final String TAG = NonlivingEntity.class.getName();
	protected Sprite sprite;


	public NonlivingEntity() {
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
		sprite.setPosition(position.x, position.y);
		sprite.setCenter(position.x, position.y);
		sprite.setOriginCenter();
		sprite.draw(batch);

	}

	// Setters
	public void setSprite(TextureRegion reg) {
		sprite = new Sprite(reg);
		sprite.setSize(dimension.x, dimension.y);
	}

	// Getters
}
