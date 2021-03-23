package com.fallingangel.model.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.IntMap;

public class AnimationComponent implements Component {

    public IntMap<com.badlogic.gdx.graphics.g2d.Animation> animations = new IntMap<Animation>(); //map where the keys are ints and the values are objects

}
