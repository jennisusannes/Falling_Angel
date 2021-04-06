package com.fallingangel.model;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.fallingangel.model.component.AngelComponent;
import com.fallingangel.model.component.BackgroundComponent;
import com.fallingangel.model.component.CoinComponent;
import com.fallingangel.model.component.ObstacleComponent;
import com.fallingangel.model.component.PlaneComponent;
import com.fallingangel.model.component.PowerUpComponent;
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

    public Entity createBackground(){
        Entity backgroundEntity = new Entity();
        BackgroundComponent backgroundComponent = new BackgroundComponent();
        TextureComponent textureComponent = new TextureComponent();
        TransformComponent transformComponent = new TransformComponent();

        textureComponent.texture = Asset.getTexture(Asset.gameBackground);

        backgroundEntity.add(backgroundComponent);
        backgroundEntity.add(textureComponent);
        backgroundEntity.add(transformComponent);

        engine.addEntity(backgroundEntity);

        return backgroundEntity;
    }





}
