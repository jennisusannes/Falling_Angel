package com.fallingangel.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
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

public class MenuView extends ScreenAdapter {

    private FallingAngel game;
    private Stage stage;
    private MainController controller;

    // Buttons
    private Button singlePlayerButton;
    private Button multiPlayerButton;
    //private Button highscoreListButton;
    private Button settingsButton;
    private Button helpButton;

    public MenuView(){
        super();
        this.game = FallingAngel.getInstance();
        this.controller = game.mc;
        stage = new Stage(new ScreenViewport()); //crates a new stage and a viewport

        // Create buttons and adds them to the stage as actors
        setSinglePlayerButton();
        stage.addActor(singlePlayerButton);

        setMultiPlayerButton();
        stage.addActor(multiPlayerButton);

        // Not fully implementet highscorelist
        /*
        setHighscoreListButton();
        stage.addActor(highscoreListButton);
        */

        setSettingsButton();
        stage.addActor(settingsButton);

        setHelpButton();
        stage.addActor(helpButton);

    }

    // Getters and setters for buttons

    public void setSinglePlayerButton() {
        this.singlePlayerButton = makeButton(Assets.singlePlayerButton,Gdx.graphics.getWidth()*0.38f, Gdx.graphics.getHeight()*0.15f, Gdx.graphics.getWidth()*0.09f, Gdx.graphics.getHeight() * 0.4f);
    }

    public Button getSinglePlayerButton(){
        return singlePlayerButton;
    }

    public void setMultiPlayerButton() {
        this.multiPlayerButton = makeButton(Assets.multiPlayerButton,Gdx.graphics.getWidth()*0.38f, Gdx.graphics.getHeight()*0.15f, Gdx.graphics.getWidth()*0.53f, Gdx.graphics.getHeight() * 0.4f);
    }

    public Button getMultiPlayerButton(){
        return multiPlayerButton;
    }

    // Removed achievements
    /*
    public void setAchievementsButton() {
        this.achievementsButton = makeButton(Asset.achievementsButton, 200,200, Gdx.graphics.getWidth()*0.33f - 200*0.67f, Gdx.graphics.getHeight() * 0.15f);
    }

    public Button getAchievementsButton(){
        return achievementsButton;
    }
     */

    // Not fully implementet highscorelist
    /*
    public void setHighscoreListButton() {
        this.highscoreListButton = makeButton(Assets.highscorelistButton,Gdx.graphics.getWidth()*0.15f, Gdx.graphics.getWidth()*0.15f,Gdx.graphics.getWidth()*0.33f - 200*0.67f, Gdx.graphics.getHeight() * 0.15f);
    }

    public Button getHighscoreListButton(){
        return highscoreListButton;
    }

     */


    public void setSettingsButton() {
        this.settingsButton = makeButton(Assets.settingsButton,Gdx.graphics.getWidth()*0.12f,Gdx.graphics.getWidth()*0.12f,Gdx.graphics.getWidth()*0.82f, Gdx.graphics.getHeight() * 0.9f);
    }

    public Button getSettingsButton(){
        return settingsButton;
    }

    // Removed highscorelist and moved help button
    /*
    public void setHelpButton() {
        this.helpButton = makeButton(Assets.helpButton,Gdx.graphics.getWidth()*0.15f, Gdx.graphics.getWidth()*0.15f, Gdx.graphics.getWidth()*0.67f - 200*0.33f, Gdx.graphics.getHeight() * 0.15f);
    }
    */
    public void setHelpButton() {
        this.helpButton = makeButton(Assets.helpButton,Gdx.graphics.getWidth()*0.12f, Gdx.graphics.getWidth()*0.12f, Gdx.graphics.getWidth()*0.82f, Gdx.graphics.getHeight() * 0.03f);
    }

    public Button getHelpButton(){
        return helpButton;
    }


    public void draw() {
        GL20 gl = Gdx.gl;
        gl.glClearColor(1, 0, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        Gdx.input.setInputProcessor(stage); // Sets the input processor

        game.batch.begin(); // Draw elements to Sprite Batch
        game.batch.draw(Assets.menuBackgroundTexture, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //Draws background photo
        game.batch.end();
        stage.draw(); // Draws the stage
    }

    // Method for creating a button, this will add the MainController as a listener
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

    public void update(float dt) {

    }

    @Override
    public void render(float dt) {
        update(dt);
        draw();
        stage.act(dt);
    }
}