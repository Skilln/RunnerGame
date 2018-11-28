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
import com.skilln.game.Application;
import com.skilln.game.GameState;

public class GameOverScreen implements Screen {
    private Stage stage;

    private TextButton restartButton;
    private TextButton toMenuButton;

    private BitmapFont font;

    private TextureAtlas buttonAtlas;

    private OrthographicCamera camera;

    private Skin skin;

    @Override
    public void show() {

        Application.currentState = GameState.MENU;

        camera = new OrthographicCamera(Application.width, Application.height);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);

        FitViewport viewport = new FitViewport(Application.width, Application.height, camera);

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

        restartButton = new TextButton("Restart", style);
        toMenuButton = new TextButton("To menu", style);

        restartButton.setWidth(400);
        restartButton.setHeight(100);

        toMenuButton.setWidth(400);
        toMenuButton.setHeight(100);

        restartButton.setX(Application.width/2-restartButton.getWidth()/2);
        restartButton.setY(Application.height/2-restartButton.getHeight()/2);

        toMenuButton.setX(Application.width/2-restartButton.getWidth()/2);
        toMenuButton.setY(Application.height/2-restartButton.getHeight()/2-200);


        restartButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.setScreen(GameState.GAME);
            }
        });

        toMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.setScreen(GameState.MENU);
            }
        });

        stage.addActor(restartButton);
        stage.addActor(toMenuButton);

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

    }
}
