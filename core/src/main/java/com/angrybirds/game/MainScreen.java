package com.angrybirds.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;

public class MainScreen implements ApplicationListener {
    private Texture homeScreenImage;
    private int ScreenWidth;
    private int ScreenHeight;
    private final Main game;
    private CustomButton ExitButton, LoadSavedGame, NewGame;

//    private CustomButton playButton;
//    private CustomButton quitButton;
//    private Texture logo;

    public MainScreen(Main game) {
        this.game = game;
        create();
    }

    public void create() {
        homeScreenImage = new Texture("FirstScreenBlurred.jpg");
        LoadSavedGame = new CustomButton("ExitButton.png", 420, 60, 100, 80);
        NewGame = new CustomButton("ExitButton.png", 570, 60, 100, 80);
        ExitButton = new CustomButton("GoBack.png", 70, 650, 60);
//        logo = new Texture("Logo.jpg");

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
        game.getBatch().begin();
        game.getBatch().draw(homeScreenImage, 0, 0, ScreenWidth, ScreenHeight);
        LoadSavedGame.draw(game.getBatch());
        NewGame.draw(game.getBatch());
        ExitButton.draw(game.getBatch());
        handleInputs();
//        batch.draw(logo, ((float) ScreenWidth / 2) - 150, ScreenHeight - 200, 350, 175);

//        playButton.draw(batch);
//        quitButton.draw(batch);

//        handleInput();

        game.getBatch().end();
    }

    private void handleInputs(){
        if(Gdx.input.justTouched()){
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (LoadSavedGame.isClicked(touchX, touchY)) {
//                game.ChangeState(GameStates.HOME_SCREEN);
                game.ChangeState(GameStates.CHOOSE_LEVEL_SCREEN);
            }

            if (NewGame.isClicked(touchX, touchY)) {
                game.ChangeState(GameStates.CHOOSE_LEVEL_SCREEN);
            }

            if (ExitButton.isClicked(touchX, touchY)) {
                game.ChangeState(GameStates.GAME_QUIT);
            }
        }
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
}
