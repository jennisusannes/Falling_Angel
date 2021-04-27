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
import com.fallingangel.model.Assets;

public class SettingsView extends ScreenAdapter {

    private FallingAngel game;
    private MainController controller;
    public Stage stage;
    private Button backButton;
    private Button musicOnButton;
    private Button musicOffButton;
    private Button bunnyButton;
    private Button pigButton;
    public String angel;

    public SettingsView(){
        super();
        this.game = FallingAngel.getInstance(); // Sets the game as the game singleton object from the FallingAngel class
        this.controller = game.mc; // Sets the controller as the main controller
        stage = new Stage(new ScreenViewport()); // Sets the stage as a new stage and a new viewport

        // Sets input processor
        Gdx.input.setInputProcessor(stage);

        // Creates a back button
        setBackButton(); // Creates a back button

        // Adds the button as an actor to the stage
        stage.addActor(backButton);

        // Creates on and off buttons for the music
        setMusicOnButton();
        setMusicOffButton();

        // Adds the buttons as actors to the stage
        stage.addActor(musicOnButton);
        stage.addActor(musicOffButton);

        //Creates buttons for choosing either bunny or pig as the angel
        setBunnyButton();
        setPigButton();

        // Adds the buttons as actors to the stage
        stage.addActor(bunnyButton);
        stage.addActor(pigButton);
    }

    // Getters and setters for the buttons
    public void setBackButton() {
        this.backButton = makeButton(Assets.backButton,Gdx.graphics.getWidth()*0.4f,Gdx.graphics.getHeight()*0.1f, Gdx.graphics.getWidth()*0.3f, Gdx.graphics.getHeight() * 0.05f);
    }

    public Button getBackButton(){
        return backButton;
    }

    public void setMusicOnButton() {
        this.musicOnButton = makeButton(Assets.musicOnButton,Gdx.graphics.getWidth()*0.4f,Gdx.graphics.getHeight()*0.1f, Gdx.graphics.getWidth()*0.55f, Gdx.graphics.getHeight() * 0.62f);
    }

    public Button getMusicOnButton(){
        return musicOnButton;
    }

    public void setMusicOffButton() {
        this.musicOffButton = makeButton(Assets.musicOffButton,Gdx.graphics.getWidth()*0.4f,Gdx.graphics.getHeight()*0.1f, -1000,-1000);
    }

    public Button getMusicOffButton(){
        return musicOffButton;
    }

    public void setBunnyButton() {
        this.bunnyButton = makeButton(Assets.bunnyButton,Gdx.graphics.getWidth()*0.4f,Gdx.graphics.getHeight()*0.2f, Gdx.graphics.getWidth()*0.05f, Gdx.graphics.getHeight() * 0.25f);
    }

    public Button getBunnyButton() {
        return bunnyButton;
    }

    public void setPigButton() {
        this.pigButton = makeButton(Assets.pigButton,Gdx.graphics.getWidth()*0.4f,Gdx.graphics.getHeight()*0.2f, Gdx.graphics.getWidth()*0.55f, Gdx.graphics.getHeight() * 0.25f);
    }

    public Button getPigButton() {
        return pigButton;
    }

    // Method for creating a button, this will add the MainController as a listener
    public Button makeButton(Texture texture, float width, float height, float xPos, float yPos) {
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

    public void draw(){
        Gdx.input.setInputProcessor(stage); // Sets input processor
        game.batch.begin();
        game.batch.draw(Assets.settingsBackgroundTexture, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Draws the sprite batch

        // Draws a green square around the chosen character
        this.controller = game.mc;
        if (controller.isPig()) {
            game.batch.draw(Assets.selected, Gdx.graphics.getWidth()*0.55f,Gdx.graphics.getHeight() * 0.25f,Gdx.graphics.getWidth()*0.4f,Gdx.graphics.getHeight()*0.2f );
        }
        else {
            game.batch.draw(Assets.selected, Gdx.graphics.getWidth()*0.05f,Gdx.graphics.getHeight() * 0.25f,Gdx.graphics.getWidth()*0.4f,Gdx.graphics.getHeight()*0.2f);
        }

        // stops drawing the sprite batch
        game.batch.end();

        // Draws the stage
        stage.draw();
    }

    public void update(float dt) {

    }

    public void render(float dt) {
        update(dt);
        draw();
        stage.act(dt);
    }
}