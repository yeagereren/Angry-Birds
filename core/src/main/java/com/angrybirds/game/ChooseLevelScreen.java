//package com.angrybirds.game;
//
//import com.badlogic.gdx.ApplicationListener;
//import com.badlogic.gdx.Game;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.ui.Skin;
//import com.badlogic.gdx.scenes.scene2d.ui.Table;
//import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
//import com.badlogic.gdx.utils.viewport.ScreenViewport;
//
//public class ChooseLevelScreen implements ApplicationListener {
//    private final SpriteBatch batch;
//    private Texture Background;
//    private int screenWidth, screenHeight;
//    private Texture levelBoard;
//    private final Main game;
//    private Stage stage;
//    private Skin skin;
//
//    private TextButton Level1Button, Level2Button, Level3Button, BackButton;
//
//    ChooseLevelScreen(SpriteBatch sprite, Main game){
//        this.batch = sprite;
//        this.game = game;
//        create();
//    }
//
//    @Override
//    public void create() {
//        skin = new Skin(Gdx.files.internal("skins/metal/metal-ui.json"));
//        stage = new Stage(new ScreenViewport());
//        Gdx.input.setInputProcessor(stage);
//        Background = new Texture("BackgroundForLevel.jpg");
//        screenHeight = Gdx.graphics.getHeight();
//        screenWidth = Gdx.graphics.getWidth();
//        levelBoard = new Texture("LevelBoard.png");
//        Level1Button = new TextButton("1", skin);
//        Level2Button = new TextButton("2", skin);
//        Level3Button = new TextButton("3", skin);
//        BackButton = new TextButton("Back", skin);
//        stage.addActor(Level1Button);
//        stage.addActor(Level2Button);
//        stage.addActor(Level3Button);
//        stage.addActor(BackButton);
//
//        Table root = new Table();
//        root.setFillParent(true);
//        stage.addActor(root);
//
//        // Add the three buttons in a row
//        root.add(Level1Button).pad(10);
//        root.add(Level2Button).pad(10);
//        root.add(Level3Button).pad(10);
//
//        // Insert two empty rows for the gap
//        root.row();
//        root.add().colspan(3);  // Empty row spanning all columns
//        root.row();
//        root.add().colspan(3);  // Another empty row spanning all columns
//
//        // Now add the "Back" button in the second column, below the gap
//        root.row();
//        root.add(); // Empty cell for the first column
//        root.add(BackButton).padTop(20); // Back button under Level2Button with padding
//        root.add(); // Empty cell for the third column
//
//        //        Level1Button = new CustomButton("Level1.png", 350, 400, 70);
////        Level2Button = new CustomButton("Level2.png", 550, 400, 70);
////        Level3Button = new CustomButton("Level3.png", 750, 400, 70);
////        BackButton = new CustomButton("ExitButton.png", 460, 150, 200, 100);
//    }
//
//
//    @Override
//    public void resize(int width, int height) {
//        this.screenWidth = width;
//        this.screenHeight = height;
//    }
//
//    @Override
//    public void render() {
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        batch.begin();
//        batch.draw(Background, 0, 0 , screenWidth, screenHeight);
//        batch.draw(levelBoard, (float) (screenWidth)/2 - 150, screenHeight - 200, 300, 120);
////        Level1Button.draw(batch);
////        Level2Button.draw(batch);
////        Level3Button.draw(batch);
////        BackButton.draw(batch);
////        handleInput();
//        stage.act();
//        stage.draw();
//        batch.end();
//    }
//
////    public void handleInput(){
////        if (Gdx.input.justTouched()) {
////            float touchX = Gdx.input.getX();
////            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
////
////            if (BackButton.isClicked(touchX, touchY)) {
//////                game.ChangeState(GameStates.HOME_SCREEN);
////                game.ChangeState(GameStates.GAME_QUIT);
////            }
////
////            if (Level1Button.isClicked(touchX, touchY)) {
////                game.ChangeState(GameStates.LEVEL1SCREEN);
////            }
////
////            if (Level2Button.isClicked(touchX, touchY)) {
////                System.out.println("Level2 Clicked!");
////            }
////
////            if (Level3Button.isClicked(touchX, touchY)) {
////                System.out.println("Level3 Clicked!");
////            }
////        }
////    }
//
//    @Override
//    public void pause() {
//
//    }
//
//    @Override
//    public void resume() {
//
//    }
//
//    @Override
//    public void dispose() {
//        Background.dispose();
//        levelBoard.dispose();
//    }
//}



package com.angrybirds.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
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
        Level1Button = new CustomButton("Level1.png", 350, 400, 70);
        Level2Button = new CustomButton("Level2.png", 550, 400, 70);
        Level3Button = new CustomButton("Level3.png", 750, 400, 70);
        BackButton = new CustomButton("ExitButton.png", 460, 150, 200, 100);
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
                game.ChangeState(GameStates.GAME_QUIT);
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
