package com.fallingangel.backend;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;

public interface FireBaseInterface {
    public void createUser(String mail, String username, String password);
    public void updateScore(int score);
    public void connectToRoom(String roomName, MultiPlayerData mpd);
    public void setOpponentScore();
    public int numberOfUsersInRoom();
    public int getOpponentScore();
 }
