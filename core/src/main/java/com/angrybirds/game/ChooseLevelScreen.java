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

import java.io.Serializable;

public class ChooseLevelScreen implements ApplicationListener, Serializable {
    private final Main game;
    private transient Stage stage;
    private CustomButton Level2Button, Level3Button, Level3LockedButton, Level2LockedButton, BackButton, level1Button;

    ChooseLevelScreen(Main game){
        this.game = game;
        create();
    }

    private void initializeNonSerializableFields(){
        if(stage!=null){
            return;
        }
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Image background = new Image(new Texture("BackgroundForLevel.jpg"));
        background.setFillParent(true);
        level1Button.setPosition(470, 300);
        Level2Button.setPosition(870, 300);
        Level3Button.setPosition(1270, 300);
        Level2LockedButton.setPosition(870, 230);
        Level3LockedButton.setPosition(1270, 230);

        level1Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                game.reloadLevel1();
                game.ChangeState(GameStates.LEVEL1SCREEN);
            }
        });

        Level2Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                game.reloadLevel2();
                game.ChangeState(GameStates.LEVEL2SCREEN);
            }
        });

        Level3Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                game.reloadLevel3();
                game.ChangeState(GameStates.LEVEL3SCREEN);
            }
        });

        BackButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                game.ChangeState(GameStates.MAIN_SCREEN);
            }
        });

        Table backButtonTable = new Table();
        backButtonTable.setFillParent(true);
        backButtonTable.top().left();
        backButtonTable.add(BackButton).pad(10);

        stage.addActor(background);
        stage.addActor(backButtonTable);
        stage.addActor(level1Button);
        stage.addActor(Level2LockedButton);
        stage.addActor(Level2Button);
        stage.addActor(Level3LockedButton);
        stage.addActor(Level3Button);
    }

    @Override
    public void create() {
        level1Button = new CustomButton("Level1.png", 285, 280);
        Level2Button = new CustomButton("Level2.png", 285,280);
        Level3Button = new CustomButton("Level3.png", 285,280);
        Level2LockedButton = new CustomButton("Lock2.png", 295,370);
        Level3LockedButton = new CustomButton("Lock3.png", 295, 370);
        BackButton = new CustomButton("GoBack.png", 90);
        Level2Button.setVisible(false);
        Level3Button.setVisible(false);
        initializeNonSerializableFields();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void render() {
        if(stage==null){
            initializeNonSerializableFields();
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        updateVisibility();
        stage.draw();
        handleKeyboardInput();
    }

    private void updateVisibility(){
        if(game.getCurrentLevel()>=2){
            Level2Button.setVisible(true);
            Level2LockedButton.setVisible(false);
        } if(game.getCurrentLevel()>=3){
            Level3Button.setVisible(true);
            Level3LockedButton.setVisible(false);
        }
    }

    public Stage getStage(){
        return stage;
    }

    private void handleKeyboardInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            game.reloadLevel1();
            game.ChangeState(GameStates.LEVEL1SCREEN);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            if(game.getCurrentLevel()>=2){
                game.reloadLevel2();
                game.ChangeState(GameStates.LEVEL2SCREEN);
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            if(game.getCurrentLevel()>=3){
                game.reloadLevel3();
                game.ChangeState(GameStates.LEVEL3SCREEN);
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)){
            game.ChangeState(GameStates.MAIN_SCREEN);
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
