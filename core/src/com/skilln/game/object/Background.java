package com.skilln.game.object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.skilln.game.WayToHeaven;
import com.skilln.game.GameAtlas;
import com.skilln.game.screen.GameScreen;
import com.skilln.game.screen.ui.ViewportScaler;

public class Background extends GameObject {

    private Texture texture_0;
    private Texture texture_1;

    private int y = ViewportScaler.GAME_HEIGHT;

    public Background(GameId id) {
        super(id);

        int a = (int) (Math.random() * 5);

        texture_0 = GameAtlas.background_0;

        texture_1 = GameAtlas.background[a];


        setWidth(ViewportScaler.GAME_WIDTH);
        setHeight(ViewportScaler.GAME_HEIGHT);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(texture_0, -(ViewportScaler.DELTA_GAME_WIDTH) / 2f, getY(), ViewportScaler.GAME_WIDTH, getHeight());
        batch.draw(texture_1, -(ViewportScaler.DELTA_GAME_WIDTH) / 2f, y, ViewportScaler.GAME_WIDTH, getHeight());
    }

    public void update(float speed) {
        if (y - speed <= 0) {
            setY(0);
            y = ViewportScaler.GAME_HEIGHT;

            int a = (int) (Math.random() * 5);

            texture_0 = texture_1;

            texture_1 = GameAtlas.background[a];


        } else {
            moveBy(0, -speed);
            y -= speed;
        }
    }


    @Override
    public Circle getHitBox() {
        return new Circle(getX(), getY(), getWidth() / 2);
    }
}
