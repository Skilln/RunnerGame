package com.skilln.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game extends ApplicationAdapter {

	public static boolean live = true;

	public static final int width = 720;
	public static final int height = 1280;

	private GameScreen screen;

	@Override
	public void create () {
		screen = new GameScreen();
		screen.show();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		screen.render(Gdx.graphics.getDeltaTime());

		if(!live) {
			screen.restart();
		}
	}
	
	@Override
	public void dispose () {
		screen.dispose();
	}
}
