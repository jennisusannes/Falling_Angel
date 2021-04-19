package com.fallingangel.backend;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;

import java.util.HashMap;

public class CoreInterfaceClass implements FireBaseInterface{

    @Override
    public void createUser(String mail, String username, String password) {

    }

    @Override
    public void updateScore(int score) {

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
    public boolean currentPlayerIsWinner() {
        return false;
    }

    @Override
    public HashMap<String, Integer> highScoreListTopTen() {
        return null;
    }

}
