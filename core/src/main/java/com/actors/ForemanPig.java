package com.actors;

import com.badlogic.gdx.graphics.Texture;

public class ForemanPig extends Pig{
    public ForemanPig(int posX, int posY, int width, int height){
        super(posX, posY, width, height);
        Face = new Texture("Birds/ForemanPig.png");
    }
}
