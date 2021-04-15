package com.fallingangel.model.component;

import com.badlogic.ashley.core.Component;
import com.fallingangel.model.Asset;

public class CoinComponent implements Component {

    public static final float WIDTH = Asset.coinTexture.getWidth()/6;
    public static final float HEIGHT = Asset.coinTexture.getHeight();
    public static final int SCORE = 20;

    public static final int STATE_NORMAL = 1;


}
