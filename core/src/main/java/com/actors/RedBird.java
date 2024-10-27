package com.actors;

import com.badlogic.gdx.graphics.Texture;

public class RedBird extends BasicBird{
    public RedBird(int posX, int posY, int width, int height){
        super(posX, posY, width, height);
        Face = new Texture("Birds/red_bird.png");
    }
}
