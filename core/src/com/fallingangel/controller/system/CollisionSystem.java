package com.fallingangel.controller.system;

import java.util.Random;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.fallingangel.controller.MainController;
import com.fallingangel.game.FallingAngel;
import com.fallingangel.model.Assets;
import com.fallingangel.model.SettingsModel;
import com.fallingangel.model.World;
import com.fallingangel.model.component.AngelComponent;
import com.fallingangel.model.component.BoundsComponent;
import com.fallingangel.model.component.CoinComponent;
import com.fallingangel.model.component.DevilComponent;
import com.fallingangel.model.component.MovementComponent;
import com.fallingangel.model.component.TextureComponent;
import com.fallingangel.model.component.TransformComponent;
import com.fallingangel.model.component.DroneComponent;
import com.fallingangel.model.component.ObstacleComponent;
import com.fallingangel.model.component.StateComponent;

public class CollisionSystem extends EntitySystem { //EntitySystem: abstact class for processing sets of Entity objects
    //component mappers to get components
    private ComponentMapper<BoundsComponent> boundsMapper;
    private ComponentMapper<MovementComponent> movementMapper;
    private ComponentMapper<StateComponent> stateMapper;
    private ComponentMapper<TransformComponent> transformMapper;
    private ComponentMapper<TextureComponent> textureMapper;

    private FallingAngel game;
    private MainController mainController;
    private SettingsModel settingsModel;
    private Engine engine;
    private World world;
    private Random rand = new Random();
    private ImmutableArray<Entity> angels;
    private ImmutableArray<Entity> coins;
    private ImmutableArray<Entity> drones;
    private ImmutableArray<Entity> devils;
    private ImmutableArray<Entity> obstacles;
    private ImmutableArray<Entity> powerups;

    private Sound coinSound = Assets.coinSound;
    private Sound collisionSound = Assets.collisionSound;

    public CollisionSystem(World world) {
        this.world = world;
        game = FallingAngel.getInstance();
        this.mainController = game.mc;
        this.settingsModel = mainController.settingsModel;
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

        //returns a collection of the entities that contain the components in the family
        angels = engine.getEntitiesFor(Family.all(AngelComponent.class, BoundsComponent.class, TransformComponent.class, StateComponent.class).get());
        coins = engine.getEntitiesFor(Family.all(CoinComponent.class, BoundsComponent.class).get());
        drones = engine.getEntitiesFor(Family.all(DroneComponent.class, BoundsComponent.class).get());
        devils = engine.getEntitiesFor(Family.all(DevilComponent.class, BoundsComponent.class).get());
        obstacles = engine.getEntitiesFor(Family.all(ObstacleComponent.class, BoundsComponent.class, TransformComponent.class).get());
    }

    @Override
    public void update(float deltatime) {
        AngelSystem angelSystem = engine.getSystem(AngelSystem.class);
        ObstacleSystem obstacleSystem = engine.getSystem(ObstacleSystem.class);

        Entity angel = angels.get(0);

        MovementComponent angelMov = movementMapper.get(angel);
        BoundsComponent angelBounds = boundsMapper.get(angel);
        TransformComponent angelPos = transformMapper.get(angel);


        //if angel hits an obstacle, the player dies
        for (int j = 0; j < obstacles.size(); ++j) {
            Entity obstacle = obstacles.get(j);

            BoundsComponent obsBounds = boundsMapper.get(obstacle);

            Rectangle rec = new Rectangle();

            rec.x = (int) obsBounds.rectangle.x;
            rec.y = (int) (obsBounds.rectangle.y + Assets.balloons.first().getRegionHeight() * 2 / 3);
            rec.width = (int) obsBounds.rectangle.width;
            rec.height = (int) obsBounds.rectangle.height * 2/3;


            if (rec.overlaps(angelBounds.rectangle)) {
                if (settingsModel.soundOn()) {
                    collisionSound.play(0.05f);
                }
                angelSystem.hitObstacle(angel);
            }
        }

        //if angel hits a drone, the player dies
        for (int j = 0; j < drones.size(); ++j) {
            Entity drone = drones.get(j);

            BoundsComponent droneBounds = boundsMapper.get(drone);

            if (droneBounds.rectangle.overlaps(angelBounds.rectangle)) {
                if (settingsModel.soundOn()) {
                    collisionSound.play(0.05f);
                }
                angelSystem.hitDrone(angel);
            }
        }

        //if angel hits a devil, the player dies
        for (int j = 0; j < devils.size(); ++j) {
            Entity devil = devils.get(j);

            BoundsComponent devilBounds = boundsMapper.get(devil);

            if (devilBounds.rectangle.overlaps(angelBounds.rectangle)) {
                if (settingsModel.soundOn()) {
                    collisionSound.play(0.05f);
                }
                angelSystem.hitDrone(angel);
            }
        }

        //if angel hits a coin, the player gets points added to their score and the coin disappears
        for (int j = 0; j < coins.size(); ++j) {
            Entity coin = coins.get(j);

            BoundsComponent coinBounds = boundsMapper.get(coin);
            TransformComponent coinPos = transformMapper.get(coin);
            TextureComponent textureComponent = textureMapper.get(coin);

            if (coinBounds.rectangle.overlaps(angelBounds.rectangle)) {
                if (settingsModel.soundOn()) {
                    coinSound.play(0.2f);
                }
                angel.getComponent(AngelComponent.class).COINS_HIT += coin.getComponent(CoinComponent.class).SCORE;
                coinPos.pos.y = -textureComponent.textureRegion.getRegionHeight() - Gdx.graphics.getHeight() / 2;
                coinPos.pos.x = rand.nextInt(Gdx.graphics.getWidth() - textureComponent.textureRegion.getRegionWidth());
            }
        }
/*
        //when player hits a powerup, the powerup disappears and the player gets an advantage
        for (int j = 0; j < powerups.size(); ++j) {
            Entity powerup = powerups.get(j);

            BoundsComponent powerupBounds = boundsMapper.get(powerup);

            if (powerupBounds.rectangle.overlaps(angelBounds.rectangle)) {
                engine.removeEntity(powerup);
                angelSystem.hitPowerUp(angel);
                //listener.hitPU();
            }
        }
        }*/

    }
}
