package com.fallingangel.model.component;

import com.badlogic.ashley.core.Component;
import com.fallingangel.model.Assets;

public class ObstacleComponent implements Component {

    public static float WIDTH = Assets.balloons.first().getRegionWidth();
    public static float HEIGHT = Assets.balloons.first().getRegionHeight();

    public static final float VELOCITY = 2;


}
