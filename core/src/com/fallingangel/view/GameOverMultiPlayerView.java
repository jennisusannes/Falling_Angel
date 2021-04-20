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
import com.fallingangel.controller.MainController;
import com.fallingangel.game.FallingAngel;
import com.fallingangel.model.Asset;

public class GameOverMultiPlayerView extends ScreenAdapter {
    //View for when the multiplayer game is over

    //TODO: change texture on exit button
    FallingAngel game = FallingAngel.getInstance();
    GameActionsController gameActionsController;
    Stage stage;

    private Button exitButton;

    public boolean isWinner;

    public GameOverMultiPlayerView(){
        this.gameActionsController = game.mc.gameActionsController;
        stage = new Stage(new ScreenViewport());
        setExitButton();
        //if your score is the best
      /*  if (FallingAngel.getInstance().FBI.gameOverStatus().equals("gameWon")){
            isWinner = true;
        }
        else {
            isWinner = false;
        }*/
       // FallingAngel.getInstance().FBI.destroyRoom();

    }

    public void draw(){
        Gdx.input.setInputProcessor(stage);
        game.batch.begin();
        game.batch.draw(Asset.heavenBackgroundPauseTextureRegion, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        if (FallingAngel.getInstance().FBI.gameOverStatus().equals("gameWon")){
            game.batch.draw(Asset.youWin, Gdx.graphics.getWidth()/2 - Asset.youWin.getWidth()/2, Gdx.graphics.getHeight()*3/4);
        }
        else {
            game.batch.draw(Asset.youLose, Gdx.graphics.getWidth()/2 - Asset.youLose.getWidth()/2, Gdx.graphics.getHeight()*3/4);
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

    public Button makeButton(Texture texture){
        Button button = new Button(new TextureRegionDrawable(new TextureRegion(texture)));
        button.setSize(200,200);
        button.setPosition(Gdx.graphics.getWidth()/2 - 100,Gdx.graphics.getHeight()/5);
        stage.addActor(button);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float xpos, float ypos) {
                gameActionsController = game.mc.gameActionsController;
                gameActionsController.handle(inputEvent);
            }
        });
        return button;
    }

    // Getter and setter for the exit button
    public void setExitButton() {
        this.exitButton = makeButton(Asset.exitButton,Gdx.graphics.getWidth()*0.4f,Gdx.graphics.getHeight()*0.1f, Gdx.graphics.getWidth()*0.3f, Gdx.graphics.getHeight() * 0.05f);
    }

    public Button getExitButton(){
        return exitButton;
    }

    // Method for creating a button, this will add the MainController as a listener
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