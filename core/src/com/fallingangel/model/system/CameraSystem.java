package com.fallingangel.model.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import com.badlogic.gdx.graphics.OrthographicCamera;

import com.fallingangel.model.component.CameraComponent;
import com.fallingangel.model.component.TransformComponent;

public class CameraSystem extends IteratingSystem { //IteratingSystem iterates over each entity and calls processEntity() for each entity every time the EntitySystem is updated.

    //component mappers to get components from entities
    private ComponentMapper<TransformComponent> tm;
    private ComponentMapper<CameraComponent> cm;

    public CameraSystem() {
        //gets all entities with a CameraComponent
        super(Family.all(CameraComponent.class).get());

        //creates our component mappers
        tm = ComponentMapper.getFor(TransformComponent.class);
        cm = ComponentMapper.getFor(CameraComponent.class);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        CameraComponent cam = cm.get(entity);

        if (cam.target == null) {
            return;
        }

        TransformComponent target = tm.get(cam.target);

        if (target == null) {
            return;
        }

        cam.camera.position.y = Math.max(cam.camera.position.y, target.pos.y);
    }

}
