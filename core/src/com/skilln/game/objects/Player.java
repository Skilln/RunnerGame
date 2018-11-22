package com.skilln.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.skilln.game.Game;

public class Player extends GameObject {

    public float xSpeed, ySpeed;

    public Player(float x, float y, GameId id) {
        super(x, y, id);

        width = 64;
        height = 64;

        ySpeed = 1;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(sprite, x, y, width, height);

        x+=xSpeed;
        if(x < 0) x = 0;
        if(x > Gdx.graphics.getWidth()-width) x = Gdx.graphics.getWidth()-width;

        update();
    }

    @Override
    public void update() {
      if(ObjectHandler.checkCollision()) {

          Game.live = false;
      }
    }

}
