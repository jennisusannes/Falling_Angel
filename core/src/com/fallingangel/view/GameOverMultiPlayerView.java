package com.fallingangel.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.fallingangel.controller.GameActionsController;
import com.fallingangel.controller.MultiplayerController;
import com.fallingangel.game.FallingAngel;
import com.fallingangel.model.Assets;

public class GameOverMultiPlayerView extends ScreenAdapter {

    // View for when the multiplayer game is over
    private FallingAngel game;
    private GameActionsController gameActionsController;
    private MultiplayerController multiplayerController;
    private Stage stage;
    private Button exitButton;

    public GameOverMultiPlayerView(){
        game = FallingAngel.getInstance();
        this.gameActionsController = game.mc.gameActionsController;
        this.multiplayerController = game.mc.gameActionsController.multiplayerController;
        stage = new Stage(new ScreenViewport());
        setExitButton();
    }

    public void draw(){
        Gdx.input.setInputProcessor(stage);
        game.batch.begin();
        game.batch.draw(Assets.gameOverMultiBackgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        if (multiplayerController.getWinnerStatus() == 1) {
            game.batch.draw(Assets.youWin, Gdx.graphics.getWidth()*0.5f - Assets.youWin.getWidth()*0.5f, Gdx.graphics.getHeight()*0.81f);
        }
        else if (multiplayerController.getWinnerStatus() == 2) {
            game.batch.draw(Assets.youLose, Gdx.graphics.getWidth()*0.5f - Assets.youLose.getWidth()*0.5f, Gdx.graphics.getHeight()*0.81f);
        }
        else if (multiplayerController.getWinnerStatus() == 0) {
            game.batch.draw(Assets.tie, Gdx.graphics.getWidth()*0.5f - Assets.tie.getWidth()*0.5f, Gdx.graphics.getHeight()*0.81f);
        }
        game.batch.end();
        stage.addActor(exitButton);
        stage.draw();
    }

    public void update(float dt){

    }

    @Override
    public void render(float dt) {
        update(dt);
        draw();
        stage.act(dt);
    }

    // Getter and setter for the exit button
    public void setExitButton() {
        this.exitButton = makeButton(Assets.exitButton,Gdx.graphics.getWidth()*0.4f,Gdx.graphics.getHeight()*0.1f, Gdx.graphics.getWidth()*0.3f, Gdx.graphics.getHeight() * 0.05f);
    }

    public Button getExitButton(){
        return exitButton;
    }

    // Method for creating a button, this will add the GameActionsController as a listener
    public Button makeButton(Texture texture, float width, float height, float xPos, float yPos) {
        Button button = new Button(new TextureRegionDrawable(new TextureRegion(texture)));
        button.setSize(width, height);
        button.setPosition(xPos, yPos);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float xpos, float ypos) {
                gameActionsController = game.mc.gameActionsController;
                gameActionsController.handle(inputEvent);
            }
        });
        return button;
    }

}