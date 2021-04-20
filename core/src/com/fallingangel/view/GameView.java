package com.fallingangel.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.Input.Keys;

import com.fallingangel.controller.system.AnimationSystem;
import com.fallingangel.controller.system.BackgroundSystem;
import com.fallingangel.controller.system.BoundsSystem;
import com.fallingangel.controller.system.CoinSystem;
import com.fallingangel.controller.system.CollisionSystem;
import com.fallingangel.controller.system.MovementSystem;
import com.fallingangel.controller.system.DroneSystem;
import com.fallingangel.controller.system.MultiplayerSystem;
import com.fallingangel.controller.system.RenderingSystem;
import com.fallingangel.controller.system.StateSystem;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.fallingangel.controller.GameActionsController;
import com.fallingangel.game.FallingAngel;
import com.fallingangel.model.Asset;

public class GameView extends ScreenAdapter {

    private FallingAngel game;
    private Button pauseButton;
    private GameActionsController gameController;
    public Stage stage;
    public boolean isMultiplayer;


    public GameView(boolean isMultiplayer) {
        super();
        this.game = FallingAngel.getInstance();
        this.gameController = game.mc.gameActionsController;
        gameController.setState(1);
        //this.game  = FallingAngel.getInstance();
        this.isMultiplayer = isMultiplayer;


    }

    //getter and setter for the pause button
    public void setPauseButton() {
        this.pauseButton = makeButton(Asset.pauseButton,Gdx.graphics.getWidth()*0.10f,Gdx.graphics.getWidth()*0.10f, Gdx.graphics.getWidth()*0.82f, Gdx.graphics.getHeight() * 0.9f);
    }

    public Button getPauseButton(){
        return pauseButton;
    }

    // Method for creating a button, this will add the GameActionsController as a listener
    private Button makeButton(Texture texture, float width, float height, float xPos, float yPos) {
        Button button = new Button(new TextureRegionDrawable(new TextureRegion(texture)));
        button.setSize(width, height);
        button.setPosition(xPos, yPos);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float xpos, float ypos) {
                gameController = game.mc.gameActionsController;
                gameController.handle(inputEvent);
            }
        });
        return button;
    }



    public void draw() {
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        setPauseButton();
        stage.addActor(pauseButton);
        stage.draw();
    }



    //if one of the gameOver fields from database are true
    //state = GAME_OVER;

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameController.update(delta);
        gameController.updateRunning(delta);
        draw();
    }
}