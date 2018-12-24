package com.skilln.game.object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.skilln.game.GameAtlas;
import com.skilln.game.screen.GameScreen;

import java.util.Random;

public class Pixel extends GameObject {

    private Texture texture;
    private Random random;

    public Pixel(GameId id) {
        super(id);
        random = new Random();
        texture = GameAtlas.pixel;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(texture, getX(), getY());

        int a = random.nextInt(4);
        if(a == 0) {
            moveBy(0.5f, 0);
        } else if(a == 1) {
            moveBy(0, 0.5f);
        } else if(a == 2) {
            moveBy(-0.5f, 0);
        } else {
            moveBy(0, -0.5f);
        }

        moveBy(0, -GameScreen.speed);

    }

    @Override
    public Rectangle getHitBox() {
        return new Rectangle();
    }
}
