package com.actors;

import com.badlogic.gdx.graphics.Texture;

public class Bomb extends BasicBird{
    public Bomb(int posX, int posY, int width, int height){
        super(posX, posY, width, height);
        Face = new Texture("Birds/bomb.png");
    }
}
