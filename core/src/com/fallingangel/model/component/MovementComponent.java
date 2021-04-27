package com.fallingangel.model.component;

import com.badlogic.ashley.core.Component;

import com.badlogic.gdx.math.Vector2;

public class MovementComponent implements Component {

    public final Vector2 move = new Vector2();
    public final Vector2 accel = new Vector2();

}
