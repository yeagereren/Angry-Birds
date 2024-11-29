package com.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import java.io.Serializable;
import java.util.ArrayList;

public class RedBird extends BasicBird implements Serializable {
    private boolean speedReduced;
    private float speedRestoreTimer;
    private transient ArrayList<Shockwave> ShockWaves = new ArrayList<>();
    private transient ShapeRenderer shapeRenderer;
    private float shockwaveTimer;
    private int shockwaveCount;

    public void initializeNonSerializableFields(World world, Stage stage, boolean parameter){
        super.initializeNonSerializableFields(world, stage, parameter);
        body.setUserData(this);
        this.Face = new Texture("Birds/red_bird.png");
        shapeRenderer = new ShapeRenderer();
        ShockWaves = new ArrayList<>();
    }

    public RedBird(World world, int posX, int posY, Stage stage) {
        super(world, posX, posY, "Birds/red_bird.png", stage);
        speedReduced = false;
        speedRestoreTimer = 0f;
        shapeRenderer = new ShapeRenderer();
        shockwaveTimer = 0f;
        shockwaveCount = 0;
        body.setUserData(this);
    }

    private static class Shockwave implements Serializable{
        Vector2 origin;
        float radius;
        float alpha;
        float maxRadius;

        Shockwave(Vector2 origin, float maxRadius) {
            this.origin = origin;
            this.radius = 0;
            this.alpha = 1.0f;
            this.maxRadius = maxRadius;
        }
    }

    @Override
    public void SpecialFunctionality() {
        if (Math.abs(getBody().getLinearVelocity().y) > 0.01f && !getAbilityUsed()) {
//            super.SpecialFunctionality();
            setAbilityUsed();
            shockwaveTimer = 0f;
            shockwaveCount = 0;
            applyShockwave();
        }
    }

    private void applyShockwave() {
        Array<Body> allBodies = new Array<>();
        world.getBodies(allBodies);

        if (!speedReduced) {
            Vector2 currentVelocity = body.getLinearVelocity();
            body.setLinearVelocity(currentVelocity.scl(0.5f));
            speedReduced = true;
            speedRestoreTimer = 1.0f;
        }

        for (Body otherBody : allBodies) {
            if (otherBody == this.body) continue;

            Vector2 otherPosition = otherBody.getPosition();
            Vector2 direction = otherPosition.sub(this.body.getPosition());
            float distance = direction.len();

            if (distance < 20.0f) {
                direction.nor();
                Vector2 force = direction.scl(10000 / (distance + 1e-5f));
                otherBody.applyForceToCenter(force, true);
                if(otherBody.getUserData() instanceof Pig){
                    Pig pig = (Pig)otherBody.getUserData();
                    pig.applyDamage(50f);
                }
            }
        }
    }

    @Override
    public void update(float delta) {
//        super.update(delta);
        if (speedReduced) {
            speedRestoreTimer -= delta;
            if (speedRestoreTimer <= 0) {
                Vector2 currentVelocity = body.getLinearVelocity();
                body.setLinearVelocity(currentVelocity.scl(2.0f));
                speedReduced = false;
            }
        }

        if (getAbilityUsed() && shockwaveCount < 3) {
            shockwaveTimer += delta;
            if (shockwaveTimer >= 0.3f) {
                float maxRadius = 200 + shockwaveCount * 50;
                ShockWaves.add(new Shockwave(this.body.getPosition().cpy().scl(PPM).add(width / 2f, height / 2f), maxRadius));
                shockwaveCount++;
                shockwaveTimer = 0f;
            }
        }
    }

    public void renderShockwaves(ShapeRenderer shapeRenderer, float delta) {
        if(ShockWaves==null){
            return;
        }
        for (int i = ShockWaves.size() - 1; i >= 0; i--) {
            Shockwave wave = ShockWaves.get(i);

            wave.radius += 200 * delta;

            if (wave.radius >= wave.maxRadius) {
                ShockWaves.remove(i);
                continue;
            }

            wave.alpha -= delta * 0.5f;
            wave.alpha = Math.max(wave.alpha, 0f);

            shapeRenderer.setColor(1, 0, 0, wave.alpha);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.circle(wave.origin.x, wave.origin.y, wave.radius);
            shapeRenderer.end();
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        batch.end();
        shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
        if (Math.abs(getBody().getLinearVelocity().y) > 0.01f) {
            renderShockwaves(shapeRenderer, Gdx.graphics.getDeltaTime());
        }
        batch.begin();
    }

    @Override
    public void dispose() {
        super.dispose();
        shapeRenderer.dispose();
    }
}
