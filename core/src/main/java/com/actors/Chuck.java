package com.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.io.Serializable;

public class Chuck extends BasicBird implements Serializable {
    public Chuck(World world, int posX, int posY, Stage stage){
        super(world, posX, posY, "Birds/yellow_bird.png", stage);
        body.setUserData(this);
    }

    public void initializeNonSerializableFields(World world, Stage stage, boolean parameter){
        super.initializeNonSerializableFields(world, stage, parameter);
        body.setUserData(this);
        this.Face = new Texture("Birds/yellow_bird.png");
    }

    @Override
    public void SpecialFunctionality() {
        Vector2 currentVelocity = body.getLinearVelocity();
        if(!this.getAbilityUsed() && currentVelocity.len()>0 && isLaunched){
            Vector2 newVelocity = new Vector2();
            newVelocity.x = currentVelocity.x*3;
            newVelocity.y = currentVelocity.y*3;
            body.setLinearVelocity(newVelocity);
            this.setAbilityUsed();
        }
    }
}
