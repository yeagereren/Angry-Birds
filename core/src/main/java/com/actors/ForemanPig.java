package com.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.levels.LevelManager;

import java.io.Serializable;

public class ForemanPig extends Pig implements Serializable {

    public void initializeNonSerializableFields(World world, LevelManager level){
        super.initializeNonSerializableFields(world, level);
        if(body!=null) {
            for (Fixture fixture : this.body.getFixtureList()) {
                fixture.setDensity(2f);
            }
        }
        Face = new Texture("Birds/ForemanPig.png");
    }

    public ForemanPig(World world, int posX, int posY, LevelManager level){
        super(world, posX, posY, level);
        this.width = 100;
        this.height = 100;
        this.health = super.health*3;
        for(Fixture fixture: this.body.getFixtureList()){
            fixture.setDensity(2f);
        }
        Face = new Texture("Birds/ForemanPig.png");
    }
}
