package com.fallingangel;

import com.badlogic.gdx.Input;

public class MyTextInputListener2 implements Input.TextInputListener {

    public String name;
    @Override
    public void input(String text) {
        name = text;

    }

    @Override
    public void canceled() {

    }
}
