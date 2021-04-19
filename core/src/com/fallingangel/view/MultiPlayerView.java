package com.fallingangel.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.fallingangel.MyTextInputListener;
import com.fallingangel.MyTextInputListener2;
import com.fallingangel.backend.MultiPlayerData;
import com.fallingangel.controller.MainController;
import com.fallingangel.controller.system.MultiplayerSystem;
import com.fallingangel.game.FallingAngel;
import com.fallingangel.model.Asset;

public class MultiPlayerView extends ScreenAdapter {

    com.badlogic.gdx.scenes.scene2d.ui.TextField textField;
    Stage stage;
    MainController controller;

    Texture readyTexture;
    public Button readyButton;

    public String roomNumber;
    public MultiPlayerData multiPlayerData;

    MyTextInputListener roomListener;
    MyTextInputListener2 nameListener;
    boolean alreadyConnected = false;

    public MultiPlayerView() {
        this.controller = FallingAngel.getInstance().mc;
        stage = new Stage(new ScreenViewport());
        readyTexture = Asset.playTexture;
        readyButton = new Button(new TextureRegionDrawable(new TextureRegion(readyTexture)));
        readyButton.setPosition(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/5);
        readyButton.setSize(200,200);
        stage.addActor(readyButton);
        readyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float xpos, float ypos) {
                controller = FallingAngel.getInstance().mc;
                controller.handle(inputEvent);
            }
        });

    }

    public void draw(){
        GL20 gl = Gdx.gl;
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.input.setInputProcessor(stage);
        FallingAngel game = FallingAngel.getInstance();
        game.batch.begin();
        game.batch.draw(Asset.backgroundHeavenTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.draw(Asset.waitingRoomTexture, 0, Gdx.graphics.getHeight()*9/14, Gdx.graphics.getWidth(), Gdx.graphics.getWidth()/10 );
        game.batch.end();
        stage.draw();
    }


    @Override
    public void render(float dt) {
        //update(dt);
        draw();
        //stage.draw();
        stage.act(dt);
        connectToDatabase();
        //System.out.println("The room number is " + roomNumber + "and the username is " + multiPlayerData.username);
    }

    public void connectToGameRoom() {
        multiPlayerData = new MultiPlayerData();

        nameListener = new MyTextInputListener2();
        Gdx.input.getTextInput(nameListener, "Write in your name", "name", "name");

        roomListener = new MyTextInputListener();
        Gdx.input.getTextInput(roomListener,"Write in the room number","room number","write the number here");

        //FallingAngel.getInstance().FBI.connectToRoom(roomListener.room, multiPlayerData);
        //System.out.println("The room number is " + roomNumber + "and the username is " + multiPlayerData.username);

    };

    public void connectToDatabase(){
        if (roomListener.room != null && nameListener.name != null && alreadyConnected == false){
            multiPlayerData.username = nameListener.name;
            roomNumber = roomListener.room;
            //System.out.println("The room number is " + roomListener.room + " and the username is " + multiPlayerData.username);
            FallingAngel.getInstance().FBI.connectToRoom(roomListener.room, multiPlayerData);
            alreadyConnected = true;
        }
    }

    public void checkIfReady(){
        //if there are two children in the same room
        //controller = FallingAngel.getInstance().mc;
        //controller.beginMultiplayerGame();
    }


}