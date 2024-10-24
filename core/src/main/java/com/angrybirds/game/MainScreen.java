package com.angrybirds.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainScreen implements ApplicationListener {
    private Texture homeScreenImage;
    private int ScreenWidth;
    private int ScreenHeight;
    private final Main game;
    private CustomButton ExitButton, LoadSavedGame, NewGame;
    private Texture logo;
    private Stage stage;

    public MainScreen(Main game) {
        this.game = game;
        create();
    }

    public void create() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        homeScreenImage = new Texture("FirstScreenBlurred.jpg");
        LoadSavedGame = new CustomButton("LoadGame.png", 600, 60, 200, 80);
        NewGame = new CustomButton("NewGame.png", 300, 60, 200, 80);
        ExitButton = new CustomButton("GoBack.png", 70, 650, 60);
        logo = new Texture("Logo.jpg");

        ExitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                game.ChangeState(GameStates.GAME_QUIT);
            }
        });
        LoadSavedGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                game.ChangeState(GameStates.CHOOSE_LEVEL_SCREEN);
            }
        });
        NewGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                game.ChangeState(GameStates.CHOOSE_LEVEL_SCREEN);
            }
        });
        stage.addActor(LoadSavedGame);
        stage.addActor(NewGame);
        stage.addActor(ExitButton);
        ScreenHeight = Gdx.graphics.getHeight();
        ScreenWidth = Gdx.graphics.getWidth();
    }

    @Override
    public void resize(int width, int height) {
        this.ScreenWidth = width;
        this.ScreenHeight = height;
    }

    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        game.getBatch().begin();
        game.getBatch().draw(homeScreenImage, 0, 0, ScreenWidth, ScreenHeight);
        game.getBatch().draw(logo, ((float) ScreenWidth / 4)-50 , ScreenHeight - 350, 700, 200);

        game.getBatch().end();
        stage.draw();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    public Stage getStage(){
        return stage;
    }

    public void dispose() {
        homeScreenImage.dispose();
        ExitButton.dispose();
        LoadSavedGame.dispose();
        NewGame.dispose();
        stage.dispose();
    }
}
