package com.fallingangel.backend;

public interface FireBaseInterface {
    public void connect();
    public void createUser(String UID, String mail, String username, String password);
    public void setHighScore(String UID, String username, String date, int score);
    public void addFriend(String UID, String friendUsername);
    public void connectToRoom(String roomName);
 }
