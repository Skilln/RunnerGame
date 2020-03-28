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
import com.skilln.game.GameConfig;
import com.skilln.game.WayToHeaven;
import com.skilln.game.GameAtlas;
import com.skilln.game.GameState;
import com.skilln.game.screen.ui.ViewportScaler;

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

    private ImageButton.ImageButtonStyle sound_style;

    private Animation<TextureRegion> menu;

    public static boolean sound_off;

    public MenuScreen() {
        init();
    }


    @Override
    public void show() {
        music = GameAtlas.menuSound;
        music.setVolume(1.0f);

        sound_off = WayToHeaven.music.getBoolean("sound");

        switchMusic();

        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);

        WayToHeaven.currentState = GameState.MENU;

        music.setLooping(true);

        if (!sound_off) music.play();
    }

    private void switchMusic() {
        if (!sound_off) {
            WayToHeaven.music.putBoolean("sound", false);
            WayToHeaven.music.flush();
            sound_off = true;
            sound.setChecked(true);

            music.stop();
        } else {
            WayToHeaven.music.putBoolean("sound", true);
            WayToHeaven.music.flush();
            sound_off = false;
            sound.setChecked(false);

            music.play();
        }
    }

    private void init() {
        camera = new OrthographicCamera(GameConfig.GAME_WIDTH, ViewportScaler.GAME_HEIGHT);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);

        FitViewport viewport = new FitViewport(GameConfig.GAME_WIDTH, ViewportScaler.GAME_HEIGHT, camera);

        batch = new SpriteBatch();

        sound_skin = new Skin(GameAtlas.sound_button);
        info_skin = new Skin(GameAtlas.info_button);
        shop_skin = new Skin(GameAtlas.shop);

        sound_style = new ImageButton.ImageButtonStyle();

        sound_style.up = sound_skin.getDrawable("sound_on");
        sound_style.checked = sound_skin.getDrawable("sound_off");

        sound = new ImageButton(sound_style);

        sound.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);

                switchMusic();

            }
        });

        sound.setWidth(100);
        sound.setHeight(100);

        sound.setX(GameConfig.GAME_WIDTH - sound.getWidth());
        sound.setY(ViewportScaler.GAME_HEIGHT - sound.getHeight());

        ImageButton.ImageButtonStyle info_style = new ImageButton.ImageButtonStyle();

        info_style.up = info_skin.getDrawable("info_0");
        info_style.down = info_skin.getDrawable("info_1");

        ImageButton.ImageButtonStyle shop_style = new ImageButton.ImageButtonStyle();

        shop_style.up = shop_skin.getDrawable("shop_0");
        shop_style.down = shop_skin.getDrawable("shop_1");

        info = new ImageButton(info_style);
        shop = new ImageButton(shop_style);

        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();

        startgame = new Button(buttonStyle);

        startgame.setWidth(GameConfig.GAME_WIDTH - 200);
        startgame.setHeight(ViewportScaler.GAME_HEIGHT - 200);

        startgame.setX(100);
        startgame.setY(100);

        startgame.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (WayToHeaven.currentState == GameState.MENU)
                    ScreenManager.setScreen(GameState.GAME);
                music.setVolume(0);
                music.pause();
                music.stop();

                return super.touchDown(event, x, y, pointer, button);

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

        info.setWidth(100);
        info.setHeight(100);

        shop.setWidth(100);
        shop.setHeight(100);

        info.setX(0);
        info.setY(ViewportScaler.GAME_HEIGHT - info.getHeight());

        shop.setX(100);
        shop.setY(ViewportScaler.GAME_HEIGHT - shop.getHeight());

        menu = new Animation<TextureRegion>(1f / 9f, GameAtlas.menu.getRegions(), Animation.PlayMode.LOOP);

        font = new BitmapFont(Gdx.files.internal("sprites/font/font.fnt"));

        stage = new Stage(viewport, batch);

        sprite = GameAtlas.text_2;

        sprite.setY(ViewportScaler.GAME_HEIGHT / 2f - 230);

        stage.addListener(new ClickListener() {
            public boolean keyDown(InputEvent event, int keycode) {

                if (keycode == Input.Keys.BACK) {
                    Gdx.app.exit();
                }

                return super.keyDown(event, keycode);
            }
        });

        stage.addActor(startgame);
        stage.addActor(info);
        stage.addActor(shop);
        stage.addActor(sound);

    }

    float a = 0;
    float alpha = 0.1f;
    boolean hide = false;

    @Override
    public void render(float delta) {
        camera.update();

        a += delta;

        batch.begin();

        batch.draw(menu.getKeyFrame(a), -(ViewportScaler.DELTA_GAME_WIDTH) / 2f,
                0, ViewportScaler.GAME_WIDTH, ViewportScaler.GAME_HEIGHT);

        sprite.draw(batch, alpha);

        batch.end();

        if (alpha < 0.95f && !hide) {
            alpha *= 1.05f;
        } else if (hide) {
            alpha *= 0.93f;
        }

        if (alpha > 0.95f && !hide) {
            hide = true;
        } else if (alpha < 0.09f) {
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
        if (!sound_off) music.play();
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
