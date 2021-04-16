package com.fallingangel.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.fallingangel.controller.MainController;
import com.fallingangel.model.Asset;

public final class FallingAngel extends Game implements ApplicationListener {

	public SpriteBatch batch;
	public BitmapFont font;
	public Music music;
	public MainController mc;
	//public Asset assets;
	private static final FallingAngel INSTANCE = new FallingAngel(); //initializing the game as INSTANCE
	private Animation<TextureRegion> chosenCharacter;

	private FallingAngel(){ //private constructor for the game
		//setChosenCharacter(Asset.pigAnimation);

	}

	//getter method for the singleton object of FallingAngel
	public static FallingAngel getInstance() {
		return INSTANCE;
	}

	public boolean soundOn(){ //burde denne være i controlleren??
		if (music.isPlaying()){
			return true;
		}
		else{
			return false;
		}
	}


	public void setChosenCharacter(Animation<TextureRegion> animation){ //setStartScreen method is called in the game class.
		this.chosenCharacter = animation;
		/*
		if (animation.equals("pig")){
			this.chosenCharacter = Asset.pigAnimation;
		}
		else if (string.equals("bunny")){
			this.chosenCharacter = Asset.bunnyAnimation;
		}
		else;

		 */
	}

	public Animation<TextureRegion> getChosenCharacter(){
		return chosenCharacter;
	}



	@Override
	public void create () {
		batch = new SpriteBatch(); //creates a new spritebatch
		font = new BitmapFont(); //kan denne fjernes?
		this.mc = new MainController(); //sets the controller as the main controller
		Asset.load();


		music = Asset.backgroundMusic;
		music.setVolume(0.02f); //sets the volume of the background music
		music.setLooping(true); //the backgrounds music will continuously loop
		music.play(); //plays the music
		mc.setStartScreen(); //the main controller sets the start screen as the menuscreen
	}

	@Override
	public void dispose () {
		batch.dispose();
		font.dispose(); //kan denne fjernes?
		music.dispose();
	}
	//Comment

}
