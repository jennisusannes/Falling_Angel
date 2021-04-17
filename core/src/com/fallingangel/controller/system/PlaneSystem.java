package com.fallingangel.controller.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.fallingangel.model.component.MovementComponent;
import com.fallingangel.model.component.PlaneComponent;
import com.fallingangel.model.component.TextureComponent;
import com.fallingangel.model.component.TransformComponent;

import java.util.Random;

public class PlaneSystem extends IteratingSystem{

    private ComponentMapper<TransformComponent> transformMapper;
    private ComponentMapper<MovementComponent> movementMapper;
    private ComponentMapper<PlaneComponent> planeMapper;
    private ComponentMapper<TextureComponent> textureMapper;


    public PlaneSystem() {
        super(Family.all(PlaneComponent.class, TransformComponent.class, MovementComponent.class).get());


        transformMapper = ComponentMapper.getFor(TransformComponent.class);
        movementMapper = ComponentMapper.getFor(MovementComponent.class);
        planeMapper = ComponentMapper.getFor(PlaneComponent.class);
        textureMapper = ComponentMapper.getFor(TextureComponent.class);

    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        TransformComponent transformComponent = transformMapper.get(entity);
        MovementComponent movementComponent = movementMapper.get(entity);
        PlaneComponent planeComponent = planeMapper.get(entity);
        TextureComponent textureComponent = textureMapper.get(entity);

        transformComponent.pos.y += planeComponent.VELOCITY * 2;
        transformComponent.pos.x += planeComponent.SPEED;

        if (transformComponent.pos.y > Gdx.graphics.getHeight()){
            transformComponent.pos.y = Gdx.graphics.getHeight() *2;
        }

        if (transformComponent.pos.x <= 0 || transformComponent.pos.x >= Gdx.graphics.getWidth() - textureComponent.textureRegion.getRegionWidth()){
            planeComponent.SPEED = - planeComponent.SPEED;
        }


    /*
    public void reposition(Entity entity) {
        TransformComponent transformComponent = transformMapper.get(entity);
        Random rand = new Random();
        //Generates a random area at the top of the screen, where the plane should come out
        int low = Gdx.graphics.getHeight()*2/3;
        int high = Gdx.graphics.getHeight()*3/4;
        int randomY = rand.nextInt(high-low) + low;
        //Random number so the planes will come at random times (must *randomnumber when creating a plane)
        //int randomNumber = rand.nextInt(10);
        int a = 0;
        int b = Gdx.graphics.getWidth();
        int randomPick = rand.nextBoolean() ? a : b;
        if (randomPick == a) {
            transformComponent.pos.x = -b;
            transformComponent.pos.y = randomY;
        }
        if (randomPick == b){
            transformComponent.pos.x = 2*b;
            transformComponent.pos.y = randomY;

    }*/


        //TODO: Endre logikken her: Flyvende hinder skal bare fly "igjennom" skjermen, og ikke snu"

        /*
        if (transformComponent.pos.x < PlaneComponent.WIDTH * 0.5f) {
            transformComponent.pos.x = PlaneComponent.WIDTH * 0.5f;
            transformComponent.pos.x += PlaneComponent.VELOCITY;
        }
        if (transformComponent.pos.x > World.WORLD_WIDTH - PlaneComponent.WIDTH * 0.5f) {
            transformComponent.pos.x = World.WORLD_WIDTH - PlaneComponent.WIDTH * 0.5f;
            transformComponent.pos.x -= PlaneComponent.VELOCITY;
        }
         */
        //bruke en speed som enten er negativ eller positiv

        //transformComponent.pos.y += PlaneComponent.VELOCITY/3;
        //transformComponent.pos.x += PlaneComponent.VELOCITY;

        //The plane has a positive velocity when it flies from the left of the screen, and a negative velocity from the right


        /*
        if (transformComponent.pos.x < 0) {
            movementComponent.move.x = - movementComponent.move.x;
            transformComponent.pos.x = planeComponent.VELOCITY;
        }

        if (transformComponent.pos.x > Gdx.graphics.getWidth()) {

        }

        */

        // movementComponent.move.set(-planeComponent.VELOCITY, planeComponent.VELOCITY);
/*
        Random rand = new Random();

        int low = Gdx.graphics.getHeight()/2;
        int high = Gdx.graphics.getHeight()*5/6;
        int area = rand.nextInt(high-low) + low;*/
/*
        if (transformComponent.pos.x > Gdx.graphics.getWidth()){
            transformComponent.pos.x = - planeComponent.WIDTH - Gdx.graphics.getWidth();
            transformComponent.pos.y = rand.nextInt(area);
        }*/


    }

}
