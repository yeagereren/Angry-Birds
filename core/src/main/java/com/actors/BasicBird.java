package com.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.io.Serializable;

public abstract class BasicBird extends Beings implements Serializable {

    protected float vx, vy;
    protected transient Body body;
    private boolean isAbilityUsed;
    protected transient Stage stage;
    protected boolean isLaunched;
    protected float angle, velocityX, velocityY;

    public void initializeNonSerializableFields(World world, Stage stage, boolean paramteter){
        super.initializeNonSerializableFields(world);
        this.stage = stage;

        BodyDef def = new BodyDef();
        if(!isLaunched){
            def.type = BodyDef.BodyType.StaticBody;
        } else{
            def.type = BodyDef.BodyType.DynamicBody;
        }
        if(!paramteter){
            posX = -150;
            posY = -250;
        }
        def.position.set(posX, posY);
        def.fixedRotation = false;

        body = world.createBody(def);
        body.setUserData(this);
        body.setAngularDamping(3.0f);
        body.setTransform(posX, posY, angle);
        if(isLaunched){
            body.setLinearVelocity(velocityX, velocityY);
            body.setAngularVelocity(3f);
        }

        CircleShape shape = new CircleShape();
        shape.setRadius(width / 2f / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = (this instanceof Terance) ? 5.0f : 1.0f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.2f;

        body.createFixture(fixtureDef);
        shape.dispose();
    }

    public BasicBird(World world, int posX, int posY, String path, Stage stage) {
        super(world, posX, posY, 75, 75);
        this.stage = stage;
        isLaunched = false;
        isAbilityUsed = false;

        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.StaticBody;
        def.position.set(posX/PPM, posY/PPM);
        def.fixedRotation = false;

        body = world.createBody(def);
        body.setUserData(this);

        body.setAngularDamping(3.0f);

        CircleShape shape = new CircleShape();
        shape.setRadius(width / 2f / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = (this instanceof Terance) ? 5.0f : 1.0f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.2f;

        body.createFixture(fixtureDef);
        shape.dispose();

        Face = new Texture(path);
    }

    public boolean getLaunched(){
        return isLaunched;
    }

    public Vector2 getPosition(){
        return body.getPosition();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        Vector2 position = body.getPosition();
        this.posX = body.getPosition().x;
        this.posY = body.getPosition().y;
        this.velocityX = body.getLinearVelocity().x;
        this.velocityY = body.getLinearVelocity().y;
        this.angle = body.getAngle();
        float renderX = (position.x * PPM) - (width / 2f);
        float renderY = (position.y * PPM) - (height / 2f);

        float rotation = (float) Math.toDegrees(body.getAngle());

        spriteBatch.draw(Face, renderX, renderY, width / 2f, height / 2f, width, height,
            1, 1, rotation, 0, 0, Face.getWidth(), Face.getHeight(),
            false, false);
    }

    public void makeDynamic() {
        body.setType(BodyDef.BodyType.DynamicBody);
    }

    public void launch(float velocity, float angle) {
        makeDynamic();
        velocity = velocity/1.25f;
        vx = velocity * (float) Math.cos(angle);
        vy = velocity * (float) Math.sin(angle);
        float mass = this.body.getMass();
        Vector2 impulse = new Vector2(vx * mass, vy * mass);
        this.getBody().applyLinearImpulse(impulse, this.getBody().getWorldCenter(), true);
        this.getBody().setAngularVelocity(3f);
        isLaunched = true;
    }

    public void load(){
        setPosition(120, 260);
    }

    public void setPosition(float posX, float posY){
        body.setTransform(posX/PPM, posY/PPM, body.getAngle());
    }

    public void dispose(){
        Face.dispose();
        if(body!=null) {
            body.setActive(false);
        }
    }

    public boolean getAbilityUsed(){
        return this.isAbilityUsed;
    }

    public void setAbilityUsed(){
        this.isAbilityUsed = true;
    }

    public void SpecialFunctionality() {
    }

    public void update(float delta) {
    }

    public Body getBody(){
        return this.body;
    }
}
