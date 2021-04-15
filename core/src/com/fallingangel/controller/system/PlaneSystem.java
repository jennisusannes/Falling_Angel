package com.fallingangel.controller.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.fallingangel.model.World;
import com.fallingangel.model.component.MovementComponent;
import com.fallingangel.model.component.PlaneComponent;
import com.fallingangel.model.component.TransformComponent;

import java.util.Random;

public class PlaneSystem extends IteratingSystem{

    private ComponentMapper<TransformComponent> tm;
    private ComponentMapper<MovementComponent> mm;
    private ComponentMapper<PlaneComponent> planeMapper;


    public PlaneSystem() {
        super(Family.all(PlaneComponent.class, TransformComponent.class, MovementComponent.class).get());


        tm = ComponentMapper.getFor(TransformComponent.class);
        mm = ComponentMapper.getFor(MovementComponent.class);
        planeMapper = ComponentMapper.getFor(PlaneComponent.class);


    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        TransformComponent transformComponent = tm.get(entity);
        MovementComponent movementComponent = mm.get(entity);
        PlaneComponent planeComponent = planeMapper.get(entity);


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

        transformComponent.pos.y += PlaneComponent.VELOCITY/3;
        transformComponent.pos.x += PlaneComponent.VELOCITY;

        /*
        if (transformComponent.pos.x < 0) {
            movementComponent.move.x = - movementComponent.move.x;
            transformComponent.pos.x = planeComponent.VELOCITY;
        }

        if (transformComponent.pos.x > Gdx.graphics.getWidth()) {

        }

        */

        // movementComponent.move.set(-planeComponent.VELOCITY, planeComponent.VELOCITY);

        Random rand = new Random();

        int low = Gdx.graphics.getHeight()/2;
        int high = Gdx.graphics.getHeight()*5/6;
        int area = rand.nextInt(high-low) + low;
/*
        if (transformComponent.pos.x > Gdx.graphics.getWidth()){
            transformComponent.pos.x = - planeComponent.WIDTH - Gdx.graphics.getWidth();
            transformComponent.pos.y = rand.nextInt(area);
        }*/


        //Tror disse to sier det samme:


        // if (t.scale.x == mov.move.x){
        //     t.scale.x = Math.abs(t.scale.x)* -1.0f
        //     }
        // else {
        //     t.scale.x = Math.abs(t.scale.x)
        //     }

        //transformComponent.scale.x = movementComponent.move.x < 0.0f ? Math.abs(transformComponent.scale.x) * -1.0f : Math.abs(transformComponent.scale.x);
    }

}
