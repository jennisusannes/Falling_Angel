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
import com.fallingangel.controller.MainController;
import com.fallingangel.game.FallingAngel;
import com.fallingangel.model.Asset;

public class LevelView extends ScreenAdapter {

    private FallingAngel game;
    private Button heavenButton;
    private Button sunsetButton;
    private Button hellButton;
    private MainController controller;
    private Stage stage;


    public LevelView(){
        super();
        this.game = FallingAngel.getInstance(); // Sets the game as the game singleton object from the FallingAngel class
        this.controller = game.mc;  // Sets the controller as the main controller
        stage = new Stage(new ScreenViewport()); // Sets the stage as a new stage and a new viewport
        //Gdx.input.setInputProcessor(stage); // Sets input processor
        setHeavenButton(); // Creates a button
        setSunsetButton();
        setHellButton();
        stage.addActor(heavenButton); // Adds the button as an actor to the stage
        stage.addActor(sunsetButton);
        stage.addActor(hellButton);
    }

    // Method for creating a button, this will add the GameActionsController as a listener
    private Button makeButton(Texture texture, float width, float height, float xPos, float yPos) {
        Button button = new Button(new TextureRegionDrawable(new TextureRegion(texture)));
        button.setSize(width, height);
        button.setPosition(xPos, yPos);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float xpos, float ypos) {
                controller = game.mc;
                controller.handle(inputEvent);
            }
        });
        return button;
    }

    // Getters and setters for the buttons
    public void setHeavenButton() {
        this.heavenButton = makeButton(Asset.heavenButton,Gdx.graphics.getWidth()*0.5f,Gdx.graphics.getHeight() * 0.2f, Gdx.graphics.getWidth()*0.25f, Gdx.graphics.getHeight() * 0.6f);
    }

    public Button getHeavenButton(){
        return heavenButton;
    }

    public void setSunsetButton() {
        this.sunsetButton = makeButton(Asset.sunsetButton,Gdx.graphics.getWidth()*0.5f,Gdx.graphics.getHeight() * 0.2f, Gdx.graphics.getWidth()*0.25f, Gdx.graphics.getHeight() * 0.35f);
    }

    public Button getSunsetButton(){
        return sunsetButton;
    }

    public void setHellButton() {
        this.hellButton = makeButton(Asset.hellButton,Gdx.graphics.getWidth()*0.5f,Gdx.graphics.getHeight() * 0.2f, Gdx.graphics.getWidth()*0.25f, Gdx.graphics.getHeight() * 0.1f);
    }

    public Button getHellButton(){
        return hellButton;
    }

    public void draw(){
        Gdx.input.setInputProcessor(stage); // Sets input processor
        game.batch.begin();
        game.batch.draw(Asset.heavenBackgroundPauseTextureRegion, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Draws the sprite batch
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



    //TODO: controlleren (game?) til denne skal bruke setLevel(TextureRegion chosenLevel) i RenderingSystems
    //TODO: lage knapper for hver level som returnerer Asset.'VALGT LEVEL'
}
