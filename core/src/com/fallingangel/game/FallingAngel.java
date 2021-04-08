package com.fallingangel.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
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
	private static final FallingAngel INSTANCE = new FallingAngel(); //Instanciating the game with a singleton-pattern
	public GameView gameView;

	//Need static variables for the screen's height and width

	public final static float HEIGHT = Gdx.graphics.getHeight();
	public final static float WIDTH = Gdx.graphics.getWidth();

	//Need static variables for the viewports height and width
	/*
	//We are still uncertain of the benefits of using cam and viewport, as the background currently fits all screens.
	public final static float V_HEIGHT = 12.8f;
    public final static float V_WIDTH = 5.76f;
	 */

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
		/*
		this.mc = new MainController(); // burde egt sette controller som setter en screen
		mc.setStartScreen();
		*/

	}

	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		//Asset.dispose();
	}

}
