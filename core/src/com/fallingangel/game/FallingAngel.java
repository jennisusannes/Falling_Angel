package com.fallingangel.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.fallingangel.controller.MainController;
import com.fallingangel.model.Asset;

public final class FallingAngel extends Game implements ApplicationListener {


	public FireBaseInterface FBI;
	//private FirebaseAuth mAuth;
	public SpriteBatch batch;
	public BitmapFont font;
	public Music music;
	public MainController mc;
	private static FallingAngel INSTANCE;
	public Asset assets;

	private FallingAngel(FireBaseInterface fireBaseInterface){
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

	/**
	 * SINGLETON IMPLEMENTATION WITHOUT BACKEND CONNECTION
	 * ALLOWS ALL CLASSES A GLOBAL ACCESS POINT TO THE TUBBYWARS INSTANCE
	 * @return TUBBYWARS INSTANCE
	 */
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
		FBI.createUser("TestUserID", "test@user.no", "usernametest", "user123");
		FBI.addFriend("id","usernametest" );
		batch = new SpriteBatch(); //creates a new spritebatch
		font = new BitmapFont(); //kan denne fjernes?
		this.mc = new MainController(); //sets the controller as the main controller
		// Music from Zapsplat.com
		assets.load();
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
