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
	private Music music;
	public MainController mc;
	private static final FallingAngel INSTANCE = new FallingAngel();

	private FallingAngel(){
	}

	public static FallingAngel getInstance() {
		return INSTANCE;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
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
