package com.skilln.game.objects;

import com.badlogic.gdx.Gdx;
import com.skilln.game.Application;

import java.util.Random;

public class EnemySpawn extends Thread implements Runnable {

    public void run() {
            final Random random = new Random();

            final int x = random.nextInt(Application.width);
            final int y = Application.height + random.nextInt(200);

            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    ObjectHandler.addObject(new Enemy(x, y, GameId.Enemy));
                }
            });

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
}
