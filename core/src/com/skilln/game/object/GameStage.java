package com.skilln.game.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.skilln.game.WayToHeaven;
import com.skilln.game.GameAtlas;
import com.skilln.game.screen.GameScreen;
import com.skilln.game.screen.MenuScreen;
import com.skilln.game.screen.ui.ViewportScaler;

public class GameStage extends Stage {

    private Array<GameObject> actors = new Array<GameObject>();
    private SpriteBatch batch;
    private Viewport viewport;

    private ShapeRenderer shapeRenderer;

    private Animation<TextureRegion> rain;

    public GameStage(Viewport viewport, SpriteBatch batch) {
        super(viewport, batch);

        this.batch = batch;
        this.viewport = viewport;

        rain = new Animation<TextureRegion>(1f/9f, GameAtlas.rain.getRegions(), Animation.PlayMode.LOOP);

        shapeRenderer = new ShapeRenderer();
    }

    public void addObject(GameObject object) {
         actors.add(object);
    }

    public void removeObject(GameObject object) {
        actors.removeValue(object, true);
    }

    public void clear() {
        actors.clear();
    }

    float a = 0;

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

            if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                shapeRenderer.setProjectionMatrix(camera.combined);

                shapeRenderer.setColor(Color.RED);

                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

                shapeRenderer.circle(object.getHitBox().x, object.getHitBox().y, object.getHitBox().radius);

                shapeRenderer.end();
            }
        }

        a += Gdx.graphics.getDeltaTime();

        batch.draw(rain.getKeyFrame(a), 0, 0, ViewportScaler.GAME_WIDTH, ViewportScaler.GAME_HEIGHT);

        batch.end();

        super.draw();
    }

    public void update(float worldSpeed) {
        GameObject object;
        GameObject player = null;
        for(int i = 0; i < actors.size; i++) {
            object = actors.get(i);

            object.update(worldSpeed);

            if(object.getId() == GameId.Player) {
                player = object;
            }

            if(player != null && object.getId() == GameId.Enemy
                    && object.getHitBox().overlaps(player.getHitBox())) {
                player.setDead(true);
            }

            if(player != null && object.getId() == GameId.Coin
                    && object.getHitBox().overlaps(player.getHitBox())) {

                GameScreen.coins++;

                removeObject(object);

                if(!MenuScreen.sound_off) GameAtlas.coinSound.play(1f);

                WayToHeaven.data.putInteger("coins", GameScreen.coins);
                WayToHeaven.data.flush();
            }

            if(object.getY() <= -700 && object.getId() != GameId.Background) {
                removeObject(object);
            }
        }
    }

    public Array<GameObject> getGameObjects() {
        return actors;
    }
}
