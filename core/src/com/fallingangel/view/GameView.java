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
import com.fallingangel.controller.GameActionsController;
import com.fallingangel.game.FallingAngel;
import com.fallingangel.model.Asset;

public class GameView extends ScreenAdapter {

    private FallingAngel game;
    private Button pauseButton;
    private GameActionsController gameController;
    public Stage stage;

    public GameView() {
        super();
        this.game = FallingAngel.getInstance();
        this.gameController = game.mc.gameActionsController;
        gameController.setState(1);

    }

    //getter and setter for the pause button
    public void setPauseButton() {
        this.pauseButton = makeButton(Asset.pauseButton,150,150, Gdx.graphics.getWidth()*0.82f, Gdx.graphics.getHeight() * 0.9f);
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

    @Override
    public void render(float delta) {
        gameController.update(delta);
        gameController.updateRunning(delta);
        draw();
    }
}