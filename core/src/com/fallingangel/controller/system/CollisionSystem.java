package com.fallingangel.controller.system;

import java.util.Random;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.fallingangel.model.World;

import com.fallingangel.model.component.AngelComponent;
import com.fallingangel.model.component.BoundsComponent;
import com.fallingangel.model.component.CoinComponent;
import com.fallingangel.model.component.MovementComponent;
import com.fallingangel.model.component.TextureComponent;
import com.fallingangel.model.component.TransformComponent;
import com.fallingangel.model.component.PlaneComponent;
import com.fallingangel.model.component.ObstacleComponent;
import com.fallingangel.model.component.StateComponent;
import com.fallingangel.model.component.PowerUpComponent;

import sun.corba.EncapsInputStreamFactory;

public class CollisionSystem extends EntitySystem { //EntitySystem: abstact class for processing sets of Entity objects
    //component mappers to get components
    private ComponentMapper<BoundsComponent> boundsMapper;
    private ComponentMapper<MovementComponent> movementMapper;
    private ComponentMapper<StateComponent> stateMapper;
    private ComponentMapper<TransformComponent> transformMapper;
    private ComponentMapper<TextureComponent> textureMapper;

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
        boundsMapper = ComponentMapper.getFor(BoundsComponent.class);
        movementMapper = ComponentMapper.getFor(MovementComponent.class);
        stateMapper = ComponentMapper.getFor(StateComponent.class);
        transformMapper = ComponentMapper.getFor(TransformComponent.class);
        textureMapper = ComponentMapper.getFor(TextureComponent.class);
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

    }

    @Override
    public void update(float deltatime) {
        AngelSystem angelSystem = engine.getSystem(AngelSystem.class);
        ObstacleSystem obstacleSystem = engine.getSystem(ObstacleSystem.class);

        Entity angel = angels.get(0);

        MovementComponent angelMov = movementMapper.get(angel);
        BoundsComponent angelBounds = boundsMapper.get(angel);
        TransformComponent angelPos = transformMapper.get(angel);

        /*for (int i = 0; i < angels.size(); ++i) {
          //  Entity angel = angels.get(i);

           /* StateComponent angelState = stateMapper.get(angel);

            if (angelState.get() == AngelComponent.STATE_HIT) {
                continue;

            }


            MovementComponent angelMov = movementMapper.get(angel);
            BoundsComponent angelBounds = boundsMapper.get(angel);
            TransformComponent angelPos = transformMapper.get(angel);
        */

          /*  if (angelMov.move.x != 0.0f) { //?? funker dette?
                TransformComponent angelPos = transformMapper.get(angel);

                for (int j = 0; j < obstacles.size(); ++j) {
                    Entity obstacle = obstacles.get(j);

                    TransformComponent obsPos = transformMapper.get(obstacle);

                    //checks if angelBounds and obsBounds are overlapping if angel position is higher than or the same as obstacle position
                    if (angelPos.pos.y >= obsPos.pos.y) {
                        BoundsComponent obsBounds = boundsMapper.get(obstacle);

                        if (angelBounds.rectangle.overlaps(obsBounds.rectangle)) {
                            angelSystem.hitObstacle(angel);
                            //listener.hitObs();
                            //break; skal den egentlig breake her eller bare kalle på hitObstacle som fører til GameOver
                        }

                    }
                }
            }*/

           for (int j = 0; j < obstacles.size(); ++j) {
                Entity obstacle = obstacles.get(j);

                TransformComponent obsPos = transformMapper.get(obstacle);
              //  BoundsComponent obsBounds = boundsMapper.get(obstacle);
                TextureComponent obsTexture = textureMapper.get(obstacle);

                if (angelPos.pos.y >= obsPos.pos.y + obsTexture.textureRegion.getRegionHeight()/2) {
                    BoundsComponent obsBounds = boundsMapper.get(obstacle);
                    if (obsBounds.rectangle.overlaps(angelBounds.rectangle)) {
                        angelSystem.hitObstacle(angel);

                        int randomNumber = rand.nextInt(10);
                        int low = - Gdx.graphics.getHeight() - randomNumber;
                        int high = 0;
                        int area = rand.nextInt(high-low) + low;
                }

                    //angelPos.pos.y = 0;
                    //listener.hitObs();
                    //break; skal den egentlig breake her eller bare kalle på hitObstacle som fører til GameOver
                }
            }


            //if angel hits a moving obstacle (plane), the player dies
            for (int j = 0; j < planes.size(); ++j) {
                Entity plane = planes.get(j);

                BoundsComponent planeBounds = boundsMapper.get(plane);

                if (planeBounds.rectangle.overlaps(angelBounds.rectangle)) {
                    //angelSystem.hitPlane(angel);
                   // listener.hitObs();
                }
            }

            //if angel hits a coin, the player gets points added to their score and the coin disappears
            for (int j = 0; j < coins.size(); ++j) {
                Entity coin = coins.get(j);

                BoundsComponent coinBounds = boundsMapper.get(coin);
                TransformComponent transformComponent = transformMapper.get(coin);
                TextureComponent textureComponent = textureMapper.get(coin);

                if (coinBounds.rectangle.overlaps(angelBounds.rectangle)) {
                    //engine.removeEntity(coin);
                    //listener.hitCoin();
                    int randomNumber = rand.nextInt(10);
                    int low = - Gdx.graphics.getHeight() - randomNumber;
                    int high = 0;
                    int area = rand.nextInt(high-low) + low;

                    angel.getComponent(AngelComponent.class).COINS_HIT += 20;
                    transformComponent.pos.y = area;
                    transformComponent.pos.x = rand.nextInt(Gdx.graphics.getWidth());


                    //transformComponent.pos.y = - textureComponent.textureRegion.getRegionHeight() - Gdx.graphics.getHeight()/2;
                    //transformComponent.pos.x = rand.nextInt(Gdx.graphics.getWidth() - textureComponent.textureRegion.getRegionWidth());
                    //reposition(coin);
                }
            }

            //when player hits a powerup, the powerup disappears and the player gets an advantage
            for (int j = 0; j < powerups.size(); ++j) {
                Entity powerup = powerups.get(j);

                BoundsComponent powerupBounds = boundsMapper.get(powerup);

                if (powerupBounds.rectangle.overlaps(angelBounds.rectangle)) {
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



    //}

    public void reposition(Entity entity){
        entity.getComponent(TransformComponent.class).pos.x = 0;
        entity.getComponent(TransformComponent.class).pos.y = 0;
    }
}
