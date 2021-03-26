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
import com.fallingangel.view.GameOverView;
import com.fallingangel.view.GameView;
import com.fallingangel.view.MenuView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class MainController extends ClickListener {
     /*TODO
     Bytte mellom skjermene
     Kanksje noe mer
    */

    //private Stack<AbstractView> views; //kunne prøvd screens
    public FallingAngel game;
    public GameView gameView = new GameView();
    public GameOverView gameOverView = new GameOverView();
    public MenuView menuView;

    public MainController() {
        //this.views = new Stack<AbstractView>();
        this.game = FallingAngel.getInstance();
        //gameView = new GameView(this.game);
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


    /*


    public void push(AbstractView view) {
        views.push(view);
    }

    public void pop() {
        views.pop();
    }

    public void set(AbstractView view) {
        views.pop();
        views.push(view);
    }

    public void update(float delta) {
        views.peek().update(delta);
    }

    public void render() {
        views.peek().render();
    }

      */


    @Override
    public boolean handle(Event event) {
        if (event.getListenerActor().equals(menuView.getPlayButtonActor())) {
            game.setScreen(gameView);
            return true;
        }
        else if (event.getListenerActor().equals(gameOverView.getGameOverButton())){
            game.setScreen(menuView);
            return true;
        }
        else{
            return false;
        }
    }







}