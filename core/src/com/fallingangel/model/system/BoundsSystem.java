package com.fallingangel.model.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.fallingangel.model.component.BoundsComponent;
import com.fallingangel.model.component.TransformComponent;

public class BoundsSystem extends IteratingSystem {

    //introduce mappers to that it gets easier to get the component from the object
    private ComponentMapper<TransformComponent> transform_mapper;
    private ComponentMapper<BoundsComponent> bounds_mapper;

    public BoundsSystem() {
        super(Family.all(BoundsComponent.class, TransformComponent.class).get()); //calls constructor of IteratingSystem

        transform_mapper = ComponentMapper.getFor(TransformComponent.class); //connect comp to mappers
        bounds_mapper = ComponentMapper.getFor(BoundsComponent.class);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) { //makes sure that the bounds are updated
        TransformComponent transform = transform_mapper.get(entity);
        BoundsComponent bounds = bounds_mapper.get(entity);

        bounds.bounds.x = transform.pos.x - bounds.bounds.width * 0.5f;
        bounds.bounds.y = transform.pos.y - bounds.bounds.height * 0.5f;
    }
}
