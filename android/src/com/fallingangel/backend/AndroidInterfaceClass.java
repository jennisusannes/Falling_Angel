package com.fallingangel.backend;
import com.fallingangel.controller.GameActionsController;
import com.fallingangel.controller.MultiplayerController;
import com.fallingangel.game.FallingAngel;
import com.fallingangel.model.MultiPlayerData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.Random;

import static android.content.ContentValues.TAG;

public class AndroidInterfaceClass implements FireBaseInterface {

    public FirebaseDatabase database;
    public DatabaseReference users;
    public DatabaseReference rooms;

    private String roomName;
    private User user;
    private int score;
    private int opponentScore;
    private int numUsersInRoom;
    private boolean roomReady;
    private boolean isMultiplayer;
    private boolean gameIsOver = false;
    private boolean multiplayerdataGameover;
    private boolean gameWon;
    private int gameWinner = 0;
    private ValueEventListener roomListener;
    private ValueEventListener scoreListener;
    private ValueEventListener gameIsOverListener;
    private ValueEventListener highScoreListener;
    private ValueEventListener gameWonListener;

    // Constructor
    public AndroidInterfaceClass(){
        database = FirebaseDatabase.getInstance("https://falling-angel-74f3f-default-rtdb.europe-west1.firebasedatabase.app/");
        users = database.getReference("users");
        rooms = database.getReference("games");
    }

    //Creates a new user with a random string for ID
    @Override
    public void createUser() {
        String UID =  createID(30);
        this.user = new User(UID);
        users.child(UID).setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("User created in db");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Error", e.toString());
                    }
                });
    }


    // Connects a user to specific room
    @Override
    public void connectToRoom(String roomName, MultiPlayerData mpd) {
        this.roomName = roomName;
        rooms.child(roomName).child(user.getUID()).setValue(mpd)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("Connected to room");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Error", e.toString());
                    }
                });
    }

    // Reads the opponents score from the database, and updates the score that is beeing used in the game.
    @Override
    public void setOpponentScore() {
        scoreListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();
                    if (!key.equals(user.getUID())){
                        MultiPlayerData opponent = ds.getValue(MultiPlayerData.class);
                        opponentScore = opponent.getScore();
                    }
                }
            }
            @Override
            public void onCancelled( DatabaseError error) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadHighscore:onCancelled", error.toException());

            }

        };
        rooms.child(roomName).addValueEventListener(scoreListener);
    }

    // sets whether a room is ready
    public void setRoomReady(boolean roomReady) {
        this.roomReady = roomReady;
    }

    // Returns true if a room is ready
    public boolean getRoomReady() {
        return roomReady;
    }

    // Sets a game to be a multiplayer
    public void setMultiplayer(boolean isMultiplayer) {
        this.isMultiplayer = isMultiplayer;
    }

    // Sets a multiplayer game to be game over
    public void setMultiPlayerDataGameOver(boolean gameOver) {
        this.multiplayerdataGameover = gameOver;
    }

    // sets game over
    public void setGameIsOver(boolean gameIsOver) {
        this.gameIsOver = gameIsOver;
    }


    // Sets if a room is ready or not based on how many users are in the game room
    @Override
    public void numberOfUsersInRoom() {
        roomListener = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!multiplayerdataGameover) {
                    numUsersInRoom = (int) dataSnapshot.getChildrenCount();
                    rooms.child(roomName).child(user.getUID()).child("numberOfUsersInRoom").setValue(numUsersInRoom);
                    if (numUsersInRoom == 2){
                        setRoomReady(true);
                    } else {
                        setRoomReady(false);
                    }
                }
            }

            @Override
            public void onCancelled( DatabaseError error) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadHighscore:onCancelled", error.toException());

            }
        };
        rooms.child(roomName).addValueEventListener(roomListener);
    }


    // Checks database for existing highscore and makes sure that the current highscore gets updated
    @Override
    public void updateScore(MultiPlayerData mpd) {
        this.getHighscoreFromDB();
        if(isMultiplayer) {
            rooms.child(roomName).child(this.user.getUID()).setValue(mpd)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("error", e.toString());
                        }
                    });
        }
        if (mpd.getScore() > user.getHighScore()){
            users.child(user.getUID()).child("highScore").setValue(this.score);
        }
    }

    //Returns opponents score
    @Override
    public int getOpponentScore(){
        return opponentScore;
    }


    // returns an integer that indicates a tie, winning or loosing
    // 0 is a tie, 1 is a win, 2 is a loose
    public int getGameWinner() {
        return gameWinner;
    }

    // Returns true if game is won
    @Override
    public boolean gameWon() {
        gameWonListener = new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int opponentFinalScore = 0;
                int currentPlayerFinalScore = 0;
                for (DataSnapshot ds:snapshot.getChildren()) {
                    String key = ds.getKey();
                    MultiPlayerData mpd = ds.getValue(MultiPlayerData.class);
                    if (key.equals(user.getUID())) {
                        currentPlayerFinalScore = mpd.getScore();

                    } else {
                        opponentFinalScore = mpd.getScore();
                    }
                }
                if (currentPlayerFinalScore == opponentFinalScore) {
                    gameWinner = 0;
                }
                if (currentPlayerFinalScore > opponentFinalScore) {
                    gameWinner = 1;
                    gameWon = true;
                }
                else if (currentPlayerFinalScore < opponentFinalScore) {
                    gameWinner = 2;
                    gameWon = false;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        rooms.child(roomName).addValueEventListener(gameWonListener);
        return gameWon;
    }

    //Returns true if a multiplayer game is over

    @Override
    public boolean gameIsOver(){
        gameIsOverListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    MultiPlayerData mpd = ds.getValue(MultiPlayerData.class);
                    if(mpd.isGameOver){
                        gameIsOver = true;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        rooms.child(roomName).addValueEventListener(gameIsOverListener);
        return gameIsOver;
    }


    // Sets highscore of user
    @Override
    public void getHighscoreFromDB(){
        highScoreListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();
                    if (key.equals(user.getUID())) {
                        User userdata = ds.getValue(User.class);
                        user.setHighScore(userdata.getHighScore());
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadHighscore:onCancelled", error.toException());

            }

        };
        users.addValueEventListener(highScoreListener);
    }

    // Deletes a room in the database when beeing called
    @Override
    public void leaveRoom() {
        if(gameIsOver) {
            rooms.child(roomName).removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.i("Room was destroyed", "nice");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("error", e.toString());
                        }
                    });
        }
    }

    // Found at: https://www.baeldung.com/java-random-string
    // Method for creating a random String

    public String createID(int IDLength) {

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = IDLength;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();

        return generatedString;
    }

}