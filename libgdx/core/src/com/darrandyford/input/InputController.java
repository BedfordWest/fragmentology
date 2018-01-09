package com.darrandyford.input;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.darrandyford.utils.CameraHelper;
import com.darrandyford.world.WorldController;

/**
 * Handles user input
 */
public class InputController extends InputAdapter {
	private WorldController worldController;
	private static final String TAG = InputController.class.getName();
	private CameraHelper cameraHelper;

	/**
	 * Constructor
	 * @param worldController the world controller
	 */
	public InputController(WorldController worldController)
	{
		Gdx.input.setInputProcessor(this);
		this.worldController = worldController;
		this.cameraHelper = worldController.getCameraHelper();
	}

	/**
	 * Handles key release events
	 * @param keycode passed from client
	 * @return boolean
	 */
	@Override
	public boolean keyUp (int keycode) {
		switch (keycode) {
			// Reset game world
			case Input.Keys.R:
				worldController.reset();
				worldController.setInitRenderState(true);
				Gdx.app.debug(TAG, "Game world reset");
				break;

			// Toggle camera follow
			case Input.Keys.ENTER:
				Gdx.app.debug(TAG, "Enter released");
				break;

			case Input.Keys.D:
				Gdx.app.debug(TAG, "D released");
				break;

			case Input.Keys.A:
				Gdx.app.debug(TAG, "A released");
				break;

			default:
		}

		return true;
	}

	/**
	 * Handles key press events
	 * @param keycode passed from client
	 * @return boolean
	 */
	@Override
	public boolean keyDown (int keycode) {
		switch(keycode)
		{
			case Input.Keys.SPACE:
				Gdx.app.debug(TAG, "Space key pressed");
				break;
			case Input.Keys.A:
				Gdx.app.debug(TAG, "A pressed");
				break;
			case Input.Keys.D:
				Gdx.app.debug(TAG, "D pressed");
				break;
			default:
		}
		return true;
	}

	public void handleDebugInput (float deltaTime) {
		if (Gdx.app.getType() != Application.ApplicationType.Desktop) return;

		// Camera Controls (move)
		float camMoveSpeed = 5 * deltaTime;
		float camMoveSpeedAccelerationFactor = 5;
		if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
			camMoveSpeed *= camMoveSpeedAccelerationFactor;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			moveCamera(-camMoveSpeed, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			moveCamera(camMoveSpeed, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			moveCamera(0, camMoveSpeed);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			moveCamera(0, -camMoveSpeed);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)) {
			cameraHelper.setPosition(0,0);
		}

		// Camera Controls (zoom)
		float camZoomSpeed = 1 * deltaTime;
		float camZoomSpeedAccelerationFactor = 5;
		if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
			camZoomSpeed *= camZoomSpeedAccelerationFactor;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.COMMA)) {
			cameraHelper.addZoom(camZoomSpeed);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.PERIOD)) {
			cameraHelper.addZoom(-camZoomSpeed);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.SLASH)) {
			cameraHelper.setZoom(1);
		}
	}

	private void moveCamera (float x, float y) {
		x += cameraHelper.getPosition().x;
		y += cameraHelper.getPosition().y;
		cameraHelper.setPosition(x, y);
	}

}
