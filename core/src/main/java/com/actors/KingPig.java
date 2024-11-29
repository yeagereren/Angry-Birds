package com.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.levels.LevelManager;

import java.io.Serializable;

public class KingPig extends Pig implements Serializable {

    public void initializeNonSerializableFields(World world, LevelManager level){
        super.initializeNonSerializableFields(world, level);
        if(body!=null) {
            for (Fixture fixture : this.body.getFixtureList()) {
                fixture.setDensity(1.5f);
            }
        }
        Face = new Texture("Birds/KingPig.png");
    }

    public KingPig(World world, int posX, int posY, LevelManager level){
        super(world, posX, posY, level);
        this.height = 90;
        this.health = super.height*2;
        for(Fixture fixture: this.body.getFixtureList()){
            fixture.setDensity(1.5f);
        }
        Face = new Texture("Birds/KingPig.png");
    }
}
