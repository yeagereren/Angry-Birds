package com.angrybirds.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ChooseLevelScreen implements ApplicationListener {
    private Image Background;
    private int screenWidth, screenHeight;
    private Texture levelBoard;
    private final Main game;
    private Stage stage;

    ChooseLevelScreen(Main game){
        this.game = game;
        create();
    }

    @Override
    public void create() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Background = new Image(new Texture("BackgroundForLevel.jpg"));
        Background.setFillParent(true);
        screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();
        CustomButton Level1Button = new CustomButton("Level1.png", 285,280);
        CustomButton Level2Button = new CustomButton("Lock2.png", 285,360);
        CustomButton Level3Button = new CustomButton("Lock3.png", 285, 360);
        CustomButton BackButton = new CustomButton("GoBack.png", 90);

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

        Table MainTable = new Table();
        MainTable.setFillParent(true);

        MainTable.add(Level1Button).width(Level1Button.getWidth()).height(Level1Button.getHeight()).pad(100).padBottom(5);
        MainTable.add(Level2Button).width(Level2Button.getWidth()).height(Level2Button.getHeight()).pad(100).padTop(300);
        MainTable.add(Level3Button).width(Level3Button.getWidth()).height(Level3Button.getHeight()).pad(100).padTop(300);

        Table backButtonTable = new Table();
        backButtonTable.setFillParent(true);
        backButtonTable.top().left();
        backButtonTable.add(BackButton).pad(10);
        stage.addActor(Background);
        stage.addActor(backButtonTable);
        stage.addActor(MainTable);
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
        stage.draw();
        handleKeyboardInput();
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
    }
}
