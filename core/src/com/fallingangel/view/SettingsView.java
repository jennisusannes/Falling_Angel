package com.fallingangel.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
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

public class SettingsView extends ScreenAdapter {

    private FallingAngel game;
    private MainController controller;
    public Stage stage;
    private Button backButton;
    private Button musicOnButton;
    private Button musicOffButton;
    private Button bunnyButton;
    private Button pigButton;

    public SettingsView(){
        super();
        this.game = FallingAngel.getInstance(); // Sets the game as the game singleton object from the FallingAngel class
        this.controller = game.mc; // Sets the controller as the main controller
        stage = new Stage(new ScreenViewport()); // Sets the stage as a new stage and a new viewport
        Gdx.input.setInputProcessor(stage); // Sets input processor
        setBackButton(); // Creates a button
        stage.addActor(getBackButton()); // Adds the button as an actor to the stage
        setMusicOnButton();
        setMusicOffButton();
        stage.addActor(musicOnButton);
        stage.addActor(musicOffButton);
        setBunnyButton();
        setPigButton();
        stage.addActor(bunnyButton);
        stage.addActor(pigButton);
    }

    // Getters and setters for the buttons
    public void setBackButton() {
        this.backButton = makeButton(Asset.backButton,600,400, Gdx.graphics.getWidth()*0.3f, Gdx.graphics.getHeight() * 0.05f);
    }

    public Button getBackButton(){
        return backButton;
    }

    public void setMusicOnButton() {
        this.musicOnButton = makeButton(Asset.musicOnButton,500,300, Gdx.graphics.getWidth()*0.6f, Gdx.graphics.getHeight() * 0.6f);
    }

    public Button getMusicOnButton(){
        return musicOnButton;
    }

    public void setMusicOffButton() {
        this.musicOffButton = makeButton(Asset.musicOffButton,500,300, -1000,-1000);
    }

    public Button getMusicOffButton(){
        return musicOffButton;
    }

    public void setBunnyButton() {
        this.bunnyButton = makeButton(Asset.bunnyButton,600,500, Gdx.graphics.getWidth()*0.05f, Gdx.graphics.getHeight() * 0.3f);
    }

    public Button getBunnyButton() {
        return bunnyButton;
    }

    public void setPigButton() {
        this.pigButton = makeButton(Asset.pigButton,600,500, Gdx.graphics.getWidth()*0.55f, Gdx.graphics.getHeight() * 0.3f);
    }

    public Button getPigButton() {
        return pigButton;
    }

    public Button rightSoundButton(){
        if (game.music.isPlaying()) {
            return musicOnButton;
        }
        else {
            return musicOffButton;
        }
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
        Gdx.input.setInputProcessor(stage);// Sets input processor
        game.batch.begin();
        game.batch.draw(Asset.settingsBackgroundTexture, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Draws the sprite batch
        //game.font.draw(game.batch, "Music: ", Gdx.graphics.getWidth() * .1f, Gdx.graphics.getHeight() * .68f);
        //game.font.getData().setScale(8, 8);
        //game.font.setColor(Color.BLACK);
        game.batch.end();
        stage.draw();// Draws the stage
    }

    public void update(float dt) {

    }

    public void render(float dt) {
        update(dt);
        draw();
        stage.act(dt);
    }
}