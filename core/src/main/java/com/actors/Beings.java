package com.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

import java.io.Serializable;

public abstract class Beings implements Serializable {
    protected float posX, posY, width, height;
    protected transient Texture Face;
    protected transient World world;
    protected static final float PPM = 32;

    protected void initializeNonSerializableFields(World world){
        this.world = world;
    }

    public Beings(World world, int posX, int posY, int width, int height){
        this.world = world;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    public void render(SpriteBatch batch){
        batch.draw(Face, posX, posY, width, height);
    }

    public Texture getFace(){
        return Face;
    }

    public void dispose(){
        Face.dispose();
    }
}
