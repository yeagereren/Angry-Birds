package com.actors;

import com.badlogic.gdx.graphics.Texture;

public class Chuck extends BasicBird{
    public Chuck(int posX, int posY, int width, int height){
        super(posX, posY, width, height);
        Face = new Texture("Birds/yellow_bird.png");
    }
}
