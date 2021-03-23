package com.fallingangel.model;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.fallingangel.model.component.AngelComponent;
import com.fallingangel.model.component.CoinComponent;
import com.fallingangel.model.component.ObstacleComponent;
import com.fallingangel.model.component.PlaneComponent;

public class World {


    //TODO: Create a world to instanciate alle the different entities.

    //TODO: Implement variables for points and height fallen etc...
    public static float WORLD_WIDTH = 10;




    //Vanlig engine på tubby, PooledEngine på superjumper. Veldig usikker her...
    //Pool: tar vare på instansene, slipper frem-og-tilbake. Spør studass her

    private Engine engine;

    public World(Engine engine){
        this.engine = engine;
        //Det _kan_ være denne skal være pooled --> spør studass :))

    }


    private Entity createAngel(){
        Entity angelEntity = new Entity();
        AngelComponent ac = new AngelComponent();
        angelEntity.add(ac);
        engine.addEntity(angelEntity);
        return angelEntity;
    }

    /*
    Kan være at funksjonen under skal være:
    private List<Entity> createObstacles(){
    List<Entity> entities = new ArrayList<Entity>();
     LOGIKK HER
     .
     .
     .

    return entities
    }

     */


    private Entity createObstacle(){
        Entity obstacleEntity = new Entity();
        ObstacleComponent oc = new ObstacleComponent();
        obstacleEntity.add(oc);
        engine.addEntity(obstacleEntity);
        return obstacleEntity;
    }

    private Entity createPlane(){
        Entity planeEntity = new Entity();
        PlaneComponent pc = new PlaneComponent();
        planeEntity.add(pc);
        engine.addEntity(planeEntity);
        return planeEntity;
    }


    private Entity createCoin(){
        Entity coinEntity = new Entity();
        CoinComponent cc = new CoinComponent();
        coinEntity.add(cc);
        engine.addEntity(coinEntity);
        return coinEntity;
    }


}
