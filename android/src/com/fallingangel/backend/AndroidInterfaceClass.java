package com.fallingangel.backend;
import com.fallingangel.game.FallingAngel;
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

    FirebaseDatabase database;
    DatabaseReference users;
    DatabaseReference rooms;

    private String roomName;
    private User user;
    private int score;
    private int opponentScore;
    private int numUsersInRoom;
    private boolean gameIsOver;
    private boolean gameWon;
    private ValueEventListener roomListener;
    private ValueEventListener scoreListener;
    private ValueEventListener gameIsOverListener;
    private ValueEventListener highScoreListener;
    private ValueEventListener gameWonListener;

    public AndroidInterfaceClass(){
        database = FirebaseDatabase.getInstance("https://falling-angel-74f3f-default-rtdb.europe-west1.firebasedatabase.app/");
        users = database.getReference("users");
        rooms = database.getReference("games");
    }


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
        scoreListener = new ValueEventListener() {
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
        roomListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!gameIsOver()) {
                    numUsersInRoom = (int) dataSnapshot.getChildrenCount();
                    rooms.child(roomName).child(user.getUID()).child("numberOfUsersInRoom").setValue(numUsersInRoom);

                    FallingAngel.getInstance().mc.waitingRoomView.multiPlayerData.setNumberOfUsersInRoom(numUsersInRoom);
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

    // Checks database for existing highscore and makes sure that the current highscore does not get overwritten
    @Override
    public void updateScore(MultiPlayerData mpd) {
        this.getHighscoreFromDB();
        if(FallingAngel.getInstance().mc.gameActionsController.isMultiplayer) {
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


    @Override
    public int getOpponentScore(){
        return opponentScore;
    }

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
                if(currentPlayerFinalScore >= opponentFinalScore){
                    gameWon = true;
                }else
                    gameWon = false;
                }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        rooms.child(roomName).addValueEventListener(gameWonListener);
        return gameWon;
    }

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

    @Override
    public void destroyRoom() {
        rooms.removeEventListener(roomListener);
        rooms.removeEventListener(scoreListener);
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

