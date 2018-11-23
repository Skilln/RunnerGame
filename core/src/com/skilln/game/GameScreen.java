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

    public static float ySpeed = 4;

    public GameScreen() {
        batch = new SpriteBatch();

        input = new InputHandler();

        background = new Texture("background.png");

        camera = new OrthographicCamera(Game.width, Game.height);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);

        Gdx.input.setInputProcessor(input);

        start();
    }

    public void start() {

        Game.live = true;

        thread = new Thread(new EnemySpawn());

        player = new Player(0,50, GameId.Player);

        player.setX(Game.width/2-(player.getWidth()/2));
        player.setY(Game.height/2-Game.height/4);

        ObjectHandler.addObject(player);

        input.player = player;

        y = 0;
        y1 = Game.height;

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

        batch.setProjectionMatrix(camera.combined);
        camera.update();

        batch.begin();

        if(y1 - ySpeed <= 0) {
            y = 0;
            y1 = Game.height;
        }

        y-=ySpeed;
        y1-=ySpeed;

        batch.draw(background, 0, y, Game.width, Game.height);
        batch.draw(background, 0, y1, Game.width, Game.height);

        ObjectHandler.render(batch);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
       System.out.println("!");
    }

    @Override
    public void resume() {
        System.out.println("$");
    }

    @Override
    public void hide() {
        System.out.println("#");
    }

    @Override
    public void dispose() {
        batch.dispose();
        stop();
    }
}
