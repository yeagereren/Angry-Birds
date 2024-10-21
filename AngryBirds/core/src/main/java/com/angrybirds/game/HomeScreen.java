package com.angrybirds.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;

public class HomeScreen implements ApplicationListener {
    private Texture homeScreenImage;
    private final SpriteBatch batch;
    private int ScreenWidth;
    private int ScreenHeight;
    private final Main game;
    private long startTime;

//    private CustomButton playButton;
//    private CustomButton quitButton;
//    private Texture logo;

    public HomeScreen(SpriteBatch batch, Main game) {
        this.batch = batch;
        this.game = game;
        create();
    }

    public void create() {
        homeScreenImage = new Texture("FirstScreen.jpg");
//        logo = new Texture("Logo.jpg");
        startTime = TimeUtils.nanoTime();

        ScreenHeight = Gdx.graphics.getHeight();
        ScreenWidth = Gdx.graphics.getWidth();

//        playButton = new CustomButton("PlayButton.png", ScreenWidth - 750, ScreenHeight - 700, 150, 75);
//        quitButton = new CustomButton("ExitButton.png", ScreenWidth - 500, ScreenHeight - 700, 150, 75);
    }

    @Override
    public void resize(int width, int height) {
        this.ScreenWidth = width;
        this.ScreenHeight = height;
    }

    public void render() {
        batch.begin();
        batch.draw(homeScreenImage, 0, 0, ScreenWidth, ScreenHeight);
//        batch.draw(logo, ((float) ScreenWidth / 2) - 150, ScreenHeight - 200, 350, 175);

//        playButton.draw(batch);
//        quitButton.draw(batch);

//        handleInput();
        handleAutoTransition();

        batch.end();
    }

//    private void handleInput() {
//        if (Gdx.input.justTouched()) {
//            float touchX = Gdx.input.getX();
//            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
//
//            if (playButton.isClicked(touchX, touchY)) {
//                game.ChangeState(GameStates.LEVEL_SCREEN);
//            }
//
//            if (quitButton.isClicked(touchX, touchY)) {
//                game.ChangeState(GameStates.GAME_QUIT);
//            }
//        }
//    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    public void dispose() {
        homeScreenImage.dispose();
//        playButton.dispose();
//        quitButton.dispose();
//        logo.dispose();
    }


    private void handleAutoTransition() {
        float elapsedTime = (TimeUtils.nanoTime() - startTime) / 1000000000.0f;  // Convert nanoseconds to seconds

        if (elapsedTime > 3) {
            game.ChangeState(GameStates.CHOOSE_LEVEL_SCREEN);
        }
    }
}
