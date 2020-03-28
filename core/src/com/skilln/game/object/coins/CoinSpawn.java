package com.skilln.game.object.coins;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.skilln.game.GameConfig;
import com.skilln.game.object.GameId;
import com.skilln.game.object.GameObject;
import com.skilln.game.object.GameStage;
import com.skilln.game.screen.ui.ViewportScaler;

public class CoinSpawn {

    private GameStage stage;
    private float spawnPeriod = 30;
    private float spawnPoint = 15;

    public CoinSpawn(GameStage stage) {
        this.stage = stage;
    }

    public void update(float distance) {
        if (distance > spawnPoint) {
            spawnPoint += spawnPeriod;

            place();
        }
    }

    private void place() {

        Coin coin = new Coin(GameId.Coin);

        float x = (float) ((Math.random() * GameConfig.GAME_WIDTH - coin.getWidth()));
        float y = (float) (Math.random() * 100 + ViewportScaler.GAME_HEIGHT);

        coin.setX(x);
        coin.setY(y);

        stage.addObject(coin);

    }

    public void updatePeriod() {
        spawnPeriod *= GameConfig.GAME_SPEED_MULTIPLIER;
    }

}
