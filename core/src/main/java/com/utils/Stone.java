package com.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

import java.io.Serializable;

public class Stone extends Material implements Serializable {

    protected void initializeNonSerializableFields(World world){
        super.initializeNonSerializableFields(world);
        this.image = new Texture("Blocks/stone.png");
        if(destroyed){
            return;
        }
        for(Fixture fixture: this.body.getFixtureList()){
            fixture.setDensity(3f);
        }
    }

    public Stone(World world, int PosX, int PosY, float width, float height){
        super(world, PosX, PosY, width, height);
        this.image = new Texture("Blocks/stone.png");
        for(Fixture fixture: this.body.getFixtureList()){
            fixture.setDensity(3f);
        }
        this.strength = 1500;
    }
}
