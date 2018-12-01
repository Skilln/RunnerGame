package com.skilln.game.object;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.skilln.game.GameState;
import com.skilln.game.screen.ScreenManager;

public class GameStage extends Stage {

    private Array<GameObject> actors = new Array<GameObject>();
    private SpriteBatch batch;
    private Viewport viewport;


    public GameStage(Viewport viewport, SpriteBatch batch) {
        super(viewport, batch);

        this.batch = batch;
        this.viewport = viewport;
    }

    public void addObject(GameObject object) {
         actors.add(object);
    }

    public void removeObject(GameObject object) {
        actors.removeValue(object, true);
    }

    @Override
    public void draw() {
        Camera camera = viewport.getCamera();
        camera.update();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        GameObject object;

        for(int i = 0; i < actors.size; i++) {
            object = actors.get(i);

            object.draw(batch, object.alpha);
        }

        batch.end();

        update();
    }

    public void update() {
        GameObject object = null;
        GameObject player = null;
        for(int i = 0; i < actors.size; i++) {
            object = actors.get(i);

            if(object.getId() == GameId.Player) {
                player = object;
            }

            if(player != null && object.getId() != GameId.Player
                    && object.getHitBox().overlaps(player.getHitBox())) {
                ScreenManager.setScreen(GameState.GAMEOVER);
            }

            if(object.getY() <= -200) {
                removeObject(object);
            }
        }


    }
}
