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
import com.fallingangel.controller.MainController;
import com.fallingangel.controller.system.MultiplayerSystem;
import com.fallingangel.game.FallingAngel;
import com.fallingangel.model.Asset;

public class MultiPlayerView extends ScreenAdapter {

    com.badlogic.gdx.scenes.scene2d.ui.TextField textField;
    Stage stage;
    SpriteBatch sb = new SpriteBatch();
    MainController controller;

    Texture readyTexture;
    public Button readyButton;

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
        game.batch.end();
        stage.draw();
    }


    @Override
    public void render(float dt) {
        //update(dt);
        draw();
        //stage.draw();
        stage.act(dt);
    }

    public void connectToGameRoom() {
        MyTextInputListener roomListener = new MyTextInputListener();
        Gdx.input.getTextInput(roomListener,"Write in the room number","room number","write the number here");
        /*MyTextInputListener nameListener = new MyTextInputListener();
        Gdx.input.getTextInput(nameListener,"Write in the name","name","write the name here");*/



    };


}