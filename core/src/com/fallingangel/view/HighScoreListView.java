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

public class HighScoreListView extends ScreenAdapter {

    private FallingAngel game;
    private Texture background;
    private Texture backTexture;
    private Button backButton;
    private MainController controller;
    private Stage stage;

    public HighScoreListView(){
        super();
        this.game = FallingAngel.getInstance(); //sets the game as the game singleton object from the FallingAngel class
        this.controller = game.mc; //sets the controller as the main controller
        background = new Texture("backgrounds/highscorelist_blurred_background.png");
        backTexture = new Texture("buttons/back_button.png");

        stage = new Stage(new ScreenViewport()); //sets the stage as a new stage and a new viewport
        Gdx.input.setInputProcessor(stage); //sets input processor
        setBackButton(); //creates a button
        stage.addActor(getBackButton()); //adds the button as an actor to the stage
    }

    //setter and getter for the back button
    public void setBackButton() {
        this.backButton = makeButton(backTexture,600,400, Gdx.graphics.getWidth()*0.3f, Gdx.graphics.getHeight() * 0.2f);
    }

    public Button getBackButton(){
        return backButton;
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
        Gdx.input.setInputProcessor(stage); //sets input processor
        game.batch.begin();
        game.batch.draw(background, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //draws the sprite batch
        game.batch.end();
        stage.draw(); //draws the stage
    }

    public void update(float dt) {

    }

    public void render(float dt) {
        update(dt);
        draw();

    }
}