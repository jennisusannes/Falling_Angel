package com.fallingangel.model.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.World;
import com.fallingangel.model.component.AngelComponent;
import com.fallingangel.model.component.MovementComponent;
import com.fallingangel.model.component.StateComponent;
import com.fallingangel.model.component.TransformComponent;

public class AngelSystem extends IteratingSystem {
    //IteratingSystem is s simple EntitySystem that iterates over each entity and calls processEntity() for each entity every time the EntitySystem is updated.

    private static final Family family = Family.all(AngelComponent.class, MovementComponent.class, StateComponent.class).get();
    //Family is an ashley class and represents a group of components.
    //used to describe what entity objects an entitysystem should process
    //the angel, movement, state and trans comp are now in the system

    private float accelY = 0.0f;
    private World world;

    private ComponentMapper<AngelComponent> angel_mapper; //makes it easy to get the component from the objects
    private ComponentMapper<MovementComponent> movement_mapper;
    private ComponentMapper<StateComponent> state_mapper;
    private ComponentMapper<TransformComponent> transform_mapper;

    public AngelSystem(World world){
        super(family); //calls the constructor of the IteratingSystem
        this.world = world;

        angel_mapper = ComponentMapper.getFor(AngelComponent.class); //sets angel, movement, state and transform comp
        movement_mapper = ComponentMapper.getFor(MovementComponent.class);
        state_mapper = ComponentMapper.getFor(StateComponent.class);
        transform_mapper = ComponentMapper.getFor(TransformComponent.class);
    }

    public void setAccelY(float accelY){
        this.accelY = accelY;
    }

    public void update(float deltaTime){
        super.update(deltaTime);
        accelY = 0.0f;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) { //method from IteratingSystem
        //the method is called every time the AngelSystem is updated

        //get the updated components
        AngelComponent angel = angel_mapper.get(entity);
        MovementComponent movement = movement_mapper.get(entity);
        StateComponent state = state_mapper.get(entity);
        TransformComponent transform = transform_mapper.get(entity);

        //see what methods should be called/ what changes should happen to the angel

        if (state.get() != AngelComponent.STATE_HIT && transform.pos.y <= 0.5f){ //if the angel is falling and ???
            hitObstacle(entity);
        }

        if (state.get() != AngelComponent.STATE_HIT){ //if the angel is falling
            movement.move.y = -accelY / 10.0f * AngelComponent.MOVE_VELOCITY; //DENNE MÅ NOK FIKSES PÅ
        }

        if (movement.move.y < 0){
            state.set(AngelComponent.STATE_FALL);
        }

    }

    public void hitObstacle(Entity entity){
        if (!family.matches(entity)) return; //to be sure that the entity matches the family requirements

        StateComponent state = state_mapper.get(entity); //to get the updated components for this entity
        MovementComponent movement = movement_mapper.get(entity);

        movement.move.set(0, 0); //the velocity is set to zero
        state.set(AngelComponent.STATE_HIT); //the state is changed to hit
    }

    public void hitPlane(Entity entity){

    }
}
