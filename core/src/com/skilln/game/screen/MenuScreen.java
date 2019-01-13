package com.skilln.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.skilln.game.Application;
import com.skilln.game.GameAtlas;
import com.skilln.game.GameState;

public class MenuScreen implements Screen {

    private Stage stage;

    private BitmapFont font;

    private OrthographicCamera camera;
    private SpriteBatch batch;

    private boolean start = false;

    private Sprite sprite;

    private Music music;

    private ImageButton sound, info;
    private Button startgame;
    private Skin sound_skin, info_skin;

    private Animation<TextureRegion> menu;

    private boolean sound_off;

    @Override
    public void show() {
        camera = new OrthographicCamera(Application.width, Application.height);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);

        FitViewport viewport = new FitViewport(Application.width, Application.height, camera);

        batch = new SpriteBatch();

        sound_skin = new Skin(GameAtlas.sound_button);
        info_skin = new Skin(GameAtlas.info_button);

        sound_off = Application.music.getBoolean("sound");

        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();


        if(!sound_off) {
            style.up = sound_skin.getDrawable("sound_on");
            style.checked = sound_skin.getDrawable("sound_off");

        } else {
            style.up = sound_skin.getDrawable("sound_off");
            style.checked = sound_skin.getDrawable("sound_on");

        }

        ImageButton.ImageButtonStyle style1 = new ImageButton.ImageButtonStyle();

        style1.up = info_skin.getDrawable("info_0");
        style1.down = info_skin.getDrawable("info_1");

        music = Gdx.audio.newMusic(Gdx.files.internal("audio/menu21.mp3"));
        music.setVolume(1.0f);

        sound = new ImageButton(style);
        info = new ImageButton(style1);

        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();

        startgame = new Button(buttonStyle);

        startgame.setWidth(Application.width-200);
        startgame.setHeight(Application.height-200);

        startgame.setX(100);
        startgame.setY(100);

        startgame.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
               if(Application.currentState == GameState.MENU)
                ScreenManager.setScreen(GameState.GAME);

                return super.touchDown(event, x, y, pointer, button);

            }
        });

        sound.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!sound_off) {
                    Application.music.putBoolean("sound", true);
                    Application.music.flush();

                    Gdx.app.log("MENU", Application.music.getBoolean("sound") + "");

                    sound_off = true;
                    if(sound.isChecked()) {
                        sound.setChecked(true);
                    } else sound.setChecked(false);

                    music.stop();
                } else {
                    Application.music.putBoolean("sound", false);
                    Application.music.flush();

                    Gdx.app.log("MENU", Application.music.getBoolean("sound") + "");

                    sound_off = false;

                    if(sound.isChecked()) {
                        sound.setChecked(true);
                    } else sound.setChecked(false);

                    music.play();
                }
            }
        });

        info.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                ScreenManager.setScreen(GameState.INFO);

                return super.touchDown(event, x, y, pointer, button);
            }
        });

        sound.setWidth(100);
        sound.setHeight(100);

        info.setWidth(100);
        info.setHeight(100);

        sound.setX(Application.width-sound.getWidth());
        sound.setY(Application.height-sound.getHeight());

        info.setX(0);
        info.setY(Application.height-info.getHeight());

        menu = new Animation<TextureRegion>(1f/9f, GameAtlas.menu.getRegions(), Animation.PlayMode.LOOP);

        font = new BitmapFont(Gdx.files.internal("sprites/font/font.fnt"));

        stage = new Stage(viewport, batch);

        sprite = GameAtlas.text_2;

        sprite.setY(Application.height/2-230);

        stage.addListener(new ClickListener() {
            public boolean keyDown(InputEvent event, int keycode) {

                if(keycode == Input.Keys.BACK) {
                    Gdx.app.log("Menu", "TOUCHED");
                    Gdx.app.exit();
                }

                return super.keyDown(event, keycode);
            }
        });


        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);

        if(start) {
            stage.addActor(sound);
            stage.addActor(startgame);
            stage.addActor(info);
        }

        Application.currentState = GameState.MENU;

    }

    float a = 0;
    float alpha = 0.1f;
    boolean hide = false;

    @Override
    public void render(float delta) {

        camera.update();

        a += delta;


        batch.begin();

        batch.draw(menu.getKeyFrame(a), 0, 0);

        if(!music.isPlaying() && !sound_off) {
                music.play();
        }

        sprite.draw(batch, alpha);

        if(!start) {
            stage.addActor(sound);
            stage.addActor(startgame);
            stage.addActor(info);
        }

        start = true;

        batch.end();

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
        startgame.setDisabled(true);
        sound.setDisabled(true);
        info.setDisabled(true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        font.dispose();
        start = false;
        a = 0;
    }
}
