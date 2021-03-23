package com.fallingangel.model.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.fallingangel.model.component.BackgroundComponent;
import com.fallingangel.model.component.TextureComponent;
import com.fallingangel.model.component.TransformComponent;

public class BackgroundSystem extends IteratingSystem {
    //IteratingSystem is s simple EntitySystem that iterates over each entity and calls processEntity() for each entity every time the EntitySystem is updated.

    private OrthographicCamera camera;
    private ComponentMapper<TransformComponent> transform_mapper;

    public BackgroundSystem() {
        super(Family.all(BackgroundComponent.class).get()); //calls constructor of IteratingSystem
        transform_mapper = ComponentMapper.getFor(TransformComponent.class);
    }

    public void setCamera(OrthographicCamera camera){
        this.camera = camera;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        //the method is called every time the BackgroundSystem is updated
        TransformComponent transform = transform_mapper.get(entity); //gets the updates trans comp
        transform.pos.set(camera.position.x, camera.position.y, 10.0f);
    }
}
