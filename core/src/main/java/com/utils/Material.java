package com.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.levels.LevelManager;

import java.io.Serializable;

public abstract class Material implements Serializable {
    protected transient Texture image;
    protected float posX,posY,width,height;
    protected transient Body body;
    protected static final float PPM = 32f;
    protected boolean destroyed;
    protected float strength;
    protected boolean isHiddenDestroyer;
    private boolean Explosion;
    private transient ParticleEffect explosionEffect;

    protected void initializeNonSerializableFields(World world){
        if(!destroyed) {
            BodyDef def = new BodyDef();
            def.type = BodyDef.BodyType.DynamicBody;
            def.position.set(posX, posY);
            def.fixedRotation = false;

            body = world.createBody(def);
            body.setUserData(this);

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(width / 2f / PPM, height / 2f / PPM);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.density = 1.0f;
            fixtureDef.friction = 0.3f;
            fixtureDef.restitution = 0f;

            body.createFixture(fixtureDef);
            shape.dispose();
            explosionEffect = new ParticleEffect();
            explosionEffect.load(Gdx.files.internal("Effects/explosion2.p"), Gdx.files.internal("Effects/"));
            explosionEffect.setPosition(body.getPosition().x * PPM, body.getPosition().y * PPM);
        }
    }

    public Material(World world, int posX, int posY, float width, float height){
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.destroyed = false;
        this.isHiddenDestroyer = false;
        this.Explosion = false;

        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(posX/PPM, posY/PPM);
        def.fixedRotation = false;

        body = world.createBody(def);
        body.setUserData(this);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2f / PPM, height / 2f / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0f;

        body.createFixture(fixtureDef);
        shape.dispose();

        explosionEffect = new ParticleEffect();
        explosionEffect.load(Gdx.files.internal("Effects/explosion2.p"), Gdx.files.internal("Effects/"));
        explosionEffect.setPosition(body.getPosition().x * PPM, body.getPosition().y * PPM);
    }

    public void render(SpriteBatch spriteBatch) {
        if(Explosion && explosionEffect!=null){
            explosionEffect.draw(spriteBatch);
        }

        if(!destroyed){
            Vector2 position = body.getPosition();
            this.posX = position.x;
            this.posY = position.y;
            float renderX = (position.x * PPM) - (width / 2f);
            float renderY = (position.y * PPM) - (height / 2f);
            float rotation = (float) Math.toDegrees(body.getAngle());

            spriteBatch.draw(image, renderX, renderY, width / 2f, height / 2f, width, height,
                1, 1, rotation, 0, 0, image.getWidth(), image.getHeight(),
                false, false);
        }
    }

    public void dispose(){
        image.dispose();
        explosionEffect.dispose();
    }

    public void setAsHiddenDestroyer(){
        this.isHiddenDestroyer = true;
    }

    public void destroy(){
        destroyed = true;
    }

    public void hit(float num){
        if(num!=1 && isHiddenDestroyer){
            Explosion = true;
            explosionEffect.start();
        }
        strength-=num;
        if(strength<=0){
            destroy();
        }
    }

    public void update(float delta, LevelManager level) {
        if (explosionEffect!=null && Explosion) {
            explosionEffect.update(delta);

            if (explosionEffect.isComplete()) {
                level.EndLevel();
            }
        }
    }

    public Body getBody(){
        return this.body;
    }
}

