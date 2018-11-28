package com.skilln.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.skilln.game.Application;

public class ObjectHandler {

    private static Array<GameObject> objects = new Array<GameObject>();

    public static void render(SpriteBatch batch) {

        GameObject current;

        for(int i = 0, size = objects.size; i < size; i++) {
            current = objects.get(i);

            current.render(batch);
        }
    }

    public static void addObject(GameObject object) {
        objects.add(object);
    }

    public static void removeObject(int index) {
        objects.removeIndex(index);
    }

    public static void removeObject(GameObject object) {
        for(int i = 0, size = objects.size; i < size; i++) {
            if(objects.get(i).equals(object)) {
                removeObject(i);
                break;
            }
        }
    }

    private static GameObject getPlayer() {
        for(int i = 0, size = objects.size; i < size; i++) {
            if(objects.get(i).getId() == GameId.Player) {
                return objects.get(i);
            }
        }
        return null;
    }

    public static void clear() {
        objects.clear();
    }


    public static boolean checkCollision() {
        GameObject player = getPlayer();

        for(int i = 1, size = objects.size; i < size; i++) {
            if(objects.get(i).getHitBox().overlaps(player.getHitBox())) {
                System.out.println("!!");
                return true;
            }
        }
        return false;
    }

}
