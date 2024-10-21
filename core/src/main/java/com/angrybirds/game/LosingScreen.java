package com.angrybirds.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.concurrent.locks.Lock;

public class LosingScreen implements ApplicationListener {
    private Texture LosingImage;
    private Main game;

    LosingScreen(Main game){
        this.game = game;
        create();
    }

    @Override
    public void create() {
        LosingImage = new Texture("LosingImage.jpg");
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void render() {
        game.getBatch().begin();
        game.getBatch().draw(LosingImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.getBatch().end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        LosingImage.dispose();
    }
}
