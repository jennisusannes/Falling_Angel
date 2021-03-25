package com.fallingangel.controller.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.fallingangel.model.component.AnimationComponent;
import com.fallingangel.model.component.StateComponent;
import com.fallingangel.model.component.TextureComponent;

public class AnimationSystem extends IteratingSystem {



    //introduce mappers to that it gets easier to get the component from the object
    private ComponentMapper<TextureComponent> texture_mapper;
    private ComponentMapper<AnimationComponent> animation_mapper;
    private ComponentMapper<StateComponent> state_mapper;

    public AnimationSystem() {

        super(Family.all(TextureComponent.class, AnimationComponent.class, StateComponent.class).get());
        //calls the constructor of the IteratingSystem
        //family is a group of the components, used to describe what entities the system should process

        //set the mappers (connect to the components)
        texture_mapper = ComponentMapper.getFor(TextureComponent.class);
        animation_mapper = ComponentMapper.getFor(AnimationComponent.class);
        state_mapper = ComponentMapper.getFor(StateComponent.class);
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) { //method from IteratingSystem
        //the method is called every time the AnimationSystem is updated

        //get the updated components
        TextureComponent texture = texture_mapper.get(entity);
        AnimationComponent animation = animation_mapper.get(entity);
        StateComponent state = state_mapper.get(entity);

        Animation anim = animation.animations.get(state.get()); //gets the animation that fits the current state

        if (anim != null) {
            texture.region = (TextureRegion) anim.getKeyFrame(state.time); //sets the texture to a part of that animation
        }

        state.time += deltaTime; //updates the time

    }
}
