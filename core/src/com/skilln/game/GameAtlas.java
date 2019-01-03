package com.skilln.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class GameAtlas {

    public static TextureAtlas enemy;
    public static TextureAtlas soul;
    public static TextureAtlas soul_left;
    public static TextureAtlas soul_right;
    public static TextureAtlas soul_die;
    public static TextureAtlas menu;
    public static TextureAtlas man;
    public static TextureAtlas logo;
    public static TextureAtlas rain;
    public static TextureAtlas cloud;
    public static TextureAtlas hollow;
    public static TextureAtlas soul_hole;
    public static TextureAtlas soul_hole1;
    public static TextureAtlas interference;

    public static TextureAtlas button;

    public static Texture background_0;
    public static Texture background_1;

    public static Sprite text_0;
    public static Sprite text_1;
    public static Sprite text_2;

    public static Texture[] background = new Texture[5];

    public static Texture pixel;

    public static Texture test_0;
    public static Texture test_1;
    public static Texture test_2;

    public static void init() {
        logo = new TextureAtlas("sprites/logo/logo.atlas");
        menu = new TextureAtlas("menu.atlas");
        interference = new TextureAtlas("interference.atlas");
        rain = new TextureAtlas("rain.atlas");
        enemy = new TextureAtlas("sprites/enemy/fire.atlas");
        soul = new TextureAtlas("sprites/soul/soul1.atlas");
        soul_left = new TextureAtlas("sprites/soul/soul1_left.atlas");
        soul_right = new TextureAtlas("sprites/soul/soul1_right.atlas");
        soul_die = new TextureAtlas("sprites/soul/Soul_die.atlas");
        man = new TextureAtlas("sprites/man/man.atlas");
        cloud = new TextureAtlas("sprites/cloud/cloud.atlas");
        hollow = new TextureAtlas("sprites/hollow/hollow.atlas");
        soul_hole = new TextureAtlas("sprites/hole/soul_hole.atlas");
        soul_hole1 = new TextureAtlas("sprites/hole/soul_hole1.atlas");

        button = new TextureAtlas("button.atlas");

        background_0 = new Texture("background_0.jpg");
        background_1 = new Texture("background.jpg");
        background[0] = new Texture("background2.jpg");
        background[1] = new Texture("background3.jpg");
        background[2] = new Texture("background4.jpg");
        background[3] = new Texture("background5.jpg");
        background[4] = new Texture("background6.jpg");
        pixel = new Texture("pixel.png");

        text_0 = new Sprite(new Texture("text_0.png"));
        text_1 = new Sprite(new Texture("text_1.png"));
        text_2 = new Sprite(new Texture("text_2.png"));

        test_0 = new Texture("sprites/test_0.png");
        test_1 = new Texture("sprites/test_1.png");
        test_2 = new Texture("sprites/test_2.png");

    }

    public static void initInBackground() {

    }
}
