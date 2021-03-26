package com.fallingangel.model;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.fallingangel.model.component.AngelComponent;
import com.fallingangel.model.component.CoinComponent;
import com.fallingangel.model.component.ObstacleComponent;
import com.fallingangel.model.component.PlaneComponent;
import com.fallingangel.model.component.PowerUpComponent;

public class World {


    //Må finne riktige statiske variabler her, sånn at appen går helt ut i kanten på alle devices
    public static final float WORLD_HEIGHT = 15*20;
    public static final float WORLD_WIDTH = 10;


    //lurer på om det skal være flere static variables her (?)
    public int score;

    /*
    //TODO: vurdere om disse skal være med i spillet
    public static final int WORLD_STATE_RUNNING = 0;
	public static final int WORLD_STATE_NEXT_LEVEL = 1;
	public static final int WORLD_STATE_GAME_OVER = 2;
	public static final Vector2 gravity = new Vector2(0, -12);
     */

    private Engine engine;

    //Er en mulighet for at det er lurere å bruke pooled engine.
    public World(Engine engine){
        this.engine = engine;
    }


    public Entity createAngel(){
        Entity angelEntity = new Entity();
        AngelComponent ac = new AngelComponent();
        angelEntity.add(ac);
        engine.addEntity(angelEntity);
        return angelEntity;
    }

    public Entity createCoin(){
        Entity coinEntity = new Entity();
        CoinComponent cc = new CoinComponent();
        coinEntity.add(cc);
        engine.addEntity(coinEntity);
        return coinEntity;
    }

    public Entity createObstacle(){
        Entity obstacleEntity = new Entity();
        ObstacleComponent oc = new ObstacleComponent();
        obstacleEntity.add(oc);
        engine.addEntity(obstacleEntity);
        return obstacleEntity;
    }


    public Entity createPlane(){
        Entity planeEntity = new Entity();
        PlaneComponent pc = new PlaneComponent();
        planeEntity.add(pc);
        engine.addEntity(planeEntity);
        return planeEntity;
    }

    public Entity powerUp(){
        Entity powerup = new Entity();
        PowerUpComponent puc = new PowerUpComponent();
        powerup.add(puc);
        engine.addEntity(powerup);
        return powerup;
    }

    //TODO: skal dette gjøres med flere systems(?)




}
