package com.skilln.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.skilln.game.Application;
import com.skilln.game.GameAtlas;
import com.skilln.game.GameState;
import com.skilln.game.object.GameId;
import com.skilln.game.object.Logo;

public class MenuScreen implements Screen {

    private Stage stage;

    private Logo logo;

    private BitmapFont font;

    private OrthographicCamera camera;
    private SpriteBatch batch;

    private TextureAtlas button;
    private TextButton startButton;

    private boolean start = false;

    private Sprite sprite;

    private Music music;

    private Animation<TextureRegion> menu;

    @Override
    public void show() {

        camera = new OrthographicCamera(Application.width, Application.height);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);

        FitViewport viewport = new FitViewport(Application.width, Application.height, camera);

        batch = new SpriteBatch();

        button = GameAtlas.button;

        Skin skin = new Skin(button);

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();

        style.up = skin.getDrawable("button_up");
        style.down = skin.getDrawable("button_down");
        style.fontColor = Color.WHITE;

        font = new BitmapFont(Gdx.files.internal("sprites/font/font.fnt"));

        style.font = font;

        startButton = new TextButton("Begin", style);

        startButton.setWidth(400);
        startButton.setHeight(100);

        startButton.setX(Application.width/2-startButton.getWidth()/2);
        startButton.setY(Application.height/2-startButton.getHeight()/2 -150);

        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.setScreen(GameState.GAME);
            }
        });

        music = Gdx.audio.newMusic(Gdx.files.internal("audio/menu21.mp3"));
        music.setVolume(1.0f);

        menu = new Animation<TextureRegion>(1f/9f, GameAtlas.menu.getRegions(), Animation.PlayMode.LOOP);

        font = new BitmapFont(Gdx.files.internal("sprites/font/font.fnt"));

        logo = new Logo(GameId.Logo);

        stage = new Stage(viewport, batch);

        sprite = GameAtlas.text_2;

        sprite.setY(Application.height/2-230);

        Gdx.input.setInputProcessor(stage);

        if(!start) {
            stage.addActor(logo);
        } else {
      //      stage.addActor(startButton);
        }


    }

    float a = 0;
    float alpha = 0.1f;
    boolean hide = false;

    @Override
    public void render(float delta) {

        a += Gdx.graphics.getDeltaTime();

        if(logo.logo.isAnimationFinished(a) ) {

            camera.update();
            batch.begin();

            batch.draw(menu.getKeyFrame(a), 0, 0);

            if(!music.isPlaying()) {
                music.play();
            }

            if(!start) {
          //      stage.addActor(startButton);

            }

            sprite.draw(batch, alpha);

            start = true;

            batch.end();
        }

        if(alpha < 0.95f && !hide) {
            alpha*=1.05f;
        } else if(hide) {
            alpha*=0.93f;
        }

        if(alpha > 0.95f && !hide) {
            hide = true;
        } else if(alpha < 0.09f) {
            hide = false;
        }

        stage.draw();

        if(start && Gdx.input.justTouched()) {
            ScreenManager.setScreen(GameState.GAME);
        }

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
        music.stop();
        alpha = 0.1f;
    }

    @Override
    public void dispose() {
        stage.dispose();
        font.dispose();
        start = false;
        a = 0;
    }
}
