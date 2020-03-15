package com.skilln.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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
import com.skilln.game.GameConfig;
import com.skilln.game.WayToHeaven;
import com.skilln.game.GameAtlas;
import com.skilln.game.GameState;
import com.skilln.game.screen.ui.ViewportScaler;

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

    private Music music;

    private Animation<TextureRegion> over;

    @Override
    public void show() {

        camera = new OrthographicCamera(GameConfig.GAME_WIDTH, ViewportScaler.GAME_HEIGHT);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);

        FitViewport viewport = new FitViewport(GameConfig.GAME_WIDTH, ViewportScaler.GAME_HEIGHT, camera);

        batch = new SpriteBatch();

        stage = new Stage(viewport, batch);

        Gdx.input.setInputProcessor(stage);

        over = new Animation<TextureRegion>(1f / 20f, GameAtlas.interference.getRegions(), Animation.PlayMode.LOOP);

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

        restartButton.setX(GameConfig.GAME_WIDTH / 2f - restartButton.getWidth() / 2);
        restartButton.setY(ViewportScaler.GAME_HEIGHT / 2f - restartButton.getHeight() / 2 - 100);

        toMenuButton.setX(GameConfig.GAME_WIDTH / 2f - toMenuButton.getWidth() / 2);
        toMenuButton.setY(ViewportScaler.GAME_HEIGHT / 2f - toMenuButton.getHeight() / 2 - 250);


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

        if (a <= 0.5) {
            sprite = GameAtlas.text_0;
        } else {
            sprite = GameAtlas.text_1;
        }

        sprite.setY(ViewportScaler.GAME_HEIGHT / 2);

        stage.addListener(new ClickListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {

                if (keycode == Input.Keys.BACK) {
                    ScreenManager.setScreen(GameState.MENU);
                }

                return super.keyDown(event, keycode);
            }
        });

        back = GameAtlas.background_1;

        if (WayToHeaven.adHandler != null) {
            WayToHeaven.adHandler.showAd();
        }

        music = GameAtlas.gameOverSound;

        music.setVolume(1f);
        music.setLooping(true);

        if (!MenuScreen.sound_off) music.play();
    }

    float a = 0;

    float alpha = 0.1f;

    @Override
    public void render(float delta) {
        batch.begin();

        a += delta;

        batch.draw(back, 0, 0);

        batch.draw(over.getKeyFrame(a), -(ViewportScaler.DELTA_GAME_WIDTH) / 2f, 0, ViewportScaler.GAME_WIDTH, ViewportScaler.GAME_HEIGHT);

        sprite.draw(batch, alpha);

        batch.end();

        stage.draw();

        if (alpha < 1.0) {
            alpha *= 1.03f;
        }

        camera.update();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        if (music.isPlaying()) {
            music.pause();
        }
    }

    @Override
    public void resume() {
        if (!MenuScreen.sound_off) music.play();
    }

    @Override
    public void hide() {
        restartButton.setDisabled(true);
        toMenuButton.setDisabled(true);
        music.setVolume(0);
        music.pause();
        music.stop();

        a = 0;
        alpha = 0.1f;
    }

    @Override
    public void dispose() {
        buttonAtlas.dispose();
        font.dispose();
        stage.dispose();
        music.dispose();
        skin.dispose();
        batch.dispose();

    }
}
