package com.fallingangel.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.fallingangel.game.FallingAngel;

public class GameOverView extends ScreenAdapter {

    private FallingAngel game;
    private Texture background;
    private Texture gameOverButton;
    private Stage stage;
    private MenuView menuView;

    public GameOverView(){
        this.game = FallingAngel.getInstance();
        background = new Texture("backgrounds/BackgroundSky.jpg");
        gameOverButton = new Texture("buttons/game-over.png");
        menuView = new MenuView();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Button pButton = menuView.makeButton(gameOverButton,Gdx.graphics.getWidth()*0.45f,Gdx.graphics.getWidth()*0.4f);
        stage.addActor(pButton);
    }


    public void render(float delta) {
        game.batch.begin(); // Draw elements to Sprite Batch
        game.batch.draw(background, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //Draws background photo
        game.batch.draw(gameOverButton, Gdx.graphics.getWidth()*0.45f, Gdx.graphics.getHeight()*0.4f, 300, 300);
        game.batch.end();

    }

}