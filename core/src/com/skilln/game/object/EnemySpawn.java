package com.skilln.game.object;

import com.badlogic.gdx.Gdx;
import com.skilln.game.Application;

import java.util.Random;

public class EnemySpawn extends Thread implements Runnable {

    private GameStage stage;

    public EnemySpawn(GameStage stage) {
        this.stage = stage;
    }

    public void run() {
            final Random random = new Random();

            final int w = (random.nextInt(2)+1)*140;

            final int x = random.nextInt(Application.width-w);
            final int y = Application.height + random.nextInt(200);

            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    GameObject object = new Enemy(GameId.Enemy);

                    object.setX(x);
                    object.setY(y);

                  //  object.setWidth(w);
                  //  object.setHeight(w+40);

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
