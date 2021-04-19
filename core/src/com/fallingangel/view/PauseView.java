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
import com.fallingangel.game.FallingAngel;
import com.fallingangel.model.Asset;

public class PauseView extends ScreenAdapter {

    private FallingAngel game;
    private Button exitButton;
    private Button resumeButton;
    private GameActionsController gameController;
    private Stage stage;

    public PauseView(){
        super();
        this.game = FallingAngel.getInstance(); // Sets the game as the game singleton object from the FallingAngel class
        this.gameController = game.mc.gameActionsController;  // Sets the controller as the main controller
        stage = new Stage(new ScreenViewport()); // Sets the stage as a new stage and a new viewport
        Gdx.input.setInputProcessor(stage); // Sets input processor
        setExitButton(); // Creates a button
        setResumeButton();
        stage.addActor(exitButton); // Adds the button as an actor to the stage
        stage.addActor(resumeButton);
    }

    // Getters and setters for the buttons
    public void setExitButton() {
        this.exitButton = makeButton(Asset.exitButton,Gdx.graphics.getWidth()*0.4f,Gdx.graphics.getHeight() * 0.1f, Gdx.graphics.getWidth()*0.3f, Gdx.graphics.getHeight() * 0.2f);
    }

    public Button getExitButton(){
        return exitButton;
    }

    public void setResumeButton() {
        this.resumeButton = makeButton(Asset.resumeButton,Gdx.graphics.getWidth()*0.65f,Gdx.graphics.getHeight() * 0.1f, Gdx.graphics.getWidth()*0.20f, Gdx.graphics.getHeight() * 0.4f);
    }

    public Button getResumeButton(){
        return resumeButton;
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

    public void draw(){
        Gdx.input.setInputProcessor(stage); // Sets input processor
        game.batch.begin();
        game.batch.draw(Asset.hellBackgroundTextureRegion, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Draws the sprite batch
        game.batch.end();
        stage.draw(); // Draws the stage
    }

    public void update(float dt) {

    }

    public void render(float dt) {
        update(dt);
        draw();
        stage.act();
    }
}