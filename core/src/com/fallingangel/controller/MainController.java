package com.fallingangel.controller;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.fallingangel.controller.system.RenderingSystem;
import com.fallingangel.game.FallingAngel;
import com.fallingangel.model.Asset;
import com.fallingangel.model.World;
import com.fallingangel.view.AchievementsView;
import com.fallingangel.view.GameOverMultiPlayerView;
import com.fallingangel.view.GameOverView;
import com.fallingangel.view.GameView;
import com.fallingangel.view.HelpView1;
import com.fallingangel.view.HelpView2;
import com.fallingangel.view.HelpView3;
import com.fallingangel.view.HighScoreListView;
import com.fallingangel.view.LevelView;
import com.fallingangel.view.MenuView;
import com.fallingangel.view.MultiPlayerView;
import com.fallingangel.view.SettingsView;

public class MainController extends ClickListener {

    public FallingAngel game;
    // Initializing the different views
    public MenuView menuView;
    public HelpView1 helpView1 = new HelpView1();
    public HelpView2 helpView2 = new HelpView2();
    public HelpView3 helpView3 = new HelpView3();
    public AchievementsView achievementsView = new AchievementsView();
    public HighScoreListView highscorelistView = new HighScoreListView();
    public SettingsView settingsView = new SettingsView();
    public MultiPlayerView multiPlayerView;
    public LevelView levelView = new LevelView();
    public GameActionsController gameActionsController;
    protected World world;
    protected Engine engine;
    public String angel;
    public TextureRegion gameBackground;
    public TextureRegion pauseBackground;

    private Sound clickSound;

    public MainController() {
        this.game = FallingAngel.getInstance();
        clickSound = Asset.clickSound;

        // Initializes engine and world
        this.engine = new Engine();
        this.world = new World(engine);
        this.angel = "pig";
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

    // Getter and setter for chosen character
    public void setChosenCharacter(Animation<TextureRegion> chosenCharacter) {
        world.setChosenCharacter(chosenCharacter);
    }

    public Animation<TextureRegion> getChosenCharacter() {
        return world.getChosenCharacter();
    }

    // Main controller listens to buttons in the different views and changes between views
    @Override
    public boolean handle(Event event) {
        if (event.getListenerActor().equals(menuView.getSinglePlayerButton())) {
            if (game.soundOn()){
                clickSound.play(0.2f);
            }
            game.setScreen(levelView);
            return true;
        }
        else if (event.getListenerActor().equals(menuView.getMultiPlayerButton())){
            if (game.soundOn()){
                clickSound.play(0.2f);
            }
            this.gameActionsController = new GameActionsController(true);
            this.multiPlayerView = new MultiPlayerView();
            multiPlayerView.connectToGameRoom();
            game.setScreen(multiPlayerView);
            //gameActionsController.setGameScreen();
            return true;
        }
        /*
           else if (event.getListenerActor().equals(menuView.getMultiPlayerButton())){
            if (game.soundOn()){
                clickSound.play(0.2f);
            }
            else;
            game.setScreen(multiPlayerView);
            multiPlayerView.connectToGameRoom();
            return true;
        }
         */


        else if (event.getListenerActor().equals(menuView.getHighscoreListButton())){
            if (game.soundOn()){
                clickSound.play(0.2f);
            }
            game.setScreen(highscorelistView);
            return true;
        }
        else if (event.getListenerActor().equals(menuView.getSettingsButton())){
            if (game.soundOn()){
                clickSound.play(0.2f);
            }
            game.setScreen(settingsView);
            return true;
        }
        else if (event.getListenerActor().equals(achievementsView.getBackButton())){
            if (game.soundOn()){
                clickSound.play(0.2f);
            }
            game.setScreen(menuView);
            return true;
        }
        else if (event.getListenerActor().equals(highscorelistView.getBackButton())){
            if (game.soundOn()){
                clickSound.play(0.2f);
            }
            game.setScreen(menuView);
            return true;
        }
        else if (event.getListenerActor().equals(settingsView.getBackButton())){
            if (game.soundOn()){
                clickSound.play(0.2f);
            }
            game.setScreen(menuView);
            return true;
        }
        else if (event.getListenerActor().equals(menuView.getHelpButton())){
            if (game.soundOn()){
                clickSound.play(0.2f);
            }
            game.setScreen(helpView1);
            return true;
        }
        else if (event.getListenerActor().equals(helpView1.getNextButton())){
            if (game.soundOn()){
                clickSound.play(0.2f);
            }
            game.setScreen(helpView2);
            return true;
        }
        else if (event.getListenerActor().equals(helpView2.getNextButton())){
            if (game.soundOn()){
                clickSound.play(0.2f);
            }
            game.setScreen(helpView3);
            return true;
        }
        else if (event.getListenerActor().equals(helpView3.getNextButton())){
            if (game.soundOn()){
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
            if (game.soundOn()){
                clickSound.play(0.2f);
            }
            //this.gameActionsController = new GameActionsController();
            setChosenCharacter(Asset.pigAnimation);
            angel = "pig";
            return true;
        }
        else if (event.getListenerActor().equals(settingsView.getBunnyButton())){
            if (game.soundOn()){
                clickSound.play(0.2f);
            }
            //this.gameActionsController = new GameActionsController();
            setChosenCharacter(Asset.bunnyAnimation);
            angel = "bunny";
            return true;
        }
        else if (event.getListenerActor().equals(levelView.getHeavenButton())) {
            if (game.soundOn()) {
                clickSound.play(0.2f);
            }
            setGameBackground(Asset.heavenBackgroundTextureRegion);
            setPauseBackground(Asset.heavenBackgroundPauseTextureRegion);
            this.gameActionsController = new GameActionsController(false);
            gameActionsController.setGameScreen(false);
            return true;
        }
        else if (event.getListenerActor().equals(levelView.getSunsetButton())) {
            if (game.soundOn()) {
                clickSound.play(0.2f);
            }
            setGameBackground(Asset.sunsetBackgroundTextureRegion);
            setPauseBackground(Asset.sunsetBackgroundPauseTextureRegion);
            this.gameActionsController = new GameActionsController(false);
            gameActionsController.setGameScreen(false);
            return true;
        }
        else if (event.getListenerActor().equals(levelView.getHellButton())) {
            if (game.soundOn()) {
                clickSound.play(0.2f);
            }
            setGameBackground(Asset.hellBackgroundTextureRegion);
            setPauseBackground(Asset.hellBackgroundPauseTextureRegion);
            this.gameActionsController = new GameActionsController(false);
            gameActionsController.setGameScreen(false);
            return true;
        }
        else if (event.getListenerActor().equals(levelView.getBackButton())) {
            if (game.soundOn()) {
                clickSound.play(0.2f);
            }
            game.setScreen(menuView);
            return true;
        }
        else if (event.getListenerActor().equals(multiPlayerView.getBackButton())) {
            if (game.soundOn()) {
                clickSound.play(0.2f);
            }
            game.setScreen(menuView);
            return true;
        }
        else{
            return false;
        }

    }

}