package com.levels;

import com.actors.*;
import com.angrybirds.game.Main;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Level1Screen implements ApplicationListener {
    private Texture Background, RockLeft, RockRight, Slingshot, PauseButton, GoBackButton;
    private final Main game;
    private final SpriteBatch batch;
    private RedBird redBird;
    private Chuck chuck;
    private Matilda matilda;
    private Bomb bomb;

    public Level1Screen(SpriteBatch batch, Main game){
        this.batch = batch;
        this.game = game;
        create();
    }

    @Override
    public void create() {
        Background = new Texture("1Assets/Waterfall.jpg");
        RockRight = new Texture("1Assets/Rock5.png");
        RockLeft = new Texture("1Assets/Rock4.png");
        Slingshot = new Texture("1Assets/Slingshot.png");
        PauseButton = new Texture("1Assets/Pause.png");
        GoBackButton = new Texture("GoBack.png");
        redBird = new RedBird();
        chuck = new Chuck();
        matilda = new Matilda();
        bomb = new Bomb();
    }

    @Override
    public void resize(int i, int i1) {}

    @Override
    public void render() {
        batch.begin();
        batch.draw(Background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(RockLeft, -10, -70, 350 , 350);
        batch.draw(RockRight, 800, -30, 300, 300);
        batch.draw(Slingshot, 50, 200, 150, 200);
        batch.draw(redBird.getFace(), 0, 0, 100, 100);
        batch.draw(chuck.getFace(), 100, 0, 100, 100);
        batch.draw(matilda.getFace(), 200, 0, 80, 100);
        batch.draw(bomb.getFace(), 300, 0, 80, 100);
        batch.draw(PauseButton, 20, 600, 100, 100);
        batch.draw(GoBackButton, 20, 500, 100, 100);
        batch.end();
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
