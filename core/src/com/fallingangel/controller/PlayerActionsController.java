package com.fallingangel.controller;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.InputProcessor;
import com.fallingangel.controller.system.AngelSystem;

public class PlayerActionsController implements InputProcessor { //denne klassen tar seg av input

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


    //metoder for å ta i mot input og kalle videre på metoder i systems. Disse kommer fra Inputprocessor
    //sier ifra om inputen har blitt processed, false hvis den ikke blir sendt videre

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button){ //Called when the screen was touched or a mouse button was pressed.
        angelSystem.press(angelEntity, screenX, screenY);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) { //Called when a finger or the mouse was dragged.
        angelSystem.drag(angelEntity, screenX, screenY);
        return false;
    }




    //skal ikke bruke disse, men må implementeres pga interfacet

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) { //Called when a finger was lifted or a mouse button was released.
        angelSystem.unpress(angelEntity);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

}