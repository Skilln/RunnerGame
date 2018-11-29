package com.skilln.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.TimeUtils;


public class Man extends GameObject {

    private long time;

    private Animation<TextureRegion> animation;

    public Man(float x, float y, GameId id) {
        super(x, y, id);

        TextureAtlas atlas = new TextureAtlas("sprites/man/Man.atlas");

        animation = new Animation<TextureRegion>(0.1f, atlas.getRegions());

        width = 512;
        height = 512;

        time = TimeUtils.millis();

    }

    float stateTime = 0;

    @Override
    public void render(SpriteBatch batch) {

        update();

        TextureRegion region;

        if(TimeUtils.millis() - time >= 70) {

            stateTime += Gdx.graphics.getDeltaTime();

            region = animation.getKeyFrame(stateTime, false);
        } else {
            region = animation.getKeyFrame(0, false);
        }

        sprite = new Sprite(region);

        sprite.setX(x);
        sprite.setY(y);

        sprite.draw(batch);


    }

    @Override
    public void update() {
        y -= Player.ySpeed;

    }
}
