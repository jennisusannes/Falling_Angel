package com.fallingangel.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Asset {

    public static Texture backgroundTexture;

    //Animation for pig:
    public static Animation<Texture> pigAnimation;

    //Pig-frames:
    public static String pigFrame1 = "characters/pig/pig_animation_1.PNG";
    public static String pigFrame2 = "characters/pig/pig_animation_2.PNG";
    public static String pigFrame3 = "characters/pig/pig_animation_3.PNG";
    public static String pigFrame4 = "characters/pig/pig_animation_4.PNG";
    public static String pigFrame5 = "characters/pig/pig_animation_5.PNG";
    public static String pigFrame6 = "characters/pig/pig_animation_6.PNG";


    //Animation for coin:
    public static Animation<TextureRegion> coinAnimation;
    public static Texture coinTexture = new Texture("coin.png");





    //Spillets bakgrunn, bruker samme gjennom hele spillet:
    //public static String gameBackground = "BackgroundSky.png";
/*


    //PNGs for select character siden:
    public static String pig_select_character = "characters/pig/pig_select_character.PNG";

    //Devil obstacles:
    public static String devil1 = "obstacles/devil/devil_1.png";
    public static String devil2 = "obstacles/devil/devil_2.png";
    //spritesheet for devilAnimation
    public static String devilAnimation = "obstacles/devil/devil_animation.png";

    //Cloud-obstacle:
    public static String cloud = "obstacles/cloud.png";

    //Plane obstacle
    public static String plane = "obstacles/plane.png";

    //balloon obstacles
    public static String blueBalloon = "obstacles/balloons/blue_balloon.png";
    public static String greenBalloon = "obstacles/balloons/green_balloon.png";
    public static String redBalloon = "obstacles/balloons/red_balloon.png";
    public static String yellowBalloon = "obstacles/balloons/yellow_balloon.png";

    //power-ups and benefits:
    public static String coin = "coin.png";
    //public static String powerUp = "[path]";


    //importerer assetManager for å håndtere innlasting av filer.
    private static AssetManager assetManager = new AssetManager();




    public Asset(){
        //this.assetManager = new AssetManager();
    }


    public static void dispose(){
        assetManager.dispose();
    }
    */
    public static void load(){
        //assetManager.load(gameBackground, Texture.class);
        backgroundTexture = new Texture("BackgroundSky.png");

        pigAnimation = new Animation<Texture>(0.2f, new Texture(pigFrame1), new Texture(pigFrame2), new Texture(pigFrame3), new Texture(pigFrame4), new Texture(pigFrame5), new Texture(pigFrame6));

        Array<TextureRegion> coinFrames = makeFrames(new Texture("coin.png"), 5);
        coinAnimation = new Animation<TextureRegion>(0.2f, coinFrames.get(0), coinFrames.get(1), coinFrames.get(2), coinFrames.get(3), coinFrames.get(4));
        coinAnimation.setPlayMode(Animation.PlayMode.LOOP);



        //"characters/pig/pig_animation.png"
    }


    //Helpmethod in order to create a list of frames. Used in order to create animations.
    public static Array<TextureRegion> makeFrames(Texture texture, int frameCount){
        Array<TextureRegion> frames = new Array<TextureRegion>();
        TextureRegion temp;
        int frameWidth = texture.getWidth() / frameCount;
        for(int i = 0; i < frameCount; i++){
            temp = new TextureRegion(texture, i * frameWidth, 0, frameWidth, texture.getHeight());
            frames.add(temp);
        }
        return frames;

    }

/*
    public static Texture getTexture(String textureName){
        //return assetManager.get(textureName, Texture.class);
        return
    }*/

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
