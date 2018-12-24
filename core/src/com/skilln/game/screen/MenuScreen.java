package com.skilln.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

    private boolean start = false;

    private Animation<TextureRegion> menu;

    @Override
    public void show() {

        camera = new OrthographicCamera(Application.width, Application.height);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);

        FitViewport viewport = new FitViewport(Application.width, Application.height, camera);

        batch = new SpriteBatch();

        menu = new Animation<TextureRegion>(1f/9f, GameAtlas.menu.getRegions(), Animation.PlayMode.LOOP);

        logo = new Logo(GameId.Logo);

        stage = new Stage(viewport, batch);

        Gdx.input.setInputProcessor(stage);

        stage.addActor(logo);

    }

    float a = 0;

    @Override
    public void render(float delta) {

        stage.draw();

        a += Gdx.graphics.getDeltaTime();

        if(logo.logo.isAnimationFinished(a) ) {

            start = true;
            camera.update();
            batch.begin();

            batch.draw(menu.getKeyFrame(a), 0, 0);

            batch.end();


            if (Gdx.input.justTouched()) {
                ScreenManager.setScreen(GameState.GAME);
            }
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

    }

    @Override
    public void dispose() {
        stage.dispose();
        font.dispose();
        start = false;
        a = 0;
    }
}
