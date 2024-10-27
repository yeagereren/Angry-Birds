package com.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Beings{
    protected int posX, posY, width, height;
    protected Texture Face;

    public Beings(int posX, int posY, int width, int height){
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
