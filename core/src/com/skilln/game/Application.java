package com.skilln.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.skilln.game.screen.ScreenManager;

public class Application extends Game {

	public static final int width = 720;
	public static final int height = 1280;

	public static GameState currentState;

	@Override
	public void create () {
		ScreenManager.init(this);

		ScreenManager.setScreen(GameState.MENU);

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		super.render();


		//ScreenManager.getCurrentScreen().render(Gdx.graphics.getDeltaTime());
	}
	
	@Override
	public void dispose () {
		super.dispose();
	}
}
