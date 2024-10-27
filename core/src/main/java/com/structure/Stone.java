package com.structure;

import com.badlogic.gdx.graphics.Texture;

public class Stone extends Material{
    public Stone(int PosX, int PosY, int width, int height){
        super(PosX, PosY, width, height);
        this.image = new Texture("Blocks/stone.png");
    }
}
