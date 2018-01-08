package com.darrandyford.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;
import com.darrandyford.utils.Constants;


public class Assets implements Disposable, AssetErrorListener {

	// Set the TAG constant for logging
	public static final String TAG = Assets.class.getName();
	public static final Assets instance = new Assets();
	private AssetManager assetManager;

	public AssetPlayer player;
	public AssetEnemy enemy;
	public AssetGround ground;
	public AssetWall wall;

	// singleton: prevent instantiation from other classes
	private Assets () {}



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
	}

	@Override
	public void dispose () {
		assetManager.dispose();
	}

	@Override
	public void error (AssetDescriptor asset, Throwable throwable) {
		Gdx.app.error(TAG, "Couldn't load asset '" + asset.fileName + "'",
			(Exception)throwable);
	}

	/* The following code contains the inner asset classes for Assets */
	/* -------------------------------------------------------------- */

	// Asset inner class for the player assets
	public class AssetPlayer {
		public final TextureAtlas.AtlasRegion left, up, down;

		public AssetPlayer (TextureAtlas atlas) {
			left = atlas.findRegion("CH_PLACEHOLDER_01_SL_32");
			up = atlas.findRegion("CH_PLACEHOLDER_01_SU_32");
			down = atlas.findRegion("CH_PLACEHOLDER_01_SD_32");
		}
	}

	// Asset inner class for the enemy assets
	public class AssetEnemy {
		public final TextureAtlas.AtlasRegion left, up, down;

		public AssetEnemy (TextureAtlas atlas) {
			left = atlas.findRegion("EN_PLACEHOLDER_01_SL_32");
			up = atlas.findRegion("EN_PLACEHOLDER_01_SU_32");
			down = atlas.findRegion("EN_PLACEHOLDER_01_SD_32");

		}
	}

	// Asset inner class for a darkness tile
	public class AssetGround {
		public final TextureAtlas.AtlasRegion terrain;

		public AssetGround(TextureAtlas atlas) {
			terrain = atlas.findRegion("TR_PLACEHOLDER_01_X_32");
		}
	}

	// Asset inner class for a wall tile
	public class AssetWall {
		public final TextureAtlas.AtlasRegion wall;

		public AssetWall (TextureAtlas atlas) {
			wall = atlas.findRegion("HO_PLACEHOLDER_01_X_32");
		}
	}

}
