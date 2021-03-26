package com.fallingangel.view;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.fallingangel.controller.system.AnimationSystem;
import com.fallingangel.controller.system.BackgroundSystem;
import com.fallingangel.controller.system.BoundsSystem;
import com.fallingangel.controller.system.CollisionSystem;
import com.fallingangel.controller.system.MovementSystem;
import com.fallingangel.controller.system.PlaneSystem;
import com.fallingangel.controller.system.RenderingSystem;
import com.fallingangel.controller.system.StateSystem;
import com.fallingangel.game.FallingAngel;
import com.fallingangel.model.World;
import com.fallingangel.controller.system.AngelSystem;
import com.fallingangel.controller.system.ObstacleSystem;

public class GameView extends ScreenAdapter {

    //Tar i bruk assets for å hente bilder
    //Må implementere pauseknapp asset, player asset

    static final int GAME_READY = 0;
    static final int GAME_RUNNING = 1;
    static final int GAME_PAUSED = 2;
    static final int GAME_LEVEL_END = 3;
    static final int GAME_OVER = 4;


    private OrthographicCamera gameCam;
    private Viewport viewPort; //Viewport manages a Camera's viewportWidth and viewportHeight
    public FallingAngel game;
    private World world;
    private Vector3 touchPoint;


    private Stage stage;
    private Stage settingsStage;

    //ASHLEY
    Engine engine;
    //CollisionListener collisionListener;
    private AngelSystem angelSystem;
    private ImmutableArray angels;
    private Entity physicsEntity;

    private int state;


    public void GameView(FallingAngel game, Engine engine) {
        this.game  = game;
        this.engine = engine;

        state = GAME_READY;

        //Camera (and viewport of the screen)
        gameCam = new OrthographicCamera(320,480);
        viewPort = new StretchViewport(320, 480, gameCam);
        viewPort.apply();
        gameCam.position.set(320 / 2, 480 / 2, 0);
        gameCam.update();

        touchPoint = new Vector3();

        //kan bytte ut med det under, men må legge inn bredde og høyde i FallingAngel filen:

        /*
        gameCam = new OrthographicCamera(FallingAngel.V_WIDTH, FallingAngel.V_HEIGHT);
        viewPort = new StretchViewport(FallingAngel.V_WIDTH, FallingAngel.V_HEIGHT, gameCam);
        viewPort.apply();
        gameCam.position.set(viewPort.getWorldWidth() / 2, viewPort.getWorldHeight() / 2, 0);
        gameCam.update();
        */




        angelSystem = engine.getSystem(AngelSystem.class);
        angels = engine.getEntities();


        //Initializes new world
        engine = new Engine();
        world = new World(engine);
        stage = new Stage();
        settingsStage = new Stage();


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


        //tror denne tar inn kamera som følger bakgrunnen
        engine.getSystem(BackgroundSystem.class).setCamera(engine.getSystem(RenderingSystem.class).getCamera());

        world.create(); //TODO: Må lage metode i World.java

        //Tubbywars har maps, trenger vi det?


    }

    public void update(float dt) {
        if (dt > 0.1f) dt = 0.1f;

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

        private void updateReady () {
            if (Gdx.input.justTouched()) {
                state = GAME_RUNNING;
                resumeSystems();
            }
        }

        private void updateRunning (float dt) {

        }

        private void updatePaused() {

        }

        private void updateGameOver() {

        }







}