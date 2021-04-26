package com.fallingangel.backend;

import com.fallingangel.model.MultiPlayerData;

public class CoreInterfaceClass implements FireBaseInterface {


    @Override
    public void createUser() {

    }

    @Override
    public void connectToRoom(String roomName, MultiPlayerData mpd) {

    }

    @Override
    public void setMultiplayer(boolean isMultiplayer) {

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
    public boolean gameWon() {
        return false;
    }

    @Override
    public int getGameWinner() {
        return 0;
    }

    @Override
    public boolean gameIsOver() {
        return false;
    }

    @Override
    public void getHighscoreFromDB() {

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
    public boolean getRoomReady() {
        return false;
    }

    @Override
    public void leaveRoom() {

    }

    @Override
    public void setGameIsOver(boolean gameIsOver) {

    }
}
