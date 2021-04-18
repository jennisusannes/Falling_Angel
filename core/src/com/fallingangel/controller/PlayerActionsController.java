package com.fallingangel.controller;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.InputProcessor;
import com.fallingangel.controller.system.AngelSystem;

public class PlayerActionsController { //this class deals with the input

    //The controller responds to the user input and performs interactions on the data model objects
    //An InputProcessor is used to receive input events from the keyboard and the touch screen (mouse on the desktop).
    //For this it has to be registered with the Input.setInputProcessor(InputProcessor) method. (i viewet)
    //It will be called each frame before the call to ApplicationListener.render().
    //Each method returns a boolean in case you want to use this with the InputMultiplexer to chain input processors.

    private AngelSystem angelSystem;
    private Entity angelEntity;

    public PlayerActionsController(AngelSystem angelSystem) {
        this.angelSystem = angelSystem;
        this.angelEntity = angelSystem.getEntities().get(0);
    }

    //methods that receive input and calls other methods in systems. These methods are from InputProcessor
    //tells whether the input has been processed, false if not

}