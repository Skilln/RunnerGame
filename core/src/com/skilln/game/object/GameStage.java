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
import com.skilln.game.GameAtlas;
import com.skilln.game.GameState;
import com.skilln.game.screen.ScreenManager;

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

        batch.draw(rain.getKeyFrame(a), 0, 0);

        batch.end();

        update();
    }

    public void update() {
        GameObject object;
        GameObject player = null;
        for(int i = 0; i < actors.size; i++) {
            object = actors.get(i);

            if(object.getId() == GameId.Player) {
                player = object;
            }

            if(player != null && object.getId() == GameId.Enemy
                    && object.getHitBox().overlaps(player.getHitBox())) {
                player.setDead(true);
            }

            if(object.getY() <= -700 && object.getId() != GameId.Background) {
                removeObject(object);
            }
        }


    }
}
