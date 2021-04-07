package com.fallingangel.model;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.fallingangel.model.component.AngelComponent;
import com.fallingangel.model.component.AnimationComponent;
import com.fallingangel.model.component.BackgroundComponent;
import com.fallingangel.model.component.BoundsComponent;
import com.fallingangel.model.component.CoinComponent;
import com.fallingangel.model.component.MovementComponent;
import com.fallingangel.model.component.ObstacleComponent;
import com.fallingangel.model.component.PlaneComponent;
import com.fallingangel.model.component.PowerUpComponent;
import com.fallingangel.model.component.StateComponent;
import com.fallingangel.model.component.TextureComponent;
import com.fallingangel.model.component.TransformComponent;

public class World {


    //Må finne riktige statiske variabler her, sånn at appen passer fint til alle
    public static final float WORLD_HEIGHT = 15*20;
    public static final float WORLD_WIDTH = 10;


    //lurer på om det skal være flere static variables her (?)
    public int score;
    public Entity angel;
    public int state;
    public Entity background;
    public Entity coin;


    //Usikker på om disse skal være med i spillet (?)
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
        this.coin = createCoin();

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
        //animations for when a collision occurs and when the pig is dead

        //put the bounds as the angels width and height
        boundsComponent.bounds.width = AngelComponent.WIDTH;
        boundsComponent.bounds.height = AngelComponent.HEIGHT;

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
        transformComponent.pos.set(5.0f, 1.0f, 0.0f); //

        //add the entity to the engine
        engine.addEntity(angelEntity);

        return angelEntity;
    }

    private Entity createCoin() { //kan ha float x, float y som input her hvis vi skal bruke pos som kommentert ut lenger ned
        Entity coinEntity = engine.createEntity();

        AnimationComponent animationComponent = engine.createComponent(AnimationComponent.class);
        CoinComponent coinComponent = engine.createComponent(CoinComponent.class);
        BoundsComponent boundsComponent = engine.createComponent(BoundsComponent.class);
        TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
        StateComponent stateComponent = engine.createComponent(StateComponent.class);
        TextureComponent textureComponent = engine.createComponent(TextureComponent.class);

        animationComponent.animations.put(CoinComponent.STATE_NORMAL, Asset.coinAnimation);

        boundsComponent.bounds.width = CoinComponent.WIDTH;
        boundsComponent.bounds.height = CoinComponent.HEIGHT;

        //transformComponent.pos.set(x, y, 3.0f);

        stateComponent.set(CoinComponent.STATE_NORMAL);

        coinEntity.add(coinComponent);
        coinEntity.add(boundsComponent);
        coinEntity.add(transformComponent);
        coinEntity.add(textureComponent);
        coinEntity.add(animationComponent);
        coinEntity.add(stateComponent);

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

    public Entity createBackground(){
        Entity backgroundEntity = new Entity();
        //BackgroundComponent backgroundComponent = new BackgroundComponent();
        TextureComponent textureComponent = new TextureComponent();
        //TransformComponent transformComponent = new TransformComponent();

        textureComponent.texture = Asset.backgroundTexture;

        //backgroundEntity.add(backgroundComponent);
        backgroundEntity.add(textureComponent);
        //backgroundEntity.add(transformComponent);

        engine.addEntity(backgroundEntity);

        return backgroundEntity;
    }





}
