package com.skilln.game.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.skilln.game.Application;

public class Player extends GameObject {

    private float xSpeed;

    private Animation<TextureRegion> animation_up, animation_left, animation_right;

    public Player(GameId id) {
        super(id);

        TextureAtlas animation_up_atlas = new TextureAtlas("sprites/soul/Soul.atlas");
        TextureAtlas animation_left_atlas = new TextureAtlas("sprites/soul/Soul_left.atlas");
        TextureAtlas animation_right_atlas = new TextureAtlas("sprites/soul/Soul_right.atlas");

        animation_up = new Animation<TextureRegion>(0.11f, animation_up_atlas.getRegions(),
                Animation.PlayMode.LOOP);

        animation_left = new Animation<TextureRegion>(0.11f, animation_left_atlas.getRegions(),
                Animation.PlayMode.LOOP);

        animation_right = new Animation<TextureRegion>(0.11f, animation_right_atlas.getRegions(),
                Animation.PlayMode.LOOP);

        setWidth(120);
        setHeight(170);
        setAlpha(0.01f);
    }

    float stateTime = 0;

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        stateTime += Gdx.graphics.getDeltaTime();

        TextureRegion region = animation_up.getKeyFrame(stateTime, true);

        if(xSpeed < 0) {
            region = animation_left.getKeyFrame(stateTime, true);
        } else if(xSpeed > 0) {
            region = animation_right.getKeyFrame(stateTime, true);
        }

        setSprite(new Sprite(region));

        sprite.draw(batch, parentAlpha);
    }

    @Override
    public void moveBy(float x, float y) {
        super.moveBy(x, y);

        if(getX() < 0) setX(0);
        if(getX() > Application.width-width)  setX(Application.width-width);

        setX(getX()+ x);

        xSpeed = x;
    }
}
