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
import com.fallingangel.controller.MainController;
import com.fallingangel.game.FallingAngel;

public class HighScoreListView extends ScreenAdapter {

    private FallingAngel game;
    private Texture background;
    private Texture backTexture;
    private Button backButton;
    private MainController controller;
    private Stage stage;

    public HighScoreListView(){
        super();
        this.game = FallingAngel.getInstance();
        this.controller = game.mc;
        background = new Texture("backgrounds/highscorelist_blurred_background.PNG");
        backTexture = new Texture("buttons/back_button.PNG");
    }

    public void setBackButton() {
        this.backButton = makeButton(backTexture,600,400, Gdx.graphics.getWidth()*0.3f, Gdx.graphics.getHeight() * 0.2f);
    }

    public Button getBackButton(){
        return backButton;
    }

    public Button makeButton(Texture texture, float width, float height, float xPos, float yPos) {
        Button button = new Button(new TextureRegionDrawable(new TextureRegion(texture)));
        button.setSize(width, height);
        button.setPosition(xPos, yPos);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float xpos, float ypos) {
                controller = game.mc;
                controller.handle(inputEvent);
            }
        });
        return button;
    }

    public void draw(){
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        setBackButton();
        stage.addActor(getBackButton());

        game.batch.begin();
        game.batch.draw(background, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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