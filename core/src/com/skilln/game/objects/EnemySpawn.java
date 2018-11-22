package com.skilln.game.objects;

import com.badlogic.gdx.Gdx;
import com.skilln.game.Game;

import java.util.Random;

public class EnemySpawn implements Runnable {

    public void run() {
        while(Game.live) {
            final Random random = new Random();

            final int x = random.nextInt(Gdx.graphics.getWidth());
            final int y = Gdx.graphics.getHeight() + random.nextInt(200);

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
}
