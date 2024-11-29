package com.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.io.Serializable;

public class Terance extends BasicBird implements Serializable {

    public void initializeNonSerializableFields(World world, Stage stage, boolean parameter){
        super.initializeNonSerializableFields(world, stage, parameter);
        this.Face = new Texture("Birds/Terance.png");
        body.setUserData(this);
    }


    public Terance(World world, int posX, int posY, Stage stage){
        super(world, posX, posY, "Birds/Terance.png", stage);
        body.setUserData(this);
        this.width = 100;
        this.height = 100;
    }
}
