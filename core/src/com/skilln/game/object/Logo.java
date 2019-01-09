package com.skilln.game.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.skilln.game.Application;
import com.skilln.game.GameAtlas;

public class Logo extends GameObject {

    public Animation<TextureRegion> logo;

    public Logo(GameId id) {
        super(id);

        logo = new Animation<TextureRegion>(1f/30f, GameAtlas.logo.getRegions(), Animation.PlayMode.NORMAL);


        setWidth(GameAtlas.logo.getRegions().first().getRegionWidth()*2);
        setHeight(GameAtlas.logo.getRegions().first().getRegionHeight()*2);

        setX((Application.width/2)-(getWidth()/2));
        setY((Application.height/2)-(getHeight()/2));

    }

    float stateTime = -2;

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        stateTime += Gdx.graphics.getDeltaTime();
        if(stateTime >= 0) {
            TextureRegion region = logo.getKeyFrame(stateTime);

            batch.draw(region, getX(), getY(), getWidth(), getHeight());
        }
    }

    @Override
    public Circle getHitBox() {
        return null;
    }
}
