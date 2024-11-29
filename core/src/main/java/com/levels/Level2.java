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

public class Level2 extends LevelManager implements ApplicationListener, Serializable {
    private final Main game;
    int ScreenWidth, ScreenHeight;

    public Level2(Main game){
        super(5+(int)(Math.random()*5));
        this.game = game;
        create();
    }

    public void initializeNonSerializableFields(Main game){
        super.initializeNonSerializableFields(game);

        ForwardButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                game.reloadLevel2();
                if(PigsNo == 0){
                    if(game.getCurrentLevel()==2){
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
                game.reloadLevel2();
                game.ChangeState(GameStates.CHOOSE_LEVEL_SCREEN);
            }
        });

        SaveGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                game.setSavedGame(Level2.this);
                game.reloadLevel2();
                game.ChangeState(GameStates.CHOOSE_LEVEL_SCREEN);
            }
        });
    }

    @Override
    public void create() {
        ScreenHeight = Gdx.graphics.getHeight();
        ScreenWidth = Gdx.graphics.getWidth();

        LoadLevelUtils(game);

        ForwardButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                game.reloadLevel2();
                if(PigsNo == 0){
                    if(game.getCurrentLevel()==2){
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
                game.reloadLevel2();
                game.ChangeState(GameStates.CHOOSE_LEVEL_SCREEN);
            }
        });

        SaveGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                game.setSavedGame(Level2.this);
                game.reloadLevel2();
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

    private void destroyQueuedBodies() {
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
