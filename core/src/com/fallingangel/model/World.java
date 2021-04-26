package com.fallingangel.model;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.fallingangel.game.FallingAngel;
import com.fallingangel.model.component.AngelComponent;
import com.fallingangel.model.component.AnimationComponent;
import com.fallingangel.model.component.BoundsComponent;
import com.fallingangel.model.component.CoinComponent;
import com.fallingangel.model.component.DevilComponent;
import com.fallingangel.model.component.MovementComponent;
import com.fallingangel.model.component.ObstacleComponent;
import com.fallingangel.model.component.DroneComponent;
import com.fallingangel.model.component.StateComponent;
import com.fallingangel.model.component.TextureComponent;
import com.fallingangel.model.component.TransformComponent;

import java.util.Random;

public class World {

    public static Random rand = new Random();

    public static int score;
    public int state;

    public Entity angel;
    private FallingAngel game;
    private Animation<TextureRegion> character = Assets.pigAnimation;

    public static final int WORLD_STATE_RUNNING = 0;
	public static final int WORLD_STATE_GAME_OVER = 2;

    private Engine engine;


    public World(Engine engine){
        this.engine = engine;
        this.game = FallingAngel.getInstance();
    }


    public void setChosenCharacter(Animation<TextureRegion> chosenCharacter) {
        this.character = chosenCharacter;
    }


    //main method that creates the world
    public void create(){
        this.state = WORLD_STATE_RUNNING;
        this.angel = createAngel();


        //creating the drones
        Array<Entity> drones = new Array<Entity>();
        for (int i = 1; i <= 3; i++) {
            int low = Gdx.graphics.getWidth()/4;
            int high = Gdx.graphics.getWidth() - Assets.droneTexture.getRegionWidth();
            int randomX  = rand.nextInt(high-low) + low;
            drones.add(createDrone(randomX , -i * Gdx.graphics.getHeight() * 2));
        }

        //creating the devils
        Array<Entity> devils = new Array<Entity>();
        for (int i = 1; i <= 3; i++) {
            int low = Gdx.graphics.getWidth()/4;
            int high = Gdx.graphics.getWidth() - Assets.devilTexture.getRegionWidth();
            int randomX  = rand.nextInt(high-low) + low;
            devils.add(createDevil(randomX , -i * Gdx.graphics.getHeight() * 5));
        }

        //creating the coins that will be used
        Array<Entity> coins = new Array<Entity>();
        for (int i = 0; i <= 8; i++) {
            coins.add(createCoin(rand.nextInt(Gdx.graphics.getWidth()), - i * Gdx.graphics.getHeight()/ 3));
        }

        //creating the obstacles that will be used
        Array<Entity> obstacles = new Array<Entity>();
        for (int i = 0; i <= 10; i++){
            obstacles.add(createObstacle(rand.nextInt(Gdx.graphics.getWidth() - Assets.balloons.size), - i * Gdx.graphics.getHeight()/4));
        }

    }


    public Entity createAngel(){
        Entity angelEntity = new Entity();

        //make new comp.
        AngelComponent angelComponent = new AngelComponent();
        AnimationComponent animationComponent = new AnimationComponent();
        BoundsComponent boundsComponent = new BoundsComponent();
        MovementComponent movementComponent = new MovementComponent();
        TransformComponent transformComponent = new TransformComponent();
        StateComponent stateComponent = new StateComponent();
        TextureComponent textureComponent = new TextureComponent();

        //connect the animation from Assets to the an.comp. IntMap
        animationComponent.animations.put(AngelComponent.STATE_FALL, character);

        //connect the comp. to the entity
        angelEntity.add(angelComponent);
        angelEntity.add(animationComponent);
        angelEntity.add(boundsComponent);
        angelEntity.add(movementComponent);
        angelEntity.add(transformComponent); //(x,y)-coordinate
        angelEntity.add(stateComponent);
        angelEntity.add(textureComponent);

        //set the state as falling
        stateComponent.set(AngelComponent.STATE_FALL);

        //set the position of the angel
        transformComponent.pos.set(Gdx.graphics.getWidth()/2 - AngelComponent.WIDTH/2, Gdx.graphics.getHeight()* 5/6, 0.5f); //

        //add the entity to the engine
        engine.addEntity(angelEntity);

        return angelEntity;
    }


    public Entity createObstacle(float x, float y){
        Entity obstacleEntity = new Entity();

        //create new components
        ObstacleComponent obstacleComponent = new ObstacleComponent();
        BoundsComponent boundsComponent = new BoundsComponent();
        TransformComponent transformComponent = new TransformComponent();
        StateComponent stateComponent = new StateComponent();
        TextureComponent textureComponent = new TextureComponent();
        MovementComponent movementComponent = new MovementComponent();

        //add the comp. to the entity
        obstacleEntity.add(obstacleComponent);
        obstacleEntity.add(boundsComponent);
        obstacleEntity.add(transformComponent);
        obstacleEntity.add(stateComponent);
        obstacleEntity.add(textureComponent);
        obstacleEntity.add(movementComponent);

        //add texture to the obstacle. At this point a random balloon is chosen.
        Random rand = new Random();
        textureComponent.textureRegion = Assets.balloons.get(rand.nextInt(Assets.balloons.size));

        //set the position
        transformComponent.pos.set(x, y, 1.0f);

        //add the entity to the engine
        engine.addEntity(obstacleEntity);

        return obstacleEntity;
    }


    public Entity createDrone(float x, float y){
        Entity planeEntity = new Entity();

        //create new components
        DroneComponent planeComponent = new DroneComponent();
        BoundsComponent boundsComponent = new BoundsComponent();
        MovementComponent movementComponent = new MovementComponent();
        TransformComponent transformComponent = new TransformComponent();
        StateComponent stateComponent = new StateComponent();
        TextureComponent textureComponent = new TextureComponent();

        //add the comp. to the entity
        planeEntity.add(planeComponent);
        planeEntity.add(boundsComponent);
        planeEntity.add(movementComponent);
        planeEntity.add(transformComponent);
        planeEntity.add(stateComponent);
        planeEntity.add(textureComponent);

        //add texture to the obstacle.
        textureComponent.textureRegion = Assets.droneTexture;

        //add the position of the plane
        transformComponent.pos.set(x, y, 4.0f);

        //random which way the drone starts to go (x-direction)
        int a = 1;
        int b = -1;
        int randomPick = rand.nextBoolean() ? a : b;
        planeComponent.SPEED = randomPick * planeComponent.VELOCITY;

        //add the entity to the engine
        engine.addEntity(planeEntity);

        return planeEntity;
    }

    public Entity createDevil(float x, float y){
        Entity devilEntity = new Entity();

        //create new components
        DevilComponent devilComponent = new DevilComponent();
        BoundsComponent boundsComponent = new BoundsComponent();
        MovementComponent movementComponent = new MovementComponent();
        TransformComponent transformComponent = new TransformComponent();
        StateComponent stateComponent = new StateComponent();
        TextureComponent textureComponent = new TextureComponent();

        //add the comp. to the entity
        devilEntity.add(devilComponent);
        devilEntity.add(boundsComponent);
        devilEntity.add(movementComponent);
        devilEntity.add(transformComponent);
        devilEntity.add(stateComponent);
        devilEntity.add(textureComponent);

        //add texture to the obstacle.
        textureComponent.textureRegion = Assets.devilTexture;

        //add the position of the devil
        transformComponent.pos.set(x, y, 4.0f);

        //random which way the devil starts to go (x-direction)
        int a = 1;
        int b = -1;
        int randomPick = rand.nextBoolean() ? a : b;
        devilComponent.SPEED = randomPick * devilComponent.VELOCITY;

        //add the entity to the engine
        engine.addEntity(devilEntity);

        return devilEntity;
    }

    public Entity createCoin(float x, float y){
        Entity coinEntity = new Entity();

        //create new components
        TextureComponent textureComponent = new TextureComponent();
        TransformComponent transformComponent = new TransformComponent();
        AnimationComponent animationComponent = new AnimationComponent();
        StateComponent stateComponent = new StateComponent();
        CoinComponent coinComponent = new CoinComponent();
        BoundsComponent boundsComponent = new BoundsComponent();

        //add the comp. to the entity
        coinEntity.add(textureComponent);
        coinEntity.add(transformComponent);
        coinEntity.add(animationComponent);
        coinEntity.add(stateComponent);
        coinEntity.add(coinComponent);
        coinEntity.add(boundsComponent);

        //set the position
        transformComponent.pos.set(x, y, 0.0f);
        animationComponent.animations.put(CoinComponent.STATE_NORMAL, Assets.coinAnimation);
        stateComponent.set(CoinComponent.STATE_NORMAL);

        //add the comp. to the entity
        engine.addEntity(coinEntity);

        return coinEntity;
    }


}
