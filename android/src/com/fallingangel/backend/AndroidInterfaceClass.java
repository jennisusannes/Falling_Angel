package com.fallingangel.backend;
import com.fallingangel.game.FallingAngel;
import com.fallingangel.view.HighScoreListView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import static android.content.ContentValues.TAG;

public class AndroidInterfaceClass implements FireBaseInterface {

    FirebaseDatabase database;
    DatabaseReference users;
    DatabaseReference rooms;

    private String roomName;
    private User user;
    private int score;
    private int opponentScore;
    private int numUsersInRoom;
    private boolean gameIsOver;
    private String gameOverStatus = "";


    public AndroidInterfaceClass(){
        database = FirebaseDatabase.getInstance("https://falling-angel-74f3f-default-rtdb.europe-west1.firebasedatabase.app/");
        users = database.getReference("users");
        rooms = database.getReference("games");
    }


    @Override
    public void createUser() {
        String UID =  "jennitest";//createID(30);
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

    @Override
    public void setOpponentScore() {
        ValueEventListener scoreListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();
                    if (!key.equals(user.getUID())){//TODO Check if this line of code is needed: && !key.equals("Usernum")) {
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

    @Override
    public void numberOfUsersInRoom() {
        ValueEventListener roomListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                numUsersInRoom = (int)dataSnapshot.getChildrenCount();
                rooms.child(roomName).child(user.getUID()).child("numberOfUsersInRoom").setValue(numUsersInRoom);

                FallingAngel.getInstance().mc.multiPlayerView.multiPlayerData.setNumberOfUsersInRoom(numUsersInRoom);
            }

            @Override
            public void onCancelled( DatabaseError error) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadHighscore:onCancelled", error.toException());

            }

        };
        rooms.child(roomName).addValueEventListener(roomListener);
    }

    // Checks database for existing highscore and makes sure that the current highscore does not get overwritten
    @Override
    public void updateScore(final int score) {
        this.getHighscoreFromDB();
        this.score = score;
        rooms.child(roomName).child(this.user.getUID()).child("score").setValue(this.score)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i("Score was updated", Integer.toString(score));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("error", e.toString());
                    }
                });
        if (this.score > user.getHighScore()){
            users.child(this.user.getUID()).child("highScore").setValue(this.score);
        }
    }


    @Override
    public int getOpponentScore(){
        return opponentScore;
    }

    @Override
    public String gameOverStatus() {
        ValueEventListener gameOverStatusListener = new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int opponentFinalScore = 0;
                int currentPlayerFinalScore = 0;
                for (DataSnapshot ds:snapshot.getChildren()){
                    String key= ds.getKey();
                    MultiPlayerData mpd = ds.getValue(MultiPlayerData.class);
                    if(key.equals(user.getUID())){
                        currentPlayerFinalScore = mpd.getScore();

                    } else{
                        opponentScore = mpd.getScore();
                    }
                    if(currentPlayerFinalScore > opponentFinalScore){
                        gameOverStatus = "gameWon";
                    }else if(currentPlayerFinalScore < opponentFinalScore){
                        gameOverStatus = "gameLost";
                    }else{
                        gameOverStatus = "gameTied";
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        rooms.child(roomName).addValueEventListener(gameOverStatusListener);
        return gameOverStatus;
    }

    @Override
    public boolean gameIsOver(){
        ValueEventListener gameIsOverListener = new ValueEventListener() {
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



    @Override
    public void getHighscoreFromDB(){
        ValueEventListener highScoreListener = new ValueEventListener() {
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

    @Override
    public void destroyRoom() {
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

    // Found at: https://www.baeldung.com/java-random-string


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

