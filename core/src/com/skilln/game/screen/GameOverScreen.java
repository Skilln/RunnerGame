package com.skilln.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.skilln.game.Application;
import com.skilln.game.GameAtlas;
import com.skilln.game.GameState;

public class GameOverScreen implements Screen {
    private Stage stage;

    private TextButton restartButton;
    private TextButton toMenuButton;

    private BitmapFont font;

    private TextureAtlas buttonAtlas;

    private OrthographicCamera camera;
    private Texture back;

    private SpriteBatch batch;

    private Skin skin;

    private Sprite sprite;

    private Animation<TextureRegion> over;

    @Override
    public void show() {

        Gdx.app.log("Debug", "OVER");

        camera = new OrthographicCamera(Application.width, Application.height);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);

        FitViewport viewport = new FitViewport(Application.width, Application.height, camera);

        batch = new SpriteBatch();

        stage = new Stage(viewport, batch);

        Gdx.input.setInputProcessor(stage);

        over = new Animation<TextureRegion>(1f/20f, GameAtlas.interference.getRegions(), Animation.PlayMode.LOOP);

        buttonAtlas = GameAtlas.button;

        skin = new Skin(buttonAtlas);

        font = new BitmapFont(false);

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();

        style.up = skin.getDrawable("button_up");
        style.down = skin.getDrawable("button_down");
        style.fontColor = Color.WHITE;

        font = new BitmapFont(Gdx.files.internal("sprites/font/font.fnt"));

        style.font = font;


        restartButton = new TextButton("Try again", style);
        toMenuButton = new TextButton("To menu", style);

        restartButton.setWidth(400);
        restartButton.setHeight(100);

        toMenuButton.setWidth(400);
        toMenuButton.setHeight(100);

        restartButton.setX(Application.width/2-restartButton.getWidth()/2);
        restartButton.setY(Application.height/2-restartButton.getHeight()/2-100);

        toMenuButton.setX(Application.width/2-toMenuButton.getWidth()/2);
        toMenuButton.setY(Application.height/2-toMenuButton.getHeight()/2-250);


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

        double a = Math.random();

        if(a <= 0.5) {
            sprite = GameAtlas.text_0;
        } else {
            sprite = GameAtlas.text_1;
        }

        sprite.setY(Application.height/2);

        stage.addListener(new ClickListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {

                if(keycode == Input.Keys.BACK) {
                   ScreenManager.setScreen(GameState.MENU);
                }

                return super.keyDown(event, keycode);
            }
        });

        back = GameAtlas.background_1;

        if(Application.adHandler != null) {
            Application.adHandler.showAd();
        }
    }

    float a = 0;

    float alpha = 0.1f;

    @Override
    public void render(float delta) {
        batch.begin();

        a += delta;

        batch.draw(back, 0, 0);

        batch.draw(over.getKeyFrame(a), -(Application.widthFixed-Application.width)/2f, 0, Application.widthFixed, Application.height);

        sprite.draw(batch, alpha);

        batch.end();

        stage.draw();

        if(alpha < 1.0) {
            alpha*=1.03f;
        }

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
        restartButton.setDisabled(true);
        toMenuButton.setDisabled(true);

        a = 0;
        alpha = 0.1f;
    }

    @Override
    public void dispose() {
        buttonAtlas.dispose();


    }
}
