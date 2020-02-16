package com.skilln.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.skilln.game.WayToHeaven;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width = (720/9)*6;
		config.height = (1280/9)*6;

		new LwjglApplication(new WayToHeaven(), config);
	}
}
