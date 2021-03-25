package com.fallingangel.controller;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;

public class PlayerActionsController implements InputProcessor {

    //The controller responds to the user input and performs interactions on the data model objects

    //denne klassen tar seg av input

    /*
    public PlayerActionController(tar inn nødvendige systems her){
        definerer this.system
        definerer evt entity også: physicsEntity = physicsSystem.getEntities().get(0);
     */


    //metoder for å ta i mot input og kalle videre på metoder i systems
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

    public boolean touchDown(int screenX, int screenY, int pointer, int button){
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
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

}