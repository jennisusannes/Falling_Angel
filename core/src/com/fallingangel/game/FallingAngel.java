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
	//private Texture img;
	private MainController mc;
	private MenuView menuView;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		setScreen(new MenuView(this)); // burde egt sette controller som setter en screen

		//mc = new MainController(this);
		//menuView = new MenuView(this);
		//mc.set(menuView);
	}

	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}

}
