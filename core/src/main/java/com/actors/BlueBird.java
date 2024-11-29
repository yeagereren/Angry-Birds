package com.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.io.Serializable;

public class BlueBird extends BasicBird implements Serializable {
    transient Body upperBody;
    transient Body lowerBody;
    private final float reducedWidth, reducedHeight;
    private Vector2 upperBodyPosition, lowerBodyPosition;
    private float lowerBodyAngle, upperBodyAngle;

    public void initializeNonSerializableFields(World world, Stage stage, boolean parameter){
        super.initializeNonSerializableFields(world, stage, parameter);
        body.setUserData(this);
        this.Face = new Texture("Birds/blue_bird.png");
        if(isLaunched && this.getAbilityUsed()){
            reduceMainBodySize();
            lowerBody = createBody(-3);
            upperBody = createBody(3);
            lowerBody.setLinearVelocity(velocityX, velocityY);
            upperBody.setLinearVelocity(velocityX, velocityY);
            lowerBody.setAngularVelocity(3f);
            upperBody.setAngularVelocity(3f);
            lowerBody.setTransform(lowerBodyPosition, lowerBodyAngle);
            upperBody.setTransform(upperBodyPosition, upperBodyAngle);
        } else{
            lowerBody = null;
            upperBody = null;
        }
    }

    public BlueBird(World world, int posX, int posY, Stage stage) {
        super(world, posX, posY, "Birds/blue_bird.png", stage);
        body.setUserData(this);
        upperBody = null;
        lowerBody = null;
        reducedWidth = width * 7 / 8f;
        reducedHeight = height * 7 / 8f;
    }

    @Override
    public void SpecialFunctionality(){
        Vector2 currentVelocity = body.getLinearVelocity();
        if(!this.getAbilityUsed() && currentVelocity.len()>1){
            reduceMainBodySize();

            lowerBody = createBody(-3);
            upperBody = createBody(3);

            float mass = this.body.getMass();
            Vector2 impulse = new Vector2(currentVelocity.x * mass, currentVelocity.y * mass);

            lowerBody.applyLinearImpulse(impulse, lowerBody.getWorldCenter(), true);
            upperBody.applyLinearImpulse(impulse, upperBody.getWorldCenter(), true);

            lowerBody.setAngularVelocity(this.body.getAngularVelocity());
            upperBody.setAngularVelocity(this.body.getAngularVelocity());

            this.setAbilityUsed();
        }
    }

    private void reduceMainBodySize() {
        width = reducedWidth;
        height = reducedHeight;

        body.destroyFixture(body.getFixtureList().first());
        CircleShape shape = new CircleShape();
        shape.setRadius(width / 2f / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.2f;

        body.createFixture(fixtureDef);
        shape.dispose();
    }

    private Body createBody(int offset){
        Body body;

        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        Vector2 position = this.body.getPosition();
        def.position.set(position.x, position.y+offset);
        def.fixedRotation = false;

        body = world.createBody(def);
        this.body.setUserData(this);

        body.setAngularDamping(3.0f);

        CircleShape shape = new CircleShape();
        shape.setRadius(reducedWidth / 2f / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.2f;

        body.createFixture(fixtureDef);
        shape.dispose();

        return body;
    }

    @Override
    public void render(SpriteBatch batch){
        super.render(batch);
        if(this.getAbilityUsed()){
            upperBodyPosition = upperBody.getPosition();
            float upperRenderX = (upperBodyPosition.x * PPM) - (reducedWidth / 2f);
            float upperRenderY = (upperBodyPosition.y * PPM) - (reducedHeight / 2f);

            float upperRotation = (float) Math.toDegrees(body.getAngle());

            batch.draw(Face, upperRenderX, upperRenderY, width / 2f, reducedHeight / 2f, reducedWidth, reducedHeight,
                1, 1, upperRotation, 0, 0, Face.getWidth(), Face.getHeight(),
                false, false);

            lowerBodyPosition = lowerBody.getPosition();
            float lowerRenderX = (lowerBodyPosition.x * PPM) - (reducedWidth / 2f);
            float lowerRenderY = (lowerBodyPosition.y * PPM) - (reducedHeight / 2f);

            float lowerRotation = (float) Math.toDegrees(body.getAngle());
            upperBodyAngle = upperBody.getAngle();
            lowerBodyAngle = lowerBody.getAngle();

            batch.draw(Face, lowerRenderX, lowerRenderY, reducedWidth / 2f, reducedHeight / 2f, reducedWidth, reducedHeight,
                1, 1, lowerRotation, 0, 0, Face.getWidth(), Face.getHeight(),
                false, false);
        }
    }
}
