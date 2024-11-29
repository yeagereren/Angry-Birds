package com.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Silver extends BasicBird{

    private boolean isRotating = false; // Track if the bird is in rotation
    private float rotationAngle = 0f;  // Current angle of rotation

    public void initializeNonSerializableFields(World world, Stage stage, boolean parameter){
        super.initializeNonSerializableFields(world,stage, parameter);
        body.setUserData(this);
        this.Face = new Texture("Birds/Silver.png");
        body.getFixtureList().first().setRestitution(0.01f);
    }

    public Silver(World world, int posX, int posY, Stage stage) {
        super(world, posX, posY, "Birds/Silver.png", stage);
        this.width = 100;
        body.setUserData(this);
        body.getFixtureList().first().setRestitution(0.01f); // Low bounce
    }

    @Override
    public void launch(float velocity, float angle) {
        makeDynamic();
        vx = velocity * (float) Math.cos(angle);
        vy = velocity * (float) Math.sin(angle);
        float mass = this.body.getMass();
        Vector2 impulse = new Vector2(vx * mass, vy * mass);
        this.getBody().applyLinearImpulse(impulse, this.getBody().getWorldCenter(), true);

        super.isLaunched = true;
    }

    @Override
    public void SpecialFunctionality() {
        if (!this.getAbilityUsed()) {
            this.setAbilityUsed();

            isRotating = true;
            body.setAngularVelocity(0f);
        }
    }

    @Override
    public void update(float deltaTime) {

        if (isRotating) {
            rotationAngle += 1800 * deltaTime;

            if (rotationAngle >= 270f) {
                rotationAngle = 270f;
                isRotating = false;

                body.setTransform(body.getPosition(), (float) Math.toRadians(rotationAngle));
                body.setLinearVelocity(new Vector2(0, -70f));
            } else {
                body.setTransform(body.getPosition(), (float) Math.toRadians(rotationAngle));
            }
        }
    }
}
