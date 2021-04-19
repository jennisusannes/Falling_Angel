package com.fallingangel.controller;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.fallingangel.controller.system.AngelSystem;

public class PlayerActionsController extends DragListener { //this class deals with the input

    //The controller responds to the user input and performs interactions on the data model objects
    //An InputProcessor is used to receive input events from the keyboard and the touch screen (mouse on the desktop).
    //For this it has to be registered with the Input.setInputProcessor(InputProcessor) method. (i viewet)
    //It will be called each frame before the call to ApplicationListener.render().
    //Each method returns a boolean in case you want to use this with the InputMultiplexer to chain input processors.

    private AngelSystem angelSystem;
    private Entity angelEntity;

    public PlayerActionsController() { //fjerner at konstruktøren tar inn et Angelsystem
        //this.angelSystem = angelSystem;
        //this.angelEntity = angelSystem.getEntities().get(0);
    }


    boolean dragging=false;

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        return true;
    }

    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        if(!dragging)dragging=true;
            //my game logic*

    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        //Gdx.app.log("touch up",""); trengs kanskje ikke
        if (dragging) {
            //my game logic*
        }

    }
    //methods that receive input and calls other methods in systems. These methods are from InputProcessor
    //tells whether the input has been processed, false if not

}