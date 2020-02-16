package com.skilln.game.object;

import com.badlogic.gdx.Gdx;
import com.skilln.game.WayToHeaven;
import com.skilln.game.screen.GameScreen;

import java.util.Random;

public class EnemySpawn extends Thread implements Runnable {

    private GameStage stage;

    public EnemySpawn(GameStage stage) {
        this.stage = stage;
    }

    public void run() {
        Random random = new Random();
        boolean coin = false;

        int count;

        if (random.nextInt(8) == 0) {
            count = 2;
        } else {
            count = 1;
        }

        if (random.nextInt(20) == 0) {
            count++;
            coin = true;
        }

        final GameObject[] object = new GameObject[count];

        object[0] = new Enemy(GameId.Enemy);

        object[0].setX(random.nextInt(WayToHeaven.width - (int) object[0].getWidth()));
        object[0].setY(WayToHeaven.height + random.nextInt(200));

        if (count != 1) {
            while (true) {
                object[1] = new Enemy((GameId.Enemy));

                object[1].setX(random.nextInt(WayToHeaven.width - (int) object[1].getWidth()));
                object[1].setY(WayToHeaven.height + random.nextInt(400));

                if (!object[0].getHitBox().overlaps(object[1].getHitBox())) break;
            }
            while (coin) {
                object[count - 1] = new Coin(GameId.Coin);

                object[count - 1].setX(random.nextInt(WayToHeaven.width - (int) object[count - 1].getWidth()));
                object[count - 1].setY(WayToHeaven.height + random.nextInt(400));

                if (count == 3 && !object[0].getHitBox().overlaps(object[count - 1].getHitBox()) &&
                        !object[1].getHitBox().overlaps(object[count - 1].getHitBox())) break;
                if (count < 3 && !object[0].getHitBox().overlaps(object[count - 1].getHitBox()))
                    break;
            }

        }

        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < object.length; i++) {
                    stage.addObject(object[i]);
                }
            }
        });

        try {
            if ((GameScreen.distance * 4) < 1250) {
                Thread.sleep(1500 - (int) (GameScreen.distance * 4));
            } else {
                Thread.sleep(250);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
