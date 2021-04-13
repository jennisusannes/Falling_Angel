package com.fallingangel.controller.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.fallingangel.model.component.CoinComponent;
import com.fallingangel.model.component.MovementComponent;
import com.fallingangel.model.component.ObstacleComponent;
import com.fallingangel.model.component.TextureComponent;
import com.fallingangel.model.component.TransformComponent;

import java.util.Random;

public class CoinSystem extends IteratingSystem {

    //should process all entities in Coin-, Transform- and MovementComponent
    private static final Family family = Family.all(CoinComponent.class).get();

    private Engine engine;
    private ComponentMapper<TransformComponent> tm;
    private ComponentMapper<MovementComponent> mm;
    private ComponentMapper<ObstacleComponent> om;
    private ComponentMapper<TextureComponent> textureMapper;

    public static Random rand = new Random();

    public CoinSystem () {
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

    //litt usikker på om vi trenger denne og hva den evt gjør.
    //svar: den bare sørger for at obstaclen er oppdatert til enhver tid, denne metoden kjøres hver gang systemet oppdateres
    //metoden er felles for mange av system-klassene, kommer fra IteratingSystem
    @Override
    public void processEntity(Entity entity, float deltaTime) {
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
