package com.fallingangel.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fallingangel.model.Asset;
import com.fallingangel.view.GameView;
import com.fallingangel.view.MenuView;

public final class FallingAngel extends Game implements ApplicationListener {


	public SpriteBatch batch;
	public BitmapFont font;
	//public MainController mc;
	private MenuView menuView;
	//Instanciating the game with a singleton-pattern
	private static final FallingAngel INSTANCE = new FallingAngel();

	public GameView gameView;

	private FallingAngel(){

	}

	//Singleton-pattern for the entire game
	public static FallingAngel getInstance() {
		return INSTANCE;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		Asset.load();
		this.gameView = new GameView();
		setScreen(gameView);
		/*this.mc = new MainController(); // burde egt sette controller som setter en screen
		mc.setStartScreen();*/

	}


	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		//Asset.dispose();
	}

}
