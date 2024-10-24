package com.angrybirds.game;

import com.actors.*;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.structure.Wood;

public class PauseLevelScreen implements ApplicationListener {
    private Texture Background, RockLeft, RockRight, Slingshot, Ground, PausedText, Circle;
    private final Main game;
    private RedBird redBird;
    private Chuck chuck;
    private Bomb bomb;
    private CustomButton ResumeButton, GoBackButton;
    private Pig pig;
    private KingPig kingPig;
    private Stage stage;

    public PauseLevelScreen(Main game){
        this.game = game;
        create();
    }

    @Override
    public void create() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Background = new Texture("1Assets/Waterfall.jpg");
        RockRight = new Texture("1Assets/Rock5.png");
        RockLeft = new Texture("1Assets/Rock4.png");
        PausedText = new Texture("Pause.png");
        Slingshot = new Texture("1Assets/Slingshot.png");
        ResumeButton = new CustomButton("1Assets/Resume.png", 1005, 648, 55);
        GoBackButton = new CustomButton("GoBack.png", 70, 650, 60);
        Ground = new Texture("1Assets/Ground.png");
        Circle = new Texture("Circle.png");
        pig = new Pig();
        kingPig = new KingPig();
        redBird = new RedBird();
        chuck = new Chuck();
        bomb = new Bomb();
        redBird = new RedBird();
        chuck = new Chuck();
        bomb = new Bomb();

        ResumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                game.ChangeState(GameStates.LEVEL1SCREEN);
            }
        });
        GoBackButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                game.ChangeState(GameStates.CHOOSE_LEVEL_SCREEN);
            }
        });
        stage.addActor(GoBackButton);
        stage.addActor(ResumeButton);
    }

    @Override
    public void resize(int i, int i1) {}

    @Override
    public void render() {
        stage.act(Gdx.graphics.getDeltaTime());
        game.getBatch().begin();
        game.getBatch().draw(Background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.getBatch().draw(Ground, 0, -20, Gdx.graphics.getWidth()+120, (float) Gdx.graphics.getHeight()-50);
        game.getBatch().draw(Slingshot, 50, 100, 170, 210);
        game.getBatch().draw(PausedText, 300, 350, 450, 130);
        game.getBatch().draw(Circle, 380, 600, 95, 100);
        game.getBatch().draw(Circle, 500, 600, 95, 100);
        game.getBatch().draw(Circle, 620, 600, 95, 100);
        game.getBatch().draw(chuck.getFace(), 395, 615, 60, 70);
        game.getBatch().draw(bomb.getFace(), 515, 615, 60, 75);
        game.getBatch().draw(redBird.getFace(), 630, 615, 65, 65);
        game.getBatch().draw(Wood.getThreeLeg(), 800, 100, 200, 200);
        game.getBatch().draw(Wood.getOneLeg(), 630, 100, 200, 200);
        game.getBatch().draw(Wood.getTwoLeg(), 730, 282, 250, 250);
        game.getBatch().draw(pig.getFace(), 872, 195, 65, 65);
        game.getBatch().draw(pig.getFace(), 690, 195, 65, 65);
        game.getBatch().draw(kingPig.getFace(), 805, 402, 80,80);
        stage.draw();
        game.getBatch().end();
        handleKeyboardInput();
    }

    private void handleKeyboardInput() {
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            game.ChangeState(GameStates.LEVEL1SCREEN);
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    public Stage getStage(){
        return stage;
    }

    @Override
    public void dispose() {
        Background.dispose();
        RockLeft.dispose();
        RockRight.dispose();
        Slingshot.dispose();
        redBird.dispose();
        ResumeButton.dispose();
        GoBackButton.dispose();
        stage.dispose();
    }
}
