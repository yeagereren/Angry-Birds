package com.structure;

import com.badlogic.gdx.graphics.Texture;

public class Glass extends Material{
    public Glass(int posX, int posY, int width, int height){
        super(posX, posY, width, height);
        this.image = new Texture("Blocks/glass.png");
    }
}
