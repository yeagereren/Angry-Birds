package com.levels;

import com.actors.*;
import com.angrybirds.game.CustomButton;
import com.angrybirds.game.GameStates;
import com.angrybirds.game.Main;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Level1Screen implements ApplicationListener {
    private Texture Background, RockLeft, RockRight, Slingshot, Ground, Circle;
    private final Main game;
    private RedBird redBird;
    private Chuck chuck;
    private Matilda matilda;
    private Bomb bomb;
    private CustomButton PauseButton, GoBackButton;

    public Level1Screen(Main game){
        this.game = game;
        create();
    }

    @Override
    public void create() {
        Background = new Texture("1Assets/Waterfall.jpg");
        RockRight = new Texture("1Assets/Rock5.png");
        RockLeft = new Texture("1Assets/Rock4.png");
        Slingshot = new Texture("1Assets/Slingshot.png");
        PauseButton = new CustomButton("1Assets/Pause.png", 1000, 650, 60);
        GoBackButton = new CustomButton("GoBack.png", 70, 650, 60);
        Ground = new Texture("1Assets/Ground.png");
        Circle = new Texture("Circle.png");
        redBird = new RedBird();
        chuck = new Chuck();
        bomb = new Bomb();
    }

    @Override
    public void resize(int i, int i1) {}

    @Override
    public void render() {
        game.getBatch().begin();
        game.getBatch().draw(Background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        game.getBatch().draw(RockLeft, -10, -70, 350 , 350);
//        game.getBatch().draw(RockRight, 800, -30, 300, 300);
        game.getBatch().draw(Ground, 0, -20, Gdx.graphics.getWidth()+120, (float) Gdx.graphics.getHeight()-50);
        game.getBatch().draw(Slingshot, 50, 100, 170, 210);
        game.getBatch().draw(Circle, 380, 600, 95, 100);
        game.getBatch().draw(Circle, 500, 600, 95, 100);
        game.getBatch().draw(Circle, 620, 600, 95, 100);
        game.getBatch().draw(chuck.getFace(), 395, 615, 60, 70);
        game.getBatch().draw(bomb.getFace(), 515, 615, 60, 75);
        game.getBatch().draw(redBird.getFace(), 630, 615, 65, 65);
        PauseButton.draw(game.getBatch());
        GoBackButton.draw(game.getBatch());
        game.getBatch().end();
        handleInputs();
    }

    private void handleInputs(){
        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (GoBackButton.isClicked(touchX, touchY)) {
                game.ChangeState(GameStates.CHOOSE_LEVEL_SCREEN);
            }

            if (PauseButton.isClicked(touchX, touchY)) {
                game.ChangeState(GameStates.PAUSE_SCREEN);
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
        RockLeft.dispose();
        RockRight.dispose();
        Slingshot.dispose();
        redBird.dispose();
        PauseButton.dispose();
        GoBackButton.dispose();
    }
}


//package com.levels;
//
//import com.actors.*;
//import com.angrybirds.game.CustomButton;
//import com.angrybirds.game.GameStates;
//import com.angrybirds.game.Main;
//import com.badlogic.gdx.ApplicationListener;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.scenes.scene2d.InputEvent;
//import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
//import com.badlogic.gdx.utils.viewport.ScreenViewport;
//
//public class Level1Screen implements ApplicationListener {
//    private Texture Background, RockLeft, RockRight, Slingshot;
//    private final Main game;
//
//    // Birds
//    private RedBird redBird;
//    private Chuck chuck;
//    private Matilda matilda;
//    private Bomb bomb;
//
//    // Stage and buttons
//    private Stage stage;
//    private CustomButton PauseButton, GoBackButton;
//
//    public Level1Screen(Main game){
//        this.game = game;
//        create();
//    }
//
//    @Override
//    public void create() {
//        // Load textures
//        Background = new Texture("1Assets/Waterfall.jpg");
//        RockRight = new Texture("1Assets/Rock5.png");
//        RockLeft = new Texture("1Assets/Rock4.png");
//        Slingshot = new Texture("1Assets/Slingshot.png");
//
//        // Birds
//        redBird = new RedBird();
//        chuck = new Chuck();
//        matilda = new Matilda();
//        bomb = new Bomb();
//
//        // Initialize the stage and set it as the input processor
//        stage = new Stage(new ScreenViewport());
//        Gdx.input.setInputProcessor(stage); // Input is handled by the stage
//
//        // Initialize buttons
//        PauseButton = new CustomButton("1Assets/Pause.png", 20, 600, 100);
//        GoBackButton = new CustomButton("GoBack.png", 20, 500, 100);
//
//        // Add buttons to the stage
//        stage.addActor(PauseButton);
//        stage.addActor(GoBackButton);
//
//        // Add input listeners for the buttons
//        addInputListeners();
//    }
//
//    private void addInputListeners() {
//        // Pause Button Listener
//        PauseButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                System.out.println("Pause Button Clicked!");
//                // Handle pause button logic (e.g., pause the game)
//            }
//        });
//
//        // Go Back Button Listener
//        GoBackButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                game.ChangeState(GameStates.CHOOSE_LEVEL_SCREEN);  // Example state change to main menu
//            }
//        });
//    }
//
//    @Override
//    public void resize(int width, int height) {
//        stage.getViewport().update(width, height, true);  // Update stage viewport on resize
//    }
//
//    @Override
//    public void render() {
//        // Draw the game background and objects using the batch
//        game.getBatch().begin();
//        game.getBatch().draw(Background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        game.getBatch().draw(RockLeft, -10, -70, 350 , 350);
//        game.getBatch().draw(RockRight, 800, -30, 300, 300);
//        game.getBatch().draw(Slingshot, 50, 200, 150, 200);
//        game.getBatch().draw(redBird.getFace(), 0, 0, 100, 100);
//        game.getBatch().draw(chuck.getFace(), 100, 0, 100, 100);
//        game.getBatch().draw(matilda.getFace(), 200, 0, 80, 100);
//        game.getBatch().draw(bomb.getFace(), 300, 0, 80, 100);
//        game.getBatch().end();
//
//        // Handle stage logic (this also handles button rendering)
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
//        RockLeft.dispose();
//        RockRight.dispose();
//        Slingshot.dispose();
//        redBird.dispose();
//        chuck.dispose();
//        matilda.dispose();
//        bomb.dispose();
//        stage.dispose();  // Dispose of the stage and its actors
//    }
//}
