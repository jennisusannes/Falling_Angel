package com.fallingangel.backend;

import com.badlogic.gdx.math.Vector3;

public class MultiPlayerData {

    public boolean connected = false;
    public boolean isGameOver = false;
    public Vector3 position;

    public int score = 0;
    public String username = "username not defined";
    public int numberOfUsersInRoom = 0;

    public MultiPlayerData(){}

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setNumberOfUsersInRoom(int numberOfUsersInRoom){
        this.numberOfUsersInRoom = numberOfUsersInRoom;
    }

    public int getNumberOfUsersInRoom(){
        return numberOfUsersInRoom;
    }
    public MultiPlayerData(boolean isGameOver, int score, String username){
        this.isGameOver = isGameOver;
        this.score = score;
        this.username = username;
    }

    //TODO: retrieve the data from Firebase and use it to update the multiplayer
}
