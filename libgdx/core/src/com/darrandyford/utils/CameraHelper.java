package com.darrandyford.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.darrandyford.entities.AbstractGameEntity;

/**
 * Utilities related to the viewport camera, perspective, etc.
 */
public class CameraHelper
{

	//Store the TAG for logging
	private static final String TAG = CameraHelper.class.getName();

	private final float MAX_ZOOM_IN = 0.25f;
	private final float MAX_ZOOM_OUT = 10.0f;

	private Vector2 position;
	private float zoom; // positive zoom = zoom out
	private AbstractGameEntity target;

	public CameraHelper () {
		position = new Vector2();
		zoom = 1.0f;
	}

	public void update (float deltaTime) {
		if (!hasTarget()) return;
		position.x = target.getPosition().x;
		position.y = target.getPosition().y;
	}

	public void setPosition (float x, float y) {
		this.position.set(x,y);
	}

	public Vector2 getPosition () { return position; }

	public void addZoom (float amount) { setZoom(zoom + amount); }
	public void setZoom (float zoom) {
		this.zoom = MathUtils.clamp(zoom, MAX_ZOOM_IN, MAX_ZOOM_OUT);
	}

	public float getZoom () { return zoom; }

	public void applyTo (OrthographicCamera camera) {
		camera.position.x = position.x;
		camera.position.y = position.y;
		camera.zoom = zoom;
		camera.update();
	}

	public void setTarget(AbstractGameEntity target) { this.target = target; }
	public AbstractGameEntity getTarget () { return target; }
	public boolean hasTarget() { return target != null; }

}
