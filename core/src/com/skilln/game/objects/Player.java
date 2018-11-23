package com.skilln.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.skilln.game.Game;

public class Player extends GameObject {

    public float xSpeed;

    public Player(float x, float y, GameId id) {
        super(x, y, id);

        width = 64;
        height = 64;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(sprite, x, y, width, height);

        x+=xSpeed;
        if(x < 0) x = 0;
        if(x > Game.width-width) x = Game.width-width;

        update();
    }

    @Override
    public void update() {
      if(ObjectHandler.checkCollision()) {

          Game.live = false;
      }
    }

}
