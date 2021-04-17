package com.fallingangel.view;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.Input.Keys;

import com.fallingangel.controller.GameActionsController;
import com.fallingangel.controller.MainController;
import com.fallingangel.controller.system.AnimationSystem;
import com.fallingangel.controller.system.BackgroundSystem;
import com.fallingangel.controller.system.BoundsSystem;
import com.fallingangel.controller.system.CoinSystem;
import com.fallingangel.controller.system.CollisionSystem;
import com.fallingangel.controller.system.MovementSystem;
import com.fallingangel.controller.system.PlaneSystem;
import com.fallingangel.controller.system.RenderingSystem;
import com.fallingangel.controller.system.StateSystem;
import com.fallingangel.game.FallingAngel;
import com.fallingangel.model.Asset;
import com.fallingangel.model.World;
import com.fallingangel.controller.system.AngelSystem;
import com.fallingangel.controller.system.ObstacleSystem;
import com.fallingangel.model.component.AnimationComponent;
import com.fallingangel.model.component.BackgroundComponent;
import com.fallingangel.model.component.MovementComponent;
import com.fallingangel.model.component.StateComponent;
import com.fallingangel.model.component.TextureComponent;
import com.fallingangel.model.component.TransformComponent;

public class GameView extends ScreenAdapter {

    private FallingAngel game;
    private Texture background;
    private Texture pauseTexture;
    private Button pauseButton;
    private GameActionsController gameController;
    public Stage stage;
    //private int state;

    //static final int GAME_READY = 0;
    //static final int GAME_RUNNING = 1;
    //static final int GAME_PAUSED = 2;
    //static final int GAME_OVER = 3;
    //This view presents playing mode
    /*
    static final int GAME_READY = 0;
    static final int GAME_RUNNING = 1;
    static final int GAME_PAUSED = 2;
    static final int GAME_OVER = 3;
    */

    //public OrthographicCamera gameCam;
    //private Viewport viewPort;

    //Viewport manages a Camera's viewportWidth and viewportHeight, ensures that the game will fit to *all* devices


    //public FallingAngel game = FallingAngel.getInstance();
    /*private World world;
    private Vector3 touchPoint;
    private Rectangle pauseBounds;
    private Rectangle resumeBounds;
    private Rectangle quitBounds;
    */
    /*
    private int lastScore = 0;
    private String scoreString;
    */

    //Might be used for pause button or other buttons

    //ASHLEY
    //public Engine engine;

    //Might need this for multiplayer
    //private ImmutableArray angels;

    //TODO: This is going to be used if we implement sound-effects and music
    //CollisionListener collisionListener;
    //private int state;


    public GameView() {
        super();
        this.game = FallingAngel.getInstance();
        this.gameController = game.mc.gameActionsController;
        background = new Texture("backgrounds/level_hell_score_background.png");
        pauseTexture = new Texture("buttons/pause_button.PNG");

        gameController.setState(1);//state = GAME_RUNNING;

    }
    //setter and getter for the back button
    public void setPauseButton() {
        this.pauseButton = makeButton(pauseTexture,150,150, Gdx.graphics.getWidth()*0.82f, Gdx.graphics.getHeight() * 0.9f);
    }

    public Button getPauseButton(){
        return pauseButton;
    }

    //method for creating a button and adding the main controller as a listener
    public Button makeButton(Texture texture, float width, float height, float xPos, float yPos) {
        Button button = new Button(new TextureRegionDrawable(new TextureRegion(texture)));
        button.setSize(width, height);
        button.setPosition(xPos, yPos);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float xpos, float ypos) {
                gameController = game.mc.gameActionsController;
                gameController.handle(inputEvent);
            }
        });
        return button;
    }
    /*
    //Calls on different functions depending on which state the game is in
    public void update(float dt) {
        if (dt > 0.1f) dt = 0.1f;

        //delta-time is the time difference between the frames and controls the speed of changing frames. (60*dt = 60fps)
        //Updates all systems.
        gameController.engine.update(dt);

        switch (state) {
            case GAME_READY:
                gameController.updateReady();
                break;
            case GAME_RUNNING:
                gameController.updateRunning(dt);
                break;
            case GAME_PAUSED:
                gameController.updatePaused();
                break;
            case GAME_OVER:
                gameController.updateGameOver();
                break;
        }

    }

     */
    /*
    //Calls on different functions depending on which state the game is in
    public void update(float dt) {
        if (dt > 0.1f) dt = 0.1f;

        //delta-time is the time difference between the frames and controls the speed of changing frames. (60*dt = 60fps)
        //Updates all systems.
        engine.update(dt);


        switch (state) {
            case GAME_READY:
                updateReady();
                break;
            case GAME_RUNNING:
                updateRunning(dt);
                break;
            case GAME_PAUSED:
                updatePaused();
                break;
            case GAME_OVER:
                updateGameOver();
                break;
            }

        }

    //Ready to start a new game
    private void updateReady() {
        if (Gdx.input.justTouched()) {
            state = GAME_RUNNING;
            resumeSystem();
        }
    }



    //Updates on what state the game is in
    //TODO: må legge inn metoder for hva som skjer mens spillet kjører
    //Koble denne opp mot PlayerActionsController, angelSystem og renderingSystem på et vis :))
    private void updateRunning (float dt) {
        //Handle input. AccelX is changed here and being set in AngelSystem

        float accelX = 0.0f;
        if (Gdx.input.isKeyPressed(Keys.DPAD_LEFT)) accelX = 10f;
        if (Gdx.input.isKeyPressed(Keys.DPAD_RIGHT)) accelX = -10f;
        engine.getSystem(AngelSystem.class).setAccelX(accelX);





        //Updates the players score and presents as a string
        if(world.score != lastScore) {
            lastScore = world.score;
            //This will be shown on the screen at all times. Should be used at drawUI()
            scoreString = "SCORE: " + lastScore;
        }

        //If player dies then :
        if (world.state == World.WORLD_STATE_GAME_OVER) {
            state = GAME_OVER;
            //legg til en if-setning om denne scoren er høyere enn highscore -> si ny highscore, ellers bare vise scoren
            //oppdatere ny highscore i highscorelist
            pauseSystem();

        }
    }

    private void updatePaused() {
        //These are "buttons" for pause-menu. Can either resume or quit
        if (Gdx.input.justTouched()) {
            gameCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            //If resume then resume
            if (resumeBounds.contains(touchPoint.x, touchPoint.y)) {
                state = GAME_RUNNING;
                resumeSystem();
                return;
            }
            //if quit then send to menu-view
            if (quitBounds.contains(touchPoint.x,touchPoint.y)) {
                //TODO: implement a method so player is sent to menu
                return;
            }
        }
    }

    //When the player dies and the game is over, the player is sent to GameOverView
    private void updateGameOver() {
        //TODO: må sende spilleren til gameover-view
    }
    */
    public void draw() {
        //Uncertain if we'll use cam.
        /*
        gameCam.update();
        game.batch.setProjectionMatrix(gameCam.combined); //setProjectMatrix should be called every time the camera is moved or the screen is resized
        */

        game.batch.begin();
        //game.batch.draw(background, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //draws the sprite batch
        //TODO: hent ut riktig state fra gameActionsController
        /*
        switch (state) {
            case GAME_READY:
                presentReady();
                break;
            case GAME_RUNNING:
                presentRunning();
                break;
            case GAME_PAUSED:
                presentPaused();
                break;
            case GAME_OVER:
                presentGameOver();
                break;
        }

         */
        game.batch.end();
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        setPauseButton();
        stage.addActor(getPauseButton());
        stage.draw();

    }
    /*
    //In the present-methods, all the GUI will be drawn/ shown for the game-view

    public void presentReady() {
        //TODO: create buttons and connect to bounds, and add text for "Ready". See superjumper for inspo .
        //TODO: if we have enough time: countdown from 3 before start
    }


    public void presentRunning() {
       //TODO: implement / draw pause button and connect to bounds
    }


    public void presentPaused() {
        //TODO: draw buttons for resume and quit and connect to pause
    }

    public void presentGameOver() {
        //We might not need this, because of the gameOverView
    }

    //This method sets all systems updating on pause.
    private void pauseSystem() {
        engine.getSystem(AngelSystem.class).setProcessing(false);
        engine.getSystem(ObstacleSystem.class).setProcessing(false);
        engine.getSystem(PlaneSystem.class).setProcessing(false);
        engine.getSystem(MovementSystem.class).setProcessing(false);
        engine.getSystem(BoundsSystem.class).setProcessing(false);
        engine.getSystem(StateSystem.class).setProcessing(false);
        engine.getSystem(AnimationSystem.class).setProcessing(false);
        engine.getSystem(CollisionSystem.class).setProcessing(false);

    }

    //Sets all systems back to working / updating
    private void resumeSystem() {
        engine.getSystem(AngelSystem.class).setProcessing(true);
        engine.getSystem(ObstacleSystem.class).setProcessing(true);
        engine.getSystem(PlaneSystem.class).setProcessing(true);
        engine.getSystem(MovementSystem.class).setProcessing(true);
        engine.getSystem(BoundsSystem.class).setProcessing(true);
        engine.getSystem(StateSystem.class).setProcessing(true);
        engine.getSystem(AnimationSystem.class).setProcessing(true);
        engine.getSystem(CollisionSystem.class).setProcessing(true);
    }



     */


    @Override
    public void render(float delta) {
        gameController.update(delta);
        gameController.updateRunning(delta);
        draw();
        //stage.act();
    }
    /*
    //buildt-in method for pausing game.
    @Override
    public void pause() {
        if (state == GAME_RUNNING) {
            state = GAME_PAUSED;
            pauseSystem();
        }
    }
     */
}