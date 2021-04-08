package com.fallingangel.controller.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.fallingangel.model.component.TextureComponent;
import com.fallingangel.model.component.TransformComponent;

import java.util.Comparator;

public class RenderingSystem extends IteratingSystem{


    //Skjønner ikke helt hva "FRUSTUM" betyr ...
    //skjønner ikke helt bruken (?)
    static final float FRUSTUM_WIDTH = 10;
    static final float FRUSTUM_HEIGHT = 15;
    static final float PIXELS_TO_METRES = 1.0f / 32.0f;

    private SpriteBatch sb;
    private Array<Entity> renderQueue;
    private Comparator<Entity> comparator;
    private OrthographicCamera cam;

    private ComponentMapper<TextureComponent> textureM;
    private ComponentMapper<TransformComponent> transformM;


    //Ser at denne konstruktøren tar inn family som argument, mens konstruktøren i SuperJumper tar inn sb
    /*
    public RenderingSystem(Family family) {
        super(family);
    }

     */


    public RenderingSystem(SpriteBatch sb) {
        super(Family.all(TransformComponent.class, TextureComponent.class).get());

        textureM= ComponentMapper.getFor(TextureComponent.class);
        transformM = ComponentMapper.getFor(TransformComponent.class);

        renderQueue = new Array<Entity>();

        //sorts based on z-value. the highest z-value will be in the back of the screen.
        comparator = new Comparator<Entity>() {
            @Override
            public int compare(Entity entityA, Entity entityB) {
                return (int)Math.signum(transformM.get(entityB).pos.z -
                        transformM.get(entityA).pos.z); //returns -1, 0 or 1 based on which z-value is the largest
            }
        };

        this.sb = sb;

        cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        cam.position.set(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2, 0);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        renderQueue.sort(comparator); //the entities are being sorted based on z-value

        cam.update();
        //sb.setProjectionMatrix(cam.combined); //denne zoomer inn kameraet, vet ikke om den skal brukes enda
        sb.begin();

        //0 bakgrunn, 1 plane, 2 obstacle (balloon), 3 angel,
        sb.draw(renderQueue.get(4).getComponent(TextureComponent.class).textureRegion, 0, 0, 800, 800);

        for (Entity entity : renderQueue) {
            TextureComponent tex = textureM.get(entity);

            if (tex.textureRegion == null) {
                continue;
            }

            TransformComponent t = transformM.get(entity);


            float width = tex.textureRegion.getRegionWidth();
            float height = tex.textureRegion.getRegionHeight();
            float originX = width * 0.5f;
            float originY = height * 0.5f;

            /*
            sb.draw(tex.textureRegion,
                    t.pos.x - originX, t.pos.y - originY,
                    originX, originY,
                    width, height,
                    t.scale.x * PIXELS_TO_METRES, t.scale.y * PIXELS_TO_METRES,
                    MathUtils.radiansToDegrees * t.rotation);*/

            //sb.draw(tex.textureRegion, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        }

        sb.end();
        renderQueue.clear();
    }


    @Override
    public void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }


    //Getter for camera
    public OrthographicCamera getCamera() {
        return cam;
    }



}
