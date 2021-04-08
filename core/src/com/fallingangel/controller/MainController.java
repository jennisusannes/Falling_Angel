package com.fallingangel.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.fallingangel.game.FallingAngel;
import com.fallingangel.view.AbstractView;
import com.fallingangel.view.AchievementsView;
import com.fallingangel.view.GameOverView;
import com.fallingangel.view.GameView;
import com.fallingangel.view.HighScoreListView;
import com.fallingangel.view.MenuView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainController extends ClickListener {

    public FallingAngel game;
    public GameView gameView = new GameView();
    public GameOverView gameOverView = new GameOverView();
    public AchievementsView achievementsView = new AchievementsView();
    public HighScoreListView highscorelistView = new HighScoreListView();
    public MenuView menuView;

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
            game.setScreen(gameView);
            return true;
        }
        else if (event.getListenerActor().equals(menuView.getMultiPlayerButton())){
            game.setScreen(gameView);
            return true;
        }
        else if (event.getListenerActor().equals(menuView.getAchievementsButton())){
            game.setScreen(achievementsView);
            return true;
        }
        else if (event.getListenerActor().equals(menuView.getHighscoreListButton())){
            game.setScreen(highscorelistView);
            return true;
        }
        else if (event.getListenerActor().equals(achievementsView.getBackButton())){
            game.setScreen(menuView);
            return true;
        }
        else if (event.getListenerActor().equals(highscorelistView.getBackButton())){
            game.setScreen(menuView);
            return true;
        }
        else if (event.getListenerActor().equals(gameOverView.getBackButton())){
            game.setScreen(menuView);
            return true;
        }
        else if (event.getListenerActor().equals(gameView.getGameOverButton())){
            game.setScreen(gameOverView);
            return true;
        }
        else{
            return false;
        }
    }
}