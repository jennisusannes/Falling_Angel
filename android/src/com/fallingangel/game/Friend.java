package com.fallingangel.game;

public class Friend {
    public String username;

    public Friend() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Friend(String username){
        this.username = username;
    }

}

