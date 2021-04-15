package com.fallingangel.model.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;

public class BoundsComponent implements Component {

    public final Rectangle rectangle = new Rectangle(); //using a rectangle to set the bounds of the screen

}
