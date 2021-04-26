package com.fallingangel.controller.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.fallingangel.game.FallingAngel;
import com.fallingangel.controller.MainController;
import com.fallingangel.model.component.AngelComponent;
import com.fallingangel.model.Assets;
import com.fallingangel.model.component.TextureComponent;
import com.fallingangel.model.component.TransformComponent;

import java.util.Comparator;

public class RenderingSystem extends IteratingSystem{

    static final float FRUSTUM_WIDTH = Gdx.graphics.getWidth();
    static final float FRUSTUM_HEIGHT = Gdx.graphics.getHeight();


    private SpriteBatch sb;
    private Array<Entity> renderQueue;
    private Comparator<Entity> comparator;

    private ComponentMapper<TextureComponent> textureMapper;
    private ComponentMapper<TransformComponent> transformMapper;
    private MainController controller;
    private FallingAngel game;
    protected TextureRegion gameBackground;


    public static float score = 0;
    boolean isMultiplayer;


    //Instanciate a RenderingSystem
    public RenderingSystem(SpriteBatch sb, boolean isMultiplayer) {
        //creates a new iteratingSystem for the entities with following components
        super(Family.all(TransformComponent.class, TextureComponent.class).get());
        this.gameBackground = Assets.heavenMultiplayerBackgroundTextureRegion;

        this.isMultiplayer = isMultiplayer;

        //gets the components through a mapper
        textureMapper = ComponentMapper.getFor(TextureComponent.class);
        transformMapper = ComponentMapper.getFor(TransformComponent.class);

        //creates a renderqueue
        renderQueue = new Array<Entity>();

        //sorts based on z-value: the highest z-value will be the bottom layer of the screen
        comparator = new Comparator<Entity>() {
            @Override
            public int compare(Entity entityA, Entity entityB) {
                return (int)Math.signum(transformMapper.get(entityB).pos.z -
                        transformMapper.get(entityA).pos.z); //returns -1, 0 or 1 based on which z-value is the largest
            }
        };

        this.sb = sb;
        this.game = FallingAngel.getInstance();
        if (!isMultiplayer){
            this.controller = game.mc;
            this.gameBackground = controller.getGameBackground(); 
        }
    }

    // Sets chosen level
    public void setLevel(TextureRegion chosenLevel){
        this.gameBackground = chosenLevel;
    }

    // This method updates and draws
    @Override
    public void update(float deltaTime) {
        //updates the iteratingSystem
        super.update(deltaTime);

        //sorts the renderQueue based on comparator (z-axis)
        renderQueue.sort(comparator); //the entities are being sorted based on z-value

        //cam.update();
        sb.begin();
        sb.draw(gameBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //iterates through all entities and gets the component.
        for (Entity entity : renderQueue) {
            TextureComponent textureComponent = textureMapper.get(entity);
            TransformComponent transformComponent = transformMapper.get(entity);

            //checks if the component contains textureRegion
            if (textureComponent.textureRegion == null) {
                continue;
            }

            //updates the score, which is saved to the angelComponent
            if (entity.getComponent(AngelComponent.class) != null){
                score = entity.getComponent(AngelComponent.class).SCORE;
            }


           //width and height for the textures
            float width = textureComponent.textureRegion.getRegionWidth();
            float height = textureComponent.textureRegion.getRegionHeight();

            //draws the textures
            sb.draw(textureComponent.textureRegion, transformComponent.pos.x, transformComponent.pos.y, width, height);

        }

        //prints the updated score
        BitmapFont font = Assets.font;
        int scoreInt = (int) score;

        String scoreString = String.valueOf(scoreInt);
        font.getData().setScale(8, 8);
        font.draw(sb, scoreString, Gdx.graphics.getWidth()*0.3f,Gdx.graphics.getHeight()*0.981f);

        if (isMultiplayer){
            String opponentScoreString = Integer.toString(this.game.FBI.getOpponentScore());
            font.getData().setScale(8, 8);
            font.draw(sb, opponentScoreString, Gdx.graphics.getWidth()*0.85f, Gdx.graphics.getHeight()*0.981f);
        }

        sb.end();
        renderQueue.clear();
    }


    //called at every render/update. Adds all entities from family in the queue
    @Override
    public void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }





}
