package com.fallingangel.backend;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;

import java.util.ArrayList;
import java.util.HashMap;

public interface FireBaseInterface {
    public void createUser(String mail, String username, String password);
    public void updateScore(int score);
    public void connectToRoom(String roomName, MultiPlayerData mpd);
    public void setOpponentScore();
    public void numberOfUsersInRoom();
    public int getOpponentScore();
    public boolean currentPlayerIsWinner();
    public HashMap<String, Integer> highScoreListTopTen();
 }
