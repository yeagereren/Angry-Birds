package com.structure;

import com.badlogic.gdx.graphics.Texture;

public class Wood extends Material{

    public Wood(int posX, int posY, int width, int height){
        super(posX, posY, width, height);
        this.image = new Texture("Blocks/wood.png");
    }
}
