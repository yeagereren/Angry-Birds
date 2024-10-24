package com.angrybirds.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.levels.Level1Screen;

public class Main extends Game {
    private SpriteBatch batch;
    private HomeScreen myHomeScreen;
    private ChooseLevelScreen myChooseLevelScreen;
    private LosingScreen myLosingScreen;
    private WinningScreen myWinningScreen;
    private Level1Screen myLevel1Screen;
    private PauseLevelScreen myPauseScreen;
    private MainScreen myMainScreen;
    private GameStates currentState;


    @Override
    public void create() {
        batch = new SpriteBatch();
        myHomeScreen = new HomeScreen(this);
        myChooseLevelScreen = new ChooseLevelScreen(this);
        myLosingScreen = new LosingScreen(this);
        myWinningScreen = new WinningScreen(this);
        myLevel1Screen = new Level1Screen(this);
        myPauseScreen = new PauseLevelScreen(this);
        myMainScreen = new MainScreen(this);

        currentState = GameStates.HOME_SCREEN;
    }

    public SpriteBatch getBatch(){
        return this.batch;
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
                Gdx.app.exit();
                break;
            case LOSING_SCREEN:
                myLosingScreen.render();
                break;
            case WINNING_SCREEN:
                myWinningScreen.render();
                break;
            case LEVEL1SCREEN:
                myLevel1Screen.render();
                break;
            case PAUSE_SCREEN:
                myPauseScreen.render();
                break;
            case MAIN_SCREEN:
                myMainScreen.render();
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
                Gdx.app.exit();
                break;
            case LEVEL1SCREEN:
                Gdx.input.setInputProcessor(myLevel1Screen.getStage());
                break;
            case PAUSE_SCREEN:
                Gdx.input.setInputProcessor(myPauseScreen.getStage());
                break;
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        myHomeScreen.dispose();
        myChooseLevelScreen.dispose();
        myLevel1Screen.dispose();
        myLosingScreen.dispose();
        myWinningScreen.dispose();
        myHomeScreen.dispose();
        myChooseLevelScreen.dispose();
    }
}
