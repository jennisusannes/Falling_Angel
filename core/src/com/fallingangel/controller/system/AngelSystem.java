package com.fallingangel.controller.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.fallingangel.model.World;
import com.fallingangel.model.component.AngelComponent;
import com.fallingangel.model.component.MovementComponent;
import com.fallingangel.model.component.ObstacleComponent;
import com.fallingangel.model.component.StateComponent;
import com.fallingangel.model.component.TransformComponent;

//GJLR OM TIL SAMME SPEED SOM OBSTACLES PÅ HITOBSTACLE


public class AngelSystem extends IteratingSystem {
    //IteratingSystem is s simple EntitySystem that iterates over each entity and calls processEntity() for each entity every time the EntitySystem is updated.

    private static final Family family = Family.all(AngelComponent.class, MovementComponent.class, StateComponent.class).get();
    //Family is an ashley class and represents a group of components.
    //used to describe what entity objects an entitysystem should process
    //the angel, movement, state and trans comp are now in the system

    private World world;

    private ComponentMapper<AngelComponent> angel_mapper; //makes it easy to get the component from the objects
    private ComponentMapper<MovementComponent> movement_mapper;
    private ComponentMapper<StateComponent> state_mapper;
    private ComponentMapper<TransformComponent> transform_mapper;

    public AngelSystem(com.fallingangel.model.World world){
        super(family); //calls the constructor of the IteratingSystem
        this.world = world;

        angel_mapper = ComponentMapper.getFor(AngelComponent.class); //sets angel, movement, state and transform comp
        movement_mapper = ComponentMapper.getFor(MovementComponent.class);
        state_mapper = ComponentMapper.getFor(StateComponent.class);
        transform_mapper = ComponentMapper.getFor(TransformComponent.class);
    }

    public void update(float deltaTime){
        super.update(deltaTime);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) { //method from IteratingSystem
        //the method is called every time the AngelSystem is updated

        //get the updated components
        AngelComponent angel = angel_mapper.get(entity);
        MovementComponent movement = movement_mapper.get(entity);
        StateComponent state = state_mapper.get(entity);
        TransformComponent transform = transform_mapper.get(entity);

        //if-setninger for å bytte state

        if (transform.pos.y == world.WORLD_HEIGHT){ //evt bounds?
            state.set(AngelComponent.STATE_DEAD);
            //DA MÅ DEN VARSLE OM DET PÅ EN MÅTE
        }

    }

    //DISSE STATESENE MÅ ENDRES TILBAKE PÅ ET TIDSPUNKT

    public void hitObstacle(Entity entity){
        if (!family.matches(entity)) return; //to be sure that the entity matches the family requirements

        StateComponent state = state_mapper.get(entity); //to get the updated components for this entity
        MovementComponent movement = movement_mapper.get(entity);

        movement.move.set(0, ObstacleComponent.VELOCITY); //it will move with the obstacle
        state.set(AngelComponent.STATE_HIT); //the state is changed to hit
    }

    public void hitPlane(Entity entity){
        if (!family.matches(entity)) return; //to be sure that the entity matches the family requirements

        StateComponent state = state_mapper.get(entity); //to get the updated components for this entity
        MovementComponent movement = movement_mapper.get(entity);

        movement.move.set(0, 0); //it can't keep falling down in the y-direction
        state.set(AngelComponent.STATE_HIT); //the state is changed to hit
    }

    public void hitPowerUp(Entity entity){
        if (!family.matches(entity)) return; //to be sure that the entity matches the family requirements

        StateComponent state = state_mapper.get(entity); //to get the updated components for this entity
        MovementComponent movement = movement_mapper.get(entity);

        //FOR ALLE OBSTACLES: (sjekk World)
            //ObstacleSystem.decreaseSpeed();
    }

    public void press(Entity angelEntity, int screenX, int screenY){
        //hva skjer når man trykker på skjermen eller holder inne en av musetastene
        Vector3 vector = new Vector3(screenX, Gdx.graphics.getHeight() - screenY, 0); //kan hende denne må flippes, litt usikker
        transform_mapper.get(angelEntity).pos.set(vector); //gets the comp of this entity and sets the position
    }

    public void drag(Entity angelEntity, int screenX, int screenY){
        //hva skjer når man drar med fingeren eller musen
        Vector3 vector = new Vector3(screenX, Gdx.graphics.getHeight() - screenY, 0); //kan hende denne må flippes, litt usikker
        transform_mapper.get(angelEntity).pos.set(vector); //gets the comp of this entity and sets the position
    }

    public void unpress(Entity angelEntity){ //kan kanskje kuttes
        //hva skjer når man slipper musen/piltastene
    }
}
