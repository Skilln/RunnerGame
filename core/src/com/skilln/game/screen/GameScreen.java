package com.skilln.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.PauseableThread;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.skilln.game.Application;
import com.skilln.game.GameAtlas;
import com.skilln.game.GameState;
import com.skilln.game.object.Background;
import com.skilln.game.object.GameStage;
import com.skilln.game.object.EnemySpawn;
import com.skilln.game.object.GameId;
import com.skilln.game.object.Man;
import com.skilln.game.object.Player;

import com.badlogic.gdx.scenes.scene2d.ui.Button;

public class GameScreen implements Screen {

    private SpriteBatch batch;
    private PauseableThread enemySpawn, pixelSpawn;

    private Player player;
    private Man man;

    private BitmapFont font;

    private OrthographicCamera camera;
    private GameStage stage;

    private boolean start = false;

    private Button left, right;

    private boolean touching;

    public static float speed = 0;
    public static float distance = 0;
    public static int record = 0;


    @Override
    public void show() {

        Application.currentState = GameState.GAME;

        batch = new SpriteBatch();

        camera = new OrthographicCamera(Application.width, Application.height);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);

        FitViewport viewport = new FitViewport(Application.width, Application.height, camera);

        stage = new GameStage(viewport, batch);
        man = new Man(GameId.Man);

        man.setX(0);
        man.setY(0);

        player = new Player(GameId.Player);

        player.setX(Application.width / 2 - (player.getWidth() / 2));
        player.setY(160);

        stage.addObject(new Background(GameId.Background));
        stage.addObject(man);
        stage.addObject(player);

        font = new BitmapFont(Gdx.files.internal("sprites/font/font_1.fnt"), false);
        font.setColor(Color.WHITE);

        int rec = Application.record.getInteger("record");

        if (rec != Integer.MIN_VALUE) {
            record = rec;
        } else {
            record = 0;
        }

        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();

        left = new Button(buttonStyle);
        right = new Button(buttonStyle);

        left.setWidth(Application.width / 2);
        left.setHeight(Application.height);

        left.setX(0);
        left.setY(0);

        left.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                if (!player.isDead() && start) {

                    player.xSpeed = -5-distance*0.02f;

                    touching = true;
                }

                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);

                player.xSpeed = 0;

                touching = false;
            }
        });

        right.setWidth(Application.width / 2);
        right.setHeight(Application.height);

        right.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                if (!player.isDead() && start) {
                    player.xSpeed = 5+distance*0.02f;
                    touching = true;
                }
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);

                player.xSpeed = 0;

                touching = false;
            }
        });

        right.setX(Application.width / 2);
        right.setY(0);

        stage.addActor(right);
        stage.addActor(left);

        Gdx.input.setInputProcessor(stage);


    }

    private synchronized void start() {

        speed = 5;

        enemySpawn = new PauseableThread(new EnemySpawn(stage));
        // pixelSpawn = new PauseableThread(new PixelSpawn(stage));

        enemySpawn.start();
        // pixelSpawn.start();
    }

    float alpha = 0.05f;

    @Override
    public void render(float delta) {
        if (player.getY() < Application.height / 2 - Application.height / 4 && man.isDead()) {

            player.setY(player.getY() + 2);

            if (player.getAlpha() < 0.95) {
                player.setAlpha(player.getAlpha() * 1.09f);
            } else player.setAlpha(1);

        } else if (!start && player.getY() >= Application.height / 2 - Application.height / 4) {
            start = true;
            start();
        }

        stage.draw();

        batch.begin();

        if (start) {
            if (alpha < 0.95) {
                font.setColor(255, 255, 255, alpha);
                alpha *= 1.09f;
            }
            font.draw(batch, "Distance : " + (int) distance, 20, Application.height - 20);
            font.draw(batch, "Record : " + (int) record, 20, Application.height - 50);
        }

        batch.end();

        update();

    }

    public void update() {

        if(touching) {
            player.moveBy(player.xSpeed, 0);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.moveBy(5 + (distance * 0.01f), 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.moveBy(-5 - (distance * 0.01f), 0);
        }


        if (start && Application.currentState != GameState.APPLICATION_PAUSE) {
            distance += (speed / 250.0f);

            if ((int) distance % 5 == 0) {
                speed *= 1.002f;
            }
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        enemySpawn.onPause();
//        pixelSpawn.onPause();
        Application.currentState = GameState.APPLICATION_PAUSE;

    }

    @Override
    public void resume() {
        enemySpawn.onResume();
        //   pixelSpawn.onResume();
        Application.currentState = GameState.GAME;

    }

    @Override
    public void hide() {
        start = false;
        distance = 0;
        speed = 0;
        stage.clear();
        enemySpawn.stopThread();
        alpha = 0.01f;
        // pixelSpawn.stopThread();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        enemySpawn.stopThread();
        // pixelSpawn.stopThread();
    }

}