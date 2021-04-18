package com.fallingangel.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.fallingangel.controller.MainController;
import com.fallingangel.model.Asset;

public final class FallingAngel extends Game implements ApplicationListener {

	private static final FallingAngel INSTANCE = new FallingAngel(); // Initializing the game as INSTANCE
	public MainController mc;
	public SpriteBatch batch;
	public Music music;
	//private Animation<TextureRegion> chosenCharacter;

	private FallingAngel(){ // Private constructor for the game
		//setChosenCharacter(Asset.pigAnimation);

	}

	// Getter method for the Singleton object of FallingAngel
	public static FallingAngel getInstance() {
		return INSTANCE;
	}

	public boolean soundOn(){
		return music.isPlaying();
	}

/*
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


	}

	public Animation<TextureRegion> getChosenCharacter(){
		return chosenCharacter;
	}
*/


	@Override
	public void create () {
		Asset.load();
		batch = new SpriteBatch(); // Creates a new spritebatch
		this.mc = new MainController(); // Sets the controller as the main controller

		music = Asset.backgroundMusic;
		music.setVolume(0.02f); // Sets the volume of the background music
		music.setLooping(true); // The backgrounds music will continuously loop
		music.play(); // Plays the music
		mc.setStartScreen(); // Main controller sets the start screen as the menuscreen
	}

	@Override
	public void dispose () {
		batch.dispose();
		music.dispose();
	}
}
