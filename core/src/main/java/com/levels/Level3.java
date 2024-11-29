package com.levels;

import com.actors.Pig;
import com.angrybirds.game.GameStates;
import com.angrybirds.game.Main;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.io.Serializable;

public class Level3 extends LevelManager implements ApplicationListener, Serializable {
    private final Main game;
    int ScreenWidth, ScreenHeight;

    public Level3(Main game){
        super(10);
        ScreenHeight = Gdx.graphics.getHeight();
        ScreenWidth = Gdx.graphics.getWidth();
        this.game = game;
        create();
    }

    public void initializeNonSerializableFields(Main game){
        super.initializeNonSerializableFields(game);

        ForwardButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                game.reloadLevel3();
                if(PigsNo == 0){
                    if(game.getCurrentLevel()==3){
                        game.incrementLevel();
                    }
                    game.ChangeState(GameStates.WINNING_SCREEN);
                } else if(BirdsNo==0){
                    game.ChangeState(GameStates.LOSING_SCREEN);
                }
            }
        });

        BackButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                game.reloadLevel3();
                game.ChangeState(GameStates.CHOOSE_LEVEL_SCREEN);
            }
        });

        SaveGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                game.setSavedGame(Level3.this);
                game.reloadLevel3();
                game.ChangeState(GameStates.CHOOSE_LEVEL_SCREEN);
            }
        });
    }

    @Override
    public void create() {

        LoadLevelUtils(game);

        ForwardButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                game.reloadLevel3();
                if(PigsNo == 0){
                    if(game.getCurrentLevel()==3){
                        game.incrementLevel();
                    }
                    game.ChangeState(GameStates.WINNING_SCREEN);
                } else if(BirdsNo==0){
                    game.ChangeState(GameStates.LOSING_SCREEN);
                }
            }
        });

        BackButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                game.reloadLevel3();
                game.ChangeState(GameStates.CHOOSE_LEVEL_SCREEN);
            }
        });

        SaveGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                game.setSavedGame(Level3.this);
                game.reloadLevel3();
                game.ChangeState(GameStates.CHOOSE_LEVEL_SCREEN);
            }
        });
    }

    @Override
    public void resize(int i, int i1) {}

    @Override
    public void render() {
        super.render();
        game.getBatch().begin();
        if(!super.paused){
            world.step(1/60f, 6, 2);
            destroyQueuedBodies();
        }
        debugRenderer.render(world, stage.getCamera().combined);
        game.getBatch().end();
        handleKeyboardInput();
    }

    void destroyQueuedBodies() {
        for (Body body : getBodiesToDestroy()) {
            if (world.isLocked()) {
                continue;
            }
            if(body.getUserData() instanceof Pig){
                super.decrementPigs();
            }
            body.setActive(false);
        }
        getBodiesToDestroy().clear();
    }

    private void handleKeyboardInput() {
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            if(super.paused){
                resume();
            } else{
                pause();
            }
        }
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
    }
}
