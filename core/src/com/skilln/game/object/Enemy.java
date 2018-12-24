package com.skilln.game.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.skilln.game.GameAtlas;
import com.skilln.game.screen.GameScreen;

import java.util.Random;

public class Enemy extends GameObject {

    private Random random = new Random();

    private Animation<TextureRegion> animation;

    public Enemy(GameId id) {
        super(id);

        int type = random.nextInt(2);

        int size = random.nextInt(5);
        int s = size%2+1;

        TextureAtlas atlas;

        if(type == 0) {
            atlas  = GameAtlas.soul_hole;
        } else {
            atlas = GameAtlas.soul_hole1;
        }

        animation = new Animation<TextureRegion>(1f/10f, atlas.getRegions(), Animation.PlayMode.LOOP);

      //  sprite = new Sprite(atlas.getRegions().get(random.nextInt(11)));

        setWidth(atlas.getRegions().first().getRegionWidth()*s);
        setHeight(atlas.getRegions().first().getRegionHeight()*s);

    }

    float stateTime = 0;

    @Override
    public Rectangle getHitBox() {
        return new Rectangle(getX()+30, getY()+30, getWidth()-30, getHeight()-30);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        stateTime += Gdx.graphics.getDeltaTime();

        TextureRegion region = animation.getKeyFrame(stateTime, true);

        batch.draw(region, getX(), getY(), getWidth(), getHeight());



        moveBy(0, -GameScreen.speed);
    }
}
