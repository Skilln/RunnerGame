package com.skilln.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


public abstract class GameObject {

    protected float x, y;
    protected GameId id;

    protected int width, height;

    protected Sprite sprite;


    public GameObject(float x, float y, GameId id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public abstract void render(SpriteBatch batch);
    public abstract void update();

    public Rectangle getHitBox() {
        return new Rectangle(x+20, y+20, width-20, height-20);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public GameId getId() {
        return id;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }


    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
