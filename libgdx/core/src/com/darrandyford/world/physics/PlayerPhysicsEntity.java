package com.darrandyford.world.physics;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.darrandyford.entities.AbstractGameEntity;

public class PlayerPhysicsEntity extends PhysicsBoxEntity {


	public PlayerPhysicsEntity(PhysicsController physicsController, AbstractGameEntity entity) {
		super(physicsController, entity);
		this.bodyType = BodyDef.BodyType.DynamicBody;
	}

	@Override
	public void initPhysics() {
		super.initPhysics();
		entity.getBody().setSleepingAllowed(false);
	}

}
