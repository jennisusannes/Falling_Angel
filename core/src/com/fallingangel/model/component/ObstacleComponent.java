package com.fallingangel.model.component;

import com.badlogic.ashley.core.Component;
import com.fallingangel.model.Asset;

public class ObstacleComponent implements Component {

    public static float WIDTH = Asset.balloons.first().getRegionWidth();
    public static float HEIGHT = Asset.balloons.first().getRegionHeight();

    public static final float VELOCITY = 2;


}
