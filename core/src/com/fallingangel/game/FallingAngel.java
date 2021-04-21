package com.fallingangel.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.fallingangel.backend.FireBaseInterface;
import com.fallingangel.controller.MainController;
import com.fallingangel.model.Asset;

public final class FallingAngel extends Game implements ApplicationListener {

	private static FallingAngel INSTANCE; // Initializing the game as INSTANCE
	public MainController mc;

	public com.fallingangel.backend.FireBaseInterface FBI;
	//private FirebaseAuth mAuth;
	public SpriteBatch batch;
	public Music music;
	public BitmapFont font;
	//private Animation<TextureRegion> chosenCharacter;
	//public Asset assets;

	private FallingAngel(com.fallingangel.backend.FireBaseInterface fireBaseInterface){
		FBI = fireBaseInterface;
	}

	/**
	 * SINGLETON IMPLEMENTATION OF THE GAME APPLICATION
	 * ALLOWS FOR ANDROID LAUNCHER TO INSTANTIATE THE FALLING ANGEL CLASS
	 * @param fireBaseInterface: CONNECTION TO FIREBASE THROUGH AN INTERFACE
	 * @return FALLING ANGEL INSTANCE
	 */

	// TODO: MÅ sjekke om denne metoden faktisk implementerer singleton,
	// endret nemlig INSTANCE fra static final til bare final
	public static FallingAngel getInstance(FireBaseInterface fireBaseInterface) {
		if (INSTANCE == null){
			INSTANCE = new FallingAngel(fireBaseInterface);
		}
		return INSTANCE;
	}

	// Getter method for the Singleton object of FallingAngel
	public static FallingAngel getInstance() {
		return INSTANCE;
	}

	public boolean soundOn(){
		return music.isPlaying();
	}


	@Override
	public void create () {
		Asset.load();
		FBI.createUser();
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
		font.dispose();
	}
}
