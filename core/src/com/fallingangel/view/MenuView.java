package com.fallingangel.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.fallingangel.controller.MainController;
import com.fallingangel.game.FallingAngel;
import java.awt.event.ActionEvent;


import javax.swing.AbstractButton;

public class MenuView extends AbstractView implements Screen {


    private Texture playTexture;
    private Texture background;
    private FallingAngel game;
    private Stage stage;
    private MainController controller;
    private Button playButton;
    //private Actor actor;

    public MenuView(){
        this.game = FallingAngel.getInstance();
        controller = new MainController();
        background = new Texture("backgrounds/BackgroundSky.jpg");
        playTexture = new Texture("buttons/play.png");
    }


    @Override
    protected void handleInput() {
    }

    public void setPlayButton() {
        this.playButton = makeButton(playTexture,Gdx.graphics.getWidth()*0.45f, Gdx.graphics.getHeight() * 0.4f);
    }

    public Button getPlayButtonActor(){
        return playButton;
    }

    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        //Button pButton = makeButton(playTexture,Gdx.graphics.getWidth()*0.45f);
        setPlayButton();
        stage.addActor(getPlayButtonActor());



        /*Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int x, int y, int pointer, int button) {
                int renderY = Gdx.graphics.getHeight() - y;
                if (Vector2.dst((float) (Gdx.graphics.getWidth()*0.45), (float)(Gdx.graphics.getHeight()*0.4), x, renderY) < 200) {
                    game.setScreen(new GameView(game));
                }
                return true;
            }
        }); */
    }


    public Button makeButton(Texture texture, float xPos, float yPos) { //
        Button b = new Button(new TextureRegionDrawable(new TextureRegion(texture)));
        b.setSize(200, 200);
        b.setPosition(xPos, yPos);
      // Array<Actor> buttons = stage.getActors();
      // for (Actor actor : buttons) {
      //     if (actor instanceof Button) {
      //         actor.addListener(controller);
      //     }
      //  }

        b.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float xpos, float ypos) {
                //game.setScreen(nextScreen);
                controller.handle(inputEvent);
            }
        });
        return b;
    }



    public void update(float dt) {

    }

    @Override
    public void render() {

    }


    //@Override
    public void dispose() {
    }


    public void render(float delta) {
            game.batch.begin(); // Draw elements to Sprite Batch
            game.batch.draw(background, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //Draws background photo
            game.batch.end();
            stage.draw();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }


    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}