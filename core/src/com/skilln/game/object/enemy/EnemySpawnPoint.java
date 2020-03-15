package com.skilln.game.object.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.skilln.game.GameConfig;
import com.skilln.game.object.GameId;
import com.skilln.game.screen.ui.ViewportScaler;

public class EnemySpawnPoint {
    public static EnemySpawnPoint createSpawnPoint(int type) {
        switch (type) {
            case 0:
                return new EnemySpawnPoint(0, 150, 1);
            case 1:
                return new EnemySpawnPoint(100, 100, 1, ViewportScaler.GAME_WIDTH - 300,  150, 2);
            case 2 :
                return new EnemySpawnPoint(ViewportScaler.GAME_WIDTH - 300, 80, 2);
            case 3 :
                return new EnemySpawnPoint(0, 100, 2, ViewportScaler.GAME_WIDTH - 200, 100, 1);
            case 4 :
                return new EnemySpawnPoint(10, 150, 1, 150, 100, 2);
            case 5 :
                return new EnemySpawnPoint(ViewportScaler.GAME_WIDTH - 300, 200, 2, ViewportScaler.GAME_WIDTH - 400, 100, 1);
            case 6 :
                return new EnemySpawnPoint(ViewportScaler.GAME_WIDTH / 2 - 50, 200, 1, ViewportScaler.GAME_WIDTH / 2 + 70, 50, 1);
            default:
                return new EnemySpawnPoint(0, 150, 1);
        }
    }

    private Array<Enemy> enemies = new Array<Enemy>();

    public EnemySpawnPoint(float x, float y, int size) {
        Enemy enemy = new Enemy(GameId.Enemy);

        enemy.setX(x);
        enemy.setY(ViewportScaler.GAME_HEIGHT   + y);

        enemy.setSize(size);

        enemies.add(enemy);
    }

    public EnemySpawnPoint(float x, float y, int size, float x1, float y1, int size1) {
        Enemy enemy = new Enemy(GameId.Enemy);
        Enemy enemy1 = new Enemy(GameId.Enemy);

        enemy.setX(x);
        enemy.setY(ViewportScaler.GAME_HEIGHT  + y);

        enemy.setSize(size);

        enemy1.setX(x1);
        enemy1.setY(ViewportScaler.GAME_HEIGHT   + y1);

        enemy1.setSize(size1);

        enemies.add(enemy, enemy1);

    }

    public Array<Enemy> getEnemies() {
        return enemies;
    }


}
