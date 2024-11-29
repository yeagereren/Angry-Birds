package com.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.io.Serializable;

public class Matilda extends BasicBird implements Serializable {
    transient Egg egg;
    private float eggPositionX, eggPositionY;

    public void initializeNonSerializableFields(World world, Stage stage, boolean parameter){
        super.initializeNonSerializableFields(world, stage, parameter);
        body.setUserData(this);
        this.Face = new Texture("Birds/matilda_egg.png");
        egg = null;
        if(this.getAbilityUsed()){
            egg = new Egg(world, new Vector2(eggPositionX, eggPositionY));
        }
    }

    public Matilda(World world, int posX, int posY, Stage stage){
        super(world, posX, posY, "Birds/matilda_egg.png", stage);
        body.setUserData(this);
        egg=null;
    }

    @Override
    public void SpecialFunctionality() {
        if(!this.getAbilityUsed() && body.getLinearVelocity().len()>0.1f && isLaunched){
            float currentSpeed = body.getLinearVelocity().scl(1.5f).len();
            float angleInRadians = (float) Math.toRadians(60);

            float vx = currentSpeed * (float) Math.cos(angleInRadians);
            float vy = currentSpeed * (float) Math.sin(angleInRadians);

            Vector2 eggPosition = new Vector2(body.getPosition().x, body.getPosition().y - 1.5f);
            egg = new Egg(world, eggPosition);

            body.setLinearVelocity(vx, vy);
            this.setAbilityUsed();
        }
    }

    @Override
    public void render(SpriteBatch batch){
        super.render(batch);

        if(egg!=null){
            egg.render(batch);
            eggPositionX = egg.body.getPosition().x;
            eggPositionY = egg.body.getPosition().y;
        }
    }

    public static class Egg implements Serializable{
        private final transient Body body;
        private final transient Texture texture;
        private static final float EGG_WIDTH = 45f;
        private static final float EGG_HEIGHT = 45f;

        public Egg(World world, Vector2 position) {
            texture = new Texture("Birds/Egg.png");

            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            bodyDef.position.set(position);

            body = world.createBody(bodyDef);
            body.setUserData(this);

            CircleShape shape = new CircleShape();
            shape.setRadius(EGG_WIDTH / 2f / BasicBird.PPM);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.density = 1f;
            fixtureDef.friction = 0.5f;
            fixtureDef.restitution = 0.2f;

            body.createFixture(fixtureDef);
            shape.dispose();
        }

        public void render(SpriteBatch batch) {
            Vector2 position = body.getPosition();

            batch.draw(
                texture,
                (position.x * BasicBird.PPM) - EGG_WIDTH / 2f,
                (position.y * BasicBird.PPM) - EGG_HEIGHT / 2f,
                EGG_WIDTH,
                EGG_HEIGHT
            );
        }

        public void dispose() {
            texture.dispose();
        }
    }

    @Override
    public void dispose(){
        super.dispose();
        if(egg!=null){
            egg.dispose();
        }
    }
}
