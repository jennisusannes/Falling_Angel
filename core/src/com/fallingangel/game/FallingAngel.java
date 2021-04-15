package com.fallingangel.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.fallingangel.controller.MainController;

public final class FallingAngel extends Game implements ApplicationListener {

	public SpriteBatch batch;
	public BitmapFont font;
	public Music music;
	public MainController mc;
	private static final FallingAngel INSTANCE = new FallingAngel(); //initializing the game as INSTANCE

	private FallingAngel(){ //private constructor for the game
	}

	//getter method for the singleton object of FallingAngel
	public static FallingAngel getInstance() {
		return INSTANCE;
	}

	public boolean soundOn(){
		if (music.getVolume() > 0){
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public void create () {
		batch = new SpriteBatch(); //creates a new spritebatch
		font = new BitmapFont(); //kan denne fjernes?
		this.mc = new MainController(); //sets the controller as the main controller
		// Music from Zapsplat.com
		music = Gdx.audio.newMusic(Gdx.files.internal("sounds/background_music.mp3")); //sets the music
		music.setVolume(0.02f); //sets the volume of the background music
		music.setLooping(true); //the backgrouns music will continuously loop
		music.play(); //plays the music
		mc.setStartScreen(); //the main controller sets the start screen as the menuscreen
	}

	@Override
	public void dispose () {
		batch.dispose();
		font.dispose(); //kan denne fjernes?
		music.dispose();
	}

}
