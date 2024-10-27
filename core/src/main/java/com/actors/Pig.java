package com.actors;

import com.badlogic.gdx.graphics.Texture;

public class Pig extends Beings{

    public Pig(int posX, int posY, int width, int height){
        super(posX, posY, width, height);
        Face = new Texture("Birds/Piggy.png");
    }
}
