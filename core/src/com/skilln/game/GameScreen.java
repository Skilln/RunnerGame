package com.skilln.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.skilln.game.objects.EnemySpawn;
import com.skilln.game.objects.GameId;
import com.skilln.game.objects.ObjectHandler;
import com.skilln.game.objects.Player;

public class GameScreen implements Screen {

    private SpriteBatch batch;
    private InputHandler input;

    private Thread thread;

    private Player player;

    private Texture background;

    private OrthographicCamera camera;

    private int y,  y1;

    public GameScreen() {
        batch = new SpriteBatch();

        input = new InputHandler();

        background = new Texture("background.png");

        camera = new OrthographicCamera();

        Gdx.input.setInputProcessor(input);

        start();
    }

    public void start() {

        Game.live = true;

        thread = new Thread(new EnemySpawn());

        player = new Player(0,50, GameId.Player);

        player.setX(Gdx.graphics.getWidth()/2-(player.getWidth()/2));

        ObjectHandler.addObject(player);

        input.player = player;

        y = 0;
        y1 = Gdx.graphics.getHeight();

        thread.start();
    }

    public void stop() {

        Game.live = false;

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void restart() {
        stop();
        ObjectHandler.clear();
        start();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        batch.begin();

        if(y1 - player.ySpeed <= 0) {
            y = 0;
            y1 = Gdx.graphics.getHeight();
        }

        y-=player.ySpeed;
        y1-=player.ySpeed;

        batch.draw(background, 0, y, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(background, 0, y1, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        ObjectHandler.render(batch);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        thread.stop();
    }

    @Override
    public void resume() {
        thread.resume();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        stop();
    }
}
