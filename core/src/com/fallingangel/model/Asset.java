package com.fallingangel.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Asset {


    //BACKGROUNDS:

    //play-view background
    public static TextureRegion backgroundTextureRegion;


    //PIG
    //Animation for pig:
    public static Animation<TextureRegion> pigAnimation;
    //Pig-frames:
    public static String pigFrame1 = "characters/pig/pig_animation_1.PNG";
    public static String pigFrame2 = "characters/pig/pig_animation_2.PNG";
    public static String pigFrame3 = "characters/pig/pig_animation_3.PNG";
    public static String pigFrame4 = "characters/pig/pig_animation_4.PNG";
    public static String pigFrame5 = "characters/pig/pig_animation_5.PNG";
    public static String pigFrame6 = "characters/pig/pig_animation_6.PNG";
    //public static String pigSpriteSheet = "characters/pig/pig_animation.png";
    public static Texture pigSpriteSheet;


    //COIN
    //Animation for coin:
    public static Animation<TextureRegion> coinAnimation;
    //public static TextureRegion coinTextureRegion;


    //OBSTACLES
    //Balloons
    public static String blueBalloon = "obstacles/balloons/blue_balloon.png";
    public static String greenBalloon = "obstacles/balloons/green_balloon.png";
    public static String redBalloon = "obstacles/balloons/red_balloon.png";
    public static String yellowBalloon = "obstacles/balloons/yellow_balloon.png";
    public static Array<TextureRegion> balloons = new Array<TextureRegion>();

    //Plane
    public static String plane = "obstacles/plane.png";
    public static TextureRegion planeTexture;
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

        //BACKGROUND
        Texture backgroundTexture = new Texture("heaven_game_background.PNG");
        backgroundTextureRegion = new TextureRegion(backgroundTexture, 0, 0, backgroundTexture.getWidth(), backgroundTexture.getHeight());


        //PIG
        /*
        TextureRegion pigFrame1Tex = new TextureRegion(new Texture(pigFrame1), 0, 0, new Texture(pigFrame1).getWidth() / 3, new Texture(pigFrame1).getHeight() / 3);
        TextureRegion pigFrame2Tex = new TextureRegion(new Texture(pigFrame2), 0, 0, new Texture(pigFrame2).getWidth() / 3, new Texture(pigFrame2).getHeight() / 3);
        TextureRegion pigFrame3Tex = new TextureRegion(new Texture(pigFrame3), 0, 0, new Texture(pigFrame3).getWidth() / 3, new Texture(pigFrame3).getHeight() / 3);
        TextureRegion pigFrame4Tex = new TextureRegion(new Texture(pigFrame4), 0, 0, new Texture(pigFrame4).getWidth() / 3, new Texture(pigFrame4).getHeight() / 3);
        TextureRegion pigFrame5Tex = new TextureRegion(new Texture(pigFrame5), 0, 0, new Texture(pigFrame5).getWidth() / 3, new Texture(pigFrame5).getHeight() / 3);
        TextureRegion pigFrame6Tex = new TextureRegion(new Texture(pigFrame6), 0, 0, new Texture(pigFrame6).getWidth() / 3, new Texture(pigFrame6).getHeight() / 3);
        pigAnimation = new Animation<TextureRegion>(0.2f, pigFrame1Tex, pigFrame2Tex, pigFrame3Tex, pigFrame4Tex, pigFrame5Tex, pigFrame6Tex, pigFrame5Tex, pigFrame4Tex, pigFrame3Tex, pigFrame2Tex);
*/
        pigSpriteSheet = new Texture("characters/pig/pig_animation.png");
        Array<TextureRegion> pigFrames = makeFrames(pigSpriteSheet, 3, 2);
        pigAnimation = new Animation<TextureRegion>(0.2f, pigFrames.get(0), pigFrames.get(1), pigFrames.get(2), pigFrames.get(3), pigFrames.get(4), pigFrames.get(5), pigFrames.get(4), pigFrames.get(3), pigFrames.get(2), pigFrames.get(1));


        //COIN
        //coinTextureRegion = new TextureRegion(new Texture("coin.png"), 0, 0, Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/3);
        Texture coinTexture = new Texture("coin.png");
        Array<TextureRegion> coinFrames = makeFrames(coinTexture, 6, 1);
        coinAnimation = new Animation<TextureRegion>(0.2f, coinFrames.get(0), coinFrames.get(1), coinFrames.get(2), coinFrames.get(3), coinFrames.get(4), coinFrames.get(5));
        //coinAnimation.setPlayMode(Animation.PlayMode.LOOP);


        //OBSTACLES
        //Balloons
        TextureRegion blueBalloonReg = new TextureRegion(new Texture(blueBalloon), 0, 0, new Texture(blueBalloon).getWidth(), new Texture(blueBalloon).getHeight());
        TextureRegion greenBalloonReg = new TextureRegion(new Texture(greenBalloon), 0, 0, new Texture(greenBalloon).getWidth(), new Texture(greenBalloon).getHeight());
        TextureRegion redBalloonReg = new TextureRegion(new Texture(redBalloon), 0, 0, new Texture(redBalloon).getWidth(), new Texture(redBalloon).getHeight());
        TextureRegion yellowBalloonReg = new TextureRegion(new Texture(yellowBalloon), 0, 0, new Texture(yellowBalloon).getWidth(), new Texture(yellowBalloon).getHeight());
        balloons.add(blueBalloonReg, greenBalloonReg, redBalloonReg, yellowBalloonReg);

        //Plane
        planeTexture = new TextureRegion(new Texture(plane), 0, 0, new Texture(plane).getWidth(), new Texture(plane).getHeight());

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
