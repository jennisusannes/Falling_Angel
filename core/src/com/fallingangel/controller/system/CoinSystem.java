package com.fallingangel.controller.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.fallingangel.model.component.AnimationComponent;
import com.fallingangel.model.component.CoinComponent;
import com.fallingangel.model.component.MovementComponent;
import com.fallingangel.model.component.ObstacleComponent;
import com.fallingangel.model.component.StateComponent;
import com.fallingangel.model.component.TextureComponent;
import com.fallingangel.model.component.TransformComponent;

import java.util.Random;

public class CoinSystem extends IteratingSystem {

    //should process all entities that contains CoinComponent
    private static final Family family = Family.all(CoinComponent.class, TextureComponent.class, TransformComponent.class, AnimationComponent.class, StateComponent.class).get();

    private Engine engine;
    private ComponentMapper<TransformComponent> transformMapper;
    private ComponentMapper<TextureComponent> textureMapper;

    public static Random rand = new Random();

    public CoinSystem () {
        super(family);

        transformMapper = ComponentMapper.getFor(TransformComponent.class);
        textureMapper = ComponentMapper.getFor(TextureComponent.class);
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        this.engine = engine;
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        TransformComponent transformComponent = transformMapper.get(entity);
        TextureComponent textureComponent = textureMapper.get(entity);

        transformComponent.pos.y += 4.0f;

        //reposition if the coin has left the screen
        if (transformComponent.pos.y > Gdx.graphics.getHeight()){
            transformComponent.pos.y = - textureComponent.textureRegion.getRegionHeight() - Gdx.graphics.getHeight()/2;
            transformComponent.pos.x = rand.nextInt(Gdx.graphics.getWidth() - textureComponent.textureRegion.getRegionWidth());
        }

    }
}
