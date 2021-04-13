package com.fallingangel.model.component;

import com.badlogic.ashley.core.Component;
import com.fallingangel.model.Asset;

public class AngelComponent implements Component {

//TODO: implement static variables for: position, Hit, falling, move, width and height, and how far fallen

    public static final int STATE_FALL = 0; //to separate the states of the object
    public static final int STATE_HIT = 1;
    public static final int STATE_DEAD = 2;
    public static final float MOVE_VELOCITY = 15;
    public static final float WIDTH = Asset.pigSpriteSheet.getWidth()/3;
    public static final float HEIGHT = Asset.pigSpriteSheet.getHeight()/2;
    //public static final float SCORE = 0;
}