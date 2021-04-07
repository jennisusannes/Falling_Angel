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
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.Input.Keys;

import com.fallingangel.controller.system.AnimationSystem;
import com.fallingangel.controller.system.BackgroundSystem;
import com.fallingangel.controller.system.BoundsSystem;
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
import com.fallingangel.model.component.BackgroundComponent;
import com.fallingangel.model.component.TextureComponent;

public class GameView extends ScreenAdapter {

    //Tar i bruk assets for å hente bilder

    static final int GAME_READY = 0;
    static final int GAME_RUNNING = 1;
    static final int GAME_PAUSED = 2;
    static final int GAME_OVER = 3;


    public OrthographicCamera gameCam;
    private Viewport viewPort; //Viewport manages a Camera's viewportWidth and viewportHeight
    public FallingAngel game = FallingAngel.getInstance();
    private World world;
    private Vector3 touchPoint;
    private Rectangle pauseBounds;
    private Rectangle resumeBounds;
    private Rectangle quitBounds;


    private int lastScore = 0;
    private String scoreString;


    private Stage stage;
    private Stage settingsStage;

    //ASHLEY
    public Engine engine;
    //CollisionListener collisionListener;
    private AngelSystem angelSystem;
    private ImmutableArray angels;
    private Entity physicsEntity;

    private int state;


    public GameView() {
        super();
        //this.game  = FallingAngel.getInstance();
        Asset.load();
        state = GAME_READY;

        //Camera (and viewport of the screen)
        /*
        this.gameCam = new OrthographicCamera(320,480);
        this.viewPort = new StretchViewport(320, 480, gameCam);
        viewPort.apply();
        gameCam.position.set(320 / 2, 480 / 2, 0);
        gameCam.update();*/

        this.touchPoint = new Vector3();



        //kan bytte ut med det under, men må legge inn bredde og høyde i FallingAngel filen:

        /*
        gameCam = new OrthographicCamera(FallingAngel.V_WIDTH, FallingAngel.V_HEIGHT);
        viewPort = new StretchViewport(FallingAngel.V_WIDTH, FallingAngel.V_HEIGHT, gameCam);
        viewPort.apply();
        gameCam.position.set(viewPort.getWorldWidth() / 2, viewPort.getWorldHeight() / 2, 0);
        gameCam.update();
        */







        //Initializes new world
        this.engine = new Engine();
        this.world = new World(engine);
        this.stage = new Stage();
        this.settingsStage = new Stage();

        this.angelSystem = engine.getSystem(AngelSystem.class);
        this.angels = engine.getEntities();

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

    //Calls on different functions depending on which state the game is in
    public void update(float dt) {
        //if (dt > 0.1f) dt = 0.1f;

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
    private void updateReady () {
        if (Gdx.input.justTouched()) {
            state = GAME_RUNNING;
            resumeSystem();
        }
    }

    //Updates on what state the game is in
    //må legge inn metoder for hva som skjer mens spillet kjører
    private void updateRunning (float dt) {
        if (Gdx.input.justTouched()) {
            //camera unproject is used to transform the screen coordinates from a click or touch to the gameworld
            gameCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (pauseBounds.contains(touchPoint.x, touchPoint.y)) {
                state = GAME_PAUSED;
                pauseSystem();
                return;
            }
        }
        //her må det legges inn metoder for å flytte på Angel
            /*
            float accelX = 0.0f;


          //  float accelX = Gdx.input.getAccelerometerX();

            if (Gdx.input.isKeyPressed(Keys.DPAD_LEFT)) accelX = 5f;
            if (Gdx.input.isKeyPressed(Keys.DPAD_RIGHT)) accelX = -5f;

             */
        //må legge inn hva som skjer når key er pressed, hvordan bruke metodene i AngelSystem?
        //lurer kanskje på om det er like greit å ha de metodene i GameView og bare sette en accelX i AngelSystem
        //engine.getSystem(AngelSystem.class).press(, 5, 0);

        //Updates the players score
        if(world.score != lastScore) {
            lastScore = world.score;
            scoreString = "SCORE" + lastScore;
        }

        //skal vi bruke states i World? Da kan det løses på denne måten isåfall
        //If player dies
        if (world.state == World.WORLD_STATE_GAME_OVER) {
            state = GAME_OVER;
            //legg til en if-setning om denne scoren er høyere enn highscore -> si ny highscore, ellers bare vise scoren
            //oppdatere ny highscore
            pauseSystem();
        }
    }

    //When the player has paused the game, can either resume or quit
    private void updatePaused() {
        if (Gdx.input.justTouched()) {
            gameCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (resumeBounds.contains(touchPoint.x, touchPoint.y)) {
                state = GAME_RUNNING;
                resumeSystem();
                return;
            }
            if (quitBounds.contains(touchPoint.x,touchPoint.y)) {
                //må sendes til menu view
                return;
            }
        }


    }

    //When the player dies and the game is over, the player is sent to GameOverView
    private void updateGameOver() {
        //må sende spilleren til gameover
    }

    public void drawUI () {
        /*Texture texture = new Texture("BackgroundSky.png");
        TextureRegion textureRegion = new TextureRegion(texture, 0, 0, 500, 200);
        Image image = new Image(textureRegion);

        stage.addActor(image);

        stage.draw();
        gameCam.update();
        game.batch.setProjectionMatrix(gameCam.combined); //setProjectMatrix should be called every time the camera is moved or the screen is resized
        */
        game.batch.begin();


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
        game.batch.end();

    }

    //Hvis vi legger inn bilder med Ready, GameOver osv. men skal kanskje sendes til et annet view uansett
    public void presentReady() {
        /*
        Texture tex = new Texture("BackgroundSky.png");
        game.batch.draw(tex, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());   //må legge inn Asset for et spill som er klart
        */
        //game.batch.draw(Asset.backgroundTexture, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.draw(world.background.getComponent(TextureComponent.class).texture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }


    public void presentRunning() {
        //Asset for et kjørende spill, må ha en pause knapp og score
    }

    public void presentPaused() {
        //Asset for pause menu, og kanskje vise scoren
    }

    public void presentGameOver() {
        //Asset for game over og scoren skal vises (må ha asset for font, og så bruke scoreString som er laget her)
    }

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

    @Override
    public void render(float delta) {
        //update(delta);
        drawUI();
    }

    @Override
    public void pause() {
        if (state == GAME_RUNNING) {
            state = GAME_PAUSED;
            pauseSystem();
        }
    }
}