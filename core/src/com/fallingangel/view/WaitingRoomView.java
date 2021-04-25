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
import com.fallingangel.controller.MultiplayerController;
import com.fallingangel.controller.MainController;
import com.fallingangel.game.FallingAngel;
import com.fallingangel.model.Assets;

public class WaitingRoomView extends ScreenAdapter {

    private Stage stage;
    private MainController mainController;
    //private GameActionsController gameActionsController;
    private MultiplayerController multiplayerController;
    private Button backButton;
    //public String roomNumber;
    //public MultiPlayerData multiPlayerData;
    private FallingAngel game;

   // RoomInputListener roomListener;
    //NameInputListener nameListener;
    //boolean alreadyConnected = false;

    public WaitingRoomView() {
        this.game = FallingAngel.getInstance();
        this.mainController = game.mc;
        stage = new Stage(new ScreenViewport());
        setBackButton(); // Creates a back button
        stage.addActor(backButton); // Adds the button as an actor to the stage
    }

    // Getter and setter for the back button
    public void setBackButton() {
        this.backButton = makeButton(Assets.backButton, Gdx.graphics.getWidth() * 0.35f, Gdx.graphics.getHeight() * 0.1f, Gdx.graphics.getWidth() * 0.3f, Gdx.graphics.getHeight() * 0.05f);
    }

    public Button getBackButton() {
        return backButton;
    }

    // Method for creating a button, this will add the MainController as a listener
    private Button makeButton(Texture texture, float width, float height, float xPos, float yPos) {
        Button button = new Button(new TextureRegionDrawable(new TextureRegion(texture)));
        button.setSize(width, height);
        button.setPosition(xPos, yPos);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float xpos, float ypos) {
                mainController = game.mc;
                mainController.handle(inputEvent);
            }
        });
        return button;
    }

    public void draw() {
        Gdx.input.setInputProcessor(stage);
        game.batch.begin();
        game.batch.draw(Assets.heavenBackgroundPauseTextureRegion, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.draw(Assets.waitingRoomTexture, Gdx.graphics.getWidth() * 0.05f, Gdx.graphics.getHeight() * 0.65f, Gdx.graphics.getWidth() * 0.8f, Gdx.graphics.getHeight() * 0.15f);
        game.batch.end();
        stage.draw();
    }

    public void update(float dt) {
        this.multiplayerController = game.mc.gameActionsController.multiplayerController;
        multiplayerController.connectToDatabase();
        multiplayerController.checkIfReady();
    }


    @Override
    public void render(float dt) {
        update(dt);
        draw();
        stage.act(dt);
    }
}