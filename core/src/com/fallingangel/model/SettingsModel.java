package com.fallingangel.model;

import com.fallingangel.game.FallingAngel;

public class SettingsModel {

    private FallingAngel game;
    private String angel;

    public SettingsModel() {
        this.game = FallingAngel.getInstance();
        this.angel = "pig";
    }

    public void setAngel(String angel){
        this.angel = angel;
    }

    public String getAngel(){
        return angel;
    }
/*
    public boolean isPig(){
        this.mainController = game.mc;
        return mainController.angel.equals("pig");
    }

 */

    public boolean soundOn(){
        return game.music.isPlaying();
    }


}