package com.skilln.game.object;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.skilln.game.Application;
import com.skilln.game.GameAtlas;
import com.skilln.game.screen.GameScreen;

public class Background extends GameObject {

    private Texture texture_0;
    private Texture texture_1;

    private int y = Application.height;

    public Background(GameId id) {
        super(id);

        texture_0 = GameAtlas.background_0;
        texture_1 = GameAtlas.background_1;

        setWidth(Application.width);
        setHeight(Application.height);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if(y - GameScreen.speed <= 0) {
            setY(0);
            y = Application.height;
            texture_0 = texture_1;
        } else {
            moveBy(0, -GameScreen.speed);
            y -= GameScreen.speed;
        }

        batch.draw(texture_0, getX(), getY(), getWidth(), getHeight());
        batch.draw(texture_1, getX(), y, getWidth(), getHeight());
    }

    @Override
    public Rectangle getHitBox() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
}
