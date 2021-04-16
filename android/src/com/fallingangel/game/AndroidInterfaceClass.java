package com.fallingangel.game;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.Random;

import static android.content.ContentValues.TAG;

public class AndroidInterfaceClass implements FireBaseInterface{

    FirebaseDatabase database;
    DatabaseReference highScoreList;
    DatabaseReference users;
    DatabaseReference friends;

    public AndroidInterfaceClass(){
        database = FirebaseDatabase.getInstance("https://falling-angel-74f3f-default-rtdb.europe-west1.firebasedatabase.app/");
        highScoreList = database.getReference("high-score-list");
        users = database.getReference("users");
        friends = database.getReference("users");

    }

    @Override
    public void connect() {

    }

    @Override
    public void createUser(String UID, String mail, String username, String password) {
        User user = new User(username, mail, password);
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
    public void addFriend(String UID, String friendUsername){
        final String[] friendUserID = new String[1];

        users.orderByChild("username")
                .equalTo(friendUsername)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                            friendUserID[0] = childSnapshot.getKey();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }


                });

        Friend friend = new Friend(friendUsername);
        friends.child(UID).child("friendlist").child(friendUserID[0]).setValue(friend)
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
    public void setHighScore(String UID, String username, String date, int score) {



    }





   /* @Override
    public void SomeFunction() {
        System.out.println("Just some function");
    }

    @Override
    public void FirstFireBaseTest() {
        if(myRef != null){
            myRef.setValue("");
            System.out.println("Set new databasereference");
        }
        else{
            System.out.println("Databasereference was not set -> therefore could not writ to DB");
        }
    }

    @Override
    public void SetOnValueChangedListener() {

        myRef.addValueEventListener(new ValueEventListener() {

           //read from the database
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                //This method is called once with the initial value and again
                // whenever data at this location is updated
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled( DatabaseError error) {

            }
        });
    }

    @Override
    public void SetValueInDb(String target, String value) {
        myRef = database.getReference(target);
        myRef.setValue(value);
    }*/
}
