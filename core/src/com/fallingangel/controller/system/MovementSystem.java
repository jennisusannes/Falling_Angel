package com.fallingangel.controller.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.fallingangel.model.component.MovementComponent;
import com.fallingangel.model.component.TransformComponent;

public class MovementSystem extends IteratingSystem {

    public Vector2 tmp;

    private ComponentMapper<TransformComponent> transformMapper;
    private ComponentMapper<MovementComponent> movementMapper;

    public MovementSystem() {
        super(Family.all(TransformComponent.class, MovementComponent.class).get());

        transformMapper = ComponentMapper.getFor(TransformComponent.class);
        movementMapper = ComponentMapper.getFor(MovementComponent.class);

        tmp = new Vector2();
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        TransformComponent transformComponent = transformMapper.get(entity);
        MovementComponent movementComponent = movementMapper.get(entity);;

        /*tmp.set(movementComponent.accel).scl(deltaTime);
        movementComponent.move.add(tmp);*/

        //Scales the move-vector by deltaTime (dt is the scalar) and adds the coordinates to that the position changes
        tmp.set(movementComponent.move);
        transformComponent.pos.add(tmp.x, tmp.y, 0.0f);

        //transformComponent.pos.add(tmp.x, tmp.y, 0.0f);

    }

}
