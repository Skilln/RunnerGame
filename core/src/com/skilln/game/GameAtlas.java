package com.skilln.game;

import com.badlogic.gdx.graphics.Texture;
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

    public static Texture background_0;
    public static Texture background_1;
    public static Texture pixel;

    public static void init() {
        menu = new TextureAtlas("Menu.atlas");
        rain = new TextureAtlas("rain.atlas");
        enemy = new TextureAtlas("sprites/enemy/fire.atlas");
        soul = new TextureAtlas("sprites/soul/soul1.atlas");
        soul_left = new TextureAtlas("sprites/soul/soul1_left.atlas");
        soul_right = new TextureAtlas("sprites/soul/soul1_right.atlas");
        soul_die = new TextureAtlas("sprites/soul/soul_die.atlas");
        man = new TextureAtlas("sprites/man/Man.atlas");
        logo = new TextureAtlas("sprites/logo/Logo.atlas");
        cloud = new TextureAtlas("sprites/cloud/cloud.atlas");
        hollow = new TextureAtlas("sprites/hollow/hollow.atlas");
        soul_hole = new TextureAtlas("sprites/hole/soul_hole.atlas");
        soul_hole1 = new TextureAtlas("sprites/hole/soul_hole1.atlas");

        background_0 = new Texture("background_0.jpg");
        background_1 = new Texture("background.jpg");
        pixel = new Texture("pixel.png");

    }
}
