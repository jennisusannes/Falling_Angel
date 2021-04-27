package com.fallingangel.model.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class TransformComponent implements Component {

    public final Vector3 pos = new Vector3();
    public final Vector2 scale = new Vector2(1.5f, 1.5f);
    public float rotation = 0.0f;


}

//entity.transformComp.pos.x
//world.angel1.getComponent(TransformComponent.class).pos.x