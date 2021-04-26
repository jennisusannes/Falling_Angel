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
    public int getGameTie();
    public boolean gameWon();
    public boolean gameIsOver();
    public void getHighscoreFromDB();
    public void destroyRoom();
    public String createID(int IDLength);
    public void setRoomReady(boolean roomReady);
    public void setMultiPlayerDataGameOver(boolean gameOver);
    public void setMultiPlayerDataNumUsers(int numUsers);
    public int getMultiPlayerDataNumUsers();
    public boolean getRoomReady();
    public void leaveRoom();
    public void setGameIsOver(boolean gameIsOver);
    //public boolean isRoomReady();
    /*
    public void connectToRoom(String roomName, MultiPlayerData mpd);
    public void leaveRoom();
    public void update(MultiPlayerData player1);
    public boolean gameOver();
    public void createUser();
    public Room getRoom();
     */
 }
