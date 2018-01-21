package com.darrandyford.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;
import com.darrandyford.utils.Constants;

import java.util.HashMap;

/**
 * Handles asset management. In particular, creates the asset manager and handles unpacking the atlas into
 * defined assets with textures for loading during render.
 */
public class Assets implements Disposable, AssetErrorListener {

	// Set the TAG constant for logging
	public static final String TAG = Assets.class.getName();
	private AssetManager assetManager;
	public static final Assets instance = new Assets();

	public AssetPlayer player;
	public AssetEnemy enemy;
	public AssetGround ground;
	public AssetWall wall;
	public AssetObject object;

	// singleton: prevent instantiation from other classes
	private Assets () {}


	/**
	 * Initialization will handle creation of the asset manager and loading of particular assets from the atlas
	 * @param assetManager will be passed from the main game class
	 */
	public void init (AssetManager assetManager) {
		this.assetManager = assetManager;
		// set asset manager error handler
		assetManager.setErrorListener(this);
		// load texture atlas
		assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS,
			TextureAtlas.class);
		// load sounds
		assetManager.load("audio/sounds/slime_sound.wav", Sound.class);
		assetManager.load("audio/sounds/item_sound.wav", Sound.class);
		assetManager.load("audio/sounds/alert_sound.wav", Sound.class);
		assetManager.load("audio/music/cave_music2.ogg", Music.class);
		// start loading assets and wait until finished
		assetManager.finishLoading();
		Gdx.app.debug(TAG, "# of assets loaded: " +
			assetManager.getAssetNames().size);
		for (String a : assetManager.getAssetNames()) {
			Gdx.app.debug(TAG, "asset: " + a);
		}

		TextureAtlas atlas =
			assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);

		// enable texture filtering for pixel smoothing
		for (Texture t : atlas.getTextures()) {
			t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		}

		// create game resource objects
		player = new AssetPlayer(atlas);
		enemy = new AssetEnemy(atlas);
		ground = new AssetGround(atlas);
		wall = new AssetWall(atlas);
		object = new AssetObject(atlas);

	}

	/**
	 * Since these are disposable, we need to make sure we properly dispose of them
	 */
	@Override
	public void dispose () {
		assetManager.dispose();
	}

	/**
	 * Error handling
	 * @param asset handle to the asset that threw the error
	 * @param throwable handle to the exception we received
	 */
	@Override
	public void error (AssetDescriptor asset, Throwable throwable) {
		Gdx.app.error(TAG, "Couldn't load asset '" + asset.fileName + "'",
			(Exception)throwable);
	}

	/* The following code contains the inner asset classes for Assets */
	/* -------------------------------------------------------------- */

	/**
	 * Allows for easy instantiation of living entities
	 */
	public abstract class AssetGenericLiving {
		protected TextureAtlas.AtlasRegion left, up, down;

		public TextureAtlas.AtlasRegion getLeft() { return left; }
		public TextureAtlas.AtlasRegion getUp() { return up; }
		public TextureAtlas.AtlasRegion getDown() { return down; }
	}

	/**
	 * Allows for easy instantiation of nonliving entities
	 */
	public abstract class AssetGenericNonLiving {
		protected HashMap<String, TextureAtlas.AtlasRegion> regions = new HashMap<String, TextureAtlas.AtlasRegion>();

		public HashMap<String, TextureAtlas.AtlasRegion> getRegions() { return regions; }
	}

	/**
	 * 	Asset inner class for the player assets
 	 */
	public class AssetPlayer extends AssetGenericLiving {

		public AssetPlayer (TextureAtlas atlas) {
			left = atlas.findRegion("CH_BASIC_01_1632_SS");
			up = atlas.findRegion("CH_BASIC_01_1632_SS");
			down = atlas.findRegion("CH_BASIC_01_1632_SS");
		}
	}

	/**
	 * Asset inner class for the enemy assets
 	 */
	public class AssetEnemy extends AssetGenericLiving {

		public AssetEnemy (TextureAtlas atlas) {
			left = atlas.findRegion("EN_BLOB_01_16_S");
			up = atlas.findRegion("EN_BLOB_01_16_S");
			down = atlas.findRegion("EN_BLOB_01_16_S");

		}
	}

	/**
	 * Asset inner class for a ground tile
	 */
	public class AssetGround extends AssetGenericNonLiving {

		public AssetGround(TextureAtlas atlas) {
			regions.put("dirtrocks", atlas.findRegion("TR_DIRTROCKS_V2_01_32_X"));
			regions.put("assorted", atlas.findRegion("TR_ASSORTED_01_32_X"));
			regions.put("empty", atlas.findRegion("TR_EMPTY_01_32_X"));
			regions.put("specks", atlas.findRegion("TR_SPECKS_01_32_X"));
			regions.put("dirtrocks2", atlas.findRegion("TR_DIRTROCKS_V3_01_32_X"));
			regions.put("dirtrocks3", atlas.findRegion("TR_DIRTROCKS_V4_01_32_X"));
		}
	}

	/**
	 * Asset inner class for a wall tile
 	 */
	public class AssetWall extends AssetGenericNonLiving {

		public AssetWall (TextureAtlas atlas) {

			regions.put("wall", atlas.findRegion("HO_CAVEWALL_01_32_X"));
			regions.put("cornerwall", atlas.findRegion(("HO_CAVEWALLCORNER_01_32_X")));
		}
	}

	/**
	 * Asset inner class for an object tile
	 */
	public class AssetObject extends AssetGenericNonLiving {

		public AssetObject (TextureAtlas atlas) {
			regions.put("object", atlas.findRegion("IT_PLACEHOLDER_01_32_X"));
			regions.put("stick", atlas.findRegion("IT_STICK_01_16_X"));
			regions.put("stickbundle", atlas.findRegion("SO_STICKBUNDLE_01_32_X"));
		}
	}

	/*******************************************************************/

	public void playSound(String soundType, float volume) {
		Sound thisSound = null;
		if(soundType.contains("slime")) thisSound = assetManager.get("audio/sounds/slime_sound.wav", Sound.class);
		else if(soundType.contains("item"))	thisSound = assetManager.get("audio/sounds/item_sound.wav", Sound.class);
		else if(soundType.contains("alert")) thisSound = assetManager.get("audio/sounds/alert_sound.wav", Sound.class);
		if(thisSound != null) {
			thisSound.play(volume);
		}
		else Gdx.app.error(TAG, "Couldn't play sound:" + soundType);

	}

	public void playSound(String soundType) {
		playSound(soundType, 1.0f);
	}

	public void playMusic(String musicType) {
		Music thisMusic = null;
		if(musicType.contains("cave")) thisMusic = assetManager.get("audio/music/cave_music2.ogg", Music.class);
		if(thisMusic != null) {
			thisMusic.setLooping(true);
			thisMusic.setVolume(0.3f);
			thisMusic.play();
		}
		else Gdx.app.error(TAG, "Couldn't play sound:" + musicType);

	}

}
