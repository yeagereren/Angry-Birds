package com.angrybirds.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.levels.*;
import com.levels.LevelManager;

import java.io.*;

public class Main extends Game implements Serializable {
    private transient SpriteBatch batch;
    private transient HomeScreen myHomeScreen;
    private transient ChooseLevelScreen myChooseLevelScreen;
    private transient LosingScreen myLosingScreen;
    private transient WinningScreen myWinningScreen;
    private transient MainScreen myMainScreen;
    private GameStates currentState;
    private LevelManager savedGame = null;
    private transient Level1 myLevel1;
    private transient Level2 myLevel2;
    private transient Level3 myLevel3;
    private int CurrentLevel = -1;
    private final String path = "io.ser";

    @Override
    public void create() {
        loadGame();
    }

    public void initializeNonSerializableFields(){
        //Required to implement
    }

    public SpriteBatch getBatch(){
        if(batch==null){
            batch = new SpriteBatch();
        }
        return this.batch;
    }

    public void reloadLevel1(){
        myLevel1 = new Level1(this);
    }

    public void reloadLevel2(){
        myLevel2 = new Level2(this);
    }

    public void reloadLevel3(){
        myLevel3 = new Level3(this);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        switch (currentState){
            case HOME_SCREEN:
                myHomeScreen.render();
                break;
            case  CHOOSE_LEVEL_SCREEN:
                myChooseLevelScreen.render();
                break;
            case GAME_QUIT:
//                saveGame();
                Gdx.app.exit();
                break;
            case LOSING_SCREEN:
                myLosingScreen.render();
                break;
            case WINNING_SCREEN:
                myWinningScreen.render();
                break;
            case LEVEL1SCREEN:
                myLevel1.render();
                break;
            case MAIN_SCREEN:
                myMainScreen.render();
                break;
            case SAVED_GAME:
                savedGame.render();
                break;
            case LEVEL2SCREEN:
                myLevel2.render();
                break;
            case LEVEL3SCREEN:
                myLevel3.render();
                break;
        }
    }

    public void ChangeState(GameStates newState){
        currentState = newState;
        switch (currentState) {
            case CHOOSE_LEVEL_SCREEN:
                Gdx.input.setInputProcessor(myChooseLevelScreen.getStage());
                break;
            case MAIN_SCREEN:
                Gdx.input.setInputProcessor(myMainScreen.getStage());
                break;
            case GAME_QUIT:
                saveGame();
                Gdx.app.exit();
                break;
            case LEVEL1SCREEN:
                Gdx.input.setInputProcessor(myLevel1.getStage());
                break;
            case SAVED_GAME:
                Gdx.input.setInputProcessor(savedGame.getStage());
                break;
            case LOSING_SCREEN:
                Gdx.input.setInputProcessor(myLosingScreen.getStage());
                break;
            case WINNING_SCREEN:
                Gdx.input.setInputProcessor(myWinningScreen.getStage());
                break;
            case LEVEL2SCREEN:
                Gdx.input.setInputProcessor(myLevel2.getStage());
                break;
            case LEVEL3SCREEN:
                Gdx.input.setInputProcessor(myLevel3.getStage());
                break;
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        myHomeScreen.dispose();
        myChooseLevelScreen.dispose();
        myLosingScreen.dispose();
        myWinningScreen.dispose();
        myHomeScreen.dispose();
        myChooseLevelScreen.dispose();
    }

    public void setSavedGame(LevelManager level){
        this.savedGame = level;
    }

    public void incrementLevel(){
        CurrentLevel = Math.min(3, CurrentLevel+1);
    }

    public int getCurrentLevel(){
        return this.CurrentLevel;
    }

    private void saveGame() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path))) {
            out.writeObject(this.savedGame);
            out.writeInt(this.CurrentLevel);
        } catch (IOException e) {
            System.out.println("IO Exception in Saving Game");
        }
    }

    private void loadGame() {
        File file = new File(path);
        batch = new SpriteBatch();
        myHomeScreen = new HomeScreen(this);
        myChooseLevelScreen = new ChooseLevelScreen(this);
        myLosingScreen = new LosingScreen(this);
        myWinningScreen = new WinningScreen(this);
        myMainScreen = new MainScreen(this);
        currentState = GameStates.HOME_SCREEN;
        myLevel1 = new Level1(this);
        myLevel2 = new Level2(this);
        myLevel3 = new Level3(this);
        savedGame = new Level1(this);
        CurrentLevel = 1;
        if (!file.exists() || file.length() == 0) {
            System.out.println("File not found or empty.");
            return;
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(path))) {
            this.savedGame = (LevelManager) in.readObject();
            this.CurrentLevel = in.readInt();
            this.savedGame.initializeNonSerializableFields(this);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("IO Exception found in loading game");
        }
    }
}
