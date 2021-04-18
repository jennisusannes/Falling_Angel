package com.fallingangel.backend;

public class HighScore {
    public int score;

    public HighScore() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public HighScore(int score){
        this.score = score;
    }
}
