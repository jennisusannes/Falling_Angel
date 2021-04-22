package com.fallingangel.backend;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;

import java.util.HashMap;

public class CoreInterfaceClass implements FireBaseInterface{

    @Override
    public void createUser() {

    }

    @Override
    public void updateScore(MultiPlayerData mpd) {

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
