package com.fallingangel.model.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.fallingangel.model.component.StateComponent;

public class StateSystem extends IteratingSystem {

    private ComponentMapper<StateComponent> state_mapper;

    public StateSystem() {
        super(Family.all(StateComponent.class).get());

        state_mapper = ComponentMapper.getFor(StateComponent.class);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        state_mapper.get(entity).time += deltaTime;
    }

}
