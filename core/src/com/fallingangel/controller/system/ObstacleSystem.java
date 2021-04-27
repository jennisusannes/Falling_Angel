package com.fallingangel.controller.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import com.badlogic.gdx.Gdx;
import com.fallingangel.model.component.MovementComponent;
import com.fallingangel.model.component.ObstacleComponent;
import com.fallingangel.model.component.TextureComponent;
import com.fallingangel.model.component.TransformComponent;

import java.util.Random;

public class ObstacleSystem extends IteratingSystem {

    //should process all entities in Obstacle-, Transform- and MovementComponent
    private static final Family family = Family.all(ObstacleComponent.class,
            TransformComponent.class,
            MovementComponent.class).get();

    private Engine engine;
    private ComponentMapper<TransformComponent> tm;
    private ComponentMapper<MovementComponent> mm;
    private ComponentMapper<ObstacleComponent> om;
    private ComponentMapper<TextureComponent> textureMapper;

    public static Random rand = new Random();

    public ObstacleSystem () {
        super(family);

        tm = ComponentMapper.getFor(TransformComponent.class);
        mm = ComponentMapper.getFor(MovementComponent.class);
        om = ComponentMapper.getFor(ObstacleComponent.class);
        textureMapper = ComponentMapper.getFor(TextureComponent.class);

    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        this.engine = engine;
    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        ObstacleComponent obstacle = om.get(entity);
        MovementComponent movementComponent = mm.get(entity);
        TransformComponent transformComponent = tm.get(entity);
        TextureComponent textureComponent = textureMapper.get(entity);

        transformComponent.pos.y += 4.0f;

        if (transformComponent.pos.y > Gdx.graphics.getHeight()){
            transformComponent.pos.y = - textureComponent.textureRegion.getRegionHeight() - Gdx.graphics.getHeight()/2;
            transformComponent.pos.x = rand.nextInt(Gdx.graphics.getWidth() - textureComponent.textureRegion.getRegionWidth());
        }

    }

}
