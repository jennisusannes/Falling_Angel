package com.fallingangel.controller.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.fallingangel.game.FallingAngel;
import com.fallingangel.model.component.AngelComponent;
import com.fallingangel.model.component.TextureComponent;
import com.fallingangel.model.component.TransformComponent;

import java.util.Comparator;

public class RenderingSystem extends IteratingSystem{

    /**
     * RenderingSystem, used for drawing
     * **/


    static final float FRUSTUM_WIDTH = Gdx.graphics.getWidth();
    static final float FRUSTUM_HEIGHT = Gdx.graphics.getHeight();


    private SpriteBatch sb;
    private Array<Entity> renderQueue;
    private Comparator<Entity> comparator;
    private OrthographicCamera cam;

    private ComponentMapper<TextureComponent> textureMapper;
    private ComponentMapper<TransformComponent> transformMapper;


    public static float score = 0;


    public FallingAngel game = FallingAngel.getInstance();

    //Instanciate a RenderingSystem
    public RenderingSystem(SpriteBatch sb) {
        //creates a new iteratingSystem for the entities with following components
        super(Family.all(TransformComponent.class, TextureComponent.class).get());

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

        //creates a camera
        cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        cam.position.set(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2, 0);
    }

    //This method updates and draws
    @Override
    public void update(float deltaTime) {
        //updates the iteratingSystem
        super.update(deltaTime);

        //sorts the renderQueue based on comparator (z-axis)
        renderQueue.sort(comparator); //the entities are being sorted based on z-value


        cam.update();
        sb.begin();

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
        BitmapFont font = new BitmapFont();
        int scoreInt = (int) score;
        // TODO create an ID for every player
        this.game.FBI.setHighScore("Jy48548u9", "Jenni", "09.18.20", (int)AngelComponent.SCORE);
       // this.game.FBI.setOnValueChangedListener();

        String scoreString = String.valueOf(scoreInt);
        font.getData().setScale(5, 5);
        font.draw(sb, scoreString, 40,Gdx.graphics.getHeight() - 40);

        sb.end();
        renderQueue.clear();
    }


    //called at every render/update. Adds all entities from family in the queue
    @Override
    public void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }


    //getter for camera
    public OrthographicCamera getCamera() {
        return cam;
    }



}
