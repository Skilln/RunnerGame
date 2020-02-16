package com.skilln.game.screen.ui;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.skilln.game.GameAtlas;
import com.skilln.game.GameState;
import com.skilln.game.WayToHeaven;
import com.skilln.game.object.player.Player;
import com.skilln.game.screen.GameScreen;
import com.skilln.game.screen.ScreenManager;

public class GameScreenUI {

    private Button left, right, toGame;

    private ImageButton pause;

    private Skin pauseSkin;

    private boolean touching;

    private Stage stage;
    private Player player;

    private GameScreen parent;

    private boolean onPause = false;

    public GameScreenUI(GameScreen parent) {
        this.parent = parent;

        player = parent.getPlayer();
        stage = parent.getStage();

        setupButton();
    }

    private void setupButton() {
        pauseSkin = new Skin(GameAtlas.pause);

        ImageButton.ImageButtonStyle pause_style = new ImageButton.ImageButtonStyle();

        pause_style.up = pauseSkin.getDrawable("pause");
        pause_style.down = pauseSkin.getDrawable("play");

        pause = new ImageButton(pause_style);

        pause.setWidth(100);
        pause.setHeight(100);

        pause.setX(WayToHeaven.width - pause.getWidth());
        pause.setY(WayToHeaven.height - pause.getHeight());

        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();

        left = new Button(buttonStyle);
        right = new Button(buttonStyle);
        toGame = new Button(buttonStyle);

        left.setWidth(WayToHeaven.width / 2f);
        left.setHeight(WayToHeaven.height - 100);

        left.setX(0);
        left.setY(0);

        left.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                if (!player.isDead()) {

                    player.getPlayerMovement().setCurrentSpeedX(-1);

                    touching = true;
                }

                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);

                player.getPlayerMovement().setCurrentSpeedX(0);

                touching = false;
            }
        });

        right.setWidth(WayToHeaven.width / 2f);
        right.setHeight(WayToHeaven.height - 100);

        right.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                if (!player.isDead()) {
                    player.getPlayerMovement().setCurrentSpeedX(1);
                    touching = true;
                }
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);

                player.getPlayerMovement().setCurrentSpeedX(0);

                touching = false;
            }
        });

        right.setX(WayToHeaven.width / 2f);
        right.setY(0);

        toGame.setWidth(WayToHeaven.width);
        toGame.setHeight(WayToHeaven.height - pause.getHeight());

        pause.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                pauseGame();

                return super.touchDown(event, x, y, pointer, button);
            }

        });

        toGame.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                pauseGame();

                toGame.setDisabled(false);

                return super.touchDown(event, x, y, pointer, button);
            }
        });

        stage.addListener(new ClickListener() {

            @Override
            public boolean keyDown(InputEvent event, int keycode) {

                if (keycode == Input.Keys.BACK) {
                    if (!onPause) {
                        pauseGame();
                        WayToHeaven.adHandler.toast("If you quit, progress wouldn't be saved");
                    } else {
                        ScreenManager.setScreen(GameState.MENU);
                    }
                }
                return super.keyDown(event, keycode);
            }

        });

        stage.addActor(right);
        stage.addActor(left);
        stage.addActor(pause);
    }

    public void disableControlls() {
        left.setDisabled(true);
        right.setDisabled(true);
    }

    public void enableControlls() {
        left.setDisabled(false);
        right.setDisabled(false);
    }

    public boolean isTouching() {
        return touching;
    }

    public boolean isOnPause() {
        return onPause;
    }

    public void pauseGame() {
        if (!onPause) {
            toGame.setDisabled(true);

            stage.addActor(toGame);

            disableControlls();
        } else {
            toGame.remove();

            enableControlls();
        }

        parent.pauseGame(onPause);
        onPause = !onPause;
    }

    public void dispose() {
        pauseSkin.dispose();
    }
}
