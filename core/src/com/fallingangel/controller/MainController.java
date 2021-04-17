package com.fallingangel.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.fallingangel.game.FallingAngel;
import com.fallingangel.model.Asset;
import com.fallingangel.view.AchievementsView;
import com.fallingangel.view.GameOverView;
import com.fallingangel.view.HelpView1;
import com.fallingangel.view.HelpView2;
import com.fallingangel.view.HelpView3;
import com.fallingangel.view.HighScoreListView;
import com.fallingangel.view.MenuView;
import com.fallingangel.view.SettingsView;

public class MainController extends ClickListener {

    public FallingAngel game;
    // Initializing the different views
    public GameActionsController gameActionsController;
    public GameOverView gameOverView = new GameOverView();
    public HelpView1 helpView1 = new HelpView1();
    public HelpView2 helpView2 = new HelpView2();
    public HelpView3 helpView3 = new HelpView3();
    public AchievementsView achievementsView = new AchievementsView();
    public HighScoreListView highscorelistView = new HighScoreListView();
    public SettingsView settingsView = new SettingsView();
    public MenuView menuView;
    private Sound clickSound;

    public MainController() {
        this.game = FallingAngel.getInstance();
        clickSound = Asset.clickSound;
    }

    // setStartScreen method is called in the game class
    public void setStartScreen(){
        this.menuView = new MenuView();
        game.setScreen(menuView);
    }

    // Main controller listens to buttons in the different views and changes between views
    @Override
    public boolean handle(Event event) {
        if (event.getListenerActor().equals(menuView.getSinglePlayerButton())) {
            if (game.soundOn()){
                clickSound.play(0.2f);
            }
            this.gameActionsController = new GameActionsController();
            gameActionsController.setGameScreen();
            return true;
        }
        else if (event.getListenerActor().equals(menuView.getMultiPlayerButton())){
            if (game.soundOn()){
                clickSound.play(0.2f);
            }
            this.gameActionsController = new GameActionsController();
            gameActionsController.setGameScreen();
            return true;
        }
        else if (event.getListenerActor().equals(menuView.getHelpButton())){ //fjerne denne
            if (game.soundOn()){
                clickSound.play(0.2f);
            }
            game.setScreen(helpView1);
            return true;
        }
        // Removed achievementsView
        /*
        else if (event.getListenerActor().equals(menuView.getAchievementsButton())){
            if (game.soundOn()){
                clickSound.play(0.2f);
            }
            game.setScreen(achievementsView);
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
        else if (event.getListenerActor().equals(gameOverView.getBackButton())){
            if (game.soundOn()){
                clickSound.play(0.2f);
            }
            game.setScreen(menuView);
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
            settingsView.getMusicOffButton().setPosition(Gdx.graphics.getWidth()*0.6f, Gdx.graphics.getHeight() * 0.6f);
            return true;
        }
        else if (event.getListenerActor().equals(settingsView.getMusicOffButton())){
            clickSound.play(0.2f);
            game.music.setVolume(0.02f);
            game.music.play();
            settingsView.getMusicOffButton().setPosition(-1000,-1000);
            settingsView.getMusicOnButton().setPosition(Gdx.graphics.getWidth()*0.6f, Gdx.graphics.getHeight() * 0.6f);
            return true;
        }
        else{
            return false;
        }
    }
}