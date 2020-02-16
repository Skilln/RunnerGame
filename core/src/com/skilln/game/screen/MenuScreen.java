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
import com.skilln.game.WayToHeaven;
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

    private ImageButton sound, info, shop;
    private Button startgame;
    private Skin sound_skin, info_skin, shop_skin;

    private Animation<TextureRegion> menu;

    public static boolean sound_off;

    @Override
    public void show() {
        camera = new OrthographicCamera(WayToHeaven.width, WayToHeaven.height);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);

        FitViewport viewport = new FitViewport(WayToHeaven.width, WayToHeaven.height, camera);

        batch = new SpriteBatch();

        sound_skin = new Skin(GameAtlas.sound_button);
        info_skin = new Skin(GameAtlas.info_button);
        shop_skin = new Skin(GameAtlas.shop);

        sound_off = WayToHeaven.music.getBoolean("sound");

        ImageButton.ImageButtonStyle sound_style = new ImageButton.ImageButtonStyle();

        if(!sound_off) {
            sound_style.up = sound_skin.getDrawable("sound_on");
            sound_style.checked = sound_skin.getDrawable("sound_off");

        } else {
            sound_style.up = sound_skin.getDrawable("sound_off");
            sound_style.checked = sound_skin.getDrawable("sound_on");

        }

        ImageButton.ImageButtonStyle info_style = new ImageButton.ImageButtonStyle();

        info_style.up = info_skin.getDrawable("info_0");
        info_style.down = info_skin.getDrawable("info_1");

        ImageButton.ImageButtonStyle shop_style = new ImageButton.ImageButtonStyle();

        shop_style.up = shop_skin.getDrawable("shop_0");
        shop_style.down = shop_skin.getDrawable("shop_1");

        music = GameAtlas.menuSound;
        music.setVolume(1.0f);

        sound = new ImageButton(sound_style);
        info = new ImageButton(info_style);
        shop = new ImageButton(shop_style);

        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();

        startgame = new Button(buttonStyle);

        startgame.setWidth(WayToHeaven.width-200);
        startgame.setHeight(WayToHeaven.height-200);

        startgame.setX(100);
        startgame.setY(100);

        startgame.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
               if(WayToHeaven.currentState == GameState.MENU)
                ScreenManager.setScreen(GameState.GAME);
                 music.setVolume(0);
                 music.pause();
                 music.stop();

                return super.touchDown(event, x, y, pointer, button);

            }
        });

        sound.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!sound_off) {
                    WayToHeaven.music.putBoolean("sound", true);
                    WayToHeaven.music.flush();
                    sound_off = true;
                    if(sound.isChecked()) {
                        sound.setChecked(true);
                    } else sound.setChecked(false);

                    music.stop();
                } else {
                    WayToHeaven.music.putBoolean("sound", false);
                    WayToHeaven.music.flush();
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

        shop.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                ScreenManager.setScreen(GameState.SHOP);

                return super.touchDown(event, x, y, pointer, button);
            }
        });

        sound.setWidth(100);
        sound.setHeight(100);

        info.setWidth(100);
        info.setHeight(100);

        shop.setWidth(100);
        shop.setHeight(100);

        sound.setX(WayToHeaven.width-sound.getWidth());
        sound.setY(WayToHeaven.height-sound.getHeight());

        info.setX(0);
        info.setY(WayToHeaven.height-info.getHeight());

        shop.setX(100);
        shop.setY(WayToHeaven.height-shop.getHeight());

        menu = new Animation<TextureRegion>(1f/9f, GameAtlas.menu.getRegions(), Animation.PlayMode.LOOP);

        font = new BitmapFont(Gdx.files.internal("sprites/font/font.fnt"));

        stage = new Stage(viewport, batch);

        sprite = GameAtlas.text_2;

        sprite.setY(WayToHeaven.height/2f-230);

        stage.addListener(new ClickListener() {
            public boolean keyDown(InputEvent event, int keycode) {

                if(keycode == Input.Keys.BACK) {
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
            stage.addActor(shop);
        }

        WayToHeaven.currentState = GameState.MENU;

        music.setLooping(true);

        if(!sound_off) music.play();

    }

    float a = 0;
    float alpha = 0.1f;
    boolean hide = false;

    @Override
    public void render(float delta) {
        camera.update();

        a += delta;

        batch.begin();

        batch.draw(menu.getKeyFrame(a), -(WayToHeaven.widthFixed- WayToHeaven.width)/2f, 0, WayToHeaven.widthFixed, WayToHeaven.height);

        sprite.draw(batch, alpha);

        if(!start) {
            stage.addActor(sound);
            stage.addActor(startgame);
            stage.addActor(info);
            stage.addActor(shop);
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
        music.pause();
    }

    @Override
    public void resume() {
        if(!sound_off) music.play();
    }

    @Override
    public void hide() {
        //music.stop();
        alpha = 0.1f;
        startgame.setDisabled(true);
        sound.setDisabled(true);
        info.setDisabled(true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        font.dispose();
        batch.dispose();
        music.dispose();
        shop_skin.dispose();
        sound_skin.dispose();
        info_skin.dispose();
        start = false;
        a = 0;
    }
}
