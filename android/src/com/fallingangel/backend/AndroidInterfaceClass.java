package com.fallingangel.backend;
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
    public DatabaseReference rooms;

    private User user;
    private Room room;

    private ValueEventListener roomListener;

    public AndroidInterfaceClass(){
        database = FirebaseDatabase.getInstance("https://falling-angel-74f3f-default-rtdb.europe-west1.firebasedatabase.app/");
        rooms = database.getReference("games");
    }

    @Override
    public void createUser() {
        String UID =  createID(30);
        this.user = new User(UID);
    }

    @Override
    public void connectToRoom(String roomName, MultiPlayerData player1) {
        room = new Room(roomName, player1);
        rooms.child(roomName).child(user.getUID()).setValue(player1)
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
    public boolean isRoomReady() {

        roomListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int numUsersInRoom = (int) dataSnapshot.getChildrenCount();
                if (numUsersInRoom == 2){
                    room.setRoomReady(true);
                } else {
                    room.setRoomReady(false);
                }
            }

            @Override
            public void onCancelled( DatabaseError error) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadHighscore:onCancelled", error.toException());

            }

        };
        rooms.child(room.getRoomName()).addValueEventListener(roomListener);
        return room.isRoomReady();
    }



    @Override
    public void leaveRoom() {
        rooms.child(room.getRoomName()).removeValue()
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

    @Override
    public void update(final MultiPlayerData player1) {
        room.setPlayer1(player1);
        rooms.child(room.getRoomName()).child(this.user.getUID()).setValue(player1)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i("success"," player1 updated");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("error", e.toString());
                    }
                });

        roomListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();
                    if (!key.equals(user.getUID())){
                        room.setPlayer2(ds.getValue(MultiPlayerData.class));
                    }
                }
            }

            @Override
            public void onCancelled( DatabaseError error) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPlayer2:onCancelled", error.toException());
            }
        };
    }

    @Override
    public boolean gameOver() {
        roomListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    MultiPlayerData player = ds.getValue(MultiPlayerData.class);
                    if(player.isGameOver){
                        room.setGameOver(true);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        rooms.child(room.getRoomName()).addValueEventListener(roomListener);
        return room.isGameOver();
    }

    public Room getRoom() {
        return this.room;
    }

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

