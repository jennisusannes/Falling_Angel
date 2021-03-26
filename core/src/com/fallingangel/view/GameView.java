package com.fallingangel.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.fallingangel.controller.MainController;
import com.fallingangel.game.FallingAngel;

public class GameView extends AbstractView implements Screen { //AbstractView

    private FallingAngel game;
    private Texture background;

    public GameView(){
        this.game = FallingAngel.getInstance();
        background = new Texture("backgrounds/BackgroundSky.jpg");
    }


    protected void handleInput() {

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.SPACE) {
                    game.setScreen(new GameOverView());
                    //game.setScreen(new GameOverView(game));
                }
                return true;
            }
        });
    }


    public void update(float dt) {

    }

    @Override
    public void render() {

    }


    public void render(float delta) {
        game.batch.begin(); // Draw elements to Sprite Batch
        game.batch.draw(background, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //Draws background photo
        game.font.draw(game.batch, "This is the game ", Gdx.graphics.getWidth() * .5f, Gdx.graphics.getHeight() * .75f);
        game.batch.end();

    }

    @Override
    public void dispose() {

    }


    public void hide(){
        Gdx.input.setInputProcessor(null);
    }

    public void create () {

    }


    public void resize(int width, int height) {

    }


    public void pause() {

    }


    public void resume() {

    }



}
