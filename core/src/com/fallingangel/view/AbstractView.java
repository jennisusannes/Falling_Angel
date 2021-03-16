package com.fallingangel.view;

public abstract class AbstractView {
    protected abstract void handleInput();
    public abstract void show();
    public abstract void update(float dt);
    public abstract void render();
    public abstract void dispose();

}
