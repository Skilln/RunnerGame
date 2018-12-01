package com.skilln.game.object;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class GameObject extends Actor {

    protected int width, height;

    protected float alpha = 1f;

    protected Sprite sprite;

    protected GameId id;

    public GameObject(GameId id) {
        this.id = id;
    }

    @Override
    public void setX(float x) {
        super.setX(x);
    }

    @Override
    public void setY(float y) {
        super.setY(y);
    }

    public void setWidth(int width) {
        super.setWidth(width);
        this.width = width;
    }

    public void setHeight(int height) {
        super.setHeight(height);
        this.height = height;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public float getAlpha() {
        return alpha;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Rectangle getHitBox() {
        return new Rectangle(getX()+20, getY()+20, width-20, height-20);
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
        sprite.setX(getX());
        sprite.setY(getY());
    }

    public GameId getId() {
        return id;
    }
}
