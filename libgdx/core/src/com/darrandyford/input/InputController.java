package com.darrandyford.input;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.darrandyford.utils.CameraHelper;
import com.darrandyford.world.WorldController;

public class InputController extends InputAdapter {
	private WorldController worldController;
	private static final String TAG = InputController.class.getName();

	public InputController(WorldController worldController)
	{
		Gdx.input.setInputProcessor(this);
		this.worldController = worldController;
	}

	@Override
	public boolean keyUp (int keycode) {
		// Reset game world
		if (keycode == Input.Keys.R) {
			worldController.reset();
			worldController.setInitRenderState(true);
			Gdx.app.debug(TAG, "Game world reset");
		}

		// Toggle camera follow
		else if (keycode == Input.Keys.ENTER) {
			Gdx.app.debug(TAG, "Enter released");
		}
		else if(keycode == Input.Keys.D)  {
			Gdx.app.debug(TAG, "D released");
		}
		else if (keycode == Input.Keys.A) {
			Gdx.app.debug(TAG, "A released");
			}
		return true;
	}

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
