package com.angrybirds.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class WinningScreen implements ApplicationListener {
    private Texture WinningImage;
    private final Main game;

    WinningScreen(Main game){
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
        game.getBatch().begin();
        game.getBatch().draw(WinningImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.getBatch().end();
        handleKeyboardInput();
    }

    private void handleKeyboardInput() {
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            game.ChangeState(GameStates.LEVEL1SCREEN);
        }
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
