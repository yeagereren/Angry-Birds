package com.angrybirds.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ChooseLevelScreen implements ApplicationListener {
    private final SpriteBatch batch;
    private Texture Background;
    private int screenWidth, screenHeight;
    private Texture levelBoard;
    private final Main game;

    private CustomButton Level1Button, Level2Button, Level3Button, BackButton;

    ChooseLevelScreen(SpriteBatch sprite, Main game){
        this.batch = sprite;
        this.game = game;
        create();
    }

    @Override
    public void create() {
        Background = new Texture("BackgroundForLevel.jpg");
        screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();
        levelBoard = new Texture("LevelBoard.png");
        Level1Button = new CustomButton("Level1.png", 350, 400, 70);
        Level2Button = new CustomButton("Level2.png", 550, 400, 70);
        Level3Button = new CustomButton("Level3.png", 750, 400, 70);
        BackButton = new CustomButton("ExitButton.png", 460, 150, 200, 100);
    }

    @Override
    public void resize(int width, int height) {
        this.screenWidth = width;
        this.screenHeight = height;
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(Background, 0, 0 , screenWidth, screenHeight);
        batch.draw(levelBoard, (float) (screenWidth)/2 - 150, screenHeight - 200, 300, 120);
        Level1Button.draw(batch);
        Level2Button.draw(batch);
        Level3Button.draw(batch);
        BackButton.draw(batch);
        handleInput();
        batch.end();
    }

    public void handleInput(){
        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (BackButton.isClicked(touchX, touchY)) {
//                game.ChangeState(GameStates.HOME_SCREEN);
                game.ChangeState(GameStates.GAME_QUIT);
            }

            if (Level1Button.isClicked(touchX, touchY)) {
                game.ChangeState(GameStates.LEVEL1SCREEN);
            }

            if (Level2Button.isClicked(touchX, touchY)) {
                System.out.println("Level2 Clicked!");
            }

            if (Level3Button.isClicked(touchX, touchY)) {
                System.out.println("Level3 Clicked!");
            }
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
        Background.dispose();
        levelBoard.dispose();
    }
}
