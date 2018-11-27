package com.skilln.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.skilln.game.Game;

public class MenuScreen implements Screen {

    private Stage stage;

    private TextButton startButton;
    private TextButton exitButton;

    private BitmapFont font;

    private TextureAtlas buttonAtlas;

    private OrthographicCamera camera;

    private Skin skin;

    @Override
    public void show() {
        camera = new OrthographicCamera(Game.width, Game.height);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);

        FitViewport viewport = new FitViewport(Game.width, Game.height, camera);

        SpriteBatch batch = new SpriteBatch();

        stage = new Stage(viewport, batch);

        Gdx.input.setInputProcessor(stage);

        buttonAtlas = new TextureAtlas("MenuButton.atlas");

        skin = new Skin(buttonAtlas);

        font = new BitmapFont(false);

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();

        style.up = skin.getDrawable("button_up");
        style.down = skin.getDrawable("button_down");
        style.fontColor = Color.BLACK;

        style.font = font;

        startButton = new TextButton("Start", style);

        startButton.setWidth(400);
        startButton.setHeight(100);

        startButton.setX(Game.width/2-startButton.getWidth()/2);
        startButton.setY(Game.height/2-startButton.getHeight()/2);

        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.setScreen(ScreenManager.GAME_SCREEN);

                ScreenManager.getCurrentScreen().show();

                dispose();
            }
        });

        stage.addActor(startButton);

    }

    @Override
    public void render(float delta) {
        stage.draw();
        camera.update();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        skin.dispose();
        stage.dispose();
        font.dispose();
        buttonAtlas.dispose();
    }
}
