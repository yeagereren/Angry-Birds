package com.angrybirds.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;

import java.io.Serializable;

public class HomeScreen implements ApplicationListener, Serializable {
    private transient Texture homeScreenImage;
    private int ScreenWidth;
    private int ScreenHeight;
    private final Main game;
    private transient long startTime;

    public HomeScreen(Main game) {
        this.game = game;
        create();
    }

    private void initializeNonSerializableFields(){
        homeScreenImage = new Texture("FirstScreen.jpg");
        startTime = TimeUtils.nanoTime();
    }

    public void create() {
        initializeNonSerializableFields();
//        homeScreenImage = new Texture("FirstScreen.jpg");
//        startTime = TimeUtils.nanoTime();

        ScreenHeight = Gdx.graphics.getHeight();
        ScreenWidth = Gdx.graphics.getWidth();
    }

    @Override
    public void resize(int width, int height) {
        this.ScreenWidth = width;
        this.ScreenHeight = height;
    }

    public void render() {
        if(homeScreenImage==null) {
            initializeNonSerializableFields();
        }
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
