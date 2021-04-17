package com.fallingangel.model.component;

import com.badlogic.ashley.core.Component;

public class StateComponent implements Component {

    public int state = 0; //begins at state 0, STATE_FALL
    public float time = 0.0f;

    public int get() {
        return state;
    }

    public void set(int newState) {
        state = newState;
        time = 0.0f;
    }

}
