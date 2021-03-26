package com.fallingangel.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.fallingangel.controller.MainController;
import com.fallingangel.view.MenuView;

public class FallingAngel extends Game implements ApplicationListener {


	public SpriteBatch batch;
	public BitmapFont font;
	public MainController controller;
	public MenuView menuView;
	private static FallingAngel INSTANCE;

	public static FallingAngel getInstance() {
		return INSTANCE;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		controller = new MainController();
		menuView = new MenuView();
		setScreen(menuView);
		//controller.changeView(menuView); // burde egt sette controller som setter en screen
		//menuView = new MenuView(this);
		//mc.set(menuView);
	}

	public MainController getController(){
		return controller;
	}
	public MenuView getMenuView(){
		return menuView;
	}


	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}

}
