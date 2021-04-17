package com.fallingangel.backend;

import com.badlogic.gdx.math.Vector3;

public class MultiPlayerData {
    public boolean connected = false;
    public boolean isGameOver = false;
    public Vector3 position;

    public int score = 0;
    public String username;

    public MultiPlayerData(){}

    //TODO: retrieve the data from Firebase and use it to update the multiplayer
}
