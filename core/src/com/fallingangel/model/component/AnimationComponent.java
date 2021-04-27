package com.fallingangel.model.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.IntMap;

public class AnimationComponent implements Component {

    public IntMap<Animation<TextureRegion>> animations = new IntMap<Animation<TextureRegion>>(); //map where the keys are ints and the values are objects

}
