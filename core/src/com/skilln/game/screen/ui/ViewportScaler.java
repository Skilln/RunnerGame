package com.skilln.game.screen.ui;

import com.badlogic.gdx.Gdx;
import com.skilln.game.GameConfig;

public class ViewportScaler {

    public static int GAME_WIDTH;
    public static int GAME_HEIGHT;

    public static int DELTA_GAME_WIDTH;

    public static void scale() {
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        GAME_HEIGHT = (int) (GameConfig.GAME_WIDTH * (height / width));
        GAME_WIDTH = (int) (GAME_HEIGHT / (16f / 9f));

        DELTA_GAME_WIDTH = GAME_WIDTH - GameConfig.GAME_WIDTH;

    }


}
