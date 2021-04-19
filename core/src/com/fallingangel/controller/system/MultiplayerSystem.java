package com.fallingangel.controller.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.fallingangel.backend.MultiPlayerData;
import com.fallingangel.game.FallingAngel;
import com.fallingangel.model.component.AngelComponent;
import com.fallingangel.model.component.MovementComponent;
import com.fallingangel.model.component.TransformComponent;

import java.util.ArrayList;

public class MultiplayerSystem extends IntervalSystem {

    public static String roomNumber = "";
    public Engine engine;

    private ArrayList<Entity> playerEntities = new ArrayList<>();

    private ComponentMapper<AngelComponent> angelMapper = ComponentMapper.getFor(AngelComponent.class);
    private ComponentMapper<TransformComponent> transformMapper = ComponentMapper.getFor(TransformComponent.class);

    public MultiplayerSystem(int priority){
        super(0.5f, priority);
    }

    @Override
    public void addedToEngine(final Engine engine) {
        this.engine = engine;
        Family family = Family.all(AngelComponent.class).get();
        for (Entity entity : engine.getEntitiesFor(family)){
            playerEntities.add(entity);
        }

        //adds an entitylistener so that it gets notifyed when an entity is removed
        engine.addEntityListener(family, new EntityListener() {
            @Override
            public void entityAdded(Entity entity) {
                playerEntities.add(entity);

            }

            @Override
            public void entityRemoved(Entity entity) {
                playerEntities.remove(entity);
            }
        });
    }


    @Override
    protected void updateInterval () {
        for (Entity entity : playerEntities){
            AngelComponent angelComponent = angelMapper.get(entity);
            TransformComponent transformComponent = transformMapper.get(entity);

            //MultiPlayerData multiPlayerData = new MultiPlayerData();
            MultiPlayerData mpd = FallingAngel.getInstance().mc.multiPlayerView.multiPlayerData;
            mpd.score = (int) (angelComponent.SCORE);
            //FallingAngel.getInstance().FBI.connectToRoom(roomNumber, mpd);
            FallingAngel.getInstance().FBI.updateScore(mpd.score);
            FallingAngel.getInstance().FBI.setOpponentScore();
            FallingAngel.getInstance().FBI.numberOfUsersInRoom();
        }
    }
}
