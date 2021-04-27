package com.fallingangel.model;

import com.fallingangel.game.FallingAngel;

public class SettingsModel {

    private FallingAngel game;
    private String angel;


    public SettingsModel() {
        this.game = FallingAngel.getInstance();

        // Sets default character to pig
        this.angel = "pig";
    }

    // Method for setting the angel
    public void setAngel(String angel){
        this.angel = angel;
    }

    // Method for getting the angel
    public String getAngel(){
        return angel;
    }

    // Method for checking if the sound is on or off
    public boolean soundOn(){
        return game.music.isPlaying();
    }


}