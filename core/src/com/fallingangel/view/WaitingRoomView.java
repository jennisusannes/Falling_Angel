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
import com.fallingangel.RoomInputListener;
import com.fallingangel.NameInputListener;
import com.fallingangel.backend.MultiPlayerData;
import com.fallingangel.controller.GameActionsController;
import com.fallingangel.controller.MainController;
import com.fallingangel.game.FallingAngel;
import com.fallingangel.model.Assets;

public class WaitingRoomView extends ScreenAdapter {
    //This view is the waiting room for the multiplayer game

    //TextField textField;
    Stage stage;
    //MainController controller;
    GameActionsController gameActionsController;
    MainController mainController;

    //Texture readyTexture;
    //public Button readyButton;
    private Button backButton;

    public String roomNumber;
    public MultiPlayerData multiPlayerData;
    public FallingAngel game = FallingAngel.getInstance();

    RoomInputListener roomListener;
    NameInputListener nameListener;
    boolean alreadyConnected = false;

    public WaitingRoomView() {
        this.gameActionsController = game.mc.gameActionsController;
        this.mainController = game.mc;
        stage = new Stage(new ScreenViewport());
        setBackButton(); // Creates a back button

    }

    public void draw(){
        GL20 gl = Gdx.gl;
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.input.setInputProcessor(stage);
        game.batch.begin();
        game.batch.draw(Assets.heavenBackgroundPauseTextureRegion, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.draw(Assets.waitingRoomTexture, Gdx.graphics.getWidth()*0.05f, Gdx.graphics.getHeight()*0.65f, Gdx.graphics.getWidth()*0.8f, Gdx.graphics.getHeight()*0.15f);
        game.batch.end();
        stage.addActor(backButton); // Adds the button as an actor to the stage
        stage.draw();
    }

    public void update(float dt){
        connectToDatabase();
        checkIfReady();
    }


    @Override
    public void render(float dt) {
        update(dt);
        draw();
        stage.act(dt);
    }

    // Getter and setter for the back button
    public void setBackButton() {
        this.backButton = makeButton(Assets.backButton,Gdx.graphics.getWidth()*0.35f,Gdx.graphics.getHeight()*0.1f, Gdx.graphics.getWidth()*0.3f, Gdx.graphics.getHeight() * 0.05f);
    }

    public Button getBackButton(){
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

    //method that creates the input fields for room number and name and saves them in MyTextInputListener classes
    public void connectToGameRoom() {
        //new file which is going to be sent to the database
        multiPlayerData = new MultiPlayerData();

        nameListener = new NameInputListener();
        Gdx.input.getTextInput(nameListener, "SELECT YOUR NAME", "NAME", "WRITE NAME HERE");

        roomListener = new RoomInputListener();
        Gdx.input.getTextInput(roomListener,"SELECT ROOM NUMBER","ROOM NUMBER","WRITE ROOM NUMBER HERE");
    };

    //method used to create the room in the database
    public void connectToDatabase(){
        //checks whether the user has committed room number and name and if it is already saved to the database
        if (roomListener.room != null && nameListener.name != null && alreadyConnected == false){

            //save to the MultiPlayerData file that is sent to the database
            multiPlayerData.username = nameListener.name;
            roomNumber = roomListener.room;

            //send to database
            FallingAngel.getInstance().FBI.connectToRoom(roomListener.room, multiPlayerData);
            alreadyConnected = true;

            FallingAngel.getInstance().FBI.numberOfUsersInRoom();
        }
    }

    //method that starts a new game if both players are in the room
    public void checkIfReady(){
        //if there are two children in the same room
        if (multiPlayerData.getNumberOfUsersInRoom() == 2){
            this.gameActionsController = game.mc.gameActionsController;
            gameActionsController.setGameScreen(true);
        }
    }


}