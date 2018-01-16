package com.darrandyford.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;
import com.darrandyford.utils.Constants;

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
		protected TextureAtlas.AtlasRegion region;

		public TextureAtlas.AtlasRegion getRegion() { return region; }
	}

	/**
	 * 	Asset inner class for the player assets
 	 */
	public class AssetPlayer extends AssetGenericLiving {

		public AssetPlayer (TextureAtlas atlas) {
			left = atlas.findRegion("CH_PLACEHOLDER_01_32_SL");
			up = atlas.findRegion("CH_PLACEHOLDER_01_32_SU");
			down = atlas.findRegion("CH_PLACEHOLDER_01_32_SD");
		}
	}

	/**
	 * Asset inner class for the enemy assets
 	 */
	public class AssetEnemy extends AssetGenericLiving {

		public AssetEnemy (TextureAtlas atlas) {
			left = atlas.findRegion("EN_PLACEHOLDER_01_32_SL");
			up = atlas.findRegion("EN_PLACEHOLDER_01_32_SU");
			down = atlas.findRegion("EN_PLACEHOLDER_01_32_SD");

		}
	}

	/**
	 * Asset inner class for a ground tile
	 */
	public class AssetGround extends AssetGenericNonLiving {

		public AssetGround(TextureAtlas atlas) {
			region = atlas.findRegion("TR_DIRTROCKS_V2_01_32_X");
		}
	}

	/**
	 * Asset inner class for a wall tile
 	 */
	public class AssetWall extends AssetGenericNonLiving {

		public AssetWall (TextureAtlas atlas) {
			region = atlas.findRegion("HO_PLACEHOLDER_01_32_X");
		}
	}

	/**
	 * Asset inner class for an object tile
	 */
	public class AssetObject extends AssetGenericNonLiving {

		public AssetObject (TextureAtlas atlas) {
			region = atlas.findRegion("IT_PLACEHOLDER_01_32_X");
		}
	}

	/*******************************************************************/

}
