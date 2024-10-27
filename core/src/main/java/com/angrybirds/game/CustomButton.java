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

public class CustomButton extends Actor {

    private enum ButtonShape {
        RECTANGLE,
        CIRCLE
    }

    private final Texture buttonTexture;
    private final ButtonShape shapeType;
    private Rectangle buttonBounds;
    private Circle buttonCircle;

    public CustomButton(String texturePath, float width, float height) {
        this.buttonTexture = new Texture(Gdx.files.internal(texturePath));
        this.shapeType = ButtonShape.RECTANGLE;
        this.buttonBounds = new Rectangle(getX(), getY(), width, height);
        this.setSize(width, height);

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (isClicked(event.getStageX(), event.getStageY())) {
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

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (isClicked(event.getStageX(), event.getStageY())) {
                    fire(new ChangeListener.ChangeEvent());
                }
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (shapeType == ButtonShape.RECTANGLE) {
            buttonBounds.setPosition(getX(), getY());  // Ensure buttonBounds are in sync
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

    public boolean isClicked(float touchX, float touchY) {
        if (shapeType == ButtonShape.RECTANGLE) {
            return buttonBounds.contains(touchX, touchY);
        } else if (shapeType == ButtonShape.CIRCLE) {
            return buttonCircle.contains(touchX, touchY);
        }
        return false;
    }

    // Override to make width and height dynamically available for table layout
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



//package com.angrybirds.game;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Batch;
//import com.badlogic.gdx.math.Circle;
//import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.scenes.scene2d.Actor;
//import com.badlogic.gdx.scenes.scene2d.InputEvent;
//import com.badlogic.gdx.scenes.scene2d.InputListener;
//import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
//
//public class CustomButton extends Actor {
//
//    private enum ButtonShape {
//        RECTANGLE,
//        CIRCLE
//    }
//
//    private final Texture buttonTexture;
//    private final ButtonShape shapeType;
//    private Rectangle buttonBounds;
//    private Circle buttonCircle;
//
//    public CustomButton(String texturePath, float x, float y, float width, float height) {
//        this.buttonTexture = new Texture(Gdx.files.internal(texturePath));
//        this.shapeType = ButtonShape.RECTANGLE;
//        this.buttonBounds = new Rectangle(x, y, width, height);
//        this.setSize(width, height);
//        this.setPosition(x, y);
//
//        addListener(new InputListener() {
//            @Override
//            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                return true;
//            }
//
//            @Override
//            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//                if (isClicked(event.getStageX(), event.getStageY())) {
//                    fire(new ChangeListener.ChangeEvent());
//                }
//            }
//        });
//    }
//
//    public CustomButton(String texturePath, float centerX, float centerY, float radius) {
//        this.buttonTexture = new Texture(Gdx.files.internal(texturePath));
//        this.shapeType = ButtonShape.CIRCLE;
//        this.buttonCircle = new Circle(centerX, centerY, radius);
//        this.setSize(radius * 2, radius * 2);
//        this.setPosition(centerX - radius, centerY - radius);
//
//        addListener(new InputListener() {
//            @Override
//            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                return true;
//            }
//
//            @Override
//            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//                if (isClicked(event.getStageX(), event.getStageY())) {
//                    fire(new ChangeListener.ChangeEvent());
//                }
//            }
//        });
//    }
//
//    @Override
//    public void draw(Batch batch, float parentAlpha) {
//        if (shapeType == ButtonShape.RECTANGLE) {
//            batch.draw(buttonTexture, getX(), getY(), getWidth(), getHeight());
//        } else if (shapeType == ButtonShape.CIRCLE) {
//            batch.draw(buttonTexture,
//                buttonCircle.x - buttonCircle.radius,
//                buttonCircle.y - buttonCircle.radius,
//                buttonCircle.radius * 2,
//                buttonCircle.radius * 2);
//        }
//    }
//
//    public boolean isClicked(float touchX, float touchY) {
//        if (shapeType == ButtonShape.RECTANGLE) {
//            return buttonBounds.contains(touchX, touchY);
//        } else if (shapeType == ButtonShape.CIRCLE) {
//            return buttonCircle.contains(touchX, touchY);
//        }
//        return false;
//    }
//
//    public void dispose() {
//        buttonTexture.dispose();
//    }
//}
