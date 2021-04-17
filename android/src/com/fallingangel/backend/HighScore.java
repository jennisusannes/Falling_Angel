package com.fallingangel.backend;

public class HighScore {
    public String username;
    public String timeStamp;
    public int score;

    public HighScore() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public HighScore(String username, String timeStamp, int score) {
        this.username = username;
        this.timeStamp = timeStamp;
        this.score = score;
    }
}
