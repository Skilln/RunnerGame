package com.skilln.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.PauseableThread;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.skilln.game.GameConfig;
import com.skilln.game.WayToHeaven;
import com.skilln.game.GameAtlas;
import com.skilln.game.GameState;
import com.skilln.game.object.Background;
import com.skilln.game.object.GameStage;
import com.skilln.game.object.coins.CoinSpawn;
import com.skilln.game.object.enemy.EnemySpawn;
import com.skilln.game.object.GameId;
import com.skilln.game.object.Man;
import com.skilln.game.object.player.Player;

import com.skilln.game.screen.ui.GameScreenUI;
import com.skilln.game.screen.ui.ViewportScaler;

public class GameScreen implements Screen {

    private SpriteBatch batch;

    private EnemySpawn enemySpawn;
    private CoinSpawn coinSpawn;

    private Player player;
    private Man man;
    private Background background;

    private BitmapFont font;

    private OrthographicCamera camera;
    private GameStage stage;

    private boolean start = false;

    private Sprite tutorial;

    private GameScreenUI ui;

    private Music music;

    //MOVE TO ATLAS
    private Texture pauseBack;
    private Texture tutorialTipLeft;
    private Texture tutorialTipRight;

    public static int record = 0;
    public static int coins = 0;

    public boolean played;

    private boolean scale = false;

    @Override
    public void show() {
        batch = new SpriteBatch();

        played = WayToHeaven.data.getBoolean("played");

        camera = new OrthographicCamera(GameConfig.GAME_WIDTH, ViewportScaler.GAME_HEIGHT);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);

        FitViewport viewport = new FitViewport(GameConfig.GAME_WIDTH, ViewportScaler.GAME_HEIGHT, camera);

        stage = new GameStage(viewport, batch);
        man = new Man(GameId.Man);

        ///////////////NEED MOVE TO GAMEATLAS

        Pixmap pauseBack = new Pixmap(ViewportScaler.GAME_WIDTH, ViewportScaler.GAME_HEIGHT, Pixmap.Format.RGBA8888);

        pauseBack.setColor(new Color(0 ,0, 0, 0.6f));
        pauseBack.fillRectangle(0, 0, ViewportScaler.GAME_WIDTH, ViewportScaler.GAME_HEIGHT);

        Pixmap tutorialTipLeft = new Pixmap(ViewportScaler.GAME_WIDTH, ViewportScaler.GAME_HEIGHT, Pixmap.Format.RGBA8888);

        tutorialTipLeft.setColor(new Color(252, 186, 3, 0.5f));
        tutorialTipLeft.fillRectangle(20, 20, GameConfig.GAME_WIDTH / 2 - 30, ViewportScaler.GAME_HEIGHT - 20);

        Pixmap tutorialTipRight = new Pixmap(GameConfig.GAME_WIDTH, ViewportScaler.GAME_HEIGHT, Pixmap.Format.RGBA8888);

        tutorialTipRight.setColor(new Color(252, 186, 3, 0.5f));
        tutorialTipRight.fillRectangle(GameConfig.GAME_WIDTH / 2 + 10, 20, GameConfig.GAME_WIDTH / 2 - 30, ViewportScaler.GAME_HEIGHT - 20);

        ////////////

        this.pauseBack = new Texture(pauseBack);
        this.tutorialTipLeft = new Texture(tutorialTipLeft);
        this.tutorialTipRight = new Texture(tutorialTipRight);

        tutorial = GameAtlas.tutorial;

        tutorial.setRegionWidth(ViewportScaler.GAME_WIDTH);
        tutorial.setRegionHeight(ViewportScaler.GAME_HEIGHT);

        man.setX(0);
        man.setY(0);

        player = new Player(GameId.Player);

        player.setX(GameConfig.GAME_WIDTH / 2f - (player.getWidth() / 2f));
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

        enemySpawn = new EnemySpawn(stage);
        coinSpawn = new CoinSpawn(stage);

        Gdx.input.setInputProcessor(stage);

        music.setVolume(1f);
        music.setLooping(true);

    }

    float alpha = 0.05f;

    @Override
    public void render(float delta) {
        boolean playerInPoint = player.getY() < ViewportScaler.GAME_HEIGHT / 2
                - ViewportScaler.GAME_HEIGHT  / 4 - ViewportScaler.GAME_HEIGHT  / 8;

        if (playerInPoint && man.isDead() && !ui.isOnPause()) {

            player.setY(player.getY() + 2);

            if (player.getAlpha() < 0.95) {
                player.setAlpha(player.getAlpha() * 1.09f);
            } else player.setAlpha(1);

        } else if (!start && !playerInPoint) {
            start = true;
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

            font.draw(batch, "Distance : " + (int) player.getPlayerMovement().getDistance(), 20, ViewportScaler.GAME_HEIGHT - 20);
            font.draw(batch, "Record : " + record, 20, ViewportScaler.GAME_HEIGHT - 50);
            font.draw(batch, "Coins : " + coins, 20, ViewportScaler.GAME_HEIGHT - 80);

            if (!played) {
                batch.draw(tutorialTipLeft, 0, 0);
                batch.draw(tutorialTipRight, 0, 0);

                font.draw(batch, "Tap to\nRIGHT", GameConfig.GAME_WIDTH / 2 + 130, ViewportScaler.GAME_HEIGHT / 2);
                font.draw(batch, "Tap to\n LEFT", 130, ViewportScaler.GAME_HEIGHT / 2);

            }

        }

        if (ui.isOnPause()) {
            batch.draw(pauseBack, 0, 0);

            font.draw(batch, "PAUSED", GameConfig.GAME_WIDTH / 2 - 60, ViewportScaler.GAME_HEIGHT / 2 + 50);
            font.draw(batch, "Tap to continue", GameConfig.GAME_WIDTH / 2 - 120, ViewportScaler.GAME_HEIGHT / 2 - 20);

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
            enemySpawn.update(player.getPlayerMovement().getDistance());
            coinSpawn.update(player.getPlayerMovement().getDistance());

        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        if (!ui.isOnPause()) {
            ui.pauseGame();
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
        alpha = 0.01f;

        music.setVolume(0);
        music.pause();
        music.stop();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        music.dispose();
        stage.dispose();
        ui.dispose();
    }

    public void pauseGame(boolean onPause) {
        if (!onPause) {
            music.pause();
        } else {

            if (!MenuScreen.sound_off) music.play();
        }
    }

    public GameStage getStage() {
        return stage;
    }

    public Player getPlayer() {
        return player;
    }
}