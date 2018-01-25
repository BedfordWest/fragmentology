package com.darrandyford.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Transform;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.darrandyford.utils.Constants;
import com.darrandyford.world.WorldController;

import java.util.ArrayList;
import java.util.Random;

/**
 * Abstract game entity - all game entities should inherit from this.
 */
public abstract class AbstractGameEntity {

	protected Vector2 position;
	protected Vector2 dimension;
	protected Vector2 origin;
	protected Vector2 scale;
	protected float rotation, direction;
	protected Vector2 moveSpeed, acceleration;
	protected Rectangle bounds;
	protected float moveRate;
	protected Body body;
	protected boolean isMoving;
	protected WorldController worldController;
	protected String entityType;

	/**
	 * Constructor - we'll want to default these values for all subclasses
	 */
	public AbstractGameEntity (float positionX, float positionY, float dimensionX, float dimensionY) {
		Random rand = new Random();
		position = new Vector2(positionX, positionY);
		dimension = new Vector2(dimensionX, dimensionY);
		origin = new Vector2(this.position.x, this.position.y);
		scale = new Vector2(1.0f,1.0f);
		bounds = new Rectangle(this.position.x - this.dimension.x/2, this.position.y - this.dimension.y/2,
			this.dimension.x, this.dimension.y);
		rotation = 0f;
		direction = (float)rand.nextInt(360);
		acceleration = new Vector2(0,0);
		moveSpeed = new Vector2(0,0);
		moveRate = 5.0f;
		entityType = "abstract";
	}

	/**
	 * Things we expect every game entity to do during a game cycle
	 * @param deltaTime time between cycles
	 */
	public void update (float deltaTime) {
		setPosition(body.getPosition());
		rotation = body.getAngle() * MathUtils.radiansToDegrees;
	}

	public void interpolate (float extraTime) {
		Transform transform = this.body.getTransform();
		Vector2 bodyPosition = transform.getPosition();
		float bodyAngle = transform.getRotation();

		rotation = bodyAngle * extraTime + rotation * (1.0f - extraTime);
		setPosition(bodyPosition.x * extraTime + position.x * (1.0f - extraTime),
			bodyPosition.y * extraTime + position.y * (1.0f - extraTime));
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
	public Vector2 getPosition() { return new Vector2(this.position); }
	public Vector2 getDimension() { return this.dimension; }
	public Vector2 getOrigin() { return this.origin; }
	public float getRotation() { return this.rotation; }
	public float getDirection() { return this.direction; }
	public Vector2 getMoveSpeed() { return moveSpeed; }
	public float getMoveRate() {
		return moveRate;
	}
	public Rectangle getBounds() { return bounds; }
	public Body getBody() { return body; }
	public boolean getMoving() { return isMoving; }
	public ArrayList<Vector2> getContactPoints() {
		ArrayList<Vector2> contactPoints = new ArrayList<Vector2>();
		contactPoints.add(new Vector2(position.x, position.y));
		contactPoints.add(new Vector2(position.x + dimension.x, position.y));
		contactPoints.add(new Vector2(position.x, position.y + dimension.y));
		contactPoints.add(new Vector2(position.x + dimension.x, position.y + dimension.y));
		contactPoints.add(new Vector2(position.x + dimension.x/2, position.y));
		contactPoints.add(new Vector2(position.x, position.y + dimension.y/2));
		contactPoints.add(new Vector2(position.x + dimension.x/2, position.y + dimension.y));
		contactPoints.add(new Vector2(position.x + dimension.x, position.y + dimension.y/2));
		return contactPoints;
	}
	public short getCollisionCategory() {
		return Constants.collisionMap.get(entityType).getCollisionCategory();
	}

	public short getCollisionMask() {
		return Constants.collisionMap.get(entityType).getCollisionMask();
	}

	public WorldController getWorldController() {
		return worldController;
	}

	// Setters
	public void setPosition(float x, float y) {
		this.position.x = x;
		this.position.y = y;
		updateOrigin();
		updateBounds();
	}
	public void setPosition(Vector2 pos) {
		this.position.set(pos);
		updateOrigin();
		updateBounds();
	}
	public void setRotation(float r) { this.rotation = r; }
	public void setDirection(float d) { this.direction = d; }
	public void setAcceleration(Vector2 newAcceleration) { this.acceleration = newAcceleration; }
	public void setMoveSpeed(Vector2 newVelocity) { this.moveSpeed = newVelocity; }
	public void setMoveRate(float rate) { this.moveRate = rate; }
	public void setMoveSpeed(float x, float y){
		this.moveSpeed.x = x;
		this.moveSpeed.y = y;
	}
	public void setBody(Body body) { this.body = body; }
	public void setMoving(boolean moving) { isMoving = moving; }
	public void setDimension(float x, float y) { this.dimension.x = x; this.dimension.y = y; }
	public void setWorldController(WorldController worldController) { this.worldController = worldController; }

}
