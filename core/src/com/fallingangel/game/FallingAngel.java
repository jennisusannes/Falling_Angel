package com.fallingangel.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fallingangel.controller.MainController;



public final class FallingAngel extends Game implements ApplicationListener {


	public FireBaseInterface FBI;
	public SpriteBatch batch;
	public BitmapFont font;
	private Music music;
	public MainController mc;
	private static FallingAngel INSTANCE;

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
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		FBI.SomeFunction();
		FBI.FirstFireBaseTest();
		//FBI.SetOnValueChangedListener();
		//FBI.SetValueInDb("message", "this
		this.mc = new MainController();
		// Music from Zapsplat.com
		music = Gdx.audio.newMusic(Gdx.files.internal("sounds/background_music.mp3"));
		music.setVolume(0.02f);
		music.setLooping(true);
		music.play();
		mc.setStartScreen();
	}

	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		music.dispose();
	}
}
