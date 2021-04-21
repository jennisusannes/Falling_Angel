package com.fallingangel.model.component;

import com.badlogic.ashley.core.Component;
import com.fallingangel.model.Assets;

public class AngelComponent implements Component {

//TODO: implement static variables for: position, Hit, falling, move, width and height, and how far fallen

    public static final int STATE_FALL = 0; //to separate the states of the object
    public static final int STATE_HIT = 1;
    public static final int STATE_DEAD = 2;
    public static final float MOVE_VELOCITY = 15;
    public static final float WIDTH = Assets.pigSpriteSheet.getWidth()/3;
    public static final float HEIGHT = Assets.pigSpriteSheet.getHeight()/2;
    public static float SCORE = 0;

    //THese are used to render the score and airtime
    public float AIRTIME = 0;
    public float COINS_HIT = 0;
}