package com.fallingangel.model;

import com.badlogic.gdx.Input;
import com.fallingangel.controller.system.MultiplayerSystem;
import com.fallingangel.game.FallingAngel;

public class RoomInputListener implements Input.TextInputListener {
    // Handles the input field for room number

    public String room;

    @Override
    public void input(String text) {
        room = text;
        MultiplayerSystem.roomNumber = text;
    }

    @Override
    public void canceled() {

    }
}
