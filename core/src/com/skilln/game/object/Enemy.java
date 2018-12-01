package com.skilln.game.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.skilln.game.screen.GameScreen;

import java.util.Random;

public class Enemy extends GameObject {

    private Random random = new Random();

    private Animation<TextureRegion> animation;

    public Enemy(GameId id) {
        super(id);

        TextureAtlas atlas = new TextureAtlas("sprites/rock/Rock.atlas");

        animation = new Animation<TextureRegion>(0.5f, atlas.getRegions(), Animation.PlayMode.LOOP);

        setWidth(random.nextInt(196)+64);
        setHeight(width);
    }

    float stateTime = 0;

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        stateTime += Gdx.graphics.getDeltaTime();

        TextureRegion region = animation.getKeyFrame(stateTime, true);

        setSprite(new Sprite(region));

        sprite.draw(batch, parentAlpha);

       moveBy(0, -GameScreen.speed);
    }
}
