package com.angrybirds.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WinningScreen implements ApplicationListener {
    private Texture WinningImage;
    private SpriteBatch spriteBatch;
    private Main game;

    WinningScreen(SpriteBatch batch, Main game){
        spriteBatch = batch;
        this.game = game;
        create();
    }

    @Override
    public void create() {
        WinningImage = new Texture("WinningScreen.jpg");
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void render() {
        spriteBatch.begin();
        spriteBatch.draw(WinningImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        spriteBatch.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        WinningImage.dispose();
    }
}
