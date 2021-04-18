package com.fallingangel.backend;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;

import java.util.Random;

public class AndroidInterfaceClass implements FireBaseInterface {

    FirebaseDatabase database;
    DatabaseReference highScoreList;
    DatabaseReference users;
    DatabaseReference friends;
    DatabaseReference rooms;

    private String roomName;
    private User user;
    private HighScore highScore;
    //private ArrayList<User> userList;
   // private World world;
    private ImmutableArray<Entity> entities;


    public AndroidInterfaceClass(){
        database = FirebaseDatabase.getInstance("https://falling-angel-74f3f-default-rtdb.europe-west1.firebasedatabase.app/");
        highScoreList = database.getReference("high-score-list");
        users = database.getReference("users");
        friends = database.getReference("friends");
        rooms = database.getReference("games");

    }

    @Override
    public void createWorldInDB(ImmutableArray<com.badlogic.ashley.core.Entity> entities) {

        this.entities = entities;
        for (Entity entity : entities
             ) {
            rooms.child("Entiteter").setValue(entity);

        }

    }

    @Override
    public void createUser(String mail, String username, String password) {
        String UID = createID(30);
        this.user = new User(UID, username, mail, password);
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


    @Override
    public void connectToRoom(String roomName) {


        this.roomName = roomName;
        // [BEGIN rtdb_write_new_user_task]
        rooms.child(roomName).child(user.UID).setValue(user)
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
    public void updateScore(int score) {

        highScore = new HighScore(score);
        // [BEGIN rtdb_write_new_user_task]
        rooms.child(roomName).child(this.user.UID).setValue(highScore)
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


    public String createID(int stringlength) {

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = stringlength;
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

