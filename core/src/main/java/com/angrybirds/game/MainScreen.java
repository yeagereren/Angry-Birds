package com.angrybirds.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainScreen implements ApplicationListener {
    private Texture backgroundTexture;
    private Texture logoTexture;
    private final Main game;
    private CustomButton backButton, loadSavedGame, newGame;
    private Stage stage;

    public MainScreen(Main game) {
        this.game = game;
        create();
    }

    public void create() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = new Texture("FirstScreenBlurred.jpg");
        logoTexture = new Texture("Logo.jpg");

        loadSavedGame = new CustomButton("LoadGame.png", 300, 120);
        newGame = new CustomButton("NewGame.png", 300, 120);
        backButton = new CustomButton("GoBack.png", 90); // Adjust the size as needed

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.ChangeState(GameStates.GAME_QUIT);
            }
        });
        loadSavedGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.ChangeState(GameStates.CHOOSE_LEVEL_SCREEN);
            }
        });
        newGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.ChangeState(GameStates.CHOOSE_LEVEL_SCREEN);
            }
        });

        Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);

        Image logoImage = new Image(logoTexture);

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

    public Stage getStage() {
        return stage;
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    public void dispose() {
        backgroundTexture.dispose();
        logoTexture.dispose();
        backButton.dispose();
        loadSavedGame.dispose();
        newGame.dispose();
        stage.dispose();
    }
}
