package com.skilln.game.screen;

import com.badlogic.gdx.Screen;

public class ScreenManager {

    public static final String MENU_SCREEN = "MENU_SCREEN";
    public static final String GAME_SCREEN = "GAME_SCREEN";

    private static Screen[] screens = new Screen[2];

    private static Screen currentScreen;

    public static void init() {
        screens[0] = new MenuScreen();
        screens[1] = new GameScreen();
    }

    public static void setScreen(String screen) {
        if(screen.equals(MENU_SCREEN)) {
            currentScreen = screens[0];
        }

        if(screen.equals(GAME_SCREEN)) {
            currentScreen = screens[1];
        }
    }

    public static Screen getCurrentScreen() {
        return currentScreen;
    }
}
