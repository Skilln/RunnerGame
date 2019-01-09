package com.skilln.game.object;

import com.badlogic.gdx.Gdx;
import com.skilln.game.Application;
import com.skilln.game.screen.GameScreen;

import java.util.Random;

public class EnemySpawn extends Thread implements Runnable {

    private GameStage stage;

    public EnemySpawn(GameStage stage) {
        this.stage = stage;
    }

    public void run() {
            Random random = new Random();

            int count;
            int chance = 0;

            if((int)GameScreen.distance < 200) {
                chance = 8;
            } else {
                chance = 4;
            }


            if(random.nextInt(chance) == 0) {
                count = 2;
            } else {
                count = 1;
            }

            final GameObject[] object = new GameObject[count];

            object[0] = new Enemy(GameId.Enemy);

            object[0].setX(random.nextInt(Application.width-(int)object[0].getWidth()));
            object[0].setY(Application.height + random.nextInt(200));

            if(count != 1) {
               while (true) {
                   object[1] = new Enemy((GameId.Enemy));

                   object[1].setX(random.nextInt(Application.width-(int)object[0].getWidth()));
                   object[1].setY(Application.height + random.nextInt(400));

                   if(!object[0].getHitBox().overlaps(object[1].getHitBox())) break;
               }
            }

            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                  for(int i = 0; i < object.length; i++) {
                      stage.addObject(object[i]);
                  }
                }
            });

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
}
