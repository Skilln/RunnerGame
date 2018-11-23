package com.skilln.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.skilln.game.GameScreen;

import java.util.Random;

public class Enemy extends GameObject {

    private Random random = new Random();

    public Enemy(float x, float y, GameId id) {
        super(x, y, id);

        width = random.nextInt(196)+64;
        height = width;


    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(sprite, x, y, width, height);

        y-=(GameScreen.ySpeed);
    }

    @Override
    public void update() {
        if(y < 0) {
            ObjectHandler.removeObject(this);
        }
    }
}
