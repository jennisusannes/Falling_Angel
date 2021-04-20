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

    public static Button exitButton;

    public boolean isWinner;

    public GameOverMultiPlayerView(){
        this.gameActionsController = game.mc.gameActionsController;
        stage = new Stage(new ScreenViewport());
        exitButton = makeButton(Asset.exitButton);
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

}