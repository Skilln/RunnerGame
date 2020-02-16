package com.skilln.game.object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.skilln.game.WayToHeaven;
import com.skilln.game.GameAtlas;
import com.skilln.game.screen.GameScreen;

public class Background extends GameObject {

    private Texture texture_0;
    private Texture texture_1;

    private int y = WayToHeaven.height;

    public Background(GameId id) {
        super(id);

        int a = (int) (Math.random() * 5);

        texture_0 = GameAtlas.background_0;

        texture_1 = GameAtlas.background[a];


        setWidth(WayToHeaven.width);
        setHeight(WayToHeaven.height);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(texture_0, -(WayToHeaven.widthFixed - WayToHeaven.width) / 2f, getY(), WayToHeaven.widthFixed, getHeight());
        batch.draw(texture_1, -(WayToHeaven.widthFixed - WayToHeaven.width) / 2f, y, WayToHeaven.widthFixed, getHeight());
    }

    public void update(float speed) {
        if (y - speed <= 0) {
            setY(0);
            y = WayToHeaven.height;

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
