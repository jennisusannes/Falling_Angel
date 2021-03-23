package com.fallingangel.model.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.fallingangel.model.World;
import com.fallingangel.model.component.MovementComponent;
import com.fallingangel.model.component.PlaneComponent;
import com.fallingangel.model.component.TransformComponent;

public class PlaneSystem extends IteratingSystem{

    private ComponentMapper<TransformComponent> tm;
    private ComponentMapper<MovementComponent> mm;

    public PlaneSystem() {
        super(Family.all(PlaneComponent.class, TransformComponent.class, MovementComponent.class).get());


        tm = ComponentMapper.getFor(TransformComponent.class);
        mm = ComponentMapper.getFor(MovementComponent.class);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        TransformComponent t = tm.get(entity);
        MovementComponent mov = mm.get(entity);


        //TODO: Endre logikken her: Flyvende hinder skal bare fly "igjennom" skjærmen, og ikke snu"

        if (t.pos.x < PlaneComponent.WIDTH * 0.5f) {
            t.pos.x = PlaneComponent.WIDTH * 0.5f;
            mov.move.x = PlaneComponent.VELOCITY;
        }
        if (t.pos.x > World.WORLD_WIDTH - PlaneComponent.WIDTH * 0.5f) {
            t.pos.x = World.WORLD_WIDTH - PlaneComponent.WIDTH * 0.5f;
            mov.move.x = -PlaneComponent.VELOCITY;
        }


        //Tror disse to sier det samme:


        // if (t.scale.x == mov.move.x){
        //     t.scale.x = Math.abs(t.scale.x)* -1.0f
        //     }
        // else {
        //     t.scale.x = Math.abs(t.scale.x)
        //     }

        t.scale.x = mov.move.x < 0.0f ? Math.abs(t.scale.x) * -1.0f : Math.abs(t.scale.x);
    }

}
