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
	public MainController mc;
	private MenuView menuView;
	private static final FallingAngel INSTANCE = new FallingAngel();

	private FallingAngel(){

	}

	public static FallingAngel getInstance() {
		return INSTANCE;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();

		//setScreen(new MenuView());
		this.mc = new MainController(); // burde egt sette controller som setter en screen
		mc.setStartScreen();

	}


	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}

}
