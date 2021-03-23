package com.fallingangel.model.component;

import com.badlogic.ashley.core.Component;

public class AngelComponent implements Component {

//TODO: implement static variables for: position, Hit, falling, move, width and height, and how far fallen

    public static final int STATE_FALL = 0; //to separate the states of the object
    public static final int STATE_HIT = 1;
    public static final float MOVE_VELOCITY = 15;
    public static final float WIDTH = 0.8f;
    public static final float HEIGHT = 0.8f;
    public static final float DISTANCE_FALLEN = 0;
}