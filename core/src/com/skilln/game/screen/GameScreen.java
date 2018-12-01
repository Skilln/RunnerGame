package com.skilln.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.PauseableThread;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.skilln.game.Application;
import com.skilln.game.GameState;
import com.skilln.game.object.GameStage;
import com.skilln.game.object.EnemySpawn;
import com.skilln.game.object.GameId;
import com.skilln.game.object.Man;
import com.skilln.game.object.Player;

public class GameScreen implements Screen {

    private SpriteBatch batch;
    private PauseableThread thread;

    private Player player;
    private Man man;

    private BitmapFont font;

    private OrthographicCamera camera;
    private GameStage stage;

    private boolean start = false;

    public static float speed = 0;
    public static float distance = 0;

    @Override
    public void show() {

        batch = new SpriteBatch();

        camera = new OrthographicCamera(Application.width, Application.height);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);

        FitViewport viewport = new FitViewport(Application.width, Application.height, camera);

        stage = new GameStage(viewport, batch);
        man = new Man(GameId.Man);

        man.setX(Application.width/2-man.getWidth()/4);
        man.setY(150);

        player = new Player(GameId.Player);

        player.setX(Application.width/2-(player.getWidth()/2));
        player.setY(150);

        stage.addObject(man);
        stage.addObject(player);

        font = new BitmapFont(false);
        font.setColor(Color.WHITE);

    }

    private synchronized void start() {

        speed = 4;

        thread = new PauseableThread(new EnemySpawn(stage));

        thread.start();
    }

    @Override
    public void render(float delta) {
        if(player.getY() < Application.height/2-Application.height/4 && man.isDead()) {

            player.setY(player.getY() + 2);

            if(player.getAlpha() < 0.95) {
                player.setAlpha(player.getAlpha() * 1.09f);
            } else player.setAlpha(1);

        } else if(!start && player.getY() >= Application.height/2-Application.height/4){
            start = true;
            start();
        }

        stage.draw();

        update();

    }

    public void update() {
        if(Gdx.input.isTouched()) {
            if(Gdx.input.getX() > Gdx.graphics.getWidth()/2) {

                player.moveBy(5,0);
            } else {
                player.moveBy(-5, 0);
            }
        } else {
            player.moveBy(0, 0);
        }

        if(start) {
            distance += (speed/200.0f);

            if((int)distance%5 == 0) {
                speed += 0.02f;
            }
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        thread.onPause();

        Application.currentState = GameState.APPLICATION_PAUSE;

    }

    @Override
    public void resume() {
        thread.onResume();

    }

    @Override
    public void hide() {
        start = false;
        distance = 0;
        speed = 0;
        thread.stopThread();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        thread.stopThread();
    }
}
