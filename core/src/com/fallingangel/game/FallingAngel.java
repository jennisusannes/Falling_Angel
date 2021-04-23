package com.fallingangel.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fallingangel.backend.FireBaseInterface;
import com.fallingangel.controller.MainController;
import com.fallingangel.model.Assets;

public final class FallingAngel extends Game implements ApplicationListener {

	private static FallingAngel INSTANCE; // Initializing the game as INSTANCE
	public MainController mc;

	public com.fallingangel.backend.FireBaseInterface FBI;
	//private FirebaseAuth mAuth;
	public SpriteBatch batch;
	public Music music;
	public BitmapFont font;



	private FallingAngel(com.fallingangel.backend.FireBaseInterface fireBaseInterface){
		FBI = fireBaseInterface;
	}

	// Getter method to connect FallingAngel to AndroidLauncher and FireBase
	public static FallingAngel getInstance(FireBaseInterface fireBaseInterface) {
		if (INSTANCE == null){
			INSTANCE = new FallingAngel(fireBaseInterface);
		}
		return INSTANCE;
	}

	// The "game object" was originally implemented as Singleton and is therefore called with getInstance() throughout the code
	// Getter method to connect FallingAngel to game logic
	public static FallingAngel getInstance() {
		return INSTANCE;
	}



	@Override
	public void create () {
		Assets.load();
		FBI.createUser();
		batch = new SpriteBatch(); // Creates a new spritebatch
		this.mc = new MainController(); // Sets the controller as the main controller
		music = Assets.backgroundMusic;
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
