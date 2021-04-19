package com.fallingangel.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
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
import com.fallingangel.controller.MainController;
import com.fallingangel.game.FallingAngel;
import com.fallingangel.model.Asset;
import com.fallingangel.model.World;

public class GameOverView extends ScreenAdapter {

    private FallingAngel game;
    private GameActionsController gameController;
    private Stage stage;
    private Button backButton;
    private Button playAgainButton;
    private Texture background;

    public GameOverView(){
        super();
        this.game = FallingAngel.getInstance(); // Sets the game as the game singleton object from the FallingAngel class
        this.gameController = game.mc.gameActionsController; // Sets the controller as the main controller
        stage = new Stage(new ScreenViewport()); // Sets the stage as a new stage and a new viewport
        Gdx.input.setInputProcessor(stage); // Sets input processor
        setBackButton(); // Creates a back button
        setPlayAgainButton(); //Creates a play again button
        stage.addActor(backButton); // Adds the button as an actor to the stage
        stage.addActor(playAgainButton);
    }

    // Getter and setter for the back button
    public void setBackButton() {
        this.backButton = makeButton(Asset.backButton,Gdx.graphics.getWidth()*0.35f,Gdx.graphics.getHeight()*0.1f, Gdx.graphics.getWidth()*0.1f, Gdx.graphics.getHeight() * 0.05f);
    }

    public Button getBackButton(){
        return backButton;
    }

    // Getter and setter for the play again button
    public void setPlayAgainButton() {
        this.playAgainButton = makeButton(Asset.playAgainButton,Gdx.graphics.getWidth()*0.35f,Gdx.graphics.getHeight()*0.1f, Gdx.graphics.getWidth()*0.55f, Gdx.graphics.getHeight() * 0.05f);
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
    // winner = 0 -> single player, winner = 1 / 2 -> multiplayer
    public void setWinner(int winner) {
        if (winner == 1) {
            background = Asset.winner1BackgroundTexture;
        }
        else if (winner == 2) {
            background = Asset.winner2BackgroundTexture;
        }
        else {
            background = Asset.highscorelistBackgroundTexture;
        }
    }

    public void draw(){

        BitmapFont font = new BitmapFont();
        Gdx.input.setInputProcessor(stage);// Sets input processor
        game.batch.begin();
        game.batch.draw(background, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());// Draws the sprite batch
        font.getData().setScale(4, 4);
        font.draw(game.batch, String.valueOf(World.score), Gdx.graphics.getWidth()*0.45f, Gdx.graphics.getHeight()*0.258f);
        font.setColor(Color.BLACK);
        font.draw(game.batch, String.valueOf(World.score), Gdx.graphics.getWidth()*0.47f, Gdx.graphics.getHeight()*0.26f);
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