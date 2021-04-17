package com.fallingangel.backend;

public class User {

    public String UID;
    public String username;
    public String email;
    public String password;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String UID, String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.UID = UID;
    }

}
