package com.darrandyford.utils;

/**
 * Constants necessary for or helpful for the game will be housed here
 */
public class Constants {
	/** Desktop Client Constants **/
	public static final int DESKTOP_HEIGHT = 600;
	public static final int DESKTOP_WIDTH = 800;

	/** World Constants **/
	// This corresponds to how many pixels are in a meter
	public static final float WORLD_SCALE = 16.0f;
	// Visible game world is 40 meters wide
	public static final float VIEWPORT_WIDTH = 50.0f;
	// Visible game world is 30 meters tall
	public static final float VIEWPORT_HEIGHT = 40.0f;
	// GUI Width
	public static final float VIEWPORT_GUI_WIDTH = 800.0f;
	// GUI Height
	public static final float VIEWPORT_GUI_HEIGHT = 480.0f;

	/** Physics Constants **/
	// Gravity for the world
	public static final float NORMAL_GRAVITY = -9.81f;
	// Length of time in a physics step
	public static final float TIME_STEP = 1/120f;
	// Amount of moveSpeed iterations in a physics step
	public static final int VELOCITY_ITERATIONS = 8;
	// Amount of position iterations in a physics step
	public static final int POSITION_ITERATIONS = 3;

	/** Collision filter groups **/


	/** Collision masks **/

	/** Asset Constants **/
	// Location of descriptor file for texture atlas
	public static final String TEXTURE_ATLAS_OBJECTS = "atlas/worldtextures.atlas";

	/** Tile Constants **/
	// Width and Height of level tiles in meters
	public static final float TILE_WIDTH = 2.0f;
	public static final float TILE_HEIGHT = 2.0f;
	public enum TileType { FLOOR, WALL, OBJECT }

	/** Player Constants **/

	/** Enemy Constants **/

	/** Zone Constants **/
	public static final int ZONE_X_TILES = 25;
	public static final int ZONE_Y_TILES = 20;

	/** General constants for code readability **/
	public static final float DIRECTION_RIGHT = 0.0f;
	public static final float DIRECTION_LEFT = 180.0f;
	public static final float DIRECTION_UP = 90.0f;
	public static final float DIRECTION_DOWN = 270.0f;

}
