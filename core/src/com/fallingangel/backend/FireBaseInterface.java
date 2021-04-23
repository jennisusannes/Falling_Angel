package com.fallingangel.backend;

import com.fallingangel.model.MultiPlayerData;

public interface FireBaseInterface {
    public boolean isRoomReady();
    public void connectToRoom(String roomName, MultiPlayerData mpd);
    public void leaveRoom();
    public void update(MultiPlayerData player1);
    public boolean gameOver();
    public void createUser();
    public Room getRoom();
 }
