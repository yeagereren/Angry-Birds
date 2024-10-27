package com.angrybirds.game;

import com.actors.*;
import com.angrybirds.game.CustomButton;
import com.angrybirds.game.GameStates;
import com.angrybirds.game.Main;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.structure.*;

public class PauseLevelScreen implements ApplicationListener {
    private Texture Slingshot;
    private Image Background;
    private final Main game;
    private RedBird redBird;
    private Chuck chuck;
    private Bomb bomb;
    private Pig pig;
    private KingPig kingPig;
    private ForemanPig foremanPig;
    private CustomButton ResumeButton, BackButton;
    private Stage stage;
    int ScreenWidth, ScreenHeight;

    public PauseLevelScreen(Main game){
        this.game = game;
        create();
    }

    @Override
    public void create() {
        ScreenHeight = Gdx.graphics.getHeight();
        ScreenWidth = Gdx.graphics.getWidth();
        pig = new Pig(1500, 453, 100, 100);
        kingPig = new KingPig(1500, 670, 100, 120);
        foremanPig = new ForemanPig(1470, 140, 150, 150);

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Image background = new Image(new Texture("1Assets/Waterfall.jpg"));
        background.setFillParent(true);

        Slingshot = new Texture("1Assets/Slingshot.png");
        Image paused = new Image(new Texture("Pause.png"));

        ResumeButton = new CustomButton("1Assets/Resume.png", 82);
        BackButton = new CustomButton("GoBack.png", 90);

        CustomButton circle1 = new CustomButton("Circle.png", 70);
        CustomButton circle2 = new CustomButton("Circle.png", 70);
        CustomButton Circle3 = new CustomButton("Circle.png", 70);
        redBird = new RedBird(905, 910, 95, 95);
        chuck = new Chuck(710, 910, 90, 95);
        bomb = new Bomb(1115, 910, 80, 95);

        Table mainTable = new Table();
        mainTable.setFillParent(true);

        mainTable.top();
        mainTable.add(circle1).width(circle1.getWidth()).height(circle1.getHeight()).padTop(70).pad(30);
        mainTable.add(circle2).width(circle2.getWidth()).height(circle2.getHeight()).padTop(70).pad(30);
        mainTable.add(Circle3).width(Circle3.getWidth()).height(Circle3.getHeight()).padTop(70).pad(30);

        ResumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                game.ChangeState(GameStates.LEVEL1SCREEN);
            }
        });

        BackButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                game.ChangeState(GameStates.CHOOSE_LEVEL_SCREEN);
            }
        });

        Table PauseTable = new Table();
        PauseTable.setFillParent(true);
        PauseTable.add(paused).width(paused.getWidth()).height(paused.getHeight()).expandX();

        Table ButtonTable = new Table();
        ButtonTable.setFillParent(true);
        ButtonTable.top().right();
        ButtonTable.add(ResumeButton).pad(12).padTop(20);

        Table backButtonTable = new Table();
        backButtonTable.setFillParent(true);
        backButtonTable.top().left();
        backButtonTable.add(BackButton).pad(10);

        stage.addActor(background);
        stage.addActor(mainTable);
        stage.addActor(ButtonTable);
        stage.addActor(backButtonTable);
        stage.addActor(PauseTable);
    }

    private void PrepareBuilding(){
        Stone stone;
        stone = new Stone(1350,120,91,253);
        stone.render(game.getBatch());
        stone = new Stone(1650, 120, 91, 253);
        stone.render(game.getBatch());
        stone = new Stone(1350,373,391,91);
        stone.render(game.getBatch());

        Wood wood;
        wood = new Wood(1450,464,43,82);
        wood.render(game.getBatch());
        wood = new Wood(1450,546,43,82);
        wood.render(game.getBatch());
        wood = new Wood(1600,464,43,82);
        wood.render(game.getBatch());
        wood = new Wood(1600,546,43,82);
        wood.render(game.getBatch());

        Glass glass;
        glass = new Glass(1450,628,200,50);
        glass.render(game.getBatch());
    }

    private void PreparePigs(){
        pig.render(game.getBatch());
        kingPig.render(game.getBatch());
        foremanPig.render(game.getBatch());
    }


    @Override
    public void resize(int i, int i1) {}

    @Override
    public void render() {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        game.getBatch().begin();
        game.getBatch().draw(Slingshot, 100, 100, 250, 320);
        chuck.render(game.getBatch());
        redBird.render(game.getBatch());
        bomb.render(game.getBatch());
        PrepareBuilding();
        PreparePigs();
        game.getBatch().end();
        handleKeyboardInput();
    }

    private void handleKeyboardInput() {
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            game.ChangeState(GameStates.PAUSE_SCREEN);
        }

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.W)) {
            game.ChangeState(GameStates.WINNING_SCREEN);
        }

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.L)) {
            game.ChangeState(GameStates.LOSING_SCREEN);
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    public Stage getStage() {
        return stage;
    }

    @Override
    public void dispose() {
        Slingshot.dispose();
        redBird.dispose();
        chuck.dispose();
        bomb.dispose();
        pig.dispose();
        kingPig.dispose();
        foremanPig.dispose();
        ResumeButton.dispose();
        BackButton.dispose();
        stage.dispose();
    }
}
