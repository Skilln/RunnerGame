package com.skilln.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.skilln.game.objects.Player;

public class InputHandler extends InputAdapter {

    public Player player;

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if(screenX > (Gdx.graphics.getWidth()/2)) {
            player.xSpeed += 5;
        } else {
            player.xSpeed += -5;
        }

        return super.touchDown(screenX, screenY, pointer, button);

    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        player.xSpeed = 0;

        return super.touchUp(screenX, screenY, pointer, button);
    }
}
