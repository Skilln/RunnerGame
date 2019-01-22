package com.skilln.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.skilln.game.Application;
import com.skilln.game.GameAtlas;
import com.skilln.game.GameState;

public class ShopScreen implements Screen {

    private OrthographicCamera camera;
    private Stage stage;
    private SpriteBatch batch;

    private Texture back;

    private TextButton toMenuButton;

    private Skin toMenuSkin;
    private Skin sold_soul[] = new Skin[4];
    private Skin soul[] = new Skin[3];

    private TextureAtlas buttonAtlas, shopButton;

    private ImageButton[] shopImageButton = new ImageButton[4];

    private BitmapFont font, label;

    private int coins, selected;
    private boolean sold[] = new boolean[4];
    private int[] price = {0, 100, 500, 1000};
    private String[] keys = {"soul", "first_soul", "hollow_soul", "alpha_soul"};

    @Override
    public void show() {

        camera = new OrthographicCamera(Application.width, Application.height);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);

        FitViewport viewport = new FitViewport(Application.width, Application.height, camera);

        batch = new SpriteBatch();

        stage = new Stage(viewport, batch);

        Gdx.input.setInputProcessor(stage);

        back = GameAtlas.background_1;

        buttonAtlas = GameAtlas.button;

        toMenuSkin = new Skin(buttonAtlas);

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();

        style.up = toMenuSkin.getDrawable("button_up");
        style.down = toMenuSkin.getDrawable("button_down");
        style.fontColor = Color.WHITE;

        font = new BitmapFont(Gdx.files.internal("sprites/font/font.fnt"));
        label = new BitmapFont(Gdx.files.internal("sprites/font/shop.fnt"));

        style.font = font;

        initButton();

        toMenuButton = new TextButton("To menu", style);

        toMenuButton.setWidth(400);
        toMenuButton.setHeight(100);

        toMenuButton.setX(Application.width/2-toMenuButton.getWidth()/2);
        toMenuButton.setY(Application.height/2-toMenuButton.getHeight()/2-450);

        toMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.setScreen(GameState.MENU);
            }
        });

        int coins = Application.data.getInteger("coins");

        if(coins == Integer.MIN_VALUE) {
            this.coins = 0;
        } else {
            this.coins = coins;
        }

        stage.addListener(new ClickListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if(keycode == Input.Keys.BACK) {
                    ScreenManager.setScreen(GameState.MENU);
                }

                return super.keyDown(event, keycode);
            }
        });

        stage.addActor(toMenuButton);
        stage.addActor(shopImageButton[0]);
        stage.addActor(shopImageButton[1]);
        stage.addActor(shopImageButton[2]);
        stage.addActor(shopImageButton[3]);
    }



    @Override
    public void render(float delta) {
        batch.begin();

        batch.draw(back, -(Application.widthFixed-Application.width)/2f, 0, Application.widthFixed, Application.height);

        String st = "coins : " + coins;

        font.draw(batch, st, Application.width/2f-((st.length()/2f)*20), Application.height/2f+400);
        label.draw(batch, "HEAVEN SHOP", 200, Application.height/2f+500);

        batch.end();

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        toMenuButton.remove();
    }

    @Override
    public void dispose() {

    }

    private void initButton() {
        shopButton = GameAtlas.shopButton;

        sold_soul[0] = new Skin(GameAtlas.soul_default);
        sold_soul[1] = new Skin(GameAtlas.first_soul_sold);
        sold_soul[2] = new Skin(GameAtlas.hollow_soul_sold);
        sold_soul[3] = new Skin(GameAtlas.alpha_soul_sold);

        soul[0] = new Skin(GameAtlas.first_soul);
        soul[1] = new Skin(GameAtlas.hollow_soul);
        soul[2] = new Skin(GameAtlas.alpha_soul);

        selected = Application.data.getInteger("selected");

        sold[0] = true;
        sold[1] = Application.data.getBoolean("first_soul");
        sold[2] = Application.data.getBoolean("hollow_soul");
        sold[3] = Application.data.getBoolean("alpha_soul");

        for(int i = 0; i < 4; i++) {

            ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();

            if(sold[i]) {
               style.checked = sold_soul[i].getDrawable("check");
               style.up = sold_soul[i].getDrawable("up");

            } else {
                style.up = soul[i-1].getDrawable("up");
                style.down = soul[i-1].getDrawable("down");
            }

            shopImageButton[i] = new ImageButton(style);

            if(selected == i) {
                Gdx.app.log("SHOP", selected + "");
                shopImageButton[i].setChecked(true);
            }

            shopImageButton[i].setWidth(300);
            shopImageButton[i].setHeight(300);

            final int index = i;

            shopImageButton[i].addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                    if(!sold[index]) {
                        if(coins >= price[index]) {
                            sold[index] = true;

                            Application.data.putBoolean(keys[index], true);
                            Application.data.putInteger("coins", coins-price[index]);
                            Application.data.flush();

                            coins -=price[index];

                            ImageButton.ImageButtonStyle imageButtonStyle = new ImageButton.ImageButtonStyle();

                            imageButtonStyle.checked = sold_soul[index].getDrawable("check");
                            imageButtonStyle.up = sold_soul[index].getDrawable("up");

                            select(index);

                            shopImageButton[index].setStyle(imageButtonStyle);
                            GameAtlas.loadSkin(index);

                        } else {

                            Gdx.app.log("SHOP", "Not enough heaven coins to purchase");

                            if(Application.adHandler != null) {
                                 Application.adHandler.toast("Not enough heaven coins to purchase!");
                            }
                        }
                    } else {
                        select(index);
                        GameAtlas.loadSkin(index);

                    }

                    return super.touchDown(event, x, y, pointer, button);
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                    shopImageButton[index].setChecked(true);

                    super.touchUp(event, x, y, pointer, button);
                }
            });



        }

        shopImageButton[0].setX(60); shopImageButton[0].setY(Application.height/2);
        shopImageButton[1].setX(360); shopImageButton[1].setY(Application.height/2);
        shopImageButton[2].setX(60); shopImageButton[2].setY(Application.height/2-300);
        shopImageButton[3].setX(360); shopImageButton[3].setY(Application.height/2-300);
    }

    private void select(int index) {
        for(int i = 0; i < 4; i++) {
            if(i == index) {
                if(sold[i]) shopImageButton[i].setChecked(true);

                selected = index;

                Application.data.putInteger("selected", selected);
                Application.data.flush();

            } else {
                if(sold[i]) shopImageButton[i].setChecked(false);
            }
        }
    }


}
