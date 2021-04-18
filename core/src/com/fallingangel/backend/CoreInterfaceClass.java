package com.fallingangel.backend;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.fallingangel.model.World;

public class CoreInterfaceClass implements FireBaseInterface{
    @Override
    public void connect() {

    }

    @Override
    public void createUser(String UID, String mail, String username, String password) {

    }

    @Override
    public void setHighScore(String UID, String username, String date, int score) {

    }
/*
    @Override
    public void addFriend(String UID, String friendUsername) {

    }*/

    @Override
    public void connectToRoom(String roomName) {

    }

    @Override
    public void updateMultiplayer(MultiPlayerData multiPlayerData) {

    }

    @Override
    public void setOnValueChangedListener() {
        
    }

    @Override
    public void createWorldInDB(ImmutableArray<Entity> entities) {

    }

}
