package com.structure;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Material {
    protected Texture image;
    protected int posX,posY,width,height;

    public Material(int posX, int posY, int width, int height){
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    public void render(SpriteBatch spriteBatch){
        spriteBatch.draw(image,posX,posY,width,height);
    }

    public void dispose(){
        image.dispose();
    }
}
