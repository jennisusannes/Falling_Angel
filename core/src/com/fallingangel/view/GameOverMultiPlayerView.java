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
        game.batch.draw(Asset.gameOverMultiBackgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        if (FallingAngel.getInstance().FBI.gameOverStatus().equals("gameWon")){
            game.batch.draw(Asset.youWin, Gdx.graphics.getWidth()*0.5f - Asset.youWin.getWidth()*0.5f, Gdx.graphics.getHeight()*0.81f);
        }
        else {
            game.batch.draw(Asset.youLose, Gdx.graphics.getWidth()*0.5f - Asset.youLose.getWidth()*0.5f, Gdx.graphics.getHeight()*0.81f);
        }
        // her er riktig str på tie text:)
        /*
        else {
            game.batch.draw(Asset.tie, Gdx.graphics.getWidth()*0.5f - Asset.tie.getWidth()*0.5f, Gdx.graphics.getHeight()*0.81f);
        }

         */
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