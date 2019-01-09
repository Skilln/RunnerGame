package com.skilln.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.skilln.game.Application;
import com.skilln.game.GameState;

import java.util.HashMap;

public class ScreenManager {

    public static final String MENU_SCREEN = "MENU_SCREEN";
    public static final String GAME_SCREEN = "GAME_SCREEN";

    //private static Screen[] screens = new Screen[2];
    private static HashMap<GameState, Screen> screens;

    private static Game game;

    private static Screen currentScreen;

    public static void init(Game game) {
        ScreenManager.game = game;

        screens = new HashMap<GameState, Screen>();

        screens.put(GameState.MENU, new MenuScreen());
        screens.put(GameState.GAME, new GameScreen());
        screens.put(GameState.GAMEOVER, new GameOverScreen());
        screens.put(GameState.INFO, new InfoScreen());

    }

    public static void setScreen(GameState state) {
        game.setScreen(screens.get(state));
        Application.currentState = state;
    }

    public static Screen getCurrentScreen() {
        return currentScreen;
    }
}
