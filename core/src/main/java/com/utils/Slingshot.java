package com.utils;

import com.actors.BasicBird;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.levels.LevelManager;

import java.io.Serializable;

public class Slingshot extends Actor implements Serializable {
    private BasicBird bird;
    private final Vector2 BirdInitialPosition;
    private final Vector2 BirdCurrentPosition;
    private final Vector2 slingshotInitialPosition;
    private boolean isDragging = false;
    private transient Texture slingshotTexture;
    private transient ShapeRenderer shapeRenderer;
    private final float velocityScale;
    private final int constraintRadius;
    private boolean paused;
    private LevelManager level;
    private final float width, height;

    public void initializeNonSerializableFields(World world, Stage stage, LevelManager level){
//        bird.initializeNonSerializableFields(world, stage);
        slingshotTexture = new Texture(Gdx.files.internal("1Assets/Slingshot.png"));
        this.shapeRenderer = new ShapeRenderer();

        this.level = level;
        setSize(width, height);
        setPosition(BirdInitialPosition.x - width / 2, BirdInitialPosition.y - height / 2);
        AddListener();
    }

    public Slingshot(BasicBird bird, Vector2 birdInitialPosition, Vector2 slingshotInitialPosition, float width, float height, LevelManager level) {
        this.bird = bird;
        paused = false;
        this.BirdInitialPosition = new Vector2(birdInitialPosition);
        this.BirdCurrentPosition = new Vector2(birdInitialPosition);
        this.slingshotInitialPosition = new Vector2(slingshotInitialPosition);
        this.slingshotTexture = new Texture(Gdx.files.internal("1Assets/Slingshot.png"));
        this.shapeRenderer = new ShapeRenderer();
        this.velocityScale = 0.3f;
        this.constraintRadius = 100;
        this.level = level;
        this.width = width;
        this.height = height;
        setSize(width, height);
        setPosition(birdInitialPosition.x - width / 2, birdInitialPosition.y - height / 2);
        AddListener();
    }

    private void AddListener(){
        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Vector2 worldCoords = new Vector2(event.getStageX(), event.getStageY());
                if (worldCoords.dst(Slingshot.this.bird.getPosition()) < 300 && !Slingshot.this.bird.getLaunched() && !paused) {
                    isDragging = true;
                    BirdCurrentPosition.set(worldCoords);
                    return true;
                }
                return false;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                if (isDragging && !paused) {
                    Vector2 worldCoords = new Vector2(event.getStageX(), event.getStageY());
                    constrainToSemicircle(worldCoords);
                    BirdCurrentPosition.set(worldCoords);
                    Slingshot.this.bird.setPosition(BirdCurrentPosition.x, BirdCurrentPosition.y);
                }
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (isDragging) {
                    isDragging = false;
                    float velocity = BirdInitialPosition.dst(BirdCurrentPosition) * velocityScale;
                    Vector2 InitialPositionCopy = new Vector2(BirdInitialPosition);
                    float angle = InitialPositionCopy.sub(BirdCurrentPosition).angleRad();

                    Slingshot.this.bird.launch(velocity, angle);
                    Slingshot.this.level.decrementBirds();
                }
            }
        });
    }

    public void togglePaused(boolean var){
        paused = var;
    }

    public void updateBird(BasicBird newBird) {
        this.bird = newBird;
    }

    private void constrainToSemicircle(Vector2 point) {
        Vector2 delta = new Vector2(point).sub(BirdInitialPosition);

        float distance = delta.len();

        if (distance > this.constraintRadius) {
            delta.setLength(this.constraintRadius);
        }

        if (delta.angleDeg() > 90 && delta.angleDeg() < 270) {
            point.set(BirdInitialPosition).add(delta);
        } else {
            point.set(BirdInitialPosition).add(new Vector2(-this.constraintRadius, 0));
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float scaledWidth = getWidth()*0.40f;
        float scaledHeight = getHeight()*0.40f;
        batch.draw(
            slingshotTexture,
            slingshotInitialPosition.x - scaledWidth,
            slingshotInitialPosition.y - scaledHeight,
            scaledWidth,
            scaledHeight
        );

        if (isDragging && !paused) {
            batch.end();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

            shapeRenderer.setColor(1, 0, 0, 1);

            drawTrajectory(level.getBatch());

            shapeRenderer.end();
            batch.begin();
        }
    }

    private void drawTrajectory(Batch batch) {

        Pixmap pixmap = new Pixmap(5, 5, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fillRectangle(0, 0, 5, 5);
        Texture dotTexture = new Texture(pixmap);
        pixmap.dispose();

        float velocity = BirdInitialPosition.dst(BirdCurrentPosition) * velocityScale;
        Vector2 InitialPositionCopy = new Vector2(BirdInitialPosition);
        float angle = InitialPositionCopy.sub(BirdCurrentPosition).angleRad();
        float velocityX = velocity * (float) Math.cos(angle);
        float velocityY = velocity * (float) Math.sin(angle);
        float gravity = 9.8f;
        float timeStep = 0.1f;
        int maxSteps = 5000;

        float halfScreenWidth = Gdx.graphics.getWidth() / 3f;

        Vector2 startPoint = new Vector2(BirdCurrentPosition);

        for (int i = 1; i <= maxSteps; i++) {
            float t = i * timeStep;

            float x = startPoint.x + velocityX * t;
            float y = startPoint.y + velocityY * t - 0.5f * gravity * t * t;

            if (y < 0 || x > halfScreenWidth) break;

            Vector2 nextPoint = new Vector2(x, y);

            batch.draw(dotTexture, nextPoint.x - 2.5f, nextPoint.y - 2.5f, 5, 5);

            startPoint = nextPoint;
        }
    }


    public void dispose() {
        slingshotTexture.dispose();
        shapeRenderer.dispose();
    }
}
