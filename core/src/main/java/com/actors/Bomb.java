package com.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.levels.LevelManager;

import java.io.Serializable;

public class Bomb extends BasicBird implements Serializable {
    private boolean isShrinkingCompleted = false;
    private float shrinkFactor = 1f;
    private transient TextureRegion FaceTextureRegion;
    private transient ParticleEffect explosionEffect;
    private boolean isExploding = false;
    public LevelManager level;
    private transient Body explosionSensorBody;
    private float explosionBodyTimer;

    public void initializeNonSerializableFields(World world, Stage stage, boolean parameter){
        if(explosionBodyTimer>=0.25){
            super.initializeNonSerializableFields(world);
            this.stage = stage;
        } else {
            super.initializeNonSerializableFields(world, stage, parameter);
            body.setUserData(this);
        }
        this.Face = new Texture("Birds/bomb.png");

        FaceTextureRegion = new TextureRegion(Face);
        explosionEffect = new ParticleEffect();
        explosionEffect.load(Gdx.files.internal("Effects/explosion.p"), Gdx.files.internal("Effects/"));
    }

    public Bomb(World world, int posX, int posY, Stage stage, LevelManager level) {
        super(world, posX, posY, "Birds/bomb.png", stage);
        body.setUserData(this);

        FaceTextureRegion = new TextureRegion(Face);
        this.level = level;

        explosionEffect = new ParticleEffect();
        explosionEffect.load(Gdx.files.internal("Effects/explosion.p"), Gdx.files.internal("Effects/"));
    }

    private void createExplosionSensor() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(body.getPosition());

        explosionSensorBody = world.createBody(bodyDef);
        explosionSensorBody.setUserData(1);

        CircleShape circleShape = new CircleShape();
        float explosionRadius = 1f;
        circleShape.setRadius(explosionRadius);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.isSensor = true;
        explosionSensorBody.createFixture(fixtureDef);

        explosionSensorBody.setLinearVelocity(body.getLinearVelocity());
        explosionSensorBody.setAngularVelocity(body.getAngularVelocity());

        circleShape.dispose();
        explosionBodyTimer = 0f;
    }

    @Override
    public void SpecialFunctionality() {
        if (Math.abs(getBody().getLinearVelocity().y) > 0.01f && !getAbilityUsed() && isLaunched) {
            setAbilityUsed();

            explosionEffect.setPosition(body.getPosition().x * PPM, body.getPosition().y * PPM);
            explosionEffect.start();

            shrinkFactor = 1f;
            isShrinkingCompleted = false;

            level.getBodiesToDestroy().add(this.body);

            isExploding = true;

            createExplosionSensor();
        }
    }

    @Override
    public void update(float delta) {
        if (isExploding) {
            explosionEffect.update(delta);

            if(explosionSensorBody!=null){
                explosionBodyTimer+=delta;

                if(explosionBodyTimer>0.25){
                    level.getBodiesToDestroy().add(explosionSensorBody);
                    explosionSensorBody = null;
                }
            }

            if (explosionEffect.isComplete()) {
                isExploding = false;
            }
        }

        if (!isShrinkingCompleted && this.getAbilityUsed()) {
            shrinkFactor -= delta * 2;
            if (shrinkFactor <= 0f) {
                shrinkFactor = 0f;
                isShrinkingCompleted = true;
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        if (!isExploding && isShrinkingCompleted) {
            return;
        }

        if (isExploding) {
            explosionEffect.draw(batch);
        }

        if (!isShrinkingCompleted) {
            batch.draw(
                FaceTextureRegion,
                body.getPosition().x * PPM - (width / 2f) * shrinkFactor,
                body.getPosition().y * PPM - (height / 2f) * shrinkFactor,
                (width / 2f) * shrinkFactor, // Origin X
                (height / 2f) * shrinkFactor, // Origin Y
                width * shrinkFactor, // Width
                height * shrinkFactor, // Height
                shrinkFactor, // Scale X
                shrinkFactor, // Scale Y
                body.getAngle() * MathUtils.radiansToDegrees
            );
        }

        this.posX = body.getPosition().x;
        this.posY = body.getPosition().y;
        this.velocityX = body.getLinearVelocity().x;
        this.velocityY = body.getLinearVelocity().y;
        this.angle = body.getAngle();
    }

    @Override
    public void dispose() {
        super.dispose();
        explosionEffect.dispose();
        explosionEffect.dispose();
    }
}
