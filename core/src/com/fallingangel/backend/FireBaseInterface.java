package com.fallingangel.backend;

import com.fallingangel.model.MultiPlayerData;

public interface FireBaseInterface {
    public void createUser();
    public void updateScore(com.fallingangel.model.MultiPlayerData mpd );
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
