package com.fallingangel.model;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.fallingangel.model.component.AngelComponent;
import com.fallingangel.model.component.AnimationComponent;
import com.fallingangel.model.component.BoundsComponent;
import com.fallingangel.model.component.CoinComponent;
import com.fallingangel.model.component.MovementComponent;
import com.fallingangel.model.component.ObstacleComponent;
import com.fallingangel.model.component.DroneComponent;
import com.fallingangel.model.component.PowerUpComponent;
import com.fallingangel.model.component.StateComponent;
import com.fallingangel.model.component.TextureComponent;
import com.fallingangel.model.component.TransformComponent;

import java.util.Random;

public class World {

    public static Random rand = new Random();

    public static final int VP_WIDTH  = 1714;
    public static final int VP_HEIGHT = 4096;

    public int score;
    public Entity angel;
    public int state;
    public Entity background;

    public static final int WORLD_STATE_RUNNING = 0;
	public static final int WORLD_STATE_NEXT_LEVEL = 1;
	public static final int WORLD_STATE_GAME_OVER = 2;
	public static final Vector2 gravity = new Vector2(0, -12);

    private Engine engine;


    public World(Engine engine){
        this.engine = engine;
    }


    //main method that creates the world
    public void create(){
        this.state = WORLD_STATE_RUNNING;
        this.angel = createAngel();
        this.background = createBackground();


        //creating the drones
        Array<Entity> drones = new Array<Entity>();
        for (int i = 1; i <= 3; i++) {
            int low = Gdx.graphics.getWidth()/4;
            int high = Gdx.graphics.getWidth() - Asset.droneTexture.getRegionWidth();
            int randomX  = rand.nextInt(high-low) + low;
            drones.add(createDrone(randomX , -i * Gdx.graphics.getHeight()));
        }

        //creating the coins that will be used
        Array<Entity> coins = new Array<Entity>();
        for (int i = 1; i <= 8; i++) {
            coins.add(createCoin(rand.nextInt(Gdx.graphics.getWidth()), - i * Gdx.graphics.getHeight()/ 3));
        }

        //creating the obstacles that will be used
        Array<Entity> obstacles = new Array<Entity>();
        for (int i = 1; i <= 5; i++){
            obstacles.add(createObstacle(rand.nextInt(Gdx.graphics.getWidth()), - i * Gdx.graphics.getHeight()/ 3));
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
        animationComponent.animations.put(AngelComponent.STATE_FALL, Asset.pigAnimation);

        //connect the comp. to the entity
        angelEntity.add(angelComponent);
        angelEntity.add(animationComponent);
        angelEntity.add(boundsComponent);
        angelEntity.add(movementComponent);
        angelEntity.add(transformComponent);
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
        AnimationComponent animationComponent = new AnimationComponent();
        BoundsComponent boundsComponent = new BoundsComponent();
        TransformComponent transformComponent = new TransformComponent();
        StateComponent stateComponent = new StateComponent();
        TextureComponent textureComponent = new TextureComponent();
        MovementComponent movementComponent = new MovementComponent();

        //add the comp. to the entity
        obstacleEntity.add(obstacleComponent);
        obstacleEntity.add(animationComponent);
        obstacleEntity.add(boundsComponent);
        obstacleEntity.add(transformComponent);
        obstacleEntity.add(stateComponent);
        obstacleEntity.add(textureComponent);
        obstacleEntity.add(movementComponent);

        //add texture to the obstacle. At this point a random balloon is chosen.
        Random rand = new Random();
        textureComponent.textureRegion = Asset.balloons.get(rand.nextInt(Asset.balloons.size));

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
        AnimationComponent animationComponent = new AnimationComponent();
        BoundsComponent boundsComponent = new BoundsComponent();
        MovementComponent movementComponent = new MovementComponent();
        TransformComponent transformComponent = new TransformComponent();
        StateComponent stateComponent = new StateComponent();
        TextureComponent textureComponent = new TextureComponent();

        //add the comp. to the entity
        planeEntity.add(planeComponent);
        planeEntity.add(animationComponent);
        planeEntity.add(boundsComponent);
        planeEntity.add(movementComponent);
        planeEntity.add(transformComponent);
        planeEntity.add(stateComponent);
        planeEntity.add(textureComponent);

        //add texture to the obstacle.
        textureComponent.textureRegion = Asset.droneTexture;

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


    //will implement if there is time
    public Entity powerUp(){
        Entity powerup = new Entity();
        PowerUpComponent puc = new PowerUpComponent();
        powerup.add(puc);
        engine.addEntity(powerup);
        return powerup;
    }


    public Entity createBackground(){
        Entity backgroundEntity = new Entity();
        //BackgroundComponent backgroundComponent = new BackgroundComponent();
        TextureComponent textureComponent = new TextureComponent();
        TransformComponent transformComponent = new TransformComponent();

        textureComponent.textureRegion = Asset.backgroundTextureRegion;
        transformComponent.pos.set(0,0, 5.0f);

        //backgroundEntity.add(backgroundComponent);
        backgroundEntity.add(textureComponent);
        backgroundEntity.add(transformComponent);

        engine.addEntity(backgroundEntity);

        return backgroundEntity;
    }

    public Entity createCoin(float x, float y){
        Entity coinEntity = new Entity();

        TextureComponent textureComponent = new TextureComponent();
        TransformComponent transformComponent = new TransformComponent();
        AnimationComponent animationComponent = new AnimationComponent();
        StateComponent stateComponent = new StateComponent();
        CoinComponent coinComponent = new CoinComponent();
        BoundsComponent boundsComponent = new BoundsComponent();

        coinEntity.add(textureComponent);
        coinEntity.add(transformComponent);
        coinEntity.add(animationComponent);
        coinEntity.add(stateComponent);
        coinEntity.add(coinComponent);
        coinEntity.add(boundsComponent);

        transformComponent.pos.set(x, y, 0.0f);
        animationComponent.animations.put(CoinComponent.STATE_NORMAL, Asset.coinAnimation);
        stateComponent.set(CoinComponent.STATE_NORMAL);


        engine.addEntity(coinEntity);

        return coinEntity;
    }




}
