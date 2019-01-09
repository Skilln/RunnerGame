package com.skilln.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.skilln.game.screen.ScreenManager;

public class Application extends Game {

	public static final int width = 720;
	public static final int height = 1280;

	public static GameState currentState;

	public static AdHandler adHandler;

	public static Preferences record;
	public static Preferences music;

	public Application(AdHandler adHandler) {
		Application.adHandler = adHandler;
	}

	public Application() {

	}

	@Override
	public void create () {
		GameAtlas.init();
		ScreenManager.init(this);

		record = Gdx.app.getPreferences("Record");
		music = Gdx.app.getPreferences("Music");

		ScreenManager.setScreen(GameState.MENU);



	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		super.render();

	}
	
	@Override
	public void dispose () {
		super.dispose();
	}
}
