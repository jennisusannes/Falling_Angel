package com.fallingangel.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.fallingangel.controller.MainController;
import com.fallingangel.view.MenuView;

public final class FallingAngel extends Game implements ApplicationListener {


	public SpriteBatch batch;
	public BitmapFont font;
<<<<<<< HEAD
	public MainController mc;
	private MenuView menuView;
	private static final FallingAngel INSTANCE = new FallingAngel();

	private FallingAngel(){

	}
=======
	public MainController controller;
	public MenuView menuView;
	private static FallingAngel INSTANCE;
>>>>>>> 74bb92b186fad318c6dd4d5bb85c07ec28070e3b

	public static FallingAngel getInstance() {
		return INSTANCE;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
<<<<<<< HEAD
		//setScreen(new MenuView());
		this.mc = new MainController(); // burde egt sette controller som setter en screen

		//mc = new MainController(this);
=======
		controller = new MainController();
		menuView = new MenuView();
		setScreen(menuView);
		//controller.changeView(menuView); // burde egt sette controller som setter en screen
>>>>>>> 74bb92b186fad318c6dd4d5bb85c07ec28070e3b
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
