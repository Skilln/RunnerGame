package com.skilln.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.skilln.game.Application;

public class Player extends GameObject {

    public float xSpeed;
    public static float ySpeed;

    public float distance;
    public int coins;

    private float stateTime = 0;

    public Animation<TextureRegion> animation_up, animation_left, animation_right;

    private boolean isDead = false;

    public Player(float x, float y, GameId id) {
        super(x, y, id);

        TextureAtlas animation_up_atlas = new TextureAtlas("sprites/soul/Soul.atlas");
        TextureAtlas animation_left_atlas = new TextureAtlas("sprites/soul/Soul_left.atlas");
        TextureAtlas animation_right_atlas = new TextureAtlas("sprites/soul/Soul_right.atlas");

        animation_up = new Animation<TextureRegion>(0.11f, animation_up_atlas.getRegions(),
                Animation.PlayMode.LOOP);

        animation_left = new Animation<TextureRegion>(0.11f, animation_left_atlas.getRegions(),
                Animation.PlayMode.LOOP);

        animation_right = new Animation<TextureRegion>(0.11f, animation_right_atlas.getRegions(),
                Animation.PlayMode.LOOP);


        System.out.println(animation_up.getAnimationDuration());

        ySpeed = 4;

        width = 120;
        height = 170;
    }



    @Override
    public void render(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();

        TextureRegion sprite = animation_up.getKeyFrame(stateTime, true);

        if(xSpeed < 0) {
            sprite = animation_left.getKeyFrame(stateTime, true);
        } else if(xSpeed > 0) {
            sprite = animation_right.getKeyFrame(stateTime, true);
        }

        batch.draw(sprite, x, y, width, height);

        x+=xSpeed;
        if(x < 0) x = 0;
        if(x > Application.width-width) x = Application.width-width;

        update();
    }

    @Override
    public void update() {
        distance += (ySpeed/200.0f);

      if((int)distance%5 == 0) {
          ySpeed += 0.02f;
      }

      System.out.println(ySpeed);

        if(ObjectHandler.checkCollision()) {
            isDead = true;
      }
    }

    public boolean isDead(){
        return isDead;
    }

}
