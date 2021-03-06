package com.fallingangel.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Assets {

    // BACKGROUNDS
    public static Texture menuBackgroundTexture;
    public static Texture settingsBackgroundTexture;
    public static Texture highscorelistBackgroundTexture;
    public static Texture achievementsBackgroundTexture;
    public static Texture help1BackgroundTexture;
    public static Texture help2BackgroundTexture;
    public static Texture help3BackgroundTexture;
    public static Texture levelBackgroundTexture;
    public static Texture gameOverSingleBackgroundTexture;
    public static Texture gameOverMultiBackgroundTexture;
    public static Texture winner1BackgroundTexture;
    public static Texture winner2BackgroundTexture;

    // Game backgrounds
    public static TextureRegion heavenMultiplayerBackgroundTextureRegion;
    public static TextureRegion heavenBackgroundTextureRegion;
    public static TextureRegion sunsetBackgroundTextureRegion;
    public static TextureRegion hellBackgroundTextureRegion;
    public static TextureRegion heavenBackgroundPauseTextureRegion;
    public static TextureRegion sunsetBackgroundPauseTextureRegion;
    public static TextureRegion hellBackgroundPauseTextureRegion;

    // PIG
    public static Animation<TextureRegion> pigAnimation;
    public static Texture pigSpriteSheet;


    // BUNNY
    public static Animation<TextureRegion> bunnyAnimation;
    public static Texture bunnySpriteSheet;

    //POWER-UPS and COIN
    //Animation for coin:
    public static Animation<TextureRegion> coinAnimation;
    public static Texture coinTexture;


    //OBSTACLES
    // Balloons
    public static String blueBalloon = "obstacles/balloons/blue_balloon.png";
    public static String greenBalloon = "obstacles/balloons/green_balloon.png";
    public static String redBalloon = "obstacles/balloons/red_balloon.png";
    public static String yellowBalloon = "obstacles/balloons/yellow_balloon.png";
    public static Array<TextureRegion> balloons = new Array<TextureRegion>();
    // Drone
    public static String drone = "obstacles/drone.png";
    public static TextureRegion droneTexture;
    // Devil
    public static String devil = "obstacles/devil/devil_2.png";
    public static TextureRegion devilTexture;
    public static Animation<TextureRegion> devilAnimation;
    public static Texture devilSpriteSheet;

    // BUTTONS
    public static Texture singlePlayerButton;
    public static Texture multiPlayerButton;
    public static Texture achievementsButton;
    public static Texture highscorelistButton;
    public static Texture settingsButton;
    public static Texture helpButton;
    public static Texture backButton;
    public static Texture pauseButton;
    public static Texture exitButton;
    public static Texture resumeButton;
    public static Texture playAgainButton;
    public static Texture musicOnButton;
    public static Texture musicOffButton;
    public static Texture heavenButton;
    public static Texture sunsetButton;
    public static Texture hellButton;
    public static Texture bunnyButton;
    public static Texture pigButton;
    public static Texture selected;

    // MUSIC AND SOUNDS
    public static Music backgroundMusic;
    public static Sound clickSound;
    public static Sound coinSound;
    public static Sound collisionSound;
    //public static Sound tixSound;

    // FONT
    public static BitmapFont font;

    //might delete later
    public static Texture singlePlayerTexture;
    public static Texture multiPlayerTexture;
    public static Texture achievementsTexture;
    public static Texture highscoreListTexture;
    public static Texture settingsTexture;
    public static Texture backTexture;
    public static Texture playTexture;

    // TEXT
    // Waiting room
    public static Texture waitingRoomTexture;

    // Game Over Multiplayer
    public static Texture youLose;
    public static Texture youWin;
    public static Texture tie;
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

        // BACKGROUNDS
        menuBackgroundTexture = new Texture("backgrounds/menu/mainmenu_background.png");
        settingsBackgroundTexture = new Texture("backgrounds/settings/settings_text_background.png");
        highscorelistBackgroundTexture = new Texture("backgrounds/highscorelist/highscorelist_score_background.png");
        achievementsBackgroundTexture = new Texture("backgrounds/achievements/achievements_background.png");
        help1BackgroundTexture = new Texture("backgrounds/help/help1_background.png");
        help2BackgroundTexture = new Texture("backgrounds/help/help2_background.png");
        help3BackgroundTexture = new Texture("backgrounds/help/help3_background.png");
        levelBackgroundTexture = new Texture("backgrounds/level/level_select_background.png");
        gameOverSingleBackgroundTexture = new Texture("backgrounds/gameover/gameover_single_background.png");
        gameOverMultiBackgroundTexture = new Texture("backgrounds/gameover/gameover_multi_background.png");;
        winner1BackgroundTexture = new Texture("backgrounds/winner/winner_player1_background.png");
        winner2BackgroundTexture = new Texture("backgrounds/winner/winner_player2_background.png");


        // Game backgrounds
        Texture heavenMultiplayerBackgroundTexture = new Texture("backgrounds/level/level_heaven_multiplayer_score_background.png");
        heavenMultiplayerBackgroundTextureRegion = new TextureRegion(heavenMultiplayerBackgroundTexture, 0, 0, heavenMultiplayerBackgroundTexture.getWidth(), heavenMultiplayerBackgroundTexture.getHeight());
        Texture heavenBackgroundTexture = new Texture("backgrounds/level/level_heaven_score_background.png");
        heavenBackgroundTextureRegion = new TextureRegion(heavenBackgroundTexture, 0, 0, heavenBackgroundTexture.getWidth(), heavenBackgroundTexture.getHeight());
        Texture sunsetBackgroundTexture = new Texture("backgrounds/level/level_sunset_score_background.png");
        sunsetBackgroundTextureRegion = new TextureRegion(sunsetBackgroundTexture, 0, 0, sunsetBackgroundTexture.getWidth(), sunsetBackgroundTexture.getHeight());
        Texture hellBackgroundTexture = new Texture("backgrounds/level/level_hell_score_background.png");
        hellBackgroundTextureRegion = new TextureRegion(hellBackgroundTexture, 0, 0, hellBackgroundTexture.getWidth(), hellBackgroundTexture.getHeight());

        // Game backgrounds without score
        Texture heavenBackgroundPauseTexture = new Texture("backgrounds/level/level_heaven_background.png");
        heavenBackgroundPauseTextureRegion = new TextureRegion(heavenBackgroundPauseTexture, 0, 0, heavenBackgroundTexture.getWidth(), heavenBackgroundTexture.getHeight());
        Texture sunsetBackgroundPauseTexture = new Texture("backgrounds/level/level_sunset_background.png");
        sunsetBackgroundPauseTextureRegion = new TextureRegion(sunsetBackgroundPauseTexture, 0, 0, sunsetBackgroundTexture.getWidth(), sunsetBackgroundTexture.getHeight());
        Texture hellBackgroundPauseTexture = new Texture("backgrounds/level/level_hell_background.png");
        hellBackgroundPauseTextureRegion = new TextureRegion(hellBackgroundPauseTexture, 0, 0, hellBackgroundTexture.getWidth(), hellBackgroundTexture.getHeight());


        //PIG
        pigSpriteSheet = new Texture("characters/pig/pig_animation.png");
        Array<TextureRegion> pigFrames = makeFrames(pigSpriteSheet, 3, 2);
        pigAnimation = new Animation<TextureRegion>(0.2f, pigFrames.get(0), pigFrames.get(1), pigFrames.get(2), pigFrames.get(3), pigFrames.get(4), pigFrames.get(5), pigFrames.get(4), pigFrames.get(3), pigFrames.get(2), pigFrames.get(1));

        //BUNNY
        bunnySpriteSheet = new Texture("characters/bunny/bunny_resize2.png");
        Array<TextureRegion> bunnyFrames = makeFrames(bunnySpriteSheet, 3, 2);
        bunnyAnimation = new Animation<TextureRegion>(0.2f, bunnyFrames.get(0), bunnyFrames.get(1), bunnyFrames.get(2), bunnyFrames.get(3), bunnyFrames.get(4), bunnyFrames.get(5), bunnyFrames.get(4), bunnyFrames.get(3), bunnyFrames.get(2), bunnyFrames.get(1));


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

        // Devil
        devilTexture = new TextureRegion(new Texture(devil),0,0, new Texture(devil).getWidth(), new Texture(devil).getHeight());
        devilSpriteSheet = new Texture("obstacles/devil/devil_animation.png");
        Array<TextureRegion> devilFrames = makeFrames(devilSpriteSheet, 2, 1);
        devilAnimation = new Animation<TextureRegion>(0.2f, devilFrames.get(0), devilFrames.get(1), devilFrames.get(0));

        // BUTTONS
        singlePlayerButton = new Texture("buttons/menu/singleplayer_button.PNG");
        multiPlayerButton = new Texture("buttons/menu/multiplayer_button.PNG");
        achievementsButton = new Texture("buttons/menu/achievements_button.PNG");
        highscorelistButton = new Texture("buttons/menu/highscorelist_button.PNG");
        settingsButton = new Texture("buttons/menu/settings_button.PNG");
        helpButton = new Texture("buttons/menu/help_button.PNG");
        backButton = new Texture("buttons/actions/back_button.png");
        pauseButton = new Texture("buttons/actions/pause_button.PNG");
        exitButton = new Texture("buttons/actions/exit_button.PNG");
        resumeButton = new Texture("buttons/actions/resume_button.PNG");
        playAgainButton = new Texture("buttons/actions/play_again_button.png");
        musicOnButton = new Texture("buttons/actions/music_on_button.png");
        musicOffButton = new Texture("buttons/actions/music_off_button.png");
        heavenButton = new Texture("buttons/level/level_heaven_button.png");
        sunsetButton = new Texture("buttons/level/level_sunset_button.png");
        hellButton = new Texture("buttons/level/level_hell_button.png");
        bunnyButton = new Texture("buttons/character/select_bunny_button.PNG");
        pigButton = new Texture("buttons/character/select_pig_button.PNG");
        selected = new Texture("buttons/character/selected.png");


        // MUSIC AND SOUNDS
        //tixSound = Gdx.audio.newSound(Gdx.files.internal("sounds/tix_sound.wav"));
        //tixSound.setPitch(tixSound.play(),0.6f);
        // Sound from Zapsplat.com
        coinSound = Gdx.audio.newSound(Gdx.files.internal("sounds/coin_sound.wav"));
        clickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/button_click_sound.wav"));
        collisionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/game_over_sound.wav"));
        // Music from Zapsplat.com
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/background_music.mp3"));
        backgroundMusic.setVolume(0.2f); // Sets volume of music
        backgroundMusic.setLooping(true); // Music will continuously loop

        // TEXT
        // Waiting room
        waitingRoomTexture = new Texture("text/waiting_room_text.png");

        // Game Over Multiplayer
        youLose = new Texture("text/lose_text.png");
        youWin = new Texture("text/win_text.png");
        tie = new Texture("text/tie_text.png");


        // FONT
        font = new BitmapFont(Gdx.files.internal("font/retro_game_font.fnt"),Gdx.files.internal("font/retro_game_font.png"), false);
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


}
