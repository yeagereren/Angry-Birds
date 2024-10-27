package com.actors;

import com.badlogic.gdx.graphics.Texture;

public class KingPig extends Pig{
    public KingPig(int posX, int posY, int width, int height){
        super(posX, posY, width, height);
        Face = new Texture("Birds/KingPig.png");
    }
}
