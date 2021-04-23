package com.fallingangel.backend;

import com.fallingangel.model.MultiPlayerData;

public class CoreInterfaceClass implements FireBaseInterface{

    @Override
    public boolean isRoomReady() {
        return false;
    }

    @Override
    public void connectToRoom(String roomName, MultiPlayerData mpd) {

    }

    @Override
    public void leaveRoom() {

    }

    @Override
    public void update(MultiPlayerData player1) {

    }

    @Override
    public boolean gameOver() {
        return false;
    }

    @Override
    public void createUser() {

    }

    @Override
    public Room getRoom() {
        return null;
    }
}
