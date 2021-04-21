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

public class HelpView3 extends ScreenAdapter {

    private FallingAngel game;
    private Texture help3;
    private MainController controller;
    private Stage stage;
    private Button nextButton;

    public HelpView3(){
        super();
        this.game = FallingAngel.getInstance(); // Sets the game as the game singleton object from the FallingAngel class
        this.controller = game.mc;  // Sets the controller as the main controller
        stage = new Stage(new ScreenViewport()); // Sets the stage as a new stage and a new viewport
        Gdx.input.setInputProcessor(stage); // Sets input processor
        setNextButton(); // Creates a button
        stage.addActor(nextButton); // Adds the button as an actor to the stage
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

    //setter and getter for the back button
    public void setNextButton() {
        this.nextButton = makeButton(Assets.help3BackgroundTexture, Gdx.graphics.getWidth(),Gdx.graphics.getHeight(), 0, 0);
    }

    public Button getNextButton(){
        return nextButton;
    }


    public void draw(){
        Gdx.input.setInputProcessor(stage); // Sets input processor
        game.batch.begin();
        //game.batch.draw(help2, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //draws the sprite batch
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