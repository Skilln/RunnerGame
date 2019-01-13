package com.skilln.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.skilln.game.Application;
import com.skilln.game.GameAtlas;
import com.skilln.game.GameState;

public class InfoScreen implements Screen {

    private Animation<TextureRegion> menu;

    private OrthographicCamera camera;

    private SpriteBatch batch;

    private Stage stage;

    private Button back;

    @Override
    public void show() {

        camera = new OrthographicCamera(Application.width, Application.height);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);

        FitViewport viewport = new FitViewport(Application.width, Application.height, camera);

        batch = new SpriteBatch();

        menu = new Animation<TextureRegion>(1f/9f, GameAtlas.info.getRegions(), Animation.PlayMode.LOOP);

        stage = new Stage(viewport, batch);

        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();

        back = new Button(buttonStyle);

        back.setWidth(Application.width-200);
        back.setHeight(Application.height-200);

        back.setX(100);
        back.setY(100);

        back.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                ScreenManager.setScreen(GameState.MENU);

                return super.touchDown(event, x, y, pointer, button);
            }
        });

        stage.addActor(back);

        stage.addListener(new ClickListener() {
            public boolean keyDown(InputEvent event, int keycode) {

                if(keycode == Input.Keys.BACK) {
                    ScreenManager.setScreen(GameState.MENU);
                }

                return super.keyDown(event, keycode);
            }
        });

        Application.currentState = GameState.INFO;


        Gdx.input.setInputProcessor(stage);

    }

    float a = 0;

    @Override
    public void render(float delta) {
        camera.update();

        a += delta;

        batch.begin();

        batch.draw(menu.getKeyFrame(a), 0, 0);

        batch.end();

        stage.draw();

        if(Gdx.input.justTouched()) {
      //      ScreenManager.setScreen(GameState.MENU);
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
        back.setDisabled(true);
    }

    @Override
    public void dispose() {

    }
}
