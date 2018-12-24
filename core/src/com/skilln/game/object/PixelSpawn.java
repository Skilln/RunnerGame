package com.skilln.game.object;

import com.badlogic.gdx.Gdx;
import com.skilln.game.Application;

import java.util.Random;

public class PixelSpawn extends Thread implements Runnable {

    private GameStage stage;

    public PixelSpawn(GameStage stage) {
        this.stage = stage;
    }

    @Override
    public void run() {
        final Random random = new Random();

        final int x = random.nextInt(150)+Application.width-200;
        final int x1 = random.nextInt(150);

        final int y = Application.height + random.nextInt(200);
        final int y1 = Application.height + random.nextInt(300);

        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                GameObject object = new Pixel(GameId.Pixel);
                GameObject object1 = new Pixel(GameId.Pixel);

                object.setX(x);
                object.setY(y);

                object1.setX(x1);
                object1.setY(y1);


                stage.addObject(object);
                stage.addObject(object1);
            }
        });

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
