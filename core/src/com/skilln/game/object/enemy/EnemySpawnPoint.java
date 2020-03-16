package com.skilln.game.object.enemy;

import com.badlogic.gdx.utils.Array;
import com.skilln.game.GameConfig;
import com.skilln.game.object.GameId;
import com.skilln.game.screen.ui.ViewportScaler;

public class EnemySpawnPoint {
    public static EnemySpawnPoint createSpawnPoint(int type) {
        switch (type) {
            case 0: return new EnemySpawnPoint(60, 243, 2, 484, 135, 1);
            case 3: return new EnemySpawnPoint(141, 82, 1, 406, 128, 2);
            case 4: return new EnemySpawnPoint(136, 216, 1, 328, 441, 2);
            case 5: return new EnemySpawnPoint(19, 405, 2, 434, 125, 1);
            case 6: return new EnemySpawnPoint(16, 496, 2, 362, 90, 1);
            case 7: return new EnemySpawnPoint(26, 366, 2, 353, 110, 1);
            case 8: return new EnemySpawnPoint(0, 278, 1, 265, 143, 2);
            case 9: return new EnemySpawnPoint(478, 198, 1);
            case 10: return new EnemySpawnPoint(11, 254, 1, 187, 443, 2);
            case 11: return new EnemySpawnPoint(524, 106, 1);
            case 13: return new EnemySpawnPoint(55, 38, 1, 303, 393, 2);
            case 14: return new EnemySpawnPoint(139, 65, 1, 409, 199, 2);
            case 15: return new EnemySpawnPoint(23, 172, 1, 251, 131, 2);
            case 17: return new EnemySpawnPoint(83, 72, 1, 266, 227, 2);
            case 18: return new EnemySpawnPoint(19, 287, 2, 482, 1, 1);
            case 19: return new EnemySpawnPoint(189, 188, 1);
            case 20: return new EnemySpawnPoint(17, 408, 2, 423, 40, 1);
            case 21: return new EnemySpawnPoint(39, 469, 2, 392, 49, 1);
            case 22: return new EnemySpawnPoint(323, 174, 1);
            case 23: return new EnemySpawnPoint(0, 80, 1, 305, 230, 2);
            case 24: return new EnemySpawnPoint(192, 158, 1);
            case 25: return new EnemySpawnPoint(336, 167, 1);
            case 26: return new EnemySpawnPoint(75, 347, 2, 414, 52, 1);
            case 29: return new EnemySpawnPoint(85, 151, 2, 440, 120, 1);
            case 30: return new EnemySpawnPoint(490, 177, 1);
            case 31: return new EnemySpawnPoint(133, 168, 1);
            case 32: return new EnemySpawnPoint(186, 27, 1, 381, 315, 2);
            case 33: return new EnemySpawnPoint(99, 485, 2, 521, 21, 1);
            case 37: return new EnemySpawnPoint(57, 101, 1, 344, 391, 2);
            case 38: return new EnemySpawnPoint(40, 182, 1, 225, 135, 2);
            case 39: return new EnemySpawnPoint(36, 175, 1, 208, 163, 2);
            case 40: return new EnemySpawnPoint(178, 153, 1, 438, 469, 2);
            case 42: return new EnemySpawnPoint(87, 406, 2, 426, 26, 1);
            case 43: return new EnemySpawnPoint(135, 120, 1, 371, 264, 2);
            case 44: return new EnemySpawnPoint(16, 124, 1);
            case 45: return new EnemySpawnPoint(92, 269, 2, 487, 118, 1);
            case 46: return new EnemySpawnPoint(33, 174, 2, 379, 105, 1);
            case 47: return new EnemySpawnPoint(62, 259, 2, 425, 83, 1);
            case 48: return new EnemySpawnPoint(537, 144, 1);
            case 49: return new EnemySpawnPoint(10, 383, 2, 450, 38, 1);

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
