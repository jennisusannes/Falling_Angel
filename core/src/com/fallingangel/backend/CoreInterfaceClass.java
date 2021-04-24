package com.fallingangel.backend;

import com.fallingangel.model.MultiPlayerData;

public class CoreInterfaceClass implements FireBaseInterface{

    @Override
    public void createUser() {

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
    public void updateScore(MultiPlayerData mpd) {

    }

    @Override
    public int getOpponentScore() {
        return 0;
    }

    @Override
    public int getGameTie() {
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

    @Override
    public String createID(int IDLength) {
        return null;
    }

    @Override
    public void setRoomReady(boolean roomReady) {

    }

    @Override
    public void setMultiPlayerDataGameOver(boolean gameOver) {

    }

    @Override
    public void setMultiPlayerDataNumUsers(int numUsers) {

    }

    @Override
    public int getMultiPlayerDataNumUsers() {
        return 0;
    }

    @Override
    public boolean getRoomReady() {
        return false;
    }


    /*
    @Override
    public boolean isRoomReady() {
        return false;
    }

    @Override
    public void connectToRoom(String roomName, MultiPlayerData mpd) {

    }

    @Override
    public void leaveRoom() {

    }

    @Override
    public void update(MultiPlayerData player1) {

    }

    @Override
    public boolean gameOver() {
        return false;
    }

    @Override
    public void createUser() {

    }

    @Override
    public Room getRoom() {
        return null;
    }
    */
}
