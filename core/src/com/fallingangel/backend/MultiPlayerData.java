package com.fallingangel.backend;

import com.badlogic.gdx.math.Vector3;

public class MultiPlayerData {

    public boolean connected = false;
    public boolean isGameOver = false;
    public Vector3 position;

    public int score = 0;
    public String username;

    public MultiPlayerData(){}

    public MultiPlayerData(boolean isGameOver,  int score, String username){
        this.isGameOver = isGameOver;
        this.score = score;
        this.username = username;
    }

    //TODO: retrieve the data from Firebase and use it to update the multiplayer
}
