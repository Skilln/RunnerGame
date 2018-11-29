package com.skilln.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.skilln.game.screen.GameScreen;

import java.util.Random;

public class Enemy extends GameObject {

    private Random random = new Random();

    private Animation<TextureRegion> animation;

    public Enemy(float x, float y, GameId id) {
        super(x, y, id);

        TextureAtlas atlas = new TextureAtlas("sprites/rock/Rock.atlas");

        animation = new Animation<TextureRegion>(0.5f, atlas.getRegions(), Animation.PlayMode.LOOP);


        width = random.nextInt(196)+64;
        height = width;

    }

    float stateTime = 0;

    @Override
    public void render(SpriteBatch batch) {

        stateTime += Gdx.graphics.getDeltaTime();

        TextureRegion region = animation.getKeyFrame(stateTime, true);

        sprite = new Sprite(region);

        sprite.setX(x);
        sprite.setY(y);

        sprite.draw(batch);

        y-=(Player.ySpeed);

    }

    @Override
    public void update() {

    }
}
