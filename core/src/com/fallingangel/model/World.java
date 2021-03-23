package com.fallingangel.model;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;

public class World {


    //TODO: Create a world to instanciate alle the different entities.


    public static float WORLD_WIDTH = 10;



    //Vanlig engine på tubby, PooledEngine på superjumper. Veldig usikker her...
    private Engine engine;

    public World(Engine engine){
        this.engine = engine;
    }


    private Entity createAngel(){
        //TODO: implement methods for instanciating angel
        return null;
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
        //TODO: implement methods for instanciating obstacles (e.g. "bad" clouds, devil etc)
        return null;
    }

    private Entity createPlane(){
        //TODO: implement methods for instanciating movable obstacle (e.g. plane)
        return null;
    }


    private Entity createCoin(){
        //TODO: implement methods for instanciating extra points --> coins
        return null;
    }


}
