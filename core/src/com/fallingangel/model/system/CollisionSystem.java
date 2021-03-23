package com.fallingangel.model.system;

import java.util.Random

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.fallingangel.World; //TODO: have to make this class

import com.fallingangel.model.component.AngelComponent;
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

    public void CollisionListener {
        public void hitObs (); //if angel hits an obstacle (and dies/looses a life)
        public void hitCoin (); //if angel hits a coin (and gets points)
        public void hitPU (); //if angel hits a power up (and gets an advantage)

    }

    private Engine engine;
    private World world;  //creates a new world
    private CollisionListener listener;
    private Random rand = new Random();
    private ImmutableArray<Entity> angels;
    private ImmutableArray<Entity> coins;
    private ImmutableArray<Entity> planes;
    private ImmutableArray<Entity> obstacles;
    private ImmutableArray<Entity> powerup;

    public CollisionSystem(World world, CollisionListener listener) {
        this.world = world;
        this.listener = listener;

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
        powerup = engine.getEntitiesFor(Family.all(PowerUpComponent.class, BoundsComponent.class).get());

        //trenger angel og obstacle TransformComponent??
    }

    @Override
    public void update(float deltatime) {
        AngelSystem angelSystem = engine.getSystem(AngelSystem.class);
        ObstacleSystem obstacleSystem = engine.getSystem(ObstacleSystem.class);

        for (int i = 0; i < angels.size(); ++i) {
            Entitiy angel = angels.get(i);

            StateComponent angelState = sm.get(angel);

            if (angelState.get() == AngelComponent.STATE_HIT) {
                continue;


            }

            MovementComponent angelMov = mm.get(angel);
            BoundsComponent angelBounds = bm.get(angel);

            if (angelMov.move.y < 0.0f) { //?? engelen skal jo ikke bevege seg i y-retning, så litt usikker på denne
                TransformComponent angelPos = tm.get(angel);

                for (int j = 0; j < obstacles.size(); ++j) {
                    Entity obstacle = obstacles.get(j);

                    TransformComponent obsPos = tm.get(obstacle);

                    if (angelPos.pos.y >= obsPos.pos.y) { //checks if angelBounds and obsBounds are overlapping if angel position is higher than or the same as obstacle position
                        BoundsComponent obsBounds = bm.get(obstacle);

                        if (angelBounds.bounds.overlaps(obsBounds.bounds)) {
                            angelSystem.hitObstacle(angel);
                            listener.hitObs();
                            break;
                        }

                    }
                }
            }

            //if angel hits a moving obstacle (plane)
            for (int j = 0; j < planes.size(); ++j) {
                Entity plane = planes.get(j);

                BoundsComponent planeBounds = bm.get(plane);

                if (planeBounds.bounds.overlaps(angelBounds.bounds)) {
                    angelSystem.hitPlane(angel);
                    listener.hitObs();
                }
            }

            //if angel hits a coin, the player gets points added to their score and the coin disappears
            for (int j = 0; j < coins.size(); ++j) {
                Entity coin = coins.get(j);

                BoundsComponent coinBounds = bm.get(coin);

                if (coinBounds.bounds.overlaps(angelBounds.bounds)) {
                    engine.removeEntity(coin);
                    listener.hitCoin();
                    world.score += CoinComponent.SCORE;
                }
            }

            //when player hits a powerup, and it disappears
            for (int j = 0; j < powerup.size(); ++j) {
                Entity powerup = powerup.get(j);

                BoundsComponent powerupBounds = bm.get(powerup);

                if (powerupBounds.bounds.overlaps(angelBounds.bounds)) {
                    engine.removeEntity(powerup);
                    listener.hitPU();
                }
            }


            //hvor skal metodene for hva som skal skje når spilleren har truffet noe være?
            //for å miste liv, må vi kanskje ha noen liv i AngelComponent eller noe??
            //så kanskje noe sånt når man treffer et hinder:
              /*  if AngelComponent.LIFE > 0 {
                    AngelComponent.LIFE +- 1;
                } else {
                    world.state = WORLD_STATE_GAME_OVER; ?
                }
                men skjønner ikke hvor i koden dette skal være .. */


        }



    }
}
