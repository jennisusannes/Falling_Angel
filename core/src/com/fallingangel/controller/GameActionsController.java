package com.fallingangel.controller;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.fallingangel.controller.system.AngelSystem;
import com.fallingangel.controller.system.AnimationSystem;
import com.fallingangel.controller.system.BoundsSystem;
import com.fallingangel.controller.system.CoinSystem;
import com.fallingangel.controller.system.CollisionSystem;
import com.fallingangel.controller.system.DevilSystem;
import com.fallingangel.controller.system.MovementSystem;
import com.fallingangel.controller.system.MultiplayerSystem;
import com.fallingangel.controller.system.ObstacleSystem;
import com.fallingangel.controller.system.DroneSystem;
import com.fallingangel.controller.system.RenderingSystem;
import com.fallingangel.controller.system.StateSystem;
import com.fallingangel.game.FallingAngel;
import com.fallingangel.model.Assets;
import com.fallingangel.model.World;
import com.fallingangel.model.component.AngelComponent;
import com.fallingangel.model.component.BoundsComponent;
import com.fallingangel.model.component.StateComponent;
import com.fallingangel.model.component.TransformComponent;
import com.fallingangel.view.GameOverMultiPlayerView;
import com.fallingangel.view.GameOverView;
import com.fallingangel.view.GameView;
import com.fallingangel.view.PauseView;

public class GameActionsController implements EventListener {
    /*TODO:
    setter hvilken character som er valgt i settings og setter den characteren i world
    denne her opprettes kun etter at spillet er trykket på play
    styrer om spillet er på pause eller i spill
    holde styr på valgt level
    håndterer skiftet til gameoverview når man dør
     */
    public FallingAngel game;
    public GameView gameView;
    public PauseView pauseView = new PauseView();
    public GameOverView gameOverView = new GameOverView();
    public GameOverMultiPlayerView gameOverMultiPlayerView = new GameOverMultiPlayerView();
    public MainController mainController;

    private Sound clickSound;

    // THe three states of the game are represented with an integer
    static final int GAME_RUNNING = 1;
    static final int GAME_PAUSED = 2;
    static final int GAME_OVER = 3;

    private int state;

    protected World world;
    private ComponentMapper<TransformComponent> transformMapper;
    private ImmutableArray<Entity> angels;

    private float angelPosX;
    public boolean isMultiplayer;

    //ASHLEY
    public Engine engine;

    // Costructs the GameActionsController depending on whether the game is single player or multi player
    public GameActionsController(boolean isMultiplayer) {
        //Setts the game as the Singleton object Falling Angel
        this.game = FallingAngel.getInstance();

        //Setts the controller as the mainController
        this.mainController = game.mc;

        //Setts the game as either multi player or single player
        this.isMultiplayer = isMultiplayer;

        clickSound = Assets.clickSound;

        // Sets the engine and world
        this.engine = mainController.engine;
        this.world = mainController.world;

        // Adds all the systems to the engine
        engine.addSystem(new AngelSystem(world));
        engine.addSystem(new ObstacleSystem());
        engine.addSystem(new DroneSystem());
        engine.addSystem(new DevilSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new CollisionSystem(world));
        engine.addSystem(new RenderingSystem(game.batch,isMultiplayer));
        engine.addSystem(new StateSystem());
        engine.addSystem(new BoundsSystem());
        engine.addSystem(new CoinSystem());

        // Adds the RenderingSystem based on whether the gameActionsController is single player or multi player
        if (isMultiplayer){
            engine.addSystem(new MultiplayerSystem(1));
            engine.addSystem(new RenderingSystem(game.batch, true));
        }
        else{
            engine.addSystem(new RenderingSystem(game.batch, false));
        }

        // Creates world
        world.create();
    }

    // Method for creating a new game, either singleplayer or multiplayer
    public void setGameScreen(Boolean multi) {
        this.gameView = new GameView(multi);
        game.setScreen(gameView);
    }

    // Method for setting the game state
    public void setState(int state) {
        this.state = state;
    }

    // Method getting the angel's position
    public TransformComponent getAngelPos(){
        transformMapper = ComponentMapper.getFor(TransformComponent.class);
        angels = engine.getEntitiesFor(Family.all(AngelComponent.class,TransformComponent.class, BoundsComponent.class, StateComponent.class).get());
        if (!(angels.size() == 0)){
            Entity angel = angels.get(0);
            TransformComponent angelPosVector = transformMapper.get(angel);
            return angelPosVector;
        }
        // returns an empty TransformComponent is no angel exists
        else{
            TransformComponent emptyTransformComponent = new TransformComponent();
            return emptyTransformComponent;
        }
    }

    // Calls on different functions depending on which state the game is in
    public void update(float dt) {
        if (dt > 0.1f) dt = 0.1f;

        // Delta-time is the time difference between the frames and controls the speed of changing frames. (60*dt = 60fps)
        // Updates all systems
        engine.update(dt);

        switch (state) {
            case GAME_RUNNING:
                updateRunning(dt);
                break;
            case GAME_PAUSED:
                pause();
                break;
            case GAME_OVER:
                // winner = 0 -> single player, winner = 1 / 2 -> multiplayer
                gameOver();
                break;
        }
    }


    //Updates on what state the game is in
    public void updateRunning(float dt) {

        // Handle input, accelX is changed here and being set in AngelSystem
        float accelX = 0.0f;
        this.angelPosX = getAngelPos().pos.x;

        //Updates the angel's position based on touch input
        if (Gdx.input.isTouched()) {
            if (Gdx.input.getY() < Gdx.graphics.getHeight()*0.9f) {
                if (Gdx.input.getX() > angelPosX) {
                    accelX = -10f;
                }
                if (Gdx.input.getX() < angelPosX) {
                    accelX = 10f;
                }
            }
            //spritePosition.set(Gdx.input.getX(), 0, 0));
        }
        engine.getSystem(AngelSystem.class).setAccelX(accelX);


        // Player dies
        if (world.state == World.WORLD_STATE_GAME_OVER) {
            state = GAME_OVER; //setter state som GAME OVER
            //TODO: gameOver()
            pauseSystem();
        }

        // A player dies in multi player
        if (isMultiplayer && state == GAME_OVER){
            game.mc.waitingRoomView.multiPlayerData.setGameOver(true);
        }

        // Sets the state to GAME OVER after a multi player game
        if(isMultiplayer && game.FBI.gameIsOver()){
            state = GAME_OVER;
        }

    }

    // This method sets all systems updating on pause
    public void pauseSystem() {
        engine.getSystem(AngelSystem.class).setProcessing(false);
        engine.getSystem(ObstacleSystem.class).setProcessing(false);
        engine.getSystem(DroneSystem.class).setProcessing(false);
        engine.getSystem(DevilSystem.class).setProcessing(false);
        engine.getSystem(MovementSystem.class).setProcessing(false);
        engine.getSystem(BoundsSystem.class).setProcessing(false);
        engine.getSystem(StateSystem.class).setProcessing(false);
        engine.getSystem(AnimationSystem.class).setProcessing(false);
        engine.getSystem(CollisionSystem.class).setProcessing(false);

    }
    // This method resumes all systems updating
    public void resumeSystem() {
        engine.getSystem(AngelSystem.class).setProcessing(true);
        engine.getSystem(ObstacleSystem.class).setProcessing(true);
        engine.getSystem(DroneSystem.class).setProcessing(true);
        engine.getSystem(DevilSystem.class).setProcessing(true);
        engine.getSystem(MovementSystem.class).setProcessing(true);
        engine.getSystem(BoundsSystem.class).setProcessing(true);
        engine.getSystem(StateSystem.class).setProcessing(true);
        engine.getSystem(AnimationSystem.class).setProcessing(true);
        engine.getSystem(CollisionSystem.class).setProcessing(true);
    }
    // This method removes all systems
    public void removeSystem() {
       engine.removeAllEntities();
    }

    // Method for pausing game
    public void pause() {
        if (state == GAME_RUNNING) { //checks if the state is running
            state = GAME_PAUSED; //sets the state to paused
            pauseSystem();
        }
    }

    // Method for resuming game
    public void resume() {
        if (state == GAME_PAUSED) { //checks if game is paused
            state = GAME_RUNNING; //sets the state to running
            resumeSystem();
        }
    }

    // Method for exiting game
    public void exit() {
        if (state == GAME_PAUSED) { //checks if game is paused
            state = GAME_OVER; //sets the state to game over
            pauseSystem();
            removeSystem();
        }
    }

    // When the player dies and the game is over, the player is sent to GameOverView
    public void gameOver() {
        // winner = 0 -> single player, winner = 1 / 2 -> multiplayer
        if (isMultiplayer){
            removeSystem();
            game.setScreen(gameOverMultiPlayerView);
        }
        else {
            removeSystem();
            game.setScreen(gameOverView);
        }
    }


    // Game controller listens to buttons in the different views and changes between views
    // Checks if sound is on and then plays click sound when button is clicked
    public boolean handle(Event event) {
        if (event.getListenerActor().equals(gameView.getPauseButton())) {
            if (game.soundOn()) {
                clickSound.play(0.2f);
            }
            pause();
            game.setScreen(pauseView);
            return true;
        }
        else if (event.getListenerActor().equals(pauseView.getResumeButton())) {
            if (game.soundOn()) {
                clickSound.play(0.2f);
            }
            resume();
            game.setScreen(gameView);
            return true;
        }
        else if (event.getListenerActor().equals(pauseView.getExitButton())) {
            if (game.soundOn()) {
                clickSound.play(0.2f);
            }
            exit();
            game.mc.setStartScreen();
            return true;
        }
        else if (event.getListenerActor().equals(gameOverView.getExitButton())) {
            if (game.soundOn()) {
                clickSound.play(0.2f);
            }
            game.mc.setStartScreen();
            return true;
        }
        else if (event.getListenerActor().equals(gameOverView.getPlayAgainButton())) {
            if (game.soundOn()) {
                clickSound.play(0.2f);
            }
            game.mc.gameActionsController = new GameActionsController(false);
            game.mc.gameActionsController.setGameScreen(false);
            return true;
        }
        else if (event.getListenerActor().equals(gameOverMultiPlayerView.getExitButton())){
            if (game.soundOn()){
                clickSound.play(0.2f);
            }
            game.mc.setStartScreen();
            return true;
        }
        else {
            return false;
        }
    }
}