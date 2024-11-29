package com.angrybirds.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.io.Serializable;

public class LosingScreen implements ApplicationListener, Serializable {
    private transient Texture LosingImage;
    private final Main game;
    private transient Stage stage;
    private CustomButton retryButton;

    LosingScreen(Main game){
        this.game = game;
        create();
    }

    private void initializeNonSerializableFields(){
        LosingImage = new Texture("LosingImage.jpg");
        retryButton.setPosition(450, 3);
        retryButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                game.ChangeState(GameStates.CHOOSE_LEVEL_SCREEN);
            }
        });
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        stage.addActor(retryButton);
    }

    @Override
    public void create() {
        LosingImage = new Texture("LosingImage.jpg");
        retryButton = new CustomButton("Retry.png", 1005, 230);
        retryButton.setVisible(true);
        initializeNonSerializableFields();
    }

    @Override
    public void resize(int i, int i1) {
    }

    @Override
    public void render() {
        if(LosingImage==null){
            initializeNonSerializableFields();
        }
        stage.act(Gdx.graphics.getDeltaTime());

        game.getBatch().begin();
        game.getBatch().draw(LosingImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.getBatch().end();

        stage.draw();
        handleKeyboardInput();
    }


    private void handleKeyboardInput() {
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ENTER)) {
            game.ChangeState(GameStates.CHOOSE_LEVEL_SCREEN);
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
        LosingImage.dispose();
    }

    public Stage getStage(){
        return this.stage;
    }
}
