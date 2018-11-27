package com.skilln.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.skilln.game.screen.MenuScreen;
import com.skilln.game.screen.ScreenManager;

public class Game extends ApplicationAdapter {

	public static boolean live = true;

	public static final int width = 720;
	public static final int height = 1280;

	public static GameState currentState;

	@Override
	public void create () {
		ScreenManager.init();

		ScreenManager.setScreen(ScreenManager.MENU_SCREEN);

		ScreenManager.getCurrentScreen().show();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		ScreenManager.getCurrentScreen().render(Gdx.graphics.getDeltaTime());

		//if(!live) {
		//	screen.restart();
		//}
	}
	
	@Override
	public void dispose () {
		ScreenManager.getCurrentScreen().dispose();
	}
}
