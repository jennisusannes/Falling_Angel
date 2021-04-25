package com.fallingangel.controller;

import com.badlogic.gdx.Gdx;
import com.fallingangel.model.NameInputListener;
import com.fallingangel.model.RoomInputListener;
import com.fallingangel.game.FallingAngel;
import com.fallingangel.model.MultiPlayerData;
import com.fallingangel.view.WaitingRoomView;

public class MultiplayerController {
    /*TODO:
    Waiting room methods
    game over multiplayermethods
    send game to gameactionscontroller when two players in room
    gameactionscontroller handles gameoverview????????
    backbutton i waitingroom i main Controller må endre til leave room()
     */
    public FallingAngel game;
    public GameActionsController gameActionsController;
    public WaitingRoomView waitingRoomView;
    public MultiPlayerData multiPlayerData;
    public String roomNumber;
    public RoomInputListener roomListener;
    public NameInputListener nameListener;
    public boolean alreadyConnected = false;


    public MultiplayerController() {
        this.game = FallingAngel.getInstance();
        this.gameActionsController = game.mc.gameActionsController;
        connectToGameRoom();
        multiPlayerData.setGameOver(false);
        game.FBI.setMultiPlayerDataGameOver(false);
        connectToDatabase();
        this.waitingRoomView = new WaitingRoomView();
        game.setScreen(waitingRoomView);
    }

    // Method that creates the input fields for room number and name and saves them in MyTextInputListener classes
    public void connectToGameRoom() {
        // New file which is going to be sent to the database
        multiPlayerData = new MultiPlayerData();

        nameListener = new NameInputListener();
        Gdx.input.getTextInput(nameListener, "SELECT YOUR NAME", "NAME", "WRITE NAME HERE");

        roomListener = new RoomInputListener();
        Gdx.input.getTextInput(roomListener,"SELECT ROOM NUMBER","ROOM NUMBER","WRITE ROOM NUMBER HERE");
    };

    // Method used to create the room in the database
    public void connectToDatabase(){
        // Checks whether the user has committed room number and name and if it is already saved to the database
        if (roomListener.room != null && nameListener.name != null && alreadyConnected == false){

            // Save to the MultiPlayerData file that is sent to the database
            multiPlayerData.username = nameListener.name;
            roomNumber = roomListener.room;

            // Send to database
            game.FBI.connectToRoom(roomListener.room, multiPlayerData);
            alreadyConnected = true;
            game.FBI.numberOfUsersInRoom();
        }
    }

    // Method that starts a new game if both players are in the room
    public void checkIfReady(){
        // If there are two children in the same room
        if (game.FBI.getRoomReady()){
            this.gameActionsController = game.mc.gameActionsController;
            gameActionsController.setGameScreen(true);
        }
    }

    public int getWinnerStatus() {
        // Tie
        int status = 0;
        // Player 1 wins
        if(game.FBI.gameWon())  {
            status = 1;
        }
        // Player 2 wins
        else if (!game.FBI.gameWon()){
            status = 2;
        }
        // return winner
        return status;
    }
}
