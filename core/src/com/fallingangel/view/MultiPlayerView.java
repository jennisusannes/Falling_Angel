package com.fallingangel.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.fallingangel.MyTextInputListener;
import com.fallingangel.controller.system.MultiplayerSystem;
import com.fallingangel.model.Asset;

public class MultiPlayerView extends ScreenAdapter {

    com.badlogic.gdx.scenes.scene2d.ui.TextField textField;
    Stage stage;
    String room;
    String name;
    Skin skin;
    SpriteBatch sb = new SpriteBatch();

    public MultiPlayerView() {
        stage = new Stage();
        /*skin = new Skin();
        textField = new TextField("hei", Asset.blueBalloon.);
        textField.setPosition(0,0);
        textField.setSize(88, 14);

        //stage.addActor(textField); */           // <-- Actor now on stage
        Gdx.input.setInputProcessor(stage);
        //connectToRoom();
    }

    public void draw(){
        GL20 gl = Gdx.gl;
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        /*sb.begin();
        BitmapFont bitmapFont = new BitmapFont();
        bitmapFont.getData().setScale(5, 5);
        bitmapFont.draw(sb, MultiplayerSystem.roomNumber, 0, 0);
        sb.end();*/
    }


    @Override
    public void render(float dt) {
        //update(dt);
        draw();
        stage.draw();
        stage.act(dt);
    }

    public void connectToGameRoom() {
        MyTextInputListener roomListener = new MyTextInputListener();
        Gdx.input.getTextInput(roomListener,"Write in the room number","room number","write the number here");
        /*MyTextInputListener nameListener = new MyTextInputListener();
        Gdx.input.getTextInput(nameListener,"Write in the name","name","write the name here");*/



        };

}