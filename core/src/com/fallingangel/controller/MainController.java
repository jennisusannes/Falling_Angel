package com.fallingangel.controller;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.fallingangel.game.FallingAngel;
import com.fallingangel.model.Assets;
import com.fallingangel.model.SettingsModel;
import com.fallingangel.model.World;
import com.fallingangel.view.HelpView1;
import com.fallingangel.view.HelpView2;
import com.fallingangel.view.HelpView3;
import com.fallingangel.view.LevelView;
import com.fallingangel.view.MenuView;
import com.fallingangel.view.WaitingRoomView;
import com.fallingangel.view.SettingsView;

public class MainController extends ClickListener {

    public FallingAngel game;
    // Initializing the different views
    public MenuView menuView;
    public HelpView1 helpView1 = new HelpView1();
    public HelpView2 helpView2 = new HelpView2();
    public HelpView3 helpView3 = new HelpView3();
    public SettingsModel settingsModel = new SettingsModel();
    public SettingsView settingsView = new SettingsView();
    // Not fully implementet highscorelist
    //public HighScoreListView highscorelistView = new HighScoreListView();
    public WaitingRoomView waitingRoomView;
    public LevelView levelView = new LevelView();
    public GameActionsController gameActionsController;
    public MultiplayerController multiplayerController;
    protected World world;
    protected Engine engine;
    public String angel;
    public TextureRegion gameBackground;
    public TextureRegion pauseBackground;

    private Sound clickSound;

    public MainController() {
        this.game = FallingAngel.getInstance();
        clickSound = Assets.clickSound;

        // Initializes engine and world
        this.engine = new Engine();
        this.world = new World(engine);
        settingsModel.setAngel("pig");
    }

    // setStartScreen method is called in the game class
    public void setStartScreen(){
        this.menuView = new MenuView();
        game.setScreen(menuView);
    }

    // Getters and setters for chosen level
    public TextureRegion getGameBackground(){
        return gameBackground;
    }

    public void setGameBackground(TextureRegion background){
        this.gameBackground = background;
    }

    public TextureRegion getPauseBackground(){
        return pauseBackground;
    }

    public void setPauseBackground(TextureRegion background){
        this.pauseBackground = background;
    }

    // Checks if the angel is a pig
    public boolean isPig() {
        return settingsModel.getAngel().equals("pig");
    }

    // Main controller listens to buttons in the different views and changes between views
    @Override
    public boolean handle(Event event) {
        if (event.getListenerActor().equals(menuView.getSinglePlayerButton())) {
            if (settingsModel.soundOn()) {
                    clickSound.play(0.2f);
            }
            game.setScreen(levelView);
            return true;
        }
        else if (event.getListenerActor().equals(menuView.getMultiPlayerButton())){
            if (settingsModel.soundOn()) {
                clickSound.play(0.2f);
            }
            this.gameActionsController = new GameActionsController(true);
            return true;
        }

        // Not fully implementet highscorelist
        /*
        else if (event.getListenerActor().equals(menuView.getHighscoreListButton())){
            if (game.soundOn()){
                clickSound.play(0.2f);
            }
            game.setScreen(highscorelistView);
            return true;
        }

        else if (event.getListenerActor().equals(highscorelistView.getBackButton())){
            if (game.soundOn()){
                clickSound.play(0.2f);
            }
            game.setScreen(menuView);
            return true;
        }
         */

        else if (event.getListenerActor().equals(menuView.getSettingsButton())){
            if (settingsModel.soundOn()) {
                clickSound.play(0.2f);
            }
            game.setScreen(settingsView);
            return true;
        }

        else if (event.getListenerActor().equals(settingsView.getBackButton())){
            if (settingsModel.soundOn()) {
                clickSound.play(0.2f);
            }
            game.setScreen(menuView);
            return true;
        }
        else if (event.getListenerActor().equals(menuView.getHelpButton())){
            if (settingsModel.soundOn()) {
                clickSound.play(0.2f);
            }
            game.setScreen(helpView1);
            return true;
        }
        else if (event.getListenerActor().equals(helpView1.getNextButton())){
            if (settingsModel.soundOn()) {
                clickSound.play(0.2f);
            }
            game.setScreen(helpView2);
            return true;
        }
        else if (event.getListenerActor().equals(helpView2.getNextButton())){
            if (settingsModel.soundOn()) {
                clickSound.play(0.2f);
            }
            game.setScreen(helpView3);
            return true;
        }
        else if (event.getListenerActor().equals(helpView3.getNextButton())){
            if (settingsModel.soundOn()) {
                clickSound.play(0.2f);
            }
            game.setScreen(menuView);
            return true;
        }
        else if (event.getListenerActor().equals(settingsView.getMusicOnButton())){
            clickSound.play(0.0f);
            game.music.pause();
            settingsView.getMusicOnButton().setPosition(-1000,-1000);
            settingsView.getMusicOffButton().setPosition(Gdx.graphics.getWidth()*0.55f, Gdx.graphics.getHeight() * 0.62f);
            return true;
        }
        else if (event.getListenerActor().equals(settingsView.getMusicOffButton())){
            clickSound.play(0.2f);
            game.music.setVolume(0.02f);
            game.music.play();
            settingsView.getMusicOffButton().setPosition(-1000,-1000);
            settingsView.getMusicOnButton().setPosition(Gdx.graphics.getWidth()*0.55f, Gdx.graphics.getHeight() * 0.62f);
            return true;
        }
        else if (event.getListenerActor().equals(settingsView.getPigButton())){
            if (settingsModel.soundOn()) {
                clickSound.play(0.2f);
            }
            world.setChosenCharacter(Assets.pigAnimation);
            settingsModel.setAngel("pig");
            return true;
        }
        else if (event.getListenerActor().equals(settingsView.getBunnyButton())){
            if (settingsModel.soundOn()) {
                clickSound.play(0.2f);
            }
            world.setChosenCharacter(Assets.bunnyAnimation);
            settingsModel.setAngel("bunny");
            return true;
        }
        else if (event.getListenerActor().equals(levelView.getHeavenButton())) {
            if (settingsModel.soundOn()) {
                clickSound.play(0.2f);
            }
            setGameBackground(Assets.heavenBackgroundTextureRegion);
            setPauseBackground(Assets.heavenBackgroundPauseTextureRegion);
            this.gameActionsController = new GameActionsController(false);
            gameActionsController.setGameScreen(false);
            return true;
        }
        else if (event.getListenerActor().equals(levelView.getSunsetButton())) {
            if (settingsModel.soundOn()) {
                clickSound.play(0.2f);
            }
            setGameBackground(Assets.sunsetBackgroundTextureRegion);
            setPauseBackground(Assets.sunsetBackgroundPauseTextureRegion);
            this.gameActionsController = new GameActionsController(false);
            gameActionsController.setGameScreen(false);
            return true;
        }
        else if (event.getListenerActor().equals(levelView.getHellButton())) {
            if (settingsModel.soundOn()) {
                clickSound.play(0.2f);
            }
            setGameBackground(Assets.hellBackgroundTextureRegion);
            setPauseBackground(Assets.hellBackgroundPauseTextureRegion);
            this.gameActionsController = new GameActionsController(false);
            gameActionsController.setGameScreen(false);
            return true;
        }
        else if (event.getListenerActor().equals(levelView.getBackButton())) {
            if (settingsModel.soundOn()) {
                clickSound.play(0.2f);
            }
            game.setScreen(menuView);
            return true;
        }
        else if (event.getListenerActor().equals(gameActionsController.multiplayerController.waitingRoomView.getBackButton())) {
            if (settingsModel.soundOn()) {
                clickSound.play(0.2f);
            }
            game.setScreen(menuView);
            game.FBI.setMultiPlayerDataGameOver(true);
            game.FBI.setGameIsOver(true);
            gameActionsController.multiplayerController.multiPlayerData.setGameOver(true);
            game.FBI.leaveRoom();
            return true;
        }
        else{
            return false;
        }
    }

}