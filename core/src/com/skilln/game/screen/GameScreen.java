package com.skilln.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.PauseableThread;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.skilln.game.AdHandler;
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
    private PauseableThread enemySpawn;

    private Player player;
    private Man man;

    private BitmapFont font;

    private OrthographicCamera camera;
    private GameStage stage;

    private boolean start = false;

    private Button left, right, toGame;
    private ImageButton pause;

    private Skin pause_skin;
    private Sprite pauseBack;

    private boolean touching;
    private boolean onPause = false;

    public static float speed = 0;
    public static float distance = 0;
    public static int record = 0;

    private float tempSpeed;

    @Override
    public void show() {

        Application.currentState = GameState.GAME;

        batch = new SpriteBatch();

        camera = new OrthographicCamera(Application.width, Application.height);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);

        FitViewport viewport = new FitViewport(Application.width, Application.height, camera);

        stage = new GameStage(viewport, batch);
        man = new Man(GameId.Man);

        pauseBack = GameAtlas.pause_back;

        man.setX(0);
        man.setY(0);

        player = new Player(GameId.Player);

        player.setX(Application.width / 2 - (player.getWidth() / 2));
        player.setY(50);

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

        pause_skin = new Skin(GameAtlas.pause);

        ImageButton.ImageButtonStyle pause_style = new ImageButton.ImageButtonStyle();

        pause_style.up = pause_skin.getDrawable("pause");
        pause_style.down = pause_skin.getDrawable("play");

        pause = new ImageButton(pause_style);

        pause.setWidth(100);
        pause.setHeight(100);

        pause.setX(Application.width-pause.getWidth());
        pause.setY(Application.height-pause.getHeight());

        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();

        left = new Button(buttonStyle);
        right = new Button(buttonStyle);
        toGame = new Button(buttonStyle);

        left.setWidth(Application.width / 2);
        left.setHeight(Application.height-100);

        left.setX(0);
        left.setY(0);

        left.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                if (!player.isDead() && start && !onPause) {

                    player.xSpeed = -5-distance*0.015f;

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
        right.setHeight(Application.height-100);

        right.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                if (!player.isDead() && start  && !onPause) {
                    player.xSpeed = 5+distance*0.015f;
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

        toGame.setWidth(Application.width);
        toGame.setHeight(Application.height-pause.getHeight());

        pause.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                pauseGame();

                return super.touchDown(event, x, y, pointer, button);
            }

        });

        toGame.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                pauseGame();

                toGame.setDisabled(false);

                return super.touchDown(event, x, y, pointer, button);
            }
        });

        stage.addListener(new ClickListener() {

            @Override
            public boolean keyDown(InputEvent event, int keycode) {

                if(keycode == Input.Keys.BACK) {
                    if(!onPause) {
                        pauseGame();
                        Application.adHandler.toast("If you quit, progress wouldn't be saved");
                    } else {
                        ScreenManager.setScreen(GameState.MENU);
                    }
                }
                return super.keyDown(event, keycode);
            }

        });


        stage.addActor(right);
        stage.addActor(left);
        stage.addActor(pause);

        Gdx.input.setInputProcessor(stage);

    }

    private synchronized void start() {

        speed = 5;

        enemySpawn = new PauseableThread(new EnemySpawn(stage));

        enemySpawn.start();
    }

    float alpha = 0.05f;

    @Override
    public void render(float delta) {
        if (player.getY() < Application.height / 2 - Application.height / 4 - Application.height / 8 && man.isDead() && !onPause) {

            player.setY(player.getY() + 2);

            if (player.getAlpha() < 0.95) {
                player.setAlpha(player.getAlpha() * 1.09f);
            } else player.setAlpha(1);

        } else if (!start && player.getY() >= Application.height / 2 - Application.height / 4 - Application.height / 8 ) {
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

        if(onPause) {
          pauseBack.draw(batch, 0.6f);
        }

        batch.end();

        update();

    }

    public void update() {

        if(touching) {
            player.moveBy(player.xSpeed, 0);
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
        if(!onPause) {
            pauseGame();
        }
        Application.currentState = GameState.APPLICATION_PAUSE;

    }

    @Override
    public void resume() {
        Application.currentState = GameState.GAME;

    }

    @Override
    public void hide() {
        start = false;
        distance = 0;
        speed = 0;
        tempSpeed = 0;
        stage.clear();
        if(enemySpawn != null) enemySpawn.stopThread();
        alpha = 0.01f;
        onPause = false;
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        enemySpawn.stopThread();
    }

    private void pauseGame() {
        if (!onPause) {
            if(enemySpawn != null) {
                enemySpawn.onPause();
            }

            toGame.setDisabled(true);

            tempSpeed = speed;

            speed = 0;

            stage.addActor(toGame);

            onPause = true;

        } else {
            if(enemySpawn != null) {
                enemySpawn.onResume();
            }
            speed = tempSpeed;

            toGame.remove();

            onPause = false;

        }
    }

}