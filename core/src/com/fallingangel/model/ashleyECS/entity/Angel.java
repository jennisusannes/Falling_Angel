package com.fallingangel.model.ashleyECS.entity;

import com.badlogic.ashley.core.Entity;
import com.fallingangel.model.ashleyECS.component.TextureComponent;

public class Angel {

    public Entity createAngel(){
        Entity angel = new Entity();

        TextureComponent textureComponent = new TextureComponent();
        return angel;
    }
}
