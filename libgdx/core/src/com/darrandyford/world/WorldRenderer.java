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

	/**
	 * Constructor.
	 * @param worldController the world controller we need to reference for rendering purposes
	 */
	public WorldRenderer (WorldController worldController) {
		this.worldController = worldController;
		init();
	}

	/**
	 * Initialize the renderer. This will set up the camera, perspective, etc.
	 */
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

	/**
	 * Go through a render cycle - generally called at specified frames.
	 */
	public void render () {
		if(worldController.getInitRenderState())
		{
			worldController.setInitRenderState(false);
		}
		renderWorld();
		renderGUI();
	}

	/**
	 * Render the world - player, enemies, level, etc.
	 */
	public void renderWorld() {
		camera.update();
		worldController.getCameraHelper().applyTo(camera);
		renderer.setView(camera);
		renderLevel();
		if(DEBUG_DRAW_BOX2D_WORLD) { renderPhysicsDebugLines(); }
		renderPlayer();
		renderEnemies();
	}

	/**
	 * Render the GUI - generally an overlay of controls, info, etc. over the world itself.
	 */
	public void renderGUI()
	{
		renderFPS();
	}

	/**
	 * Called when the window or viewport gets resized somehow
	 * @param width
	 * @param height
	 */
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

	/**
	 * Force some necessary garbage collection
	 */
	@Override public void dispose () {
		renderer.dispose();
	}

	/**
	 * Render the player
	 */
	private void renderPlayer()
	{
	}

	/**
	 * Render the enemies
	 */
	private void renderEnemies()
	{
	}

	/**
	 * Render the level
	 */
	private void renderLevel()
	{
	}

	/**
	 * Render physics debug lines - useful when trying to understand what is happening in strange physics interactions
	 */
	private void renderPhysicsDebugLines() {
	}

	/**
	 * Render the FPS display
	 */
	private void renderFPS()
	{
	}

}
