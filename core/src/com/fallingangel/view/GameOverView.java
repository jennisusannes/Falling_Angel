package com.fallingangel.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.fallingangel.controller.GameActionsController;
import com.fallingangel.game.FallingAngel;
import com.fallingangel.model.Assets;
import com.fallingangel.model.World;

public class GameOverView extends ScreenAdapter {

    private FallingAngel game;
    private GameActionsController gameController;
    private Stage stage;
    private Button exitButton;
    private Button playAgainButton;
    private BitmapFont font = new BitmapFont();

    public GameOverView(){
        super();
        this.game = FallingAngel.getInstance(); // Sets the game as the game singleton object from the FallingAngel class
        this.gameController = game.mc.gameActionsController; // Sets the controller as the main controller
        stage = new Stage(new ScreenViewport()); // Sets the stage as a new stage and a new viewport
        Gdx.input.setInputProcessor(stage); // Sets input processor
        setExitButton(); // Creates a back button
        setPlayAgainButton(); //Creates a play again button
        stage.addActor(exitButton); // Adds the button as an actor to the stage
        stage.addActor(playAgainButton);
    }

    // Getter and setter for the exit button
    public void setExitButton() {
        this.exitButton = makeButton(Assets.exitButton,Gdx.graphics.getWidth()*0.4f,Gdx.graphics.getHeight()*0.1f, Gdx.graphics.getWidth()*0.3f, Gdx.graphics.getHeight() * 0.05f);
    }

    public Button getExitButton(){
        return exitButton;
    }

    // Getter and setter for the play again button
    public void setPlayAgainButton() {
        this.playAgainButton = makeButton(Assets.playAgainButton,Gdx.graphics.getWidth()*0.65f,Gdx.graphics.getHeight() * 0.1f, Gdx.graphics.getWidth()*0.18f, Gdx.graphics.getHeight() * 0.16f);
    }

    public Button getPlayAgainButton(){
        return playAgainButton;
    }

    // Method for creating a button, this will add the MainController as a listener
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
        this.font = Assets.font;
        Gdx.input.setInputProcessor(stage);// Sets input processor
        game.batch.begin();
        game.batch.draw(Assets.gameOverSingleBackgroundTexture, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());// Draws the sprite batch
        font.getData().setScale(8, 8);
        font.draw(game.batch, String.valueOf(World.score), Gdx.graphics.getWidth()*0.45f, Gdx.graphics.getHeight()*0.4f);
        game.batch.end();
        stage.draw(); // Draws the stage
    }

    public void update(float dt) {

    }

    public void render(float dt) {
        update(dt);
        draw();
        stage.act(dt);
    }
}