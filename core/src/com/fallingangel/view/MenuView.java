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

public class MenuView extends ScreenAdapter {

    private Texture background;

    // textures for buttons
    private Texture singlePlayerTexture;
    private Texture multiPlayerTexture;
    private Texture achievementsTexture;
    private Texture highscoreListTexture;
    private Texture settingsTexture;
    private FallingAngel game;
    private Stage stage;
    private MainController controller;

    // buttons
    private Button singlePlayerButton;
    private Button multiPlayerButton;
    private Button achievementsButton;
    private Button highscoreListButton;
    private Button settingsButton;

    public MenuView(){
        super();
        this.game = FallingAngel.getInstance();
        this.controller = game.mc;
        background = new Texture("backgrounds/mainmenu_background.png");

        // button textures
        singlePlayerTexture = new Texture("buttons/singleplayer_button.PNG");
        multiPlayerTexture = new Texture("buttons/multiplayer_button.PNG");
        achievementsTexture = new Texture("buttons/achievements_button.PNG");
        highscoreListTexture = new Texture("buttons/highscorelist_button.PNG");
        settingsTexture = new Texture("buttons/settings_button.PNG");
    }

    // getters and setters for buttons

    public void setSinglePlayerButton() {
        this.singlePlayerButton = makeButton(singlePlayerTexture,600, 400, Gdx.graphics.getWidth()*0.05f, Gdx.graphics.getHeight() * 0.4f);
    }

    public Button getSinglePlayerButton(){
        return singlePlayerButton;
    }


    public void setMultiPlayerButton() {
        this.multiPlayerButton = makeButton(multiPlayerTexture,600, 400, Gdx.graphics.getWidth()*0.55f, Gdx.graphics.getHeight() * 0.4f);
    }

    public Button getMultiPlayerButton(){
        return multiPlayerButton;
    }


    public void setAchievementsButton() {
        this.achievementsButton = makeButton(achievementsTexture, 200,200, Gdx.graphics.getWidth()*0.2f, Gdx.graphics.getHeight() * 0.15f);
    }

    public Button getAchievementsButton(){
        return achievementsButton;
    }


    public void setHighscoreListButton() {
        this.highscoreListButton = makeButton(highscoreListTexture,200, 200,Gdx.graphics.getWidth()*0.65f, Gdx.graphics.getHeight() * 0.15f);
    }

    public Button getHighscoreListButton(){
        return highscoreListButton;
    }


    public void setSettingsButton() {
        this.settingsButton = makeButton(settingsTexture,200,200,Gdx.graphics.getWidth()*0.8f, Gdx.graphics.getHeight() * 0.89f);
    }

    public Button getSettingsButton(){
        return settingsButton;
    }


    public void draw() {
        GL20 gl = Gdx.gl;
        gl.glClearColor(1, 0, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage = new Stage(new ScreenViewport()); //crates a new stage and a viewport
        Gdx.input.setInputProcessor(stage); //sets the input processor

        //created buttons and adds them to the stage as actors

        setSinglePlayerButton();
        stage.addActor(getSinglePlayerButton());

        setMultiPlayerButton();
        stage.addActor(getMultiPlayerButton());

        setAchievementsButton();
        stage.addActor(getAchievementsButton());

        setHighscoreListButton();
        stage.addActor(getHighscoreListButton());

        setSettingsButton();
        stage.addActor(getSettingsButton());

        game.batch.begin(); // Draw elements to Sprite Batch
        game.batch.draw(background, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //Draws background photo
        game.batch.end();
        stage.draw(); //draws the stage
    }

    //method for creating a button. This will set the MainController as a listener
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

    public void update(float dt) {

    }

    @Override
    public void render(float dt) {
        update(dt);
        draw();
    }

}