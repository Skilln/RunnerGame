package com.skilln.game.object.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.skilln.game.WayToHeaven;
import com.skilln.game.GameAtlas;
import com.skilln.game.GameState;
import com.skilln.game.object.GameId;
import com.skilln.game.object.GameObject;
import com.skilln.game.screen.GameScreen;
import com.skilln.game.screen.MenuScreen;
import com.skilln.game.screen.ScreenManager;
import com.skilln.game.screen.ui.ViewportScaler;

public class Player extends GameObject {

    private Animation<TextureRegion> animation_up, animation_left, animation_right, animation_die;

    private Sound sound;

    private PlayerMovement playerMovement;

    public Player(GameId id) {
        super(id);

        playerMovement = new PlayerMovement(this);

        TextureAtlas animation_up_atlas = GameAtlas.soul;
        TextureAtlas animation_left_atlas = GameAtlas.soul_left;
        TextureAtlas animation_right_atlas = GameAtlas.soul_right;
        TextureAtlas animation_die_atlas = GameAtlas.soul_die;

        animation_up = new Animation<TextureRegion>(1f / (float) (animation_up_atlas.getRegions().size + 4),
                animation_up_atlas.getRegions(),
                Animation.PlayMode.LOOP);

        animation_left = new Animation<TextureRegion>(1f / (float) (animation_up_atlas.getRegions().size + 4),
                animation_left_atlas.getRegions(),
                Animation.PlayMode.LOOP);

        animation_right = new Animation<TextureRegion>(1f / (float) (animation_up_atlas.getRegions().size + 4),
                animation_right_atlas.getRegions(),
                Animation.PlayMode.LOOP);

        animation_die = new Animation<TextureRegion>(1f / (float) (animation_up_atlas.getRegions().size + 4),
                animation_die_atlas.getRegions(),
                Animation.PlayMode.NORMAL);

        setWidth(animation_die_atlas.getRegions().first().getRegionWidth());
        setHeight(animation_die_atlas.getRegions().first().getRegionHeight());
        setAlpha(0.01f);

        sound = GameAtlas.dieSound;
    }

    @Override
    public Circle getHitBox() {
        return new Circle(getX() + getWidth() / 2, getY() + getHeight() - 60, getWidth() / 2 - 25);
    }

    @Override
    public void update(float worldSpeed) {

        playerMovement.move();
    }

    float stateTime = 0;
    float dieTime = 0;

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        stateTime += Gdx.graphics.getDeltaTime();

        TextureRegion region = animation_up.getKeyFrame(stateTime, true);

        if (playerMovement.getCurrentSpeedX() < 0) {
            region = animation_left.getKeyFrame(stateTime, true);
        } else if (playerMovement.getCurrentSpeedX() > 0) {
            region = animation_right.getKeyFrame(stateTime, true);
        }

        if (dead) {
            if (dieTime == 0) {

                if (!MenuScreen.sound_off) sound.play();
                dieTime = stateTime;
            }

            playerMovement.setCurrentSpeedX(0);
            playerMovement.setSpeedY(1);

            region = animation_die.getKeyFrame(stateTime - dieTime, false);

            if (animation_die.isAnimationFinished(stateTime - dieTime)) {

                int rec = WayToHeaven.data.getInteger("record");

                if ((int) playerMovement.getDistance() > rec) {

                    WayToHeaven.data.putInteger("record", (int) playerMovement.getDistance());
                    WayToHeaven.data.flush();

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


        if (getX() < 0) setX(0);
        if (getX() > ViewportScaler.GAME_WIDTH - getWidth())
            setX(ViewportScaler.GAME_WIDTH - getWidth());

        setX(getX() + x);

    }

    public PlayerMovement getPlayerMovement() {
        return playerMovement;
    }
}
