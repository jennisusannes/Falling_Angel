package com.fallingangel.controller;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.fallingangel.controller.system.AngelSystem;
import com.fallingangel.controller.system.AnimationSystem;
import com.fallingangel.controller.system.BackgroundSystem;
import com.fallingangel.controller.system.BoundsSystem;
import com.fallingangel.controller.system.CoinSystem;
import com.fallingangel.controller.system.CollisionSystem;
import com.fallingangel.controller.system.MovementSystem;
import com.fallingangel.controller.system.ObstacleSystem;
import com.fallingangel.controller.system.PlaneSystem;
import com.fallingangel.controller.system.RenderingSystem;
import com.fallingangel.controller.system.StateSystem;
import com.fallingangel.game.FallingAngel;
import com.fallingangel.model.Asset;
import com.fallingangel.model.World;
import com.fallingangel.view.GameView;

public class GameActionsController extends ClickListener {

    /*TODO:
    setter hvilken character som er valgt i settings og setter den characteren i world
    denne her opprettes kun etter at spillet er trykket på play
    styrer om spillet er på pause eller i spill
    holde styr på valgt level
    håndterer skiftet til gameoverview når man dør

     */
    public FallingAngel game;
    public GameView gameView;

    private Sound clickSound;

    static final int GAME_READY = 0;
    static final int GAME_RUNNING = 1;
    static final int GAME_PAUSED = 2;
    static final int GAME_OVER = 3;

    private World world;
    private Vector3 touchPoint;
    private Rectangle pauseBounds;
    private Rectangle resumeBounds;
    private Rectangle quitBounds;

    private int lastScore = 0;
    private String scoreString;

    //ASHLEY
    public Engine engine;

    //Might need this for multiplayer
    private ImmutableArray angels;

    private int state;

    public GameActionsController() {
        this.game = FallingAngel.getInstance();
        clickSound = Asset.clickSound;
        // coinSound = Asset.coinSound;
        state = GAME_READY;

        this.touchPoint = new Vector3();
        //Initializes new world, engine, stage and settingsStage.
        //Uncertain whether we are going to use stage.
        this.engine = new Engine();
        this.world = new World(engine);

        //Gets all the entities for the angel and puts in an array.
        //Might use this later.
        this.angels = engine.getEntities();

        //Adds all the systems to the engine
        engine.addSystem(new AngelSystem(world));
        engine.addSystem(new ObstacleSystem());
        engine.addSystem(new PlaneSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new BackgroundSystem());
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new CollisionSystem(world));
        engine.addSystem(new RenderingSystem(game.batch));
        engine.addSystem(new StateSystem());
        engine.addSystem(new BoundsSystem());
        engine.addSystem(new CoinSystem());

        //Creates world
        world.create();
    }


    public void setGameScreen() {
        this.gameView = new GameView();
        game.setScreen(gameView);
    }

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
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) accelX = 10f;
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) accelX = -10f;
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

    public void switchState() {

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
    }

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

    //buildt-in method for pausing game.
    @Override
    public void pause() {
        if (state == GAME_RUNNING) {
            state = GAME_PAUSED;
            pauseSystem();
        }
    }

    public boolean handle(Event event) { //the Main controller listenens to the buttons on the different views and changes bewteen the different views
        if (event.getListenerActor().equals(gameView.getPauseButton())) {
            if (game.soundOn()){
                clickSound.play(0.2f);
            }
            else;

            game.setScreen(pauseView); //må lege et pauseView
            return true;
        }
        else {
            return false;
        }
}