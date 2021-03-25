package com.fallingangel.view;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.fallingangel.game.FallingAngel;
import com.fallingangel.model.World;
import com.fallingangel.controller.system.AngelSystem;

public class GameView extends ScreenAdapter {

    //Tar i bruk assets for å hente bilder
    //Må implementere pauseknapp asset, player asset


    private OrthographicCamera gameCam;
    private Viewport viewPort; //Viewport manages a Camera's viewportWidth and viewportHeight
    public FallingAngel game;
    private World world;


    private Stage stage;
    private Stage settingsStage;

    //ASHLEY
    private Engine engine;
    private AngelSystem angelSystem;
    private ImmutableArray angels;
    private Entity physicsEntity;


    public void GameView(FallingAngel game, Engine engine) {
        this.game  = game;
        this.engine = engine;

        angelSystem = engine.getSystem(AngelSystem.class);
        angels = engine.getEntities();



        //Camera and viewport of the screen
        //TODO: Må legge til bredde og høyde i FallingAngel spill filen

        /* gameCam = new OrthographicCamera(FallingAngel.V_WIDTH, FallingAngel.V_HEIGHT);
        viewPort = new StretchViewport(FallingAngel.V_WIDTH, FallingAngel.V_HEIGHT, gameCam);
        viewPort.apply();
        gameCam.position.set(viewPort.getWorldWidth() / 2, viewPort.getWorldHeight() / 2, 0);
        gameCam.update(); */

        //Initializes new world
        world = new World();
        stage = new Stage();
        settingsStage = new Stage();


    }






}