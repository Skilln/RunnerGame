package com.skilln.game.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.skilln.game.GameAtlas;
import com.skilln.game.screen.GameScreen;

public class Coin extends GameObject {

    private Animation<TextureRegion> animation;

    public Coin(GameId id) {
        super(id);

        TextureAtlas atlas = GameAtlas.coin;

        animation = new Animation<TextureRegion>(0.11f, atlas.getRegions(), Animation.PlayMode.LOOP);

        setWidth(atlas.getRegions().first().getRegionWidth());
        setHeight(atlas.getRegions().first().getRegionWidth());

    }

    float stateTime = 0;

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        stateTime += Gdx.graphics.getDeltaTime();

        TextureRegion region = animation.getKeyFrame(stateTime);

        setSprite(new Sprite(region));

        sprite.draw(batch, parentAlpha);

    }

    @Override
    public Circle getHitBox() {
        return new Circle(getX()+getWidth()/2, getY()+getHeight()/2, getWidth()/2);
    }

    @Override
    public void update(float worldSpeed) {
        moveBy(0, -worldSpeed);
    }
}
