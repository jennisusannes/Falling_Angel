package com.fallingangel.controller.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.fallingangel.game.FallingAngel;
import com.fallingangel.model.World;
import com.fallingangel.model.component.AngelComponent;
import com.fallingangel.model.component.MovementComponent;
import com.fallingangel.model.component.ObstacleComponent;
import com.fallingangel.model.component.StateComponent;
import com.fallingangel.model.component.TextureComponent;
import com.fallingangel.model.component.TransformComponent;
import com.fallingangel.view.GameView;


public class AngelSystem extends IteratingSystem {
    //IteratingSystem is s simple EntitySystem that iterates over each entity and calls processEntity() for each entity every time the EntitySystem is updated.

    private static final Family family = Family.all(AngelComponent.class, MovementComponent.class, StateComponent.class, TransformComponent.class).get();
    //Family is an ashley class and represents a group of components.
    //used to describe what entity objects an entitysystem should process

    private World world;

    private ComponentMapper<AngelComponent> angel_mapper; //makes it easy to get the component from the objects
    private ComponentMapper<MovementComponent> movement_mapper;
    private ComponentMapper<StateComponent> state_mapper;
    private ComponentMapper<TransformComponent> transform_mapper;

    private float accelX = 0.0f;
    public float xPos;


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


    public void update(float deltaTime){
        super.update(deltaTime);
        //0.0f so that the angel entity does not move if there is no new input
        accelX = 0.0f;
    }

    //method from IteratingSystem, the method is called every time the AngelSystem is updated
    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        //get the updated components
        AngelComponent angelComponent = angel_mapper.get(entity);
        MovementComponent movementComponent = movement_mapper.get(entity);
        StateComponent stateComponent = state_mapper.get(entity);
        TransformComponent transformComponent = transform_mapper.get(entity);

        //if the angel has hit an obstacle/plane, the game is over and the state of the world changes.
        if (stateComponent.state == angelComponent.STATE_HIT){
            world.state = world.WORLD_STATE_GAME_OVER;
        }

        //the move vector is changed to what comes from the input, and will be used to change the position of the angel
        movementComponent.move.x = - accelX;

        this.xPos = transformComponent.pos.x;
        //makes sure the angel does not leave the screen in the x-direction
        if (transformComponent.pos.x < 0){
            transformComponent.pos.x = 0;
        }

        if (transformComponent.pos.x > Gdx.graphics.getWidth() - AngelComponent.WIDTH){
            transformComponent.pos.x = Gdx.graphics.getWidth() - AngelComponent.WIDTH;
        }

        //updates the score
        angelComponent.AIRTIME += deltaTime;
        angelComponent.SCORE = angelComponent.AIRTIME + angelComponent.COINS_HIT;
        world.score = (int) angelComponent.SCORE;
    }


    public void hitObstacle(Entity entity){
        if (!family.matches(entity)) return; //to be sure that the entity matches the family requirements

        StateComponent state = state_mapper.get(entity);

        state.set(AngelComponent.STATE_HIT); //the state is changed to hit
    }

    public void hitDrone(Entity entity){
        if (!family.matches(entity)) return; //to be sure that the entity matches the family requirements

        StateComponent state = state_mapper.get(entity); //to get the updated components for this entity

        state.set(AngelComponent.STATE_HIT); //the state is changed to hit
    }

    //will be used when powerups are implemented
    public void hitPowerUp(Entity entity){
        if (!family.matches(entity)) return; //to be sure that the entity matches the family requirements

        StateComponent state = state_mapper.get(entity); //to get the updated components for this entity

        //what should happen when the angel hits a powerup
    }

}
