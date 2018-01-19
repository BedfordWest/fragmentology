package com.darrandyford.input;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.darrandyford.entities.living.characters.Player;
import com.darrandyford.utils.CameraHelper;
import com.darrandyford.utils.Constants;
import com.darrandyford.world.WorldController;
import com.darrandyford.world.WorldRenderer;

/**
 * Handles user input
 *
 */
public class InputController extends InputAdapter {
	private WorldController worldController;
	private static final String TAG = InputController.class.getName();
	private CameraHelper cameraHelper;
	private Player player;

	/**
	 * Constructor
	 * @param worldController the world controller
	 */
	public InputController(WorldController worldController)
	{
		Gdx.input.setInputProcessor(this);
		this.worldController = worldController;
		this.cameraHelper = worldController.getCameraHelper();
		this.player = worldController.getPlayer();
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
				worldController.getCameraHelper().setTarget(
					cameraHelper.hasTarget() ? null : player);
				Gdx.app.debug(TAG, "Camera follow is enabled: " +
					cameraHelper.hasTarget());
				break;

			//Player control released
			case Input.Keys.SPACE:
				Gdx.app.debug(TAG, "SPACE released");
				break;
			case Input.Keys.A:
				if(Gdx.input.isKeyPressed(Input.Keys.D)){
					Gdx.app.debug(TAG, "A released, D pressed");
					player.setMoveSpeed(player.getMoveRate(),0.0f);
					player.setDirection(Constants.DIRECTION_RIGHT);
					player.update(0.0f);
					break;
				} else if(Gdx.input.isKeyPressed(Input.Keys.W)){
					Gdx.app.debug(TAG, "A released, W pressed");
					player.setMoveSpeed(0.0f,player.getMoveRate());
					player.setDirection(Constants.DIRECTION_UP);
					player.update(0.0f);
					break;
				} else if(Gdx.input.isKeyPressed(Input.Keys.S)){
					Gdx.app.debug(TAG, "A released, S pressed");
					player.setMoveSpeed(0.0f,-player.getMoveRate());
					player.setDirection(Constants.DIRECTION_DOWN);
					player.update(0.0f);
					break;
				}

				// normal key release
				Gdx.app.debug(TAG, "A released");
				player.setMoveSpeed(0.0f,0.0f);
				player.update(0.0f);
				break;

			case Input.Keys.D:
				if(Gdx.input.isKeyPressed(Input.Keys.A)){
					Gdx.app.debug(TAG, "D released, A pressed");
					player.setMoveSpeed(-player.getMoveRate(),0.0f);
					player.setDirection(Constants.DIRECTION_LEFT);
					player.update(0.0f);
					break;
				} else if (Gdx.input.isKeyPressed(Input.Keys.W)){
					Gdx.app.debug(TAG, "D released, W pressed");
					player.setMoveSpeed(0.0f,player.getMoveRate());
					player.setDirection(Constants.DIRECTION_UP);
					player.update(0.0f);
					break;
				} else if (Gdx.input.isKeyPressed(Input.Keys.S)){
					Gdx.app.debug(TAG, "D released, S pressed");
					player.setMoveSpeed(0.0f, -player.getMoveRate());
					player.setDirection(Constants.DIRECTION_DOWN);
					player.update(0.0f);
					break;
				}

				// normal key release
				Gdx.app.debug(TAG, "D released");
				player.setMoveSpeed(0.0f,0.0f);
				player.update(0.0f);
				break;

			case Input.Keys.S:
				if(Gdx.input.isKeyPressed(Input.Keys.W)){
					Gdx.app.debug(TAG, "S released, W pressed");
					player.setMoveSpeed(0.0f,player.getMoveRate());
					player.setDirection(Constants.DIRECTION_UP);
					player.update(0.0f);
					break;
				} else if(Gdx.input.isKeyPressed(Input.Keys.D)){
					Gdx.app.debug(TAG, "S released, D pressed");
					player.setMoveSpeed(player.getMoveRate(),0.0f);
					player.setDirection(Constants.DIRECTION_RIGHT);
					player.update(0.0f);
					break;
				} else if(Gdx.input.isKeyPressed(Input.Keys.A)){
					Gdx.app.debug(TAG, "S released, A pressed");
					player.setMoveSpeed(-player.getMoveRate(),0.0f);
					player.setDirection(Constants.DIRECTION_LEFT);
					player.update(0.0f);
					break;
				}

				// normal key release
				Gdx.app.debug(TAG, "S released");
				player.setMoveSpeed(0.0f,0.0f);
				player.update(0.0f);
				break;

			case Input.Keys.W:
				if(Gdx.input.isKeyPressed(Input.Keys.S)){
					Gdx.app.debug(TAG, "W released, S pressed");
					player.setMoveSpeed(0.0f,-player.getMoveRate());
					player.setDirection(Constants.DIRECTION_DOWN);
					player.update(0.0f);
					break;
				}else if(Gdx.input.isKeyPressed(Input.Keys.D)){
					Gdx.app.debug(TAG, "W released, D pressed");
					player.setMoveSpeed(player.getMoveRate(),0.0f);
					player.setDirection(Constants.DIRECTION_RIGHT);
					player.update(0.0f);
					break;
				} else if(Gdx.input.isKeyPressed(Input.Keys.A)){
					Gdx.app.debug(TAG, "W released, A pressed");
					player.setMoveSpeed(-player.getMoveRate(),0.0f);
					player.setDirection(Constants.DIRECTION_LEFT);
					player.update(0.0f);
					break;
				}

				// normal key release
				Gdx.app.debug(TAG, "W released");
				player.setMoveSpeed(0.0f,0.0f);
				player.update(0.0f);
				break;

			case Input.Keys.I:
				Gdx.app.debug(TAG, "I released");
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
			//Player control pressed
			case Input.Keys.SPACE:
				Gdx.app.debug(TAG, "SPACE pressed");
				//player attacks
				break;

			case Input.Keys.ESCAPE:
				Gdx.app.debug(TAG, "ESC pressed -> quitting game");
				Gdx.app.exit();
				break;

			case Input.Keys.ALT_RIGHT:
				WorldRenderer.toggleDebugDrawBox2dWorld();
				Gdx.app.debug(TAG, "ALT_RIGHT pressed -> Box 2D Debug Mode: " +
					WorldRenderer.getDebugDrawBox2dWorld());
				break;

			case Input.Keys.A:
				if(Gdx.input.isKeyPressed(Input.Keys.D)){
					//player can't move if pressing opposite directions
					Gdx.app.debug(TAG, "A pressed with D");
					player.setMoveSpeed(0.0f,0.0f);
					player.setDirection(Constants.DIRECTION_LEFT);
					player.update(0.0f);
					break;
				} else if(Gdx.input.isKeyPressed(Input.Keys.S)){
					Gdx.app.debug(TAG, "A pressed with S");
					player.setMoveSpeed(-player.getMoveRate(),-player.getMoveRate());
					player.setDirection(Constants.DIRECTION_LEFT);
					player.update(0.0f);
					break;
				} else if(Gdx.input.isKeyPressed(Input.Keys.W)){
					Gdx.app.debug(TAG, "A pressed with W");
					player.setMoveSpeed(-player.getMoveRate(),player.getMoveRate());
					player.setDirection(Constants.DIRECTION_LEFT);
					player.update(0.0f);
					break;
				}

				//normal single key pressed
				Gdx.app.debug(TAG, "A pressed");
				player.setMoveSpeed(-player.getMoveRate(),0.0f);
				player.setDirection(Constants.DIRECTION_LEFT);
				player.update(0.0f);
				break;

			case Input.Keys.D:
				if(Gdx.input.isKeyPressed(Input.Keys.A)){
					//player can't move if pressing opposite directions
					Gdx.app.debug(TAG, "D pressed with A");
					player.setMoveSpeed(0.0f,0.0f);
					player.setDirection(Constants.DIRECTION_RIGHT);
					player.update(0.0f);
					break;
				} else if(Gdx.input.isKeyPressed(Input.Keys.S)){
					Gdx.app.debug(TAG, "D pressed with S");
					player.setMoveSpeed(player.getMoveRate(),-player.getMoveRate());
					player.setDirection(Constants.DIRECTION_RIGHT);
					player.update(0.0f);
					break;
				} else if(Gdx.input.isKeyPressed(Input.Keys.W)){
					Gdx.app.debug(TAG, "D pressed with W");
					player.setMoveSpeed(player.getMoveRate(),player.getMoveRate());
					player.setDirection(Constants.DIRECTION_RIGHT);
					player.update(0.0f);
					break;
				}

				//normal single key pressed
				Gdx.app.debug(TAG, "D pressed");
				player.setMoveSpeed(player.getMoveRate(),0.0f);
				player.setDirection(Constants.DIRECTION_RIGHT);
				player.update(0.0f);
				break;

			case Input.Keys.S:
				if(Gdx.input.isKeyPressed(Input.Keys.W)){
					//player can't move if pressing opposite directions
					Gdx.app.debug(TAG, "S pressed with W");
					player.setMoveSpeed(0.0f,0.0f);
					player.setDirection(Constants.DIRECTION_DOWN);
					player.update(0.0f);
					break;
				} else if(Gdx.input.isKeyPressed(Input.Keys.D)){
					Gdx.app.debug(TAG, "S pressed with D");
					player.setMoveSpeed(player.getMoveRate(),-player.getMoveRate());
					player.setDirection(Constants.DIRECTION_DOWN);
					player.update(0.0f);
					break;
				} else if(Gdx.input.isKeyPressed(Input.Keys.A)){
					Gdx.app.debug(TAG, "S pressed with A");
					player.setMoveSpeed(-player.getMoveRate(),-player.getMoveRate());
					player.setDirection(Constants.DIRECTION_DOWN);
					player.update(0.0f);
					break;
				}

				//normal single key pressed
				Gdx.app.debug(TAG, "S pressed");
				player.setMoveSpeed(0.0f,-player.getMoveRate());
				player.setDirection(Constants.DIRECTION_DOWN);
				player.update(0.0f);
				break;

			case Input.Keys.W:
				if(Gdx.input.isKeyPressed(Input.Keys.S)){
					//player can't move if pressing opposite directions
					Gdx.app.debug(TAG, "W pressed with S");
					player.setMoveSpeed(0.0f,0.0f);
					player.setDirection(Constants.DIRECTION_UP);
					player.update(0.0f);
					break;
				} else if (Gdx.input.isKeyPressed(Input.Keys.D)){
					Gdx.app.debug(TAG, "W pressed with D");
					player.setMoveSpeed(player.getMoveRate(),player.getMoveRate());
					player.setDirection(Constants.DIRECTION_UP);
					player.update(0.0f);
					break;
				} else if (Gdx.input.isKeyPressed(Input.Keys.A)){
					Gdx.app.debug(TAG, "W pressed with A");
					player.setMoveSpeed(-player.getMoveRate(),player.getMoveRate());
					player.setDirection(Constants.DIRECTION_UP);
					player.update(0.0f);
					break;
				}

				//normal single key pressed
				Gdx.app.debug(TAG, "W pressed");
				player.setMoveSpeed(0.0f,player.getMoveRate());
				player.setDirection(Constants.DIRECTION_UP);
				player.update(0.0f);
				break;

			case Input.Keys.I:
				Gdx.app.debug(TAG, "I pressed");
				// player opens inventory
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
