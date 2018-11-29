package com.skilln.game.objects;

public enum GameId {

    Player("player"), Background("background"), Enemy("enemy"), Man("man");

    String name;

    GameId(String name) {
        this.name = name;
    }

    GameId findByName(GameId[] id, String name) {
        for (int i = 0, size = id.length; i < size; i++) {
            if(id[i].name == name) {
                return id[i];
            }
        }
        return null;
    }
}
