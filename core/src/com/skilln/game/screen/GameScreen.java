package com.skilln.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.PauseableThread;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.skilln.game.WayToHeaven;
import com.skilln.game.GameAtlas;
import com.skilln.game.GameState;
import com.skilln.game.object.Background;
import com.skilln.game.object.GameStage;
import com.skilln.game.object.EnemySpawn;
import com.skilln.game.object.GameId;
import com.skilln.game.object.Man;
import com.skilln.game.object.player.Player;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.skilln.game.screen.ui.GameScreenUI;

public class GameScreen implements Screen {

    private SpriteBatch batch;
    private PauseableThread enemySpawn;

    private Player player;
    private Man man;
    private Background background;

    private BitmapFont font;

    private OrthographicCamera camera;
    private GameStage stage;

    private boolean start = false;

    private Sprite pauseBack, tutorial;

    private GameScreenUI ui;

    private Music music;

    public static int record = 0;
    public static int coins = 0;

    private boolean played;

    private boolean scale = false;

    @Override
    public void show() {
        batch = new SpriteBatch();

        played = WayToHeaven.data.getBoolean("played");

        camera = new OrthographicCamera(WayToHeaven.width, WayToHeaven.height);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);

        FitViewport viewport = new FitViewport(WayToHeaven.width, WayToHeaven.height, camera);


        stage = new GameStage(viewport, batch);
        man = new Man(GameId.Man);

        pauseBack = GameAtlas.pause_back;

        pauseBack.setRegionWidth(WayToHeaven.width);
        pauseBack.setRegionHeight(WayToHeaven.height);

        tutorial = GameAtlas.tutorial;

        tutorial.setRegionWidth(WayToHeaven.width);
        tutorial.setRegionHeight(WayToHeaven.height);

        man.setX(0);
        man.setY(0);

        player = new Player(GameId.Player);

        player.setX(WayToHeaven.width / 2f - (player.getWidth() / 2f));
        player.setY(50);

        background = new Background(GameId.Background);

        stage.addObject(background);
        stage.addObject(man);
        stage.addObject(player);

        font = new BitmapFont(Gdx.files.internal("sprites/font/font_1.fnt"), false);
        font.setColor(Color.WHITE);

        music = GameAtlas.gameSound;

        int rec = WayToHeaven.data.getInteger("record");
        int coins = WayToHeaven.data.getInteger("coins");

        if (rec != Integer.MIN_VALUE) {
            record = rec;
        } else {
            record = 0;
        }

        if (coins != Integer.MIN_VALUE) {
            GameScreen.coins = coins;
        } else {
            GameScreen.coins = 0;
        }

        ui = new GameScreenUI(this);


        Gdx.input.setInputProcessor(stage);

        if (WayToHeaven.ratio > 1.78 && !scale) {
            pauseBack.scale(0.2f);
            tutorial.scale(0.2f);

            scale = true;
        }

        music.setVolume(1f);
        music.setLooping(true);


    }

    private synchronized void start() {

        if (!MenuScreen.sound_off) music.play();

        enemySpawn = new PauseableThread(new EnemySpawn(stage));

        enemySpawn.start();
    }

    float alpha = 0.05f;

    @Override
    public void render(float delta) {
        if (player.getY() < WayToHeaven.height / 2 - WayToHeaven.height / 4 - WayToHeaven.height / 8 && man.isDead() && !ui.isOnPause()) {

            player.setY(player.getY() + 2);

            if (player.getAlpha() < 0.95) {
                player.setAlpha(player.getAlpha() * 1.09f);
            } else player.setAlpha(1);

        } else if (!start && player.getY() >= WayToHeaven.height / 2 - WayToHeaven.height / 4 - WayToHeaven.height / 8) {
            start = true;
            start();
        }

        stage.draw();

        batch.begin();

        if (start) {
            if (alpha < 0.95) {
                font.setColor(255, 255, 255, alpha);
                alpha *= 1.09f;
            } else {
                alpha = 1;
            }

            font.draw(batch, "Distance : " + (int) player.getPlayerMovement().getDistance(), 20, WayToHeaven.height - 20);
            font.draw(batch, "Record : " + record, 20, WayToHeaven.height - 50);
            font.draw(batch, "Coins : " + coins, 20, WayToHeaven.height - 80);

            if (!played) {
                tutorial.draw(batch, alpha);
            }

        }

        if (ui.isOnPause()) {

            pauseBack.draw(batch, 0.6f);

        }

        if (player.isDead()) {
            if (music.isPlaying()) music.stop();
        }

        batch.end();

        update();

    }

    public void update() {

        if (ui.isTouching()) {
            player.moveBy(player.getPlayerMovement().getCurrentSpeedX(), 0);
        }

        if (start && !ui.isOnPause()) {

            stage.update(player.getPlayerMovement().getSpeedY());

        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        if (!ui.isOnPause()) {
            pauseGame(!ui.isOnPause());
        }
        music.pause();
        WayToHeaven.currentState = GameState.APPLICATION_PAUSE;

    }

    @Override
    public void resume() {
        if (!MenuScreen.sound_off && !ui.isOnPause()) music.play();
        WayToHeaven.currentState = GameState.GAME;

    }

    @Override
    public void hide() {
        start = false;
        stage.clear();
        if (enemySpawn != null) enemySpawn.stopThread();
        alpha = 0.01f;

        music.setVolume(0);
        music.pause();
        music.stop();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        enemySpawn.stopThread();
        music.dispose();
        stage.dispose();
        ui.dispose();
    }

    public void pauseGame(boolean onPause) {
        if (!onPause) {
            if (enemySpawn != null) {
                enemySpawn.onPause();
                music.pause();
            }

        } else {
            if (enemySpawn != null) {
                enemySpawn.onResume();
                if (!MenuScreen.sound_off) music.play();
            }
        }
    }

    public GameStage getStage() {
        return stage;
    }

    public Player getPlayer() {
        return player;
    }
}