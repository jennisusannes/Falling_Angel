package com.fallingangel.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.fallingangel.game.FallingAngel;
import com.fallingangel.view.AchievementsView;
import com.fallingangel.view.GameOverView;
import com.fallingangel.view.GameView;
import com.fallingangel.view.HighScoreListView;
import com.fallingangel.view.MenuView;
import com.fallingangel.view.SettingsView;

public class MainController extends ClickListener {

    public FallingAngel game;
    public GameView gameView = new GameView();
    public GameOverView gameOverView = new GameOverView();
    public AchievementsView achievementsView = new AchievementsView();
    public HighScoreListView highscorelistView = new HighScoreListView();
    public SettingsView settingsView = new SettingsView();
    public MenuView menuView;
    // Sound from Zapsplat.com
    private Sound clickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/button_click_sound.wav"));


    public MainController() {
        this.game = FallingAngel.getInstance();
    }

    public void setStartScreen(){
        this.menuView = new MenuView();
        game.setScreen(menuView);
    }

    public void changeView(Screen nextScreen){
        game.setScreen(nextScreen);
    }

  public MenuView getMenuView(){
      return menuView;
    }

    public GameView getGameView(){
        return gameView;
    }

    @Override
    public boolean handle(Event event) {
        if (event.getListenerActor().equals(menuView.getSinglePlayerButton())) {
            clickSound.play(0.2f);
            //long id = clickSound.play(0.2f);
            //clickSound.setPitch(id,2);
            //clickSound.setLooping(id,false);
            game.setScreen(gameView);
            return true;
        }
        else if (event.getListenerActor().equals(menuView.getMultiPlayerButton())){
            clickSound.play(0.2f);
            game.setScreen(gameView);
            return true;
        }
        else if (event.getListenerActor().equals(menuView.getAchievementsButton())){
            clickSound.play(0.2f);
            game.setScreen(achievementsView);
            return true;
        }
        else if (event.getListenerActor().equals(menuView.getHighscoreListButton())){
            clickSound.play(0.2f);
            game.setScreen(highscorelistView);
            return true;
        }
        else if (event.getListenerActor().equals(menuView.getSettingsButton())){
            clickSound.play(0.2f);
            game.setScreen(settingsView);
            return true;
        }
        else if (event.getListenerActor().equals(achievementsView.getBackButton())){
            clickSound.play(0.2f);
            game.setScreen(menuView);
            return true;
        }
        else if (event.getListenerActor().equals(highscorelistView.getBackButton())){
            clickSound.play(0.2f);
            game.setScreen(menuView);
            return true;
        }
        else if (event.getListenerActor().equals(settingsView.getBackButton())){
            clickSound.play(0.2f);
            game.setScreen(menuView);
            return true;
        }
        else if (event.getListenerActor().equals(gameOverView.getBackButton())){
            clickSound.play(0.2f);
            game.setScreen(menuView);
            return true;
        }
        else if (event.getListenerActor().equals(gameView.getGameOverButton())){
            clickSound.play(0.2f);
            game.setScreen(gameOverView);
            return true;
        }
        else{
            return false;
        }
    }
}