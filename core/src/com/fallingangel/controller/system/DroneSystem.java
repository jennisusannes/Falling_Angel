package com.fallingangel.controller.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.fallingangel.model.component.MovementComponent;
import com.fallingangel.model.component.DroneComponent;
import com.fallingangel.model.component.TextureComponent;
import com.fallingangel.model.component.TransformComponent;

public class DroneSystem extends IteratingSystem{

    private ComponentMapper<TransformComponent> transformMapper;
    private ComponentMapper<MovementComponent> movementMapper;
    private ComponentMapper<DroneComponent> droneMapper;
    private ComponentMapper<TextureComponent> textureMapper;


    public DroneSystem() {
        super(Family.all(DroneComponent.class, TransformComponent.class, MovementComponent.class).get());


        transformMapper = ComponentMapper.getFor(TransformComponent.class);
        movementMapper = ComponentMapper.getFor(MovementComponent.class);
        droneMapper = ComponentMapper.getFor(DroneComponent.class);
        textureMapper = ComponentMapper.getFor(TextureComponent.class);

    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        TransformComponent transformComponent = transformMapper.get(entity);
        DroneComponent planeComponent = droneMapper.get(entity);
        TextureComponent textureComponent = textureMapper.get(entity);

        //changes the position for both x and y
        transformComponent.pos.y += planeComponent.VELOCITY * 2;
        transformComponent.pos.x += planeComponent.SPEED;

        //reposition when it leaves the screen
        if (transformComponent.pos.y > Gdx.graphics.getHeight()){
            transformComponent.pos.y = - Gdx.graphics.getHeight() * 2;
        }

        //change direction when it hits the edges of the screen
        if (transformComponent.pos.x <= 0 || transformComponent.pos.x >= Gdx.graphics.getWidth() - textureComponent.textureRegion.getRegionWidth()){
            planeComponent.SPEED = - planeComponent.SPEED;
        }
    }

}
