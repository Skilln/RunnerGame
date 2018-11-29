package com.skilln.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.PauseableThread;
import com.skilln.game.Application;
import com.skilln.game.GameState;
import com.skilln.game.InputHandler;
import com.skilln.game.objects.EnemySpawn;
import com.skilln.game.objects.GameId;
import com.skilln.game.objects.Man;
import com.skilln.game.objects.ObjectHandler;
import com.skilln.game.objects.Player;

public class GameScreen implements Screen {

    private SpriteBatch batch;
    private InputHandler input;

    private PauseableThread thread;

    private Player player;
    private Man man;

    private Texture background;

    private BitmapFont font;

    private OrthographicCamera camera;

    private synchronized void start() {

        Player.ySpeed = 4;

        thread = new PauseableThread(new EnemySpawn());

        thread.start();
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        input = new InputHandler();

        background = new Texture("background.png");

        camera = new OrthographicCamera(Application.width, Application.height);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);

        Gdx.input.setInputProcessor(input);

        man = new Man(0, 50, GameId.Man);

        man.setX(Application.width/2-(man.getWidth()/2));

        player = new Player(0,0, GameId.Player);

        player.setX(Application.width/2-(player.getWidth()/2));
        player.setY(100);

        input.player = player;

        ObjectHandler.addObject(player);


        font = new BitmapFont(false);
        font.setColor(Color.WHITE);

        ObjectHandler.addObject(man);

    }

    @Override
    public void render(float delta) {
        if(player.getY() < Application.height/2-Application.height/4) {

            player.setY(player.getY() + 2);
            player.alpha *= 1.1f;
        } else if(!player.start){

            player.start = true;
            start();
        }


        if(!player.isDead()) {
            batch.setProjectionMatrix(camera.combined);
            camera.update();

            batch.begin();

          //  if (y1 - (int)Player.ySpeed <= 0) {
          //      y = 0;
           //     y1 = Application.height;
          //  }

         //   y -= (int)Player.ySpeed;
         //   y1 -= (int)Player.ySpeed;

          // batch.draw(background, 0, y, Application.width, Application.height);
          //  batch.draw(background, 0, y1, Application.width, Application.height);

            ObjectHandler.render(batch);

            font.draw(batch, "Distance : " + (int)player.distance, 100, 100 );

            batch.end();
        } else {
            ScreenManager.setScreen(GameState.GAMEOVER);
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
     //  System.out.println("!");
       // Gdx.app.log("PAUSE", "SCREEN PAUSE");

        thread.onPause();

        Application.currentState = GameState.APPLICATION_PAUSE;

    }

    @Override
    public void resume() {
      //  System.out.println("$");
        thread.onResume();
    //   Gdx.app.log("RESUME", "SCREEN RESUME");
    }

    @Override
    public void hide() {
     //   System.out.println("#");

      //  Gdx.app.log("HIDE", "SCREEN HIDE");

        ObjectHandler.clear();

        thread.stopThread();
    }

    @Override
    public void dispose() {
        batch.dispose();
        thread.stopThread();
    }
}
