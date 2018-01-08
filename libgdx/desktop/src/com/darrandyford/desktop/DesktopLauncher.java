package com.darrandyford.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.darrandyford.DarrAndyFord;
import com.darrandyford.utils.Constants;

public class DesktopLauncher {
	private static boolean rebuildAtlas = true;
	private static boolean drawDebugOutline = false;

	public static void main (String[] arg) {
		if (rebuildAtlas) {
			TexturePacker.Settings settings = new TexturePacker.Settings();
			settings.maxWidth = 1024;
			settings.maxHeight = 1024;
			settings.duplicatePadding = true;
			settings.debug = drawDebugOutline;
			settings.combineSubdirectories = true;
			TexturePacker.process(settings, "./",
				"atlas", "worldtextures");
		}

		LwjglApplicationConfiguration config =
			new LwjglApplicationConfiguration();

		config.title = "DarrAndyFord Development Build v0.1";
		config.width = Constants.DESKTOP_WIDTH;
		config.height = Constants.DESKTOP_HEIGHT;

		new LwjglApplication(new DarrAndyFord(), config);
	}
}
