package com.actors;

import com.badlogic.gdx.graphics.Texture;

public class Matilda extends BasicBird{
    public Matilda(int posX, int posY, int width, int height){
        super(posX, posY, width, height);
        Face = new Texture("Birds/matilda_egg.png");
    }
}
