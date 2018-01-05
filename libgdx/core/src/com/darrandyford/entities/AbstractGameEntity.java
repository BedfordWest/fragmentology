package com.darrandyford.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Transform;
import com.darrandyford.utils.Constants;

public abstract class AbstractGameEntity {

	protected Vector2 position;
	protected Vector2 dimension;
	protected Vector2 origin;
	protected Vector2 scale;
	protected float rotation, direction;
	protected Vector2 acceleration;
	protected Rectangle bounds = new Rectangle();
	public enum MoveState { MS_LEFT, MS_RIGHT, MS_UP, MS_DOWN, MS_NONE }
	MoveState moveState;

	public void AbstractGameObject () {
		position = new Vector2();
		dimension = new Vector2(1, 1);
		origin = new Vector2();
		scale = new Vector2(1,1);
		rotation = 0;
		direction = Constants.DIRECTION_RIGHT;
	}

	public void update (float deltaTime) {
	}

	public abstract void render (SpriteBatch batch);

	// Getters
	public Vector2 getPosition() { return this.position; }
	public Vector2 getDimension() { return this.dimension; }
	public Vector2 getOrigin() { return this.origin; }
	public float getRotation() { return this.rotation; }
	public float getDirection() { return this.direction; }
	public MoveState getMoveState() { return this.moveState; }

	// Setters
	public void setPosition(float x, float y) {
		this.position.x = x;
		this.position.y = y;
	}
	public void setRotation(float r) { this.rotation = r; }
	public void setDirection(float d) { this.direction = d; }
	public void setMoveState(MoveState move) { this.moveState = move; }

}
