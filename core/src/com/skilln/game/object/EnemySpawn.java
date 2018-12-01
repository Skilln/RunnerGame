package com.skilln.game.object;

import com.badlogic.gdx.Gdx;
import com.skilln.game.Application;
import com.skilln.game.object.GameId;
import com.skilln.game.object.GameObject;
import com.skilln.game.object.GameStage;
import com.skilln.game.object.Enemy;

import java.util.Random;

public class EnemySpawn extends Thread implements Runnable {

    private GameStage stage;

    public EnemySpawn(GameStage stage) {
        this.stage = stage;
    }

    public void run() {
            final Random random = new Random();

            final int x = random.nextInt(Application.width);
            final int y = Application.height + random.nextInt(200);

            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    GameObject object = new Enemy(GameId.Enemy);

                    object.setX(x);
                    object.setY(y);

                    stage.addObject(object);
                }
            });

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
}
