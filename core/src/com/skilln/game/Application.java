package com.skilln.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.skilln.game.screen.ScreenManager;

public class Application extends Game {

	private static float WIDTH, HEIGHT;

	public static int width = 720, widthFixed;
	public static int height = 1280;

	public static float ratio;

	public static GameState currentState;

	public static AdHandler adHandler;

	public static Preferences data;
	public static Preferences music;

	public Application(AdHandler adHandler) {
		Application.adHandler = adHandler;
	}

	public Application() {
	}

	@Override
	public void create () {

		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();

		ratio = HEIGHT/WIDTH;

		height = (int)(width*ratio);
		widthFixed = (int)(height/(16f/9f));

		Gdx.app.log("START", "WIDTH : " + width + " HEIGHT : " + height);

		data = Gdx.app.getPreferences("Data");
		music = Gdx.app.getPreferences("Music");

		GameAtlas.init();
		ScreenManager.init(this);

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
