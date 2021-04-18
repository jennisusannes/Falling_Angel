package com.fallingangel.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Asset {

    //BACKGROUNDS:
    public static Texture backgroundHellTexture;
    public static Texture backgroundHeavenTexture;

    //play-view background
    public static TextureRegion backgroundTextureRegion;

    //PIG
    public static Animation<TextureRegion> pigAnimation;
    public static Texture pigSpriteSheet;

    //BUNNY
    public static Animation<TextureRegion> bunnyAnimation;
    public static Texture bunnySpriteSheet;

    //POWER-UPS and COIN
    //Animation for coin:
    public static Animation<TextureRegion> coinAnimation;
    public static Texture coinTexture;


    //OBSTACLES
    //Balloons
    public static String blueBalloon = "obstacles/balloons/blue_balloon.png";
    public static String greenBalloon = "obstacles/balloons/green_balloon.png";
    public static String redBalloon = "obstacles/balloons/red_balloon.png";
    public static String yellowBalloon = "obstacles/balloons/yellow_balloon.png";
    public static Array<TextureRegion> balloons = new Array<TextureRegion>();
    //Drone
    public static String drone = "obstacles/drone.png";
    public static TextureRegion droneTexture;


    //MUSIC AND SOUNDS
    public static Music backgroundMusic;
    public static Sound clickSound;


    public static Texture singlePlayerTexture;
    public static Texture multiPlayerTexture;
    public static Texture achievementsTexture;
    public static Texture highscoreListTexture;
    public static Texture settingsTexture;
    public static Texture backTexture;
    public static Texture playTexture;
/*
    //Select Character
    public static String pig_select_character = "characters/pig/pig_select_character.PNG";

    //Devil obstacles:
    public static String devil1 = "obstacles/devil/devil_1.png";
    public static String devil2 = "obstacles/devil/devil_2.png";
    //spritesheet for devilAnimation
    public static String devilAnimation = "obstacles/devil/devil_animation.png";

    //Cloud-obstacle:
    public static String cloud = "obstacles/cloud.png";

    //power-ups and benefits:
    public static String coin = "coin.png";
    //public static String powerUp = "[path]";

    */

    public static void load(){
        //assetManager.load(gameBackground, Texture.class);

        //BACKGROUND
        backgroundHellTexture = new Texture("backgrounds/level_hell_score_background.png");
        backgroundTextureRegion = new TextureRegion(backgroundHellTexture, 0, 0, backgroundHellTexture.getWidth(), backgroundHellTexture.getHeight());

        backgroundHeavenTexture = new Texture("backgrounds/level_heaven_background.png");

        //PIG
        pigSpriteSheet = new Texture("characters/pig/pig_animation.png");
        Array<TextureRegion> pigFrames = makeFrames(pigSpriteSheet, 3, 2);
        pigAnimation = new Animation<TextureRegion>(0.2f, pigFrames.get(0), pigFrames.get(1), pigFrames.get(2), pigFrames.get(3), pigFrames.get(4), pigFrames.get(5), pigFrames.get(4), pigFrames.get(3), pigFrames.get(2), pigFrames.get(1));

        //BUNNY
        bunnySpriteSheet = new Texture("characters/bunny/bunny_animation.png");
        Array<TextureRegion> bunnyFrames = makeFrames(bunnySpriteSheet, 3, 2);
        bunnyAnimation = new Animation<TextureRegion>(0.2f, pigFrames.get(0), pigFrames.get(1), pigFrames.get(2), pigFrames.get(3), pigFrames.get(4), pigFrames.get(5), pigFrames.get(4), pigFrames.get(3), pigFrames.get(2), pigFrames.get(1));


        //COIN
        coinTexture = new Texture("powerups/coin.png");
        Array<TextureRegion> coinFrames = makeFrames(coinTexture, 6, 1);
        coinAnimation = new Animation<TextureRegion>(0.2f, coinFrames.get(0), coinFrames.get(1), coinFrames.get(2), coinFrames.get(3), coinFrames.get(4), coinFrames.get(5));


        //OBSTACLES
        //Balloons
        TextureRegion blueBalloonReg = new TextureRegion(new Texture(blueBalloon), 0, 0, new Texture(blueBalloon).getWidth(), new Texture(blueBalloon).getHeight());
        TextureRegion greenBalloonReg = new TextureRegion(new Texture(greenBalloon), 0, 0, new Texture(greenBalloon).getWidth(), new Texture(greenBalloon).getHeight());
        TextureRegion redBalloonReg = new TextureRegion(new Texture(redBalloon), 0, 0, new Texture(redBalloon).getWidth(), new Texture(redBalloon).getHeight());
        TextureRegion yellowBalloonReg = new TextureRegion(new Texture(yellowBalloon), 0, 0, new Texture(yellowBalloon).getWidth(), new Texture(yellowBalloon).getHeight());
        balloons.add(blueBalloonReg, greenBalloonReg, redBalloonReg, yellowBalloonReg);

        //Drone
        droneTexture = new TextureRegion(new Texture(drone), 0, 0, new Texture(drone).getWidth(), new Texture(drone).getHeight());

        //Buttons
        singlePlayerTexture= new Texture("buttons/singleplayer_button.PNG");
        multiPlayerTexture = new Texture("buttons/multiplayer_button.PNG");
        achievementsTexture = new Texture("buttons/achievements_button.PNG");
        highscoreListTexture = new Texture("buttons/highscorelist_button.PNG");
        settingsTexture = new Texture("buttons/settings_button.PNG");
        backTexture = new Texture("buttons/back_button.png");
        playTexture = new Texture("buttons/play.png");

        //Music and sounds
        // Sound from Zapsplat.com
        clickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/button_click_sound.wav"));
        // Music from Zapsplat.com
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/background_music.mp3"));
        backgroundMusic.setVolume(0.02f); //sets the volume of the background music
        backgroundMusic.setLooping(true); //the backgrounds music will continuously loop

    }


    public static Array<TextureRegion> makeFrames(Texture texture, int frameCountX, int frameCountY){
        Array<TextureRegion> frames = new Array<TextureRegion>();
        TextureRegion temp;
        int frameWidth = texture.getWidth() / frameCountX;
        int frameHeight = texture.getHeight() / frameCountY;
        for(int j = 0; j < frameCountY; j++){
            for (int i = 0; i < frameCountX; i++){
                temp = new TextureRegion(texture, i * frameWidth, j * frameHeight, frameWidth, texture.getHeight()/ frameCountY);
                frames.add(temp);
            }
        }
        return frames;

    }



    public void update(){

    }



    /*
    //TODO: metoder fra tubby for laste-state. Legger metodene her dersom vi skulle få bruk for det.
    public static float getProgress(){
        return assetManager.getProgress();
    }

    public static Texture getTexture(String tex){
        return assetManager.get(tex, Texture.class);
    }
     */


    /*
    //TODO: metode fra tubby for musikk og lyd, dersom vi skulle implementert det senere:
    music = Gdx.audio.newMusic(Gdx.files.internal("data/music.mp3"));
		music.setLooping(true);
		music.setVolume(0.5f);
		if (Settings.soundEnabled) music.play();
		jumpSound = Gdx.audio.newSound(Gdx.files.internal("data/jump.wav"));
		highJumpSound = Gdx.audio.newSound(Gdx.files.internal("data/highjump.wav"));
		hitSound = Gdx.audio.newSound(Gdx.files.internal("data/hit.wav"));
		coinSound = Gdx.audio.newSound(Gdx.files.internal("data/coin.wav"));
		clickSound = Gdx.audio.newSound(Gdx.files.internal("data/click.wav"));
     */




}
