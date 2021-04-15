package com.fallingangel.controller.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
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

    //makes sure that the bounds are updated as the entities are moving
    @Override
    public void processEntity(Entity entity, float deltaTime) {

        TransformComponent transformComponent = transform_mapper.get(entity);
        BoundsComponent boundsComponent = bounds_mapper.get(entity);

        //the x- and y-coordinates are the bottom left corner of the rectangle
        /*boundsComponent.rectangle.x = transformComponent.pos.x - boundsComponent.rectangle.width * 0.5f;
        boundsComponent.rectangle.y = transformComponent.pos.y - boundsComponent.rectangle.height * 0.5f;*/
        boundsComponent.rectangle.x = transformComponent.pos.x;
        boundsComponent.rectangle.y = transformComponent.pos.y;
    }
}
