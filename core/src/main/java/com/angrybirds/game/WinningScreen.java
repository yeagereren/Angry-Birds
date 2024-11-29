package com.angrybirds.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.io.Serializable;

public class WinningScreen implements ApplicationListener, Serializable {
    private transient Texture WinningImage;
    private final Main game;
    private transient Stage stage;
    private CustomButton nextButton;

    WinningScreen(Main game){
        this.game = game;
        create();
    }

    private void initializeNonSerializableFields(){
        WinningImage = new Texture("WinningScreen.jpg");
        nextButton.setPosition(750, 5);
        nextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                game.ChangeState(GameStates.CHOOSE_LEVEL_SCREEN);
            }
        });
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        stage.addActor(nextButton);
    }

    @Override
    public void create() {
        nextButton = new CustomButton("Replay.png", 520, 250);
        nextButton.setVisible(true);
        initializeNonSerializableFields();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void render() {
        if(WinningImage==null){
            initializeNonSerializableFields();
        }
        stage.act(Gdx.graphics.getDeltaTime());
        game.getBatch().begin();
        game.getBatch().draw(WinningImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.getBatch().end();
        stage.draw();
        handleKeyboardInput();
    }

    private void handleKeyboardInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
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
        WinningImage.dispose();
    }

    public Stage getStage(){
        return this.stage;
    }
}
