//package com.angrybirds.game;
//
//import com.badlogic.gdx.ApplicationListener;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.InputEvent;
//import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
//import com.badlogic.gdx.utils.viewport.ScreenViewport;
//import org.w3c.dom.ls.LSOutput;
//
//public class ChooseLevelScreen implements ApplicationListener {
//    private Texture Background;
//    private int screenWidth, screenHeight;
//    private Texture levelBoard;
//    private final Main game;
//    private Stage stage;
//
//    private CustomButton Level1Button, Level2Button, Level3Button, BackButton;
//
//    public ChooseLevelScreen(Main game) {
//        this.game = game;
//        create();
//    }
//
//    @Override
//    public void create() {
//        Background = new Texture("BackgroundForLevel.jpg");
//        screenHeight = Gdx.graphics.getHeight();
//        screenWidth = Gdx.graphics.getWidth();
//        levelBoard = new Texture("LevelBoard.png");
//
//        stage = new Stage(new ScreenViewport());
//
//        Level1Button = new CustomButton("Level1.png", 350, 400, 70);
//        Level2Button = new CustomButton("Level2.png", 550, 400, 70);
//        Level3Button = new CustomButton("Level3.png", 750, 400, 70);
//        BackButton = new CustomButton("ExitButton.png", 460, 150, 200, 100);
//
//        stage.addActor(Level1Button);
//        stage.addActor(Level2Button);
//        stage.addActor(Level3Button);
//        stage.addActor(BackButton);
//
//        addInputListeners();
//
//        Gdx.input.setInputProcessor(stage);
//    }
//
//    private void addInputListeners() {
//        Level1Button.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                System.out.println("Level 1 Button Clicked");
//                game.ChangeState(GameStates.LEVEL1SCREEN);
//            }
//        });
//
//        Level2Button.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                System.out.println("Level2 Clicked!");
//            }
//        });
//
//        Level3Button.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                System.out.println("Level3 Clicked!");
//            }
//        });
//
//        BackButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                game.ChangeState(GameStates.GAME_QUIT);
//            }
//        });
//    }
//
//    @Override
//    public void resize(int width, int height) {
//        this.screenWidth = width;
//        this.screenHeight = height;
//        stage.getViewport().update(width, height, true);
//    }
//
//    @Override
//    public void render() {
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        game.getBatch().begin();
//        game.getBatch().draw(Background, 0, 0, screenWidth, screenHeight);
//        game.getBatch().draw(levelBoard, (float) (screenWidth) / 2 - 150, screenHeight - 200, 300, 120);
//        game.getBatch().end();
//
//        stage.act(Gdx.graphics.getDeltaTime());
//        stage.draw();
//    }
//
//    @Override
//    public void pause() {}
//
//    @Override
//    public void resume() {}
//
//    @Override
//    public void dispose() {
//        Background.dispose();
//        levelBoard.dispose();
//        stage.dispose();
//    }
//}


package com.angrybirds.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ChooseLevelScreen implements ApplicationListener {
    private final SpriteBatch batch;
    private Texture Background;
    private int screenWidth, screenHeight;
    private Texture levelBoard;
    private final Main game;

    private CustomButton Level1Button, Level2Button, Level3Button, BackButton;

    ChooseLevelScreen(SpriteBatch sprite, Main game){
        this.batch = sprite;
        this.game = game;
        create();
    }

    @Override
    public void create() {
        Background = new Texture("BackgroundForLevel.jpg");
        screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();
        levelBoard = new Texture("LevelBoard.png");
        Level1Button = new CustomButton("Level1.png", 300, 400, 90);
        Level2Button = new CustomButton("Lock2.png", 450, 260, 190,230);
        Level3Button = new CustomButton("Lock3.png", 700,250, 190, 240);
        BackButton = new CustomButton("GoBack.png", 70, 650, 60);
    }

    @Override
    public void resize(int width, int height) {
        this.screenWidth = width;
        this.screenHeight = height;
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(Background, 0, 0 , screenWidth, screenHeight);
        batch.draw(levelBoard, (float) (screenWidth)/2 - 150, screenHeight - 200, 300, 120);
        Level1Button.draw(batch);
        Level2Button.draw(batch);
        Level3Button.draw(batch);
        BackButton.draw(batch);
        handleInput();
        batch.end();
    }

    public void handleInput(){
        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (BackButton.isClicked(touchX, touchY)) {
//                game.ChangeState(GameStates.HOME_SCREEN);
                game.ChangeState(GameStates.MAIN_SCREEN);
            }

            if (Level1Button.isClicked(touchX, touchY)) {
                game.ChangeState(GameStates.LEVEL1SCREEN);
            }

            if (Level2Button.isClicked(touchX, touchY)) {
                System.out.println("Level2 Clicked!");
            }

            if (Level3Button.isClicked(touchX, touchY)) {
                System.out.println("Level3 Clicked!");
            }
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
        Background.dispose();
        levelBoard.dispose();
    }
}
