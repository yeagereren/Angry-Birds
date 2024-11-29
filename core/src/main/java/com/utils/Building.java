package com.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

import java.io.Serializable;
import java.util.ArrayList;

public class Building implements Serializable {
    private final ArrayList<Material> materials;

    public void initializeNonSerializableFields(World world){
        for(Material material: materials){
            material.initializeNonSerializableFields(world);
        }
    }

    public Building(){
        materials = new ArrayList<>();
    }

    public void Add(Material material){
        materials.add(material);
    }

    public void render(SpriteBatch batch){
        for (Material material : materials) {
            material.render(batch);
        }
    }

    public ArrayList<Material> getMaterials(){
        return this.materials;
    }
}
