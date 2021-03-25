package com.fallingangel.controller.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import com.fallingangel.model.component.MovementComponent;
import com.fallingangel.model.component.ObstacleComponent;
import com.fallingangel.model.component.TransformComponent;

public class ObstacleSystem extends IteratingSystem {

    //should process all entities in Obstacle-, Transform- and MovementComponent
    private static final Family family = Family.all(ObstacleComponent.class,
            TransformComponent.class,
            MovementComponent.class).get();

    private Engine engine;
    private ComponentMapper<TransformComponent> tm;
    private ComponentMapper<MovementComponent> mm;
    private ComponentMapper<ObstacleComponent> om;

    public ObstacleSystem () {
        super(family);

        tm = ComponentMapper.getFor(TransformComponent.class);
        mm = ComponentMapper.getFor(MovementComponent.class);
        om = ComponentMapper.getFor(ObstacleComponent.class);

    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        this.engine = engine;
    }

    //litt usikker på om vi trenger denne og hva den evt gjør.
    //svar: den bare sørger for at obstaclen er oppdatert til enhver tid, denne metoden kjøres hver gang systemet oppdateres
    //metoden er felles for mange av system-klassene, kommer fra IteratingSystem
    @Override
    public void processEntity(Entity entity, float deltaTime) {
        ObstacleComponent obstacle = om.get(entity);
    }

    /*
    public void decreaseSpeed() { //trenger denne i angelsystem sin hitpowerUp-metode, men må fikses
        mm.move.set(0, om.VELOCITY / 2);
    }

     */






    /* TODO: implementere logikk ala flappy
    public void update(float dt) {
            handleInput();
            updateGround();
            bird.update(dt);
            cam.position.set(bird.getX() + 80, cam.viewportHeight / 2, 0);
            for(Tube tube : tubes){
                if(cam.position.x - cam.viewportWidth / 2 > tube.getPosTopTube().x + tube.getTopTube().getWidth()){
                    tube.reposition(tube.getPosTopTube().x +((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
                }

                if(tube.collides(bird.getBounds())){
                    bird.colliding = true;
                    gameover = true;
                }
            }
            if(bird.getY() <= ground.getHeight() + GROUND_Y_OFFSET){
                gameover = true;
                bird.colliding = true;
            }
            cam.update();
        }

 */









}
