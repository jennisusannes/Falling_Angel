package com.fallingangel;

import com.badlogic.gdx.Input;
import com.fallingangel.controller.system.MultiplayerSystem;
import com.fallingangel.game.FallingAngel;

public class MyTextInputListener implements Input.TextInputListener {

    String room;
    FallingAngel game = FallingAngel.getInstance();

    @Override
    public void input(String text) {
        room = text;
        MultiplayerSystem.roomNumber = text;
        this.game.FBI.connectToRoom(MultiplayerSystem.roomNumber);
    }

    @Override
    public void canceled() {

    }
}
