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
import com.fallingangel.model.MultiPlayerData;
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
import com.fallingangel.model.SettingsModel;
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
    public FallingAngel game;
    public GameView gameView;
    public PauseView pauseView = new PauseView();
    public GameOverView gameOverView = new GameOverView();
    public GameOverMultiPlayerView gameOverMultiPlayerView;
    protected MainController mainController;
    public MultiplayerController multiplayerController;
    public SettingsModel settingsModel;

    private Sound clickSound;

    // The three states of the game are represented with an integer
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

    // Constructs the GameActionsController depending on whether the game is single player or multi player
    public GameActionsController(boolean isMultiplayer) {
        // Sets the game as the Singleton object Falling Angel
        this.game = FallingAngel.getInstance();

        // Sets the controller as the mainController
        this.mainController = game.mc;

        // Sets the game as either multi player or single player
        this.isMultiplayer = isMultiplayer;

        clickSound = Assets.clickSound;

        // Sets the engine and world
        this.engine = mainController.engine;
        this.world = mainController.world;

        this.settingsModel = mainController.settingsModel;

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
            this.multiplayerController = new MultiplayerController();
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
        // Returns an empty TransformComponent is no angel exists
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
                gameOver();
                break;
        }
    }

    // Updates on what state the game is in
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
        }
        engine.getSystem(AngelSystem.class).setAccelX(accelX);

        // Player dies
        if (world.state == World.WORLD_STATE_GAME_OVER) {
            state = GAME_OVER; // Sets state to GAME OVER
            pauseSystem();
        }

        // A player dies in multi player
        if (isMultiplayer && state == GAME_OVER){
            gameOverMultiPlayerView = new GameOverMultiPlayerView();
            multiplayerController.multiPlayerData.setGameOver(true);
        }

        // Sets the state to GAME OVER after a multi player game
        if(isMultiplayer && game.FBI.gameOver()){
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
        if (state == GAME_RUNNING) { // Checks if the state is running
            state = GAME_PAUSED; // Sets the state to paused
            pauseSystem();
        }
    }

    // Method for resuming game
    public void resume() {
        if (state == GAME_PAUSED) { // Checks if game is paused
            state = GAME_RUNNING; // Sets the state to running
            resumeSystem();
        }
    }

    // Method for exiting game
    public void exit() {
        if (state == GAME_PAUSED) { // Checks if game is paused
            state = GAME_OVER; // Sets the state to game over
            pauseSystem();
            removeSystem();
        }
    }

    // When the player dies and the game is over, the player is sent to GameOverView
    public void gameOver() {
        if (isMultiplayer){
            MultiPlayerData player1 = multiplayerController.multiPlayerData;
            removeSystem();
            game.setScreen(gameOverMultiPlayerView);
            multiplayerController.multiPlayerData.setGameOver(true);
            game.FBI.update(player1);
            isMultiplayer = false;
        }
        else {
            removeSystem();
            game.setScreen(gameOverView);
        }
    }


    // Game controller listens to buttons in the different views and changes between views
    // Checks if sound is on and then plays click sound when button is clicked
    public boolean handle(Event event) {

        // Pauses the game if the pause button is pressed
        if (event.getListenerActor().equals(gameView.getPauseButton())) {
            if (settingsModel.soundOn()) {
                clickSound.play(0.2f);
            }
            pause();
            game.setScreen(pauseView);
            return true;
        }

        // Resumes the game if the resume button is pressed
        else if (event.getListenerActor().equals(pauseView.getResumeButton())) {
            if (settingsModel.soundOn()) {
                clickSound.play(0.2f);
            }
            resume();
            game.setScreen(gameView);
            return true;
        }
        // Exits the game if the exit button is pressed
        else if (event.getListenerActor().equals(pauseView.getExitButton())) {
            if (settingsModel.soundOn()) {
                clickSound.play(0.2f);
            }
            exit();
            game.mc.setStartScreen();
            return true;
        }
        // Exits the gameOverView and sets the menuView
        else if (event.getListenerActor().equals(gameOverView.getExitButton())) {
            if (settingsModel.soundOn()) {
                clickSound.play(0.2f);
            }
            game.mc.setStartScreen();
            return true;
        }
        // Starts a new game on the same level
        else if (event.getListenerActor().equals(gameOverView.getPlayAgainButton())) {
            if (settingsModel.soundOn()) {
                clickSound.play(0.2f);
            }
            game.mc.gameActionsController = new GameActionsController(false);
            game.mc.gameActionsController.setGameScreen(false);
            return true;
        }
        // Exits the gameOverMultiPlayerView and sets the menuView
        else if (event.getListenerActor().equals(gameOverMultiPlayerView.getExitButton())){
            if (settingsModel.soundOn()){
                clickSound.play(0.2f);
            }
            game.FBI.leaveRoom();
            game.mc.setStartScreen();
            return true;
        }
        else {
            return false;
        }
    }
}