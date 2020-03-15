package com.skilln.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.skilln.game.WayToHeaven;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

//        config.width = (1080 / 18) * 7;
//        config.height = (2340 / 18) * 7;

        config.width = (720 / 5) * 3;
        config.height = (1280 / 5) * 3;

        new LwjglApplication(new WayToHeaven(), config);
    }
}
