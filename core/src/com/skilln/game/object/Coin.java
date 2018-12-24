package com.skilln.game.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.skilln.game.screen.GameScreen;

public class Coin extends GameObject {

    private Animation<TextureRegion> animation;

    public Coin(GameId id) {
        super(id);


        TextureAtlas atlas = new TextureAtlas("sprites/coin/Coin.atlas");

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

        moveBy(0, -GameScreen.speed);

    }

    @Override
    public Rectangle getHitBox() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
}
