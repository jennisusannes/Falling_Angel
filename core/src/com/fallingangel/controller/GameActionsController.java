package com.fallingangel.controller;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.fallingangel.game.FallingAngel;
import com.fallingangel.model.Asset;
import com.fallingangel.view.GameView;

public class GameActionsController extends ClickListener {

    /*TODO:
    setter hvilken character som er valgt i settings og setter den characteren i world
    denne her opprettes kun etter at spillet er trykket på play
    styrer om spillet er på pause eller i spill
    håndterer skiftet til gameoverview når man dør

     */
    public FallingAngel game;
    public GameView gameView;

    private Sound clickSound;

    public GameActionsController() {
        this.game = FallingAngel.getInstance();
        clickSound = Asset.clickSound;
        // coinSound = Asset.coinSound;
    }


    public void setGameScreen() {
        this.gameView = new GameView();
        game.setScreen(gameView);
    }

}