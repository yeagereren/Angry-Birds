package com.angrybirds.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;

public class HomeScreen implements ApplicationListener {
    private Texture homeScreenImage;
    private int ScreenWidth;
    private int ScreenHeight;
    private final Main game;
    private long startTime;

    public HomeScreen(Main game) {
        this.game = game;
        create();
    }

    public void create() {
        homeScreenImage = new Texture("FirstScreen.jpg");
        startTime = TimeUtils.nanoTime();

        ScreenHeight = Gdx.graphics.getHeight();
        ScreenWidth = Gdx.graphics.getWidth();
    }

    @Override
    public void resize(int width, int height) {
        this.ScreenWidth = width;
        this.ScreenHeight = height;
    }

    public void render() {
        game.getBatch().begin();
        game.getBatch().draw(homeScreenImage, 0, 0, ScreenWidth, ScreenHeight);
        handleAutoTransition();

        game.getBatch().end();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    public void dispose() {
        homeScreenImage.dispose();
    }

    private void handleAutoTransition() {
        float elapsedTime = (TimeUtils.nanoTime() - startTime) / 1000000000.0f;  // Convert nanoseconds to seconds

        if (elapsedTime > 3) {
            game.ChangeState(GameStates.MAIN_SCREEN);
        }
    }
}
