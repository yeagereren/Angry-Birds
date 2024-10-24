package com.angrybirds.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ChooseLevelScreen implements ApplicationListener {
    private Texture Background;
    private int screenWidth, screenHeight;
    private Texture levelBoard;
    private final Main game;
    private Stage stage;

    private CustomButton Level1Button, Level2Button, Level3Button, BackButton;

    ChooseLevelScreen(Main game){
        this.game = game;
        create();
    }

    @Override
    public void create() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Background = new Texture("BackgroundForLevel.jpg");
        screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();
        levelBoard = new Texture("LevelBoard.png");
        Level1Button = new CustomButton("Level1.png", 300, 400, 90);
        Level2Button = new CustomButton("Lock2.png", 450, 260, 190,230);
        Level3Button = new CustomButton("Lock3.png", 700,250, 190, 240);
        BackButton = new CustomButton("GoBack.png", 70, 650, 60);

        Level1Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                game.ChangeState(GameStates.LEVEL1SCREEN);
            }
        });
        BackButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                game.ChangeState(GameStates.MAIN_SCREEN);
            }
        });
        stage.addActor(Level1Button);
        stage.addActor(Level2Button);
        stage.addActor(Level3Button);
        stage.addActor(BackButton);
    }

    @Override
    public void resize(int width, int height) {
        this.screenWidth = width;
        this.screenHeight = height;
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        game.getBatch().begin();
        game.getBatch().draw(Background, 0, 0 , screenWidth, screenHeight);
        game.getBatch().draw(levelBoard, (float) (screenWidth)/2 - 150, screenHeight - 200, 300, 120);
        stage.draw();
        handleKeyboardInput();
        game.getBatch().end();
    }

    public Stage getStage(){
        return stage;
    }

    private void handleKeyboardInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
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
        Background.dispose();
        levelBoard.dispose();
//        stage.dispose();
    }
}
