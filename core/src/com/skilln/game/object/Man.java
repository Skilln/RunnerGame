package com.skilln.game.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.skilln.game.GameAtlas;
import com.skilln.game.screen.GameScreen;

public class Man extends GameObject {
    private long time;
    private boolean dead = false;

    private Animation<TextureRegion> animation;

    public Man(GameId id) {
        super(id);
        TextureAtlas atlas = GameAtlas.man;

        animation = new Animation<TextureRegion>(0.1f, atlas.getRegions());

        time = TimeUtils.millis();

    }

    public boolean isDead() {
        return dead;
    }

    float stateTime = 0;

    @Override
    public Rectangle getHitBox() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        TextureRegion region;

        if(TimeUtils.millis() - time >= 100 ) {

            stateTime += Gdx.graphics.getDeltaTime();

            region = animation.getKeyFrame(stateTime, false);
        } else {
            region = animation.getKeyFrame(0, false);
        }

        if(animation.isAnimationFinished(stateTime)) {
            dead = true;
        }

        setSprite(new Sprite(region));

        sprite.draw(batch, parentAlpha);

        moveBy(0, -GameScreen.speed);
    }
}
