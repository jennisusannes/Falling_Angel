package com.fallingangel.model.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.fallingangel.model.component.MovementComponent;
import com.fallingangel.model.component.TransformComponent;

public class MovementSystem extends IteratingSystem {

    private Vector2 tmp = new Vector2();

    private ComponentMapper<TransformComponent> tm;
    private ComponentMapper<MovementComponent> mm;

    public MovementSystem() {
        super(Family.all(TransformComponent.class, MovementComponent.class).get());

        tm = ComponentMapper.getFor(TransformComponent.class);
        mm = ComponentMapper.getFor(MovementComponent.class);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        TransformComponent pos = tm.get(entity);
        MovementComponent mov = mm.get(entity);;

        tmp.set(mov.accel).scl(deltaTime);
        mov.move.add(tmp);

        tmp.set(mov.move).scl(deltaTime);
        pos.pos.add(tmp.x, tmp.y, 0.0f);
    }

}
