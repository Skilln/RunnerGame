package com.skilln.game.object.enemy;

import com.badlogic.gdx.utils.Array;
import com.skilln.game.GameConfig;
import com.skilln.game.object.GameStage;

import java.util.Random;

public class EnemySpawn {

    private GameStage stage;
    private float spawnPeriod = 5;
    private float spawnPoint = 0;

    private Random random = new Random();

    public EnemySpawn(GameStage stage) {
        this.stage = stage;
    }

    public void update(float distance) {
        if (distance > spawnPoint) {
            spawnPoint += spawnPeriod;

            Array<Enemy> objects = EnemySpawnPoint.createSpawnPoint(random.nextInt(50)).getEnemies();

            for (int i = 0; i < objects.size; i++) {
                stage.addObject(objects.get(i));
            }
        }
    }

    public void updatePeriod() {
        spawnPeriod *= GameConfig.GAME_SPEED_MULTIPLIER;
    }

}
