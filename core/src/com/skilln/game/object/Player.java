package com.skilln.game.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.skilln.game.Application;
import com.skilln.game.GameAtlas;
import com.skilln.game.GameState;
import com.skilln.game.screen.GameScreen;
import com.skilln.game.screen.ScreenManager;

import java.util.logging.FileHandler;

public class Player extends GameObject {

    public float xSpeed;

    private Animation<TextureRegion> animation_up, animation_left, animation_right, animation_die;

    public Player(GameId id) {
        super(id);

        TextureAtlas animation_up_atlas = GameAtlas.soul;
        TextureAtlas animation_left_atlas = GameAtlas.soul_left;
        TextureAtlas animation_right_atlas = GameAtlas.soul_right;
        TextureAtlas animation_die_atlas =GameAtlas.soul_die;

        animation_up = new Animation<TextureRegion>(1f/12f, animation_up_atlas.getRegions(),
                Animation.PlayMode.LOOP);

        animation_left = new Animation<TextureRegion>(1f/12f, animation_left_atlas.getRegions(),
                Animation.PlayMode.LOOP);

        animation_right = new Animation<TextureRegion>(1f/12f, animation_right_atlas.getRegions(),
                Animation.PlayMode.LOOP);

        animation_die = new Animation<TextureRegion>(1f/12f, animation_die_atlas.getRegions(),
                Animation.PlayMode.NORMAL);

        setWidth(animation_die_atlas.getRegions().first().getRegionWidth());
        setHeight(animation_die_atlas.getRegions().first().getRegionHeight());
        setAlpha(0.01f);
    }

    @Override
    public Circle getHitBox() {
        return new Circle(getX()+getWidth()/2, getY()+getHeight()-50, getWidth()/2-40);
    }

    float stateTime = 0;
    float dieTime = 0;

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

        if(dead) {
            if(dieTime == 0) {
                dieTime = stateTime;
            }

            xSpeed = 0;

            GameScreen.speed = 1;

            region = animation_die.getKeyFrame(stateTime-dieTime, false);

            if(animation_die.isAnimationFinished(stateTime-dieTime)) {

                int rec = Application.record.getInteger("record");

                if((int)GameScreen.distance > rec) {

                    Application.record.putInteger("record", (int)GameScreen.distance);
                    Application.record.flush();

                }

               ScreenManager.setScreen(GameState.GAMEOVER);
            }
        }

        setSprite(new Sprite(region));

        sprite.draw(batch, parentAlpha);

    }

    @Override
    public void moveBy(float x, float y) {
        super.moveBy(x, y);


        if(getX() < 0) setX(0);
        if(getX() > Application.width-getWidth())  setX(Application.width-getWidth());

        setX(getX()+ x);

    }
}
