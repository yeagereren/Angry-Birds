package com.actors;

import com.badlogic.gdx.graphics.Texture;

public abstract class BasicBird {
    protected Texture Face;

    public void dispose(){
        Face.dispose();
    }

    public Texture getFace(){
        return Face;
    }
}
