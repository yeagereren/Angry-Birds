package com.angrybirds.game;

import com.actors.Bomb;
import com.actors.Chuck;
import com.actors.Matilda;
import com.actors.RedBird;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class PauseLevelScreen implements ApplicationListener {
    private Texture Background, RockLeft, RockRight, Slingshot, Ground, PausedText, Circle;
    private final Main game;
    private RedBird redBird;
    private Chuck chuck;
    private Bomb bomb;
    private CustomButton ResumeButton, GoBackButton;

    public PauseLevelScreen(Main game){
        this.game = game;
        create();
    }

    @Override
    public void create() {
        Background = new Texture("1Assets/Waterfall.jpg");
        RockRight = new Texture("1Assets/Rock5.png");
        RockLeft = new Texture("1Assets/Rock4.png");
        PausedText = new Texture("PAUSED.png");
        Slingshot = new Texture("1Assets/Slingshot.png");
        ResumeButton = new CustomButton("1Assets/Resume.png", 1005, 648, 55);
        GoBackButton = new CustomButton("GoBack.png", 70, 650, 60);
        Ground = new Texture("1Assets/Ground.png");
        Circle = new Texture("Circle.png");
        redBird = new RedBird();
        chuck = new Chuck();
        bomb = new Bomb();
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
        game.getBatch().draw(PausedText, 200, 150, Gdx.graphics.getWidth()-350, Gdx.graphics.getHeight()-200);
        game.getBatch().draw(Circle, 380, 600, 95, 100);
        game.getBatch().draw(Circle, 500, 600, 95, 100);
        game.getBatch().draw(Circle, 620, 600, 95, 100);
        game.getBatch().draw(chuck.getFace(), 395, 615, 60, 70);
        game.getBatch().draw(bomb.getFace(), 515, 615, 60, 75);
        game.getBatch().draw(redBird.getFace(), 630, 615, 65, 65);
//        game.getBatch().draw(chuck.getFace(), 100, 0, 100, 100);
//        game.getBatch().draw(matilda.getFace(), 200, 0, 80, 100);
//        game.getBatch().draw(bomb.getFace(), 300, 0, 80, 100);
//        game.getBatch().draw(redBird.getFace(), 0, 0, 100, 100);
        ResumeButton.draw(game.getBatch());
        GoBackButton.draw(game.getBatch());
        game.getBatch().end();
        handleInputs();
    }

    private void handleInputs(){
        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (ResumeButton.isClicked(touchX, touchY)) {
                game.ChangeState(GameStates.LEVEL1SCREEN);
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
        ResumeButton.dispose();
        GoBackButton.dispose();
    }
}
