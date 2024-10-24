package com.structure;

import com.badlogic.gdx.graphics.Texture;

public class Wood {
    private static Texture oneLeg, twoLeg, threeLeg;

    public Wood(){
        oneLeg = new Texture("Wood1.png");
        twoLeg = new Texture("Wood2.png");
        threeLeg = new Texture("Wood3.png");
    }

    public static Texture getOneLeg(){
        return oneLeg;
    }

    public static Texture getTwoLeg(){
        return twoLeg;
    }

    public static Texture getThreeLeg(){
        return threeLeg;
    }
}
