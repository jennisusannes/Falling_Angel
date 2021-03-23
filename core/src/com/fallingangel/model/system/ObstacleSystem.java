package com.fallingangel.model.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.fallingangel.model.World;
import com.fallingangel.model.component.MovementComponent;
import com.fallingangel.model.component.ObstacleComponent;
import com.fallingangel.model.component.TransformComponent;
import com.fallingangel.model.component.StateComponent;

public class ObstacleSystem extends IteratingSystem {

    //should process all entities in Obstacle-, State-, Transform- and MovementComponent
    private static final Family family = Family.all(ObstacleComponent.class,
            StateComponent.class,
            TransformComponent.class,
            MovementComponent.class).get(); //usikker på om vi trenger state fordi hinderene har vel bare en state, og usikker på transform

    private Enigne enigne;

    public ObstacleSystem () {
        super(family);
        private ComponentMapper<TransformComponent> tm;
        private ComponentMapper<MovementComponent> mm;
        private ComponentMapper<ObstacleComponent> om;
        private ComponentMapper<StateComponent> sm; //trenger kanskje ikke denne

    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        this.engine = engine;
    }

    //usikker på om vi trenger denne og hva den evt gjør.
    //I superjumper finnes det ulike platformer (bevegelige og noen som går i oppløsning) og det er det denne sjekker der
    @Override
    public void processEntity(Entity entity, float deltaTime) {
        ObstacleComponent obstacle = om.get(entity);


    }
















}
