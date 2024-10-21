package com.angrybirds.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

public class CustomButton {

    private enum ButtonShape {
        RECTANGLE,
        CIRCLE
    }

    private final Texture buttonTexture;
    private final ButtonShape shapeType;
    private Rectangle buttonBounds;
    private Circle buttonCircle;

    public CustomButton(String texturePath, float x, float y, float width, float height) {
        this.buttonTexture = new Texture(Gdx.files.internal(texturePath));
        this.shapeType = ButtonShape.RECTANGLE;
        this.buttonBounds = new Rectangle(x, y, width, height);
    }

    public CustomButton(String texturePath, float centerX, float centerY, float radius) {
        this.buttonTexture = new Texture(Gdx.files.internal(texturePath));
        this.shapeType = ButtonShape.CIRCLE;
        this.buttonCircle = new Circle(centerX, centerY, radius);
    }

    public void draw(SpriteBatch batch) {
        if (shapeType == ButtonShape.RECTANGLE) {
            batch.draw(buttonTexture, buttonBounds.x, buttonBounds.y, buttonBounds.width, buttonBounds.height);
        } else if (shapeType == ButtonShape.CIRCLE) {
            batch.draw(buttonTexture,
                buttonCircle.x - buttonCircle.radius,
                buttonCircle.y - buttonCircle.radius,
                buttonCircle.radius * 2,
                buttonCircle.radius * 2);
        }
    }

    public boolean isClicked(float touchX, float touchY) {
        if (shapeType == ButtonShape.RECTANGLE) {
            return buttonBounds.contains(touchX, touchY);
        } else if (shapeType == ButtonShape.CIRCLE) {
            return buttonCircle.contains(touchX, touchY);
        }
        return false;
    }

    public void dispose() {
        buttonTexture.dispose();
    }
}
