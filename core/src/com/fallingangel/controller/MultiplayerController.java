package com.fallingangel.controller;

import com.badlogic.gdx.Gdx;
import com.fallingangel.NameInputListener;
import com.fallingangel.RoomInputListener;
import com.fallingangel.backend.Room;
import com.fallingangel.game.FallingAngel;
import com.fallingangel.model.MultiPlayerData;
import com.fallingangel.view.WaitingRoomView;

public class MultiplayerController {
    /*TODO:
    Waiting room methods
    game over multiplayermethods
    send game to gameactionscontroller when two players in room
    gameactionscontroller handles gameoverview????????
     */
    public FallingAngel game;
    public GameActionsController gameActionsController;
    public WaitingRoomView waitingRoomView;
    public MultiPlayerData multiPlayerData;
    public Room room;
    public String roomNumber;
    public RoomInputListener roomListener;
    public NameInputListener nameListener;
    public boolean alreadyConnected = false;


    public MultiplayerController() {
        this.game = FallingAngel.getInstance();
        this.gameActionsController = game.mc.gameActionsController;
        connectToGameRoom();
        connectToDatabase();
        this.waitingRoomView = new WaitingRoomView();
        game.setScreen(waitingRoomView);

    }

    // Method that creates the input fields for room number and name and saves them in MyTextInputListener classes
    public void connectToGameRoom() {
        //new file which is going to be sent to the database
        multiPlayerData = new MultiPlayerData();

        nameListener = new NameInputListener();
        Gdx.input.getTextInput(nameListener, "SELECT YOUR NAME", "NAME", "WRITE NAME HERE");

        roomListener = new RoomInputListener();
        Gdx.input.getTextInput(roomListener,"SELECT ROOM NUMBER","ROOM NUMBER","WRITE ROOM NUMBER HERE");
    };

    // Method used to create the room in the database
    public void connectToDatabase(){
        //checks whether the user has committed room number and name and if it is already saved to the database
        if (roomListener.room != null && nameListener.name != null && alreadyConnected == false){

            //save to the MultiPlayerData file that is sent to the database
            multiPlayerData.username = nameListener.name;
            roomNumber = roomListener.room;

            //send to database
            game.FBI.connectToRoom(roomListener.room, multiPlayerData);
            alreadyConnected = true;
        }
    }

    // Method that starts a new game if both players are in the room
    public void checkIfReady(){
        // If there are two children in the same room
        if (game.FBI.isRoomReady()){
            this.gameActionsController = game.mc.gameActionsController;
            gameActionsController.setGameScreen(true);
        }
    }

    public int getWinnerStatus() {
        // Tie
        int status = 0;
        // Player 1 wins
        if(game.FBI.getRoom().getPlayer1().getScore() > game.FBI.getRoom().getPlayer2().getScore())  {
            status = 1;
        }
        // Player 2 wins
        else if (game.FBI.getRoom().getPlayer1().getScore() < game.FBI.getRoom().getPlayer2().getScore()){
            status = 2;
        }
        // return winner
        return status;
    }
}
