package com.angrybirds.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.io.Serializable;

public class CustomButton extends Actor implements Serializable {

    private enum ButtonShape {
        RECTANGLE,
        CIRCLE
    }

    private transient Texture buttonTexture;
    private final ButtonShape shapeType;
    private transient Rectangle buttonBounds;
    private transient Circle buttonCircle;
    private boolean isEnabled;
    private final String path;
    private final float width, height, radius;
    private boolean visibility;
    private float posX, posY;

    public CustomButton(String texturePath, float width, float height) {
        this.buttonTexture = new Texture(Gdx.files.internal(texturePath));
        this.shapeType = ButtonShape.RECTANGLE;
        this.buttonBounds = new Rectangle(getX(), getY(), width, height);
        this.setSize(width, height);
        this.isEnabled = true;
        this.path = texturePath;
        this.width = width;
        this.height = height;
        this.radius = 0;

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return isEnabled;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (isEnabled && isClicked(event.getStageX(), event.getStageY())) {
                    fire(new ChangeListener.ChangeEvent());
                }
            }
        });
    }

    public CustomButton(String texturePath, float radius) {
        this.buttonTexture = new Texture(Gdx.files.internal(texturePath));
        this.shapeType = ButtonShape.CIRCLE;
        this.buttonCircle = new Circle(getX() + radius, getY() + radius, radius);
        this.setSize(radius * 2, radius * 2);
        this.isEnabled = true;
        this.path = texturePath;
        this.width = 0;
        this.height = 0;
        this.radius = radius;

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return isEnabled;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (isEnabled && isClicked(event.getStageX(), event.getStageY())) {
                    fire(new ChangeListener.ChangeEvent());
                }
            }
        });
    }

    public void initializeNonSerializableFields(){
        if(buttonTexture==null){
            buttonTexture = new Texture(Gdx.files.internal(path));
            this.setVisible(visibility);
            if(this.shapeType == ButtonShape.RECTANGLE){
                setSize(width, height);
                buttonBounds = new Rectangle(getX(), getY(), width, height);
            } else if(this.shapeType == ButtonShape.CIRCLE){
                setSize(radius*2, radius*2);
                buttonCircle = new Circle(getX() + radius, getY() + radius, radius);
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        initializeNonSerializableFields();
        if (shapeType == ButtonShape.RECTANGLE) {
            buttonBounds.setPosition(getX(), getY());
            batch.draw(buttonTexture, getX(), getY(), getWidth(), getHeight());
        } else if (shapeType == ButtonShape.CIRCLE) {
            buttonCircle.setPosition(getX() + buttonCircle.radius, getY() + buttonCircle.radius);  // Sync position
            batch.draw(buttonTexture,
                buttonCircle.x - buttonCircle.radius,
                buttonCircle.y - buttonCircle.radius,
                buttonCircle.radius * 2,
                buttonCircle.radius * 2);
        }
    }

    @Override
    public void setPosition(float x, float y){
        super.setPosition(x, y);
        this.posX = x;
        this.posY = y;
    }

    public void disableButton(){
        isEnabled = false;
    }

    public void enableButton(){
        isEnabled = true;
    }

    public boolean isClicked(float touchX, float touchY) {
        if (shapeType == ButtonShape.RECTANGLE) {
            return buttonBounds.contains(touchX, touchY);
        } else if (shapeType == ButtonShape.CIRCLE) {
            return buttonCircle.contains(touchX, touchY);
        }
        return false;
    }

    @Override
    public void setVisible(boolean visibility){
        super.setVisible(visibility);
        this.visibility = visibility;
    }

    @Override
    public float getWidth() {
        return super.getWidth();
    }

    @Override
    public float getHeight() {
        return super.getHeight();
    }

    public void dispose() {
        buttonTexture.dispose();
    }
}
