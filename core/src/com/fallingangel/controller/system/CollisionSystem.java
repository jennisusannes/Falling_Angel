package com.fallingangel.controller.system;

import java.util.Random;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.fallingangel.model.World;

import com.fallingangel.model.component.AngelComponent;
import com.fallingangel.model.component.BoundsComponent;
import com.fallingangel.model.component.CoinComponent;
import com.fallingangel.model.component.MovementComponent;
import com.fallingangel.model.component.TransformComponent;
import com.fallingangel.model.component.PlaneComponent;
import com.fallingangel.model.component.ObstacleComponent;
import com.fallingangel.model.component.StateComponent;
import com.fallingangel.model.component.PowerUpComponent;

public class CollisionSystem extends EntitySystem { //EntitySystem: abstact class for processing sets of Entity objects
    //component mappers to get components
    private ComponentMapper<BoundsComponent> bm;
    private ComponentMapper<MovementComponent> mm;
    private ComponentMapper<StateComponent> sm;
    private ComponentMapper<TransformComponent> tm;

    //CollisionListener brukes hvis vi skal lage lyde når den treffer noe
   /* public static interface CollisionListener {
        public void hitObs (); //hits an obstacle
        public void hitCoin (); //hits a coin
        public void hitPU (); //hits a power up

    } */

    private Engine engine;
    private World world;
    //private CollisionListener listener;
    private Random rand = new Random();
    private ImmutableArray<Entity> angels;
    private ImmutableArray<Entity> coins;
    private ImmutableArray<Entity> planes;
    private ImmutableArray<Entity> obstacles;
    private ImmutableArray<Entity> powerups;

    // om vi skal ha listener (lyd): public CollisionSystem(World world, CollisionListener listener)
    public CollisionSystem(World world) {
        this.world = world;
     //   this.listener = listener;

        //creates our componentMappers
        bm = ComponentMapper.getFor(BoundsComponent.class);
        mm = ComponentMapper.getFor(MovementComponent.class);
        sm = ComponentMapper.getFor(StateComponent.class);
        tm = ComponentMapper.getFor(TransformComponent.class);
    }

    @Override
    public void addedToEngine(Engine engine) {
        this.engine = engine;

        //gets all entities with a AngelComponent, BoundsComponent, TransformCompont and StateComponent
        angels = engine.getEntitiesFor(Family.all(AngelComponent.class, BoundsComponent.class, TransformComponent.class, StateComponent.class).get());
        //gets all entities with a CoinComponent and BoundsComponent
        coins = engine.getEntitiesFor(Family.all(CoinComponent.class, BoundsComponent.class).get());
        //gets all entities with a PlaneComponent and BoundsComponent
        planes = engine.getEntitiesFor(Family.all(PlaneComponent.class, BoundsComponent.class).get());
        //gets all entities with a ObstacleComponent, BoundsComponent and TransformCompon
        obstacles = engine.getEntitiesFor(Family.all(ObstacleComponent.class, BoundsComponent.class, TransformComponent.class).get());
        //gets all entities with a PowerUpComponent and BoundsComponent
        powerups = engine.getEntitiesFor(Family.all(PowerUpComponent.class, BoundsComponent.class).get());

        //trenger angel og obstacle TransformComponent??
    }

    @Override
    public void update(float deltatime) {
        AngelSystem angelSystem = engine.getSystem(AngelSystem.class);
        ObstacleSystem obstacleSystem = engine.getSystem(ObstacleSystem.class);

        for (int i = 0; i < angels.size(); ++i) {
            Entity angel = angels.get(i);

            StateComponent angelState = sm.get(angel);

            if (angelState.get() == AngelComponent.STATE_HIT) {
                continue;


            }

            MovementComponent angelMov = mm.get(angel);
            BoundsComponent angelBounds = bm.get(angel);


            if (angelMov.move.x != 0.0f) { //?? funker dette?
                TransformComponent angelPos = tm.get(angel);

                for (int j = 0; j < obstacles.size(); ++j) {
                    Entity obstacle = obstacles.get(j);

                    TransformComponent obsPos = tm.get(obstacle);

                    //checks if angelBounds and obsBounds are overlapping if angel position is higher than or the same as obstacle position
                    if (angelPos.pos.y >= obsPos.pos.y) {
                        BoundsComponent obsBounds = bm.get(obstacle);

                        if (angelBounds.bounds.overlaps(obsBounds.bounds)) {
                            angelSystem.hitObstacle(angel);
                            //listener.hitObs();
                            //break; skal den egentlig breake her eller bare kalle på hitObstacle som fører til GameOver
                        }

                    }
                }
            }

            //if angel hits a moving obstacle (plane), the player dies
            for (int j = 0; j < planes.size(); ++j) {
                Entity plane = planes.get(j);

                BoundsComponent planeBounds = bm.get(plane);

                if (planeBounds.bounds.overlaps(angelBounds.bounds)) {
                    angelSystem.hitPlane(angel);
                   // listener.hitObs();
                }
            }

            //if angel hits a coin, the player gets points added to their score and the coin disappears
            for (int j = 0; j < coins.size(); ++j) {
                Entity coin = coins.get(j);

                BoundsComponent coinBounds = bm.get(coin);

                if (coinBounds.bounds.overlaps(angelBounds.bounds)) {
                    engine.removeEntity(coin);
                    //listener.hitCoin();
                    world.score += CoinComponent.SCORE; //score må legges til i world
                }
            }

            //when player hits a powerup, the powerup disappears and the player gets an advantage
            for (int j = 0; j < powerups.size(); ++j) {
                Entity powerup = powerups.get(j);

                BoundsComponent powerupBounds = bm.get(powerup);

                if (powerupBounds.bounds.overlaps(angelBounds.bounds)) {
                    engine.removeEntity(powerup);
                    angelSystem.hitPowerUp(angel); //hitPowerup må lages i AngelSystem
                    //listener.hitPU();
                }
            }



            //så kanskje noe sånt når man treffer et hinder:
              /*  if AngelComponent.LIFE > 0 {
                    AngelComponent.LIFE +- 1;
                } else {
                    world.state = WORLD_STATE_GAME_OVER; ?
                }
                 */


        }



    }
}
