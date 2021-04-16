package com.fallingangel.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
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
    private Texture background;
    private Texture backTexture;
    private Texture musicOnTexture;
    private Texture musicOffTexture;
    private Texture bunnyTexture;
    private Texture pigTexture;
    private Button backButton;
    private Button musicOnButton;
    private Button musicOffButton;
    private Button bunnyButton;
    private Button pigButton;
    private MainController controller;
    public Stage stage;

    public SettingsView(){
        super();
        this.game = FallingAngel.getInstance(); //sets the game as the game singleton object from the FallingAngel class
        this.controller = game.mc;//sets the controller as the main controller
        background = new Texture("backgrounds/settings_background.png");
        backTexture = new Texture("buttons/back_button.png");
        musicOnTexture = new Texture("buttons/on_button.png");
        musicOffTexture = new Texture("buttons/off_button.png");
        bunnyTexture = new Texture("characters/bunny/bunny_select_character.png");
        pigTexture = new Texture("characters/pig/pig_select_character.PNG");
        stage = new Stage(new ScreenViewport());//sets the stage as a new stage and a new viewport
        Gdx.input.setInputProcessor(stage);//sets input processor
        setBackButton();//creates a button
        setMusicOnButton();
        setMusicOffButton();
        setBunnyButton();
        setPigButton();
        stage.addActor(getBackButton());//adds the button as an actor to the stage
        stage.addActor(musicOnButton);
        stage.addActor(musicOffButton);
        stage.addActor(bunnyButton);
        stage.addActor(pigButton);
        game.setChosenCharacter(Asset.pigAnimation);
    }

    //setter and getter for the buttons
    public void setBackButton() {
        this.backButton = makeButton(backTexture,600,400, Gdx.graphics.getWidth()*0.5f-300, Gdx.graphics.getHeight() * 0.2f);
    }

    public Button getBackButton(){
        return backButton;
    }

    public void setMusicOnButton() {
        this.musicOnButton = makeButton(musicOnTexture,400,300, Gdx.graphics.getWidth()*0.6f, Gdx.graphics.getHeight() * 0.6f);
    }

    public Button getMusicOnButton(){
        return musicOnButton;
    }

    public void setMusicOffButton() {
        this.musicOffButton = makeButton(musicOffTexture,400,300, -1000,-1000);
    }

    public Button getMusicOffButton(){
        return musicOffButton;
    }

    //buttons for choosing character
    public void setBunnyButton() {
        this.bunnyButton = makeButton(bunnyTexture,200,400, -1000, -1000);
    }

    public Button getBunnyButton(){
        return bunnyButton;
    }

    public void setPigButton() {
        this.pigButton = makeButton(pigTexture,200,300, Gdx.graphics.getWidth()*0.8f-200, Gdx.graphics.getHeight() * 0.42f);
    }

    public Button getPigButton(){
        return pigButton;
    }


    //method for creating a button and adding the main controller as a listener
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
        Gdx.input.setInputProcessor(stage);//sets input processor
        game.batch.begin();
        game.batch.draw(background, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());//draws the sprite batch
        game.font.draw(game.batch, "Music: ", Gdx.graphics.getWidth() * .1f, Gdx.graphics.getHeight() * .68f);
        game.font.draw(game.batch, "Character: ", Gdx.graphics.getWidth() * .1f, Gdx.graphics.getHeight() * .5f);
        game.font.getData().setScale(8, 8);
        game.font.setColor(Color.BLACK);
        game.batch.end();
        stage.draw();//draws the stage
    }

    public void update(float dt) {

    }

    public void render(float dt) {
        update(dt);
        draw();
        stage.act(dt);
    }
}