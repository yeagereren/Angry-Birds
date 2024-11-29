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

public class MainScreen implements ApplicationListener, Serializable {
    private transient Texture logoTexture;
    private final Main game;
    private CustomButton backButton, loadSavedGame, newGame;
    private transient Stage stage;

    public MainScreen(Main game) {
        this.game = game;
        create();
    }

    private void initializeNonSerializableFields(){
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        logoTexture = new Texture("Logo.jpg");

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.ChangeState(GameStates.GAME_QUIT);
            }
        });
        loadSavedGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.ChangeState(GameStates.SAVED_GAME);
            }
        });
        newGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.ChangeState(GameStates.CHOOSE_LEVEL_SCREEN);
            }
        });

        Image backgroundImage = new Image(new Texture("FirstScreenBlurred.jpg"));
        backgroundImage.setFillParent(true);

        Table mainTable = new Table();
        mainTable.setFillParent(true);

        mainTable.padTop(100);
        mainTable.row();
        for(int i=0; i<10; i++){
            mainTable.row();
        }
        mainTable.add(newGame).width(newGame.getWidth()).height(newGame.getHeight()).pad(50).padTop(700);
        mainTable.add(loadSavedGame).width(loadSavedGame.getWidth()).height(loadSavedGame.getHeight()).pad(50).padTop(700);

        Table backButtonTable = new Table();
        backButtonTable.setFillParent(true);
        backButtonTable.top().left();
        backButtonTable.add(backButton).pad(10);

        stage.addActor(backgroundImage);
        stage.addActor(mainTable);
        stage.addActor(backButtonTable);
    }

    public void create() {

        loadSavedGame = new CustomButton("LoadGame.png", 350, 120);
        newGame = new CustomButton("NewGame.png", 350, 120);
        backButton = new CustomButton("GoBack.png", 90);
        initializeNonSerializableFields();
    }

    public Stage getStage() {
        return stage;
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render() {
        if(stage==null){
            initializeNonSerializableFields();
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        if(Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)){
            game.ChangeState(GameStates.GAME_QUIT);
        }
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    public void dispose() {
        logoTexture.dispose();
        backButton.dispose();
        loadSavedGame.dispose();
        newGame.dispose();
        stage.dispose();
    }
}
