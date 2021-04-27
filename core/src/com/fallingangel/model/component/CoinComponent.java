package com.fallingangel.model.component;

import com.badlogic.ashley.core.Component;
import com.fallingangel.model.Assets;

public class CoinComponent implements Component {

    public static final float WIDTH = Assets.coinTexture.getWidth()/6;
    public static final float HEIGHT = Assets.coinTexture.getHeight();
    public static final int SCORE = 20;

    public static final int STATE_NORMAL = 1;


}
