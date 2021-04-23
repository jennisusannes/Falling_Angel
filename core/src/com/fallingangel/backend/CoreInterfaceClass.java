package com.fallingangel.backend;

import com.fallingangel.model.MultiPlayerData;

public class CoreInterfaceClass implements FireBaseInterface{

    @Override
    public void createUser() {

    }

    @Override
    public void updateScore(com.fallingangel.model.MultiPlayerData mpd) {

    }

    @Override
    public void connectToRoom(String roomName, MultiPlayerData mpd) {

    }

    @Override
    public void setOpponentScore() {

    }


    @Override
    public void numberOfUsersInRoom() {
    }

    @Override
    public int getOpponentScore() {
        return 0;
    }

    @Override
    public int getGameTie() {
        return 0;
    }

    @Override
    public boolean gameWon() {
        return false;
    }


    @Override
    public boolean gameIsOver() {
        return false;
    }

    @Override
    public void getHighscoreFromDB() {

    }

    @Override
    public void destroyRoom() {

    }


}
