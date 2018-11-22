package com.skilln.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


public abstract class GameObject {

    protected float x, y;
    protected GameId id;

    protected int width, height;

    protected Texture sprite;


    public GameObject(float x, float y, GameId id) {
        this.x = x;
        this.y = y;
        this.id = id;

        sprite = new Texture(id.name + ".png");
    }

    public abstract void render(SpriteBatch batch);
    public abstract void update();

    public Rectangle getHitBox() {
        return new Rectangle(x, y, width-20, height-20);
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

    public Texture getSprite() {
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





}
