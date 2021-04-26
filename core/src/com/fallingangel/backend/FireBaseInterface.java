package com.fallingangel.backend;

import com.fallingangel.model.MultiPlayerData;

public interface FireBaseInterface {
    public void createUser();
    public void connectToRoom(String roomName, MultiPlayerData mpd);
    public void setMultiplayer(boolean isMultiplayer);
    public void setOpponentScore();
    public void numberOfUsersInRoom();
    public void updateScore(MultiPlayerData mpd);
    public int getOpponentScore();
    public boolean gameWon();
    public int getGameWinner();
    public boolean gameIsOver();
    public void getHighscoreFromDB();
    public String createID(int IDLength);
    public void setRoomReady(boolean roomReady);
    public void setMultiPlayerDataGameOver(boolean gameOver);
    public boolean getRoomReady();
    public void leaveRoom();
    public void setGameIsOver(boolean gameIsOver);
 }
