package com.darrandyford.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.darrandyford.utils.Constants;

/**
 * Handle rendering of the world. This primarily involves:
 * Cameras
 * Textures
 * Display
 */
public class WorldRenderer implements Disposable {
	private OrthographicCamera camera;
	private OrthographicCamera cameraGUI;
	private OrthogonalTiledMapRenderer renderer;
	private WorldController worldController;

	// Rendering constants/switches
	private static final boolean DEBUG_DRAW_BOX2D_WORLD = true;

	public WorldRenderer (WorldController worldController) {
		this.worldController = worldController;
		init();
	}

	private void init () {
		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH,
			Constants.VIEWPORT_HEIGHT);
		camera.position.set(0, 0, 0);
		camera.update();

		cameraGUI = new OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH,
			Constants.VIEWPORT_GUI_HEIGHT);
		cameraGUI.position.set(0, 0, 0);
		cameraGUI.setToOrtho(false);
		cameraGUI.update();
	}

	public void render () {
		if(worldController.getInitRenderState())
		{
			worldController.setInitRenderState(false);
		}
		renderWorld();
		renderGUI();
	}

	public void renderWorld() {
		camera.update();
		worldController.getCameraHelper().applyTo(camera);
		renderer.setView(camera);
		renderMap();
		if(DEBUG_DRAW_BOX2D_WORLD) { renderPhysicsDebugLines(); }
		renderPlayer();
		renderEnemies();
	}

	public void renderGUI()
	{
		renderFPS();
	}

	public void resize (int width, int height) {
		camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
		camera.update();
		cameraGUI.viewportHeight = Constants.VIEWPORT_GUI_HEIGHT;
		cameraGUI.viewportWidth = (Constants.VIEWPORT_GUI_HEIGHT /
			(float)height) * (float)width;
		cameraGUI.position.set(cameraGUI.viewportWidth / 2,
			cameraGUI.viewportHeight / 2, 0);
		cameraGUI.update();
	}

	@Override public void dispose () {
		renderer.dispose();
	}

	private void renderPlayer()
	{
	}

	private void renderEnemies()
	{
	}

	private void renderMap()
	{
	}

	private void renderPhysicsDebugLines() {
	}

	private void renderFPS()
	{
	}

}
