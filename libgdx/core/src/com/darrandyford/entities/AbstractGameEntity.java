package com.darrandyford.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Transform;
import com.darrandyford.utils.Constants;

/**
 * Abstract game entity - all game entities should inherit from this.
 */
public abstract class AbstractGameEntity {

	protected Vector2 position;
	protected Vector2 dimension;
	protected Vector2 origin;
	protected Vector2 scale;
	protected float rotation, direction;
	protected Vector2 velocity, acceleration;
	protected Rectangle bounds;
	public enum MoveState { MS_LEFT, MS_RIGHT, MS_UP, MS_DOWN, MS_NONE }
	MoveState moveState;

	/**
	 * Constructor - we'll want to default these values for all subclasses
	 */
	public AbstractGameEntity () {
		position = new Vector2(0f,0f);
		dimension = new Vector2(2f, 2f);
		origin = new Vector2(this.position.x, this.position.y);
		scale = new Vector2(1f,1f);
		bounds = new Rectangle(this.position.x - this.dimension.x/2, this.position.y - this.dimension.y/2,
			this.dimension.x, this.dimension.y);
		rotation = 0f;
		direction = Constants.DIRECTION_LEFT;
		acceleration = new Vector2(0,0);
		velocity = new Vector2(0,0);

	}

	/**
	 * Things we expect every game entity to do during a game cycle
	 * @param deltaTime time between cycles
	 */
	public void update (float deltaTime) {
		velocity.x += acceleration.x * deltaTime;
		velocity.y += acceleration.y * deltaTime;
		setPosition(position.x + (velocity.x * deltaTime), position.y + (velocity.y * deltaTime));
	}

	public void updateOrigin() {
		origin.x = position.x;
		origin.y = position.y;
	}

	public void updateBounds() {
		bounds.x = position.x - dimension.x/2;
		bounds.y = position.y - dimension.y/2;
	}

	/**
	 * All game entities need to be able to render, when required
	 * @param batch the Sprite Batch used to render the entity
	 */
	public abstract void render (SpriteBatch batch);

	// Getters
	public Vector2 getPosition() { return this.position; }
	public Vector2 getDimension() { return this.dimension; }
	public Vector2 getOrigin() { return this.origin; }
	public float getRotation() { return this.rotation; }
	public float getDirection() { return this.direction; }
	public MoveState getMoveState() { return this.moveState; }
	public Rectangle getBounds() { return bounds; }

	// Setters
	public void setPosition(float x, float y) {
		this.position.x = x;
		this.position.y = y;
		updateOrigin();
		updateBounds();
	}
	public void setRotation(float r) { this.rotation = r; }
	public void setDirection(float d) { this.direction = d; }
	public void setMoveState(MoveState move) { this.moveState = move; }
	public void setAcceleration(Vector2 newAcceleration) { this.acceleration = newAcceleration; }
	public void setVelocity(Vector2 newVelocity) { this.velocity = newVelocity; }
	public void setVelocity(float x, float y){
		this.velocity.x = x;
		this.velocity.y = y;
	}

}
