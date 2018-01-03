package com.darrandyford.utils;

public class Constants {
	/** Desktop Client Constants **/
	public static final int DESKTOP_HEIGHT = 600;
	public static final int DESKTOP_WIDTH = 800;

	/** World Constants **/
	// Visible game world is 5 meters wide
	public static final float VIEWPORT_WIDTH = 5.0f;
	// Visible game world is 5 meters tall
	public static final float VIEWPORT_HEIGHT = 5.0f;
	// GUI Width
	public static final float VIEWPORT_GUI_WIDTH = 800.0f;
	// GUI Height
	public static final float VIEWPORT_GUI_HEIGHT = 480.0f;
	// This corresponds to how many pixels are in a meter
	public static final float WORLD_SCALE = 16.0f;

	/** Physics Constants **/
	// Gravity for the world
	public static final float NORMAL_GRAVITY = -9.81f;
	// Length of time in a physics step
	public static final float TIME_STEP = 1/120f;
	// Amount of velocity iterations in a physics step
	public static final int VELOCITY_ITERATIONS = 8;
	// Amount of position iterations in a physics step
	public static final int POSITION_ITERATIONS = 3;

	/** Collision filter groups **/


	/** Collision masks **/

	/** Asset Constants **/

	/** Level Tile Constants **/

	/** Player Constants **/

	/** Enemy Constants **/

	/** General constants for code readability **/
	public static final float DIRECTION_RIGHT = 0.0f;
	public static final float DIRECTION_LEFT = 180.0f;
	public static final float DIRECTION_UP = 270.0f;
	public static final float DIRECTION_DOWN = 90.0f;

}
