package com.fallingangel.controller.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.fallingangel.model.component.BoundsComponent;
import com.fallingangel.model.component.TextureComponent;
import com.fallingangel.model.component.TransformComponent;

public class BoundsSystem extends IteratingSystem {

    //introduce mappers to that it gets easier to get the component from the object
    private ComponentMapper<TransformComponent> transform_mapper;
    private ComponentMapper<BoundsComponent> bounds_mapper;
    private ComponentMapper<TextureComponent> texture_mapper;


    public BoundsSystem() {
        super(Family.all(BoundsComponent.class, TransformComponent.class).get()); //calls constructor of IteratingSystem

        //connect comp to mappers
        transform_mapper = ComponentMapper.getFor(TransformComponent.class);
        bounds_mapper = ComponentMapper.getFor(BoundsComponent.class);
        texture_mapper = ComponentMapper.getFor(TextureComponent.class);
    }

    //makes sure that the bounds are updated as the entities are moving
    @Override
    public void processEntity(Entity entity, float deltaTime) {

        TransformComponent transformComponent = transform_mapper.get(entity);
        BoundsComponent boundsComponent = bounds_mapper.get(entity);
        TextureComponent textureComponent = texture_mapper.get(entity);

        //the x- and y-coordinates are the bottom left corner of the rectangle
        boundsComponent.rectangle.x = transformComponent.pos.x;
        boundsComponent.rectangle.y = transformComponent.pos.y;
        boundsComponent.rectangle.width = textureComponent.textureRegion.getRegionWidth();
        boundsComponent.rectangle.height = textureComponent.textureRegion.getRegionHeight();
    }
}
