package com.fallingangel.controller.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.fallingangel.model.component.TextureComponent;
import com.fallingangel.model.component.TransformComponent;

import java.util.Comparator;

public class RenderingSystem extends IteratingSystem{

    //RenderingSystem


    static final float FRUSTUM_WIDTH = 10;
    static final float FRUSTUM_HEIGHT = 15;
    static final float PIXELS_TO_METRES = 1.0f / 32.0f;

    private SpriteBatch sb;
    private Array<Entity> renderQueue;
    private Comparator<Entity> comparator;
    private OrthographicCamera cam;

    private ComponentMapper<TextureComponent> textureMapper;
    private ComponentMapper<TransformComponent> transformMapper;


    //Instanciate a RenderingSystem
    public RenderingSystem(SpriteBatch sb) {
        //Creates a new iteratingSystem for the entities with following components
        super(Family.all(TransformComponent.class, TextureComponent.class).get());

        //Gets the components through a mapper
        textureMapper = ComponentMapper.getFor(TextureComponent.class);
        transformMapper = ComponentMapper.getFor(TransformComponent.class);

        //Creates a renderqueue
        renderQueue = new Array<Entity>();

        //sorts based on z-value: the highest z-value will be in the back of the screen (Det bakerste laget)
        comparator = new Comparator<Entity>() {
            @Override
            public int compare(Entity entityA, Entity entityB) {
                return (int)Math.signum(transformMapper.get(entityB).pos.z -
                        transformMapper.get(entityA).pos.z); //returns -1, 0 or 1 based on which z-value is the largest
            }
        };

        this.sb = sb;

        // ?
        cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        cam.position.set(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2, 0);
    }

    //This method updates and draws
    @Override
    public void update(float deltaTime) {
        //Updates the iteratingSystem
        super.update(deltaTime);

        //Sort the renderQueue based on comparator (z-axis)
        renderQueue.sort(comparator); //the entities are being sorted based on z-value


        cam.update();
        //denne zoomer inn kameraet, vet ikke om den skal brukes enda
        //sb.setProjectionMatrix(cam.combined);
        sb.begin();

        //iterates through all entities and gets the component.
        for (Entity entity : renderQueue) {
            TextureComponent textureComponent = textureMapper.get(entity);
            TransformComponent transformComponent = transformMapper.get(entity);

            //Check if contains textureRegion
            if (textureComponent.textureRegion == null) {
                continue;
            }


            //Not used yet
            float width = textureComponent.textureRegion.getRegionWidth();
            float height = textureComponent.textureRegion.getRegionHeight();
            float originX = width * 0.5f;
            float originY = height * 0.5f;

            //TODO: create methods for drawing
            /*
            sb.draw(textureComponent.textureRegion,
                    transformComponent.pos.x - originX, transformComponent.pos.y - originY,
                    originX, originY,
                    width, height,
                    transformComponent.scale.x * PIXELS_TO_METRES, transformComponent.scale.y * PIXELS_TO_METRES,
                    MathUtils.radiansToDegrees * transformComponent.rotation);*/

            //Working code, remove when method is up and running:
            sb.draw(textureComponent.textureRegion, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        }

        sb.end();
        renderQueue.clear();
    }


    //called at every render/ update. Adds all entities from family in the queue
    @Override
    public void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }


    //Getter for camera
    public OrthographicCamera getCamera() {
        return cam;
    }



}
