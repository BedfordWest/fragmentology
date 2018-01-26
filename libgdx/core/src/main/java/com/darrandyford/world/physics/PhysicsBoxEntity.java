package main.java.com.darrandyford.world.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import main.java.com.darrandyford.entities.AbstractGameEntity;

public class PhysicsBoxEntity implements PhysicsEntity {

	protected AbstractGameEntity entity;
	protected BodyDef.BodyType bodyType;
	protected PhysicsController physicsController;

	public PhysicsBoxEntity(PhysicsController physicsController, AbstractGameEntity entity) {
		this.entity = entity;
		this.bodyType = BodyDef.BodyType.DynamicBody;
		this.physicsController = physicsController;
		this.physicsController.addPhysicsEntity(this);
	}

	public PhysicsBoxEntity(PhysicsController physicsController, AbstractGameEntity entity, BodyDef.BodyType bodyType) {
		this.entity = entity;
		this.bodyType = bodyType;
		this.physicsController = physicsController;
		this.physicsController.addPhysicsEntity(this);
	}

	public void initPhysics() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(entity.getPosition());
		entity.setBody(physicsController.getB2World().createBody((bodyDef)));
		PolygonShape polygonShape = new PolygonShape();
		polygonShape.setAsBox(
			entity.getDimension().x / 2.0f,
			entity.getDimension().y / 2.0f,
			new Vector2(0.0f,0.0f),
			0
		);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = polygonShape;
		fixtureDef.restitution = 0f;
		fixtureDef.filter.categoryBits = entity.getCollisionCategory();
		fixtureDef.filter.maskBits = entity.getCollisionMask();
		entity.getBody().createFixture(fixtureDef);
		entity.getBody().setUserData(entity);
		polygonShape.dispose();

	}

	public void updatePhysics(float deltaTime) {
		Vector2 moveSpeed = entity.getMoveSpeed();
		entity.getBody()
			.setLinearVelocity(moveSpeed);
		if(!(moveSpeed.x == 0.0f && moveSpeed.y == 0.0f)) {
			entity.setMoving(true);
		}
		else entity.setMoving(false);
	}

}
