package com.skilln.game.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.skilln.game.GameAtlas;
import com.skilln.game.screen.GameScreen;

import java.util.Random;

public class Enemy extends GameObject {

    private Random random = new Random();

    private Animation<TextureRegion> animation;

    private int size;

    public Enemy(GameId id) {
        super(id);


        int type = random.nextInt(2);

        int s = random.nextInt(5);
        size = s % 2 + 1;

        TextureAtlas atlas;

        if (type == 0) {
            atlas = GameAtlas.soul_hole;
        } else {
            atlas = GameAtlas.soul_hole1;
        }

        animation = new Animation<TextureRegion>(1f / 10f, atlas.getRegions(), Animation.PlayMode.LOOP);

        //  sprite = new Sprite(atlas.getRegions().get(random.nextInt(11)));

        setWidth(atlas.getRegions().first().getRegionWidth() * size);
        setHeight(atlas.getRegions().first().getRegionHeight() * size);

    }

    float stateTime = 0;

    @Override
    public Circle getHitBox() {
        return new Circle(getX() + getWidth() / 2, getY() + getHeight() / 2, getWidth() / 2 - (20 * size));
    }

    @Override
    public void update(float worldSpeed) {
        moveBy(0, -worldSpeed);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        stateTime += Gdx.graphics.getDeltaTime();

        TextureRegion region = animation.getKeyFrame(stateTime, true);

        batch.draw(region, getX(), getY(), getWidth(), getHeight());
    }

}
