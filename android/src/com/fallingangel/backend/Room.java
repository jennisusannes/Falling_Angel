package com.fallingangel.backend;

import java.util.ArrayList;

public class Room {
    private boolean roomReady = false;
    private boolean gameOver = false;
    private MultiPlayerData player1;
    private MultiPlayerData player2;
    private String roomName;

    public Room(String roomName, MultiPlayerData player1){
        this.roomName = roomName;
        this.player1 = player1;
    }


    public boolean isRoomReady() {
        return roomReady;
    }

    public void setRoomReady(boolean roomReady) {
        this.roomReady = roomReady;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public MultiPlayerData getPlayer1() {
        return player1;
    }

    public void setPlayer1(MultiPlayerData player1) {
        this.player1 = player1;
    }

    public MultiPlayerData getPlayer2() {
        return player2;
    }

    public void setPlayer2(MultiPlayerData player2) {
        this.player2 = player2;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
