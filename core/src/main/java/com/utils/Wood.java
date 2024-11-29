package com.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

import java.io.Serializable;

public class Wood extends Material implements Serializable {

    protected void initializeNonSerializableFields(World world){
        super.initializeNonSerializableFields(world);
        this.image = new Texture("Blocks/wood.png");
        if(destroyed){
            return;
        }
        for(Fixture fixture: this.body.getFixtureList()){
            fixture.setDensity(3f);
        }
    }

    public Wood(World world, int posX, int posY, float width, float height){
        super(world, posX, posY, width, height);
        this.image = new Texture("Blocks/wood.png");
        for(Fixture fixture: this.body.getFixtureList()){
            fixture.setDensity(1.5f);
        }
        this.strength = 1000;
    }
}
