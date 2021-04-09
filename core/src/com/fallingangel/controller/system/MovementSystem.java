package com.fallingangel.controller.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.fallingangel.model.component.MovementComponent;
import com.fallingangel.model.component.TransformComponent;

public class MovementSystem extends IteratingSystem {

    public Vector2 tmp = new Vector2();

    private ComponentMapper<TransformComponent> transformMapper;
    private ComponentMapper<MovementComponent> movementMapper;

    public MovementSystem() {
        super(Family.all(TransformComponent.class, MovementComponent.class).get());

        transformMapper = ComponentMapper.getFor(TransformComponent.class);
        movementMapper = ComponentMapper.getFor(MovementComponent.class);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        TransformComponent transformComponent = transformMapper.get(entity);
        MovementComponent movementComponent = movementMapper.get(entity);;
/*
        tmp.set(movementComponent.accel).scl(deltaTime);
        movementComponent.move.add(tmp);*/

        //Scales the move-vector by deltaTime (dt is the scalar) and adds the coordinates to that the position changes
        tmp.set(movementComponent.move).scl(deltaTime);
        transformComponent.pos.add(tmp.x, tmp.y, 0.0f);
    }

}
