package com.fallingangel.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.fallingangel.controller.MainController;
import com.fallingangel.game.FallingAngel;

public class GameOverView extends ScreenAdapter {

    private FallingAngel game;
    private Texture background;
    private Texture gameOverTexture;
    private Button gameOverButton;
    private MainController controller;
    private Stage stage;
    //private MenuView menuView;

    public GameOverView(){
        super();
        this.game = FallingAngel.getInstance();
        this.controller = game.mc;
        background = new Texture("backgrounds/winner_player1_background.PNG");
        gameOverTexture = new Texture("buttons/game-over.png");
        //controller = game.mc;
        //menuView = controller.getMenuView();
    }

    public void setGameOverButton() {
        controller = game.mc;
        this.gameOverButton = controller.getMenuView().makeButton(gameOverTexture,200,200,Gdx.graphics.getWidth()*0.4f, Gdx.graphics.getHeight() * 0.4f);
    }

    public Button getGameOverButton(){
        return gameOverButton;
    }

    /*
    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        //Button pButton = menuView.makeButton(gameOverButton,Gdx.graphics.getWidth()*0.45f,Gdx.graphics.getWidth()*0.4f);
        stage.addActor(gameOverButton);
    }

     */

    public void draw(){
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        setGameOverButton();
        stage.addActor(getGameOverButton());

        game.batch.begin(); // Draw elements to Sprite Batch
        game.batch.draw(background, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //Draws background photo
        game.batch.end();
        stage.draw();
    }

    public void update(float dt) {

    }


    public void render(float dt) {
        update(dt);
        draw();

    }

}