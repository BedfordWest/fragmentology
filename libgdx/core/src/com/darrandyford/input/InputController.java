package com.darrandyford.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.darrandyford.world.WorldController;

/**
 * Handles user input
 */
public class InputController extends InputAdapter {
	private WorldController worldController;
	private static final String TAG = InputController.class.getName();

	/**
	 * Constructor
	 * @param worldController the world controller
	 */
	public InputController(WorldController worldController)
	{
		Gdx.input.setInputProcessor(this);
		this.worldController = worldController;
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

}
