package com.fallingangel.backend;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;

import java.util.ArrayList;
import java.util.HashMap;

public interface FireBaseInterface {
    public void createUser();
    public void updateScore(MultiPlayerData mpd );
    public void connectToRoom(String roomName, MultiPlayerData mpd);
    public void setOpponentScore();
    public void numberOfUsersInRoom();
    public int getOpponentScore();
    public int getGameTie();

    public boolean gameWon();
    public boolean gameIsOver();
    public void getHighscoreFromDB();
    public void destroyRoom();
 }
