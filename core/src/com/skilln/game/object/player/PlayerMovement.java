package com.skilln.game.object.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.TimeUtils;
import com.skilln.game.GameConfig;

public class PlayerMovement {

    private Player player;

    private float speedX = 5;
    private float currentSpeedX = 0;
    private float speedY = 5;

    private float ySpeedMultiplier = GameConfig.GAME_SPEED_MULTIPLIER;
    private float xSpeedMultiplier = 1.05f;
    private float speedMultiplierPoint = 250;

    private long lastTimeUpdate;
    private static final int PERIOD_OF_DISTANCE_UPDATE = 100;

    private float distance;

    private final float POINTS_IN_METER = 20;

    public PlayerMovement(Player player) {
        this.player = player;
    }

    public void move() {
        if (TimeUtils.millis() - lastTimeUpdate > PERIOD_OF_DISTANCE_UPDATE) {
            distance += speedY;
            lastTimeUpdate = TimeUtils.millis();

            Gdx.app.log("PlayerMovement", "Distance: " + (distance / POINTS_IN_METER) + " speed: " + speedY);
        }

        if (distance >= speedMultiplierPoint) {
            speedY *= ySpeedMultiplier;
            speedMultiplierPoint = speedMultiplierPoint * ySpeedMultiplier + speedMultiplierPoint;
            speedX *= xSpeedMultiplier;
        }
    }

    public float getDistance() {
        return distance / POINTS_IN_METER;
    }

    public float getCurrentSpeedX() {
        return currentSpeedX;
    }

    public void setCurrentSpeedX(float direction) {
        this.currentSpeedX = speedX * direction;
    }

    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }
}
