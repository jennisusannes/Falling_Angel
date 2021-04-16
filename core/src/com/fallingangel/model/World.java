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
import com.fallingangel.model.component.PlaneComponent;
import com.fallingangel.model.component.PowerUpComponent;
import com.fallingangel.model.component.StateComponent;
import com.fallingangel.model.component.TextureComponent;
import com.fallingangel.model.component.TransformComponent;

import java.util.Random;

public class World {

    public static Random rand = new Random();

    //Might have some other values here.
    public static final float WORLD_HEIGHT = 15*20;
    public static final float WORLD_WIDTH = 10;

    public static final int VP_WIDTH  = 1714;
    public static final int VP_HEIGHT = 4096;


    public int score;
    public Entity angel;
    public int state;
    public Entity background;
    public Entity coin;
    public Entity plane;
    public Entity obstacle;



    public static final int WORLD_STATE_RUNNING = 0;
	public static final int WORLD_STATE_NEXT_LEVEL = 1;
	public static final int WORLD_STATE_GAME_OVER = 2;
	public static final Vector2 gravity = new Vector2(0, -12);


    private Engine engine;


    public World(Engine engine){
        this.engine = engine;
    }
    //Mulig å bruke pooled engine også


    public void create(){
        this.angel = createAngel();
        this.state = WORLD_STATE_RUNNING;
        this.background = createBackground();
        //generateObjects();

        //creating the planes that will be used
        /*
        Array<Entity> planes = new Array<Entity>();
        for (int i = 1; i<= 3; i++){
            int a = 0;
            int b = Gdx.graphics.getWidth();
            int randomOfTwoInts = rand.nextBoolean() ? a : b;
            planes.add(createPlane(randomOfTwoInts, Gdx.graphics.getHeight()/2));
        }

         */


        //creating the planes
        Array<Entity> planes = new Array<Entity>();
        for (int i = 1; i <= 3; i++) {
            //Generates a random area at the top of the screen, where the plane should come out
            int low = Gdx.graphics.getHeight()*2/3;
            int high = Gdx.graphics.getHeight()*3/4;
            int area = rand.nextInt(high-low) + low;
            //Random number so the planes will come at random times (must *randomnumber when creating a plane)
            //int randomNumber = rand.nextInt(10);
            int a = 0;
            int b = Gdx.graphics.getWidth();
            int randomPick = rand.nextBoolean() ? a : b;
            if (randomPick == a) {
                planes.add(createPlane(-i*a,area));
            }
            else {
                Asset.planeTexture.flip(true,false);
                planes.add(createPlane(-i*b,area));
            }

        }


        //making an Array with planes that fly from the right
        /*
        TextureComponent textureComponent = new TextureComponent();

        Asset.planeTexture.flip(true,false);

        Array<Entity> planesFlipped = new Array<Entity>();
        for (int i = 1; i <= 3; i++) {
            planesFlipped.add(createPlane(Gdx.graphics.getWidth(),area));
        }*/


        //creating the coins that will be used
        Array<Entity> coins = new Array<Entity>();
        for (int i = 1; i <= 8; i++) {
            coins.add(createCoin(rand.nextInt(Gdx.graphics.getWidth()), - i * Gdx.graphics.getHeight()/ 2));
        }

        //creating the obstacles that will be used
        Array<Entity> obstacles = new Array<Entity>();
        for (int i = 1; i <= 5; i++){
            obstacles.add(createObstacle(rand.nextInt(Gdx.graphics.getWidth()), - i * Gdx.graphics.getHeight()/ 3));
        }

    }

    /*
    public void generateObjects(){

        float y = ObstacleComponent.HEIGHT / 2; //0.15f
        while (y < WORLD_HEIGHT -WORLD_WIDTH / 2) { //300-5

            //creating an obstacle
            float x = rand.nextFloat() * (WORLD_WIDTH - ObstacleComponent.WIDTH) + ObstacleComponent.WIDTH / 2;
            createObstacle(x, y);
            //createObstacle(rand.nextInt(Gdx.graphics.getWidth()), Gdx.graphics.getHeight()/ 3);

            //creating a plane
            if (y > WORLD_HEIGHT / 3 && rand.nextFloat() > 0.8f) {
                createPlane(x + rand.nextFloat(), y + PlaneComponent.HEIGHT + rand.nextFloat() * 2);
            }

            if (rand.nextFloat() > 0.6f) {
                createCoin(x + MathUtils.random(-0.5f, 0.5f), y + CoinComponent.HEIGHT + rand.nextFloat() * 3);
            }
        }

    }*/


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
        //animations for when a collision occurs and when the pig is dead

        //put the bounds as the angels width and height
        boundsComponent.rectangle.width = AngelComponent.WIDTH;
        boundsComponent.rectangle.height = AngelComponent.HEIGHT;

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


    //Random whether the obstacle is a devil or balloon, random colour on the balloon.
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
        transformComponent.pos.set(x, y, 1.0f);
        boundsComponent.rectangle.width = obstacleComponent.WIDTH;
        boundsComponent.rectangle.height = obstacleComponent.HEIGHT;

        //add the entity to the engine
        engine.addEntity(obstacleEntity);

        return obstacleEntity;
    }

    public Entity createPlane(float x, float y){
        Entity planeEntity = new Entity();

        //create new components
        PlaneComponent planeComponent = new PlaneComponent();
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
        textureComponent.textureRegion = Asset.planeTexture;

        //add the position of the plane
        transformComponent.pos.set(x, y, 4.0f);

        //add the entity to the engine
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

        boundsComponent.rectangle.width = coinComponent.WIDTH;
        boundsComponent.rectangle.height = coinComponent.HEIGHT;

        engine.addEntity(coinEntity);

        return coinEntity;
    }





}
