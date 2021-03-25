package com.fallingangel.view;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.fallingangel.game.FallingAngel;
import com.fallingangel.model.World;
import com.fallingangel.model.system.AngelSystem;

public class GameView{

    //Tar i bruk assets for å hente bilder

    private OrthographicCamera gameCam;
    private Viewport viewPort; //Viewport manages a Camera's viewportWidth and viewportHeight
    public FallingAngel game;
    private World world;


    //ASHLEY
    private Engine engine;
    private AngelSystem angelSystem;
    private ImmutableArray players;
    private Entity physicsEntity;

    public void GameView(FallingAngel game) {

    }



}