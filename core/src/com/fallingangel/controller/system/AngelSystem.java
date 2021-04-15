package com.fallingangel.controller.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.fallingangel.model.World;
import com.fallingangel.model.component.AngelComponent;
import com.fallingangel.model.component.MovementComponent;
import com.fallingangel.model.component.ObstacleComponent;
import com.fallingangel.model.component.StateComponent;
import com.fallingangel.model.component.TextureComponent;
import com.fallingangel.model.component.TransformComponent;
import com.fallingangel.view.GameView;

//GJLR OM TIL SAMME SPEED SOM OBSTACLES PÅ HITOBSTACLE


public class AngelSystem extends IteratingSystem {
    //IteratingSystem is s simple EntitySystem that iterates over each entity and calls processEntity() for each entity every time the EntitySystem is updated.

    private static final Family family = Family.all(AngelComponent.class, MovementComponent.class, StateComponent.class, TransformComponent.class).get();
    //Family is an ashley class and represents a group of components.
    //used to describe what entity objects an entitysystem should process
    //the angel, movement, state and trans comp are now in the system

    private World world;

    private ComponentMapper<AngelComponent> angel_mapper; //makes it easy to get the component from the objects
    private ComponentMapper<MovementComponent> movement_mapper;
    private ComponentMapper<StateComponent> state_mapper;
    private ComponentMapper<TransformComponent> transform_mapper;

    private float accelX = 0.0f;

    private float timeCount = 0;

    public AngelSystem(com.fallingangel.model.World world){
        super(family); //calls the constructor of the IteratingSystem. Creates an entity system that iterates over each entity and calls processEntity()
        this.world = world;

        angel_mapper = ComponentMapper.getFor(AngelComponent.class); //sets angel, movement, state and transform comp mappers
        movement_mapper = ComponentMapper.getFor(MovementComponent.class);
        state_mapper = ComponentMapper.getFor(StateComponent.class);
        transform_mapper = ComponentMapper.getFor(TransformComponent.class);
    }

    public void setAccelX(float accelX){
        this.accelX = accelX;
    }


    public void update(float deltaTime){ //makes sure the IteratingSystem is updated as well
        super.update(deltaTime);
        accelX = 0.0f;
        //0.0f so that the angel entity does not move if there is no new input
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) { //method from IteratingSystem
        //the method is called every time the AngelSystem is updated

        //get the updated components
        AngelComponent angelComponent = angel_mapper.get(entity);
        MovementComponent movementComponent = movement_mapper.get(entity);
        StateComponent stateComponent = state_mapper.get(entity);
        TransformComponent transformComponent = transform_mapper.get(entity);

        //if the angel has hit an obstacle/plane, the game is over and the state of the world changes.
        /*if (stateComponent.state == angelComponent.STATE_HIT){
            world.state = world.WORLD_STATE_GAME_OVER;
        }*/

        movementComponent.move.x = - accelX;

        if (transformComponent.pos.x < 0){
            transformComponent.pos.x = 0;
        }

        if (transformComponent.pos.x > Gdx.graphics.getWidth() - AngelComponent.WIDTH){
            transformComponent.pos.x = Gdx.graphics.getWidth() - AngelComponent.WIDTH;
        }
        //Each entity's move vector should be changed by the accelX from GameView
        //movementComponent.move.x = - accelX;
        //TODO: implementere samme logikk som Brent Aureli i Supermario Bro's

        angelComponent.AIRTIME += deltaTime;
        angelComponent.SCORE += deltaTime;

    }

    //DISSE STATESENE MÅ ENDRES TILBAKE PÅ ET TIDSPUNKT

    public void hitObstacle(Entity entity){
        if (!family.matches(entity)) return; //to be sure that the entity matches the family requirements

        StateComponent state = state_mapper.get(entity); //to get the updated components for this entity
        MovementComponent movement = movement_mapper.get(entity);

        //movement.move.set(0, 0);
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

    public void handleInput(Entity entity){
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)){
            float x = transform_mapper.get(entity).pos.x;
            float y = transform_mapper.get(entity).pos.y;
            float z = transform_mapper.get(entity).pos.z;
            transform_mapper.get(entity).pos.set(x-2f, y, z);
        }
    }

    public void press(Entity angelEntity, int screenX, int screenY){
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) accelX = 0.5f;
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) accelX = -0.5f;
        setAccelX(accelX);


        //Called when the screen was touched or a mouse button was pressed.
        //Vector3 vector = new Vector3(screenX, Gdx.graphics.getHeight() - screenY, 0); //kan hende denne må flippes, litt usikker
        //transform_mapper.get(angelEntity).pos.set(vector); //gets the comp of this entity and sets the position
    }

    public void drag(Entity angelEntity, int screenX, int screenY){
        //Called when a finger or the mouse was dragged.
        Vector3 vector = new Vector3(screenX, Gdx.graphics.getHeight() - screenY, 0); //kan hende denne må flippes, litt usikker
        transform_mapper.get(angelEntity).pos.set(vector); //gets the comp of this entity and sets the position
    }

    public void unpress(Entity angelEntity){ //kan kanskje kuttes
        //hva skjer når man slipper musen/piltastene
    }


}
