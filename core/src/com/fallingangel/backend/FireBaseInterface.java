package com.fallingangel.backend;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.fallingangel.model.World;

public interface FireBaseInterface {
    public void connect();
    public void createUser(String UID, String mail, String username, String password);
    public void setHighScore(String UID, String username, String date, int score);
    //public void addFriend(String UID, String friendUsername);
    public void connectToRoom(String roomName);
    public void updateMultiplayer(String UID);
    public void setOnValueChangedListener();
    public void createWorldInDB(ImmutableArray<Entity> entities);
 }
