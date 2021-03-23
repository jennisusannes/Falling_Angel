package com.fallingangel.model.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.fallingangel.model.component.TextureComponent;
import com.fallingangel.model.component.TransformComponent;

import java.util.Comparator;

public class RenderingSystem extends IteratingSystem{

    //Lurer på om denne enten skal være på Controller eller på View... --> se nærmere på hva denne gjør


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

        textureM = ComponentMapper.getFor(TextureComponent.class);
        transformM = ComponentMapper.getFor(TransformComponent.class);

        renderQueue = new Array<Entity>();

        comparator = new Comparator<Entity>() {
            @Override
            public int compare(Entity entityA, Entity entityB) {
                return (int)Math.signum(transformM.get(entityB).pos.z -
                        transformM.get(entityA).pos.z);
            }
        };

        this.sb = sb;

        cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        cam.position.set(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2, 0);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        renderQueue.sort(comparator);

        cam.update();
        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        for (Entity entity : renderQueue) {
            TextureComponent tex = textureM.get(entity);

            if (tex.region == null) {
                continue;
            }

            TransformComponent t = transformM.get(entity);

            float width = tex.region.getRegionWidth();
            float height = tex.region.getRegionHeight();
            float originX = width * 0.5f;
            float originY = height * 0.5f;

            sb.draw(tex.region,
                    t.pos.x - originX, t.pos.y - originY,
                    originX, originY,
                    width, height,
                    t.scale.x * PIXELS_TO_METRES, t.scale.y * PIXELS_TO_METRES,
                    MathUtils.radiansToDegrees * t.rotation);
        }

        sb.end();
        renderQueue.clear();
    }


    //Her har de renderQueue --> Jeg lurer på om dette skal være i controlleren. Ganske usikker.
    @Override
    public void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }


    //Getter for camera
    public OrthographicCamera getCamera() {
        return cam;
    }



}
