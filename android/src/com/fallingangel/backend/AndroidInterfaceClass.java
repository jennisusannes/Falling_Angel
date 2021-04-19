package com.fallingangel.backend;
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


    public AndroidInterfaceClass(){
        database = FirebaseDatabase.getInstance("https://falling-angel-74f3f-default-rtdb.europe-west1.firebasedatabase.app/");
        users = database.getReference("users");
        rooms = database.getReference("games");


    }


    @Override
    public void createUser(String mail, String username, String password) {
        String UID =  createID(30);

        this.user = new User(UID, username, mail, password);
     //   userInfoListener(users);
        // [BEGIN rtdb_write_new_user_task]
        users.child(UID).setValue(user)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    // Write was successful!
                    // ...
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Write failed
                            // ...
                        }
                    });
            // [END rtdb_write_new_user_task]


    }

    // Checks database for existing highscore and makes sure that the current highscore does not get overwritten
    private void userInfoListener(DatabaseReference mPostReference) {
        // [START post_value_event_listener]
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user.setHighScore(dataSnapshot.child(user.getUID()).getValue(User.class).getHighScore());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadHighscore:onCancelled", error.toException());

            }

        };
        mPostReference.addValueEventListener(userListener);
        // [END post_value_event_listener]
    }


    @Override
    public void connectToRoom(String roomName, MultiPlayerData mpd) {

        this.roomName = roomName;
        // [BEGIN rtdb_write_new_user_task]
        rooms.child(roomName).child(user.getUID()).setValue(mpd)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        // ...
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        // ...
                    }
                });
        // [END rtdb_write_new_user_task]
    }

    @Override
    public void setOpponentScore() {
        ValueEventListener scoreListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();
                    if (!key.equals(user.getUID())) {
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
    public int numberOfUsersInRoom() {
        return 0;
    }


    @Override
    public void updateScore(int score) {

        this.score = score;
        // [BEGIN rtdb_write_new_user_task]
        rooms.child(roomName).child(this.user.getUID()).child("score").setValue(this.score)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        // ...
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        // ...
                    }
                });
        // [END rtdb_write_new_user_task]
        if (this.score > this.user.getHighScore()){
            users.child(this.user.getUID()).child("highScore").setValue(this.score);
        }

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


    @Override
    public int getOpponentScore(){
        return opponentScore;
    }

}

