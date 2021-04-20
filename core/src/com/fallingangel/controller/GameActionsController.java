package com.fallingangel.controller;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.fallingangel.controller.system.AngelSystem;
import com.fallingangel.controller.system.AnimationSystem;
import com.fallingangel.controller.system.BackgroundSystem;
import com.fallingangel.controller.system.BoundsSystem;
import com.fallingangel.controller.system.CoinSystem;
import com.fallingangel.controller.system.CollisionSystem;
import com.fallingangel.controller.system.MovementSystem;
import com.fallingangel.controller.system.MultiplayerSystem;
import com.fallingangel.controller.system.ObstacleSystem;
import com.fallingangel.controller.system.DroneSystem;
import com.fallingangel.controller.system.RenderingSystem;
import com.fallingangel.controller.system.StateSystem;
import com.fallingangel.game.FallingAngel;
import com.fallingangel.model.Asset;
import com.fallingangel.model.World;
import com.fallingangel.model.component.AngelComponent;
import com.fallingangel.model.component.BoundsComponent;
import com.fallingangel.model.component.DroneComponent;
import com.fallingangel.model.component.StateComponent;
import com.fallingangel.model.component.TransformComponent;
import com.fallingangel.view.GameOverMultiPlayerView;
import com.fallingangel.view.GameOverView;
import com.fallingangel.view.GameView;
import com.fallingangel.view.MenuView;
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
    private AngelSystem angelSystem;
    private Entity entity;
    public GameView gameView;
    public PauseView pauseView = new PauseView();
    public GameOverView gameOverView = new GameOverView();
    public GameOverMultiPlayerView gameOverMultiPlayerView = new GameOverMultiPlayerView();
    public MainController mainController;
    public PlayerActionsController playerActionsController= new PlayerActionsController();

    private Sound clickSound;

    static final int GAME_READY = 0;
    static final int GAME_RUNNING = 1;
    static final int GAME_PAUSED = 2;
    static final int GAME_OVER = 3;

    private int state;

    protected World world;
    private Rectangle pauseBounds;
    private Rectangle resumeBounds;
    private Rectangle quitBounds;
    private ComponentMapper<TransformComponent> transform_mapper;
    private ComponentMapper<TransformComponent> transformMapper;
    private TransformComponent transformComponent;
    private ImmutableArray<Entity> angels;
    private TransformComponent angelPos;
    private TextureRegion gamebackground;

    private int lastScore = 0;
    private String scoreString;

    private float angelPosX;
    private float getAngelPosY; //trengs kanskje ikke
    public boolean isMultiplayer;

    //ASHLEY
    public Engine engine;

    public GameActionsController(boolean isMultiplayer) {
        this.game = FallingAngel.getInstance();
        this.mainController = game.mc;
        this.isMultiplayer = isMultiplayer;

        clickSound = Asset.clickSound;
        //coinSound = Asset.coinSound;

        // Initializes new engine and world
        //this.engine = new Engine();
        //this.world = new World(engine);
        //transform_mapper = ComponentMapper.getFor(TransformComponent.class);
        //this.transformComponent = transform_mapper.get(entity);
        this.engine = mainController.engine;
        this.world = mainController.world;
        //transform_mapper = ComponentMapper.getFor(TransformComponent.class);
        //TransformComponent transformComponent = transform_mapper.get(entity);

        //this.getAngelPosY = transformComponent.pos.y;

        // Adds all the systems to the engine
        engine.addSystem(new AngelSystem(world));
        engine.addSystem(new ObstacleSystem());
        engine.addSystem(new DroneSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new BackgroundSystem());
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new CollisionSystem(world));
        engine.addSystem(new RenderingSystem(game.batch,isMultiplayer));
        engine.addSystem(new StateSystem());
        engine.addSystem(new BoundsSystem());
        engine.addSystem(new CoinSystem());

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

    public void setGameScreen(Boolean multi) {
        this.gameView = new GameView(multi);
        game.setScreen(gameView);
    }
    /*
    public void beginMultiplayerGame(){
        game.setScreen(new GameView(true));
    }

     */

    public void setState(int state) {
        this.state = state;
    }

    public int getState(){
        return state;
    }

    public TransformComponent getAngelPos(){
        transformMapper = ComponentMapper.getFor(TransformComponent.class);
        angels = engine.getEntitiesFor(Family.all(AngelComponent.class,TransformComponent.class, BoundsComponent.class, StateComponent.class).get());
        if (!(angels.size() == 0)){
            Entity angel = angels.get(0);
            TransformComponent angelPosVector = transformMapper.get(angel);
            return angelPosVector;
        }
        else{
            TransformComponent emptyTransformcomponent = new TransformComponent();
            return emptyTransformcomponent;
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
    //TODO: må legge inn metoder for hva som skjer mens spillet kjører
    //Koble denne opp mot PlayerActionsController, angelSystem og renderingSystem på et vis :))
    public void updateRunning(float dt) {
        // Handle input, accelX is changed here and being set in AngelSystem
        //TransformComponent transformComponent = transform_mapper.get(entity);

        //transformMapper = ComponentMapper.getFor(TransformComponent.class);

        float accelX = 0.0f;
        //angels = engine.getEntitiesFor(Family.all(AngelComponent.class,TransformComponent.class, BoundsComponent.class, StateComponent.class).get());
        //Entity angel = angels.get(0);
        //TransformComponent angelPosVector = transformMapper.get(angel);
        this.angelPosX = getAngelPos().pos.x;
                //angelSystem.xPos;
        //if (playerActionsController.touchDragged(angelEntity.ge,))

        /*
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) accelX = 10f;
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) accelX = -10f;

         */

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

        // Updates the players score
        /*if (world.score != lastScore) {
            lastScore = world.score;
            // Present the score
            scoreString = "SCORE: " + lastScore;
        }

         */

        // Player dies
        if (world.state == World.WORLD_STATE_GAME_OVER) {
            state = GAME_OVER; //setter state som GAME OVER
            //TODO: gameOver()
            //TODO: oppdatere ny highscore i highscorelist
            pauseSystem();
        }

        if (isMultiplayer && state == GAME_OVER){
            game.mc.multiPlayerView.multiPlayerData.setGameOver(true);
        }

        if(isMultiplayer && game.FBI.gameIsOver()){
            state = GAME_OVER;
        }

    }

    // This method sets all systems updating on pause
    public void pauseSystem() {
        engine.getSystem(AngelSystem.class).setProcessing(false);
        engine.getSystem(ObstacleSystem.class).setProcessing(false);
        engine.getSystem(DroneSystem.class).setProcessing(false);
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



    public void setGameOverScreen() {
        game.setScreen(gameOverView);
    }

    // Game controller listens to buttons in the different views and changes between views
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