package com.fallingangel.controller;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.fallingangel.game.FallingAngel;
import com.fallingangel.view.AbstractView;
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
    private FallingAngel game;
    private MenuView menuView;
    private GameView gameView;

    public MainController() {
        //this.views = new Stack<AbstractView>();
        this.game = FallingAngel.getInstance();
        //gameView = new GameView(this.game);
        game.setScreen(new MenuView());
    }

    /*

    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();
        if (command.equals("Play")) {
            game.setScreen(gameView);
        //} else if (command.equals("Pause/Continue")) {
        //    model.setPause(! model.getPause());
        //} else if (command.equals("Flip Vertically")) {
         //   view.setFlipVertical(! view.getFlipVertical());
        }
    }




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

   // public void playButtonEvent(Event event){
    //    game.setScreen(gameView);
   // }






    @Override
    public boolean handle(Event event) {
        if (event.getListenerActor() == menuView.getPlayButtonActor()) {
            game.setScreen(gameView);
            return true;
        }
        else{
            return false;
        }
    }





}