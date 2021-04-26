package com.fallingangel.model;

import com.badlogic.gdx.Input;

public class NameInputListener implements Input.TextInputListener {
    // Handles the input field for usernamegit pull

    public String name;
    @Override
    public void input(String text) {
        name = text;

    }

    @Override
    public void canceled() {

    }
}
