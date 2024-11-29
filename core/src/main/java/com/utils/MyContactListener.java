package com.utils;

import com.actors.Pig;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.TimeUtils;
import com.actors.*;
import com.levels.LevelManager;

import java.io.Serializable;

public class MyContactListener implements ContactListener, Serializable {
    private final float StartTime;
    private final LevelManager level;
    private boolean isSilverA, isSilverB, isEggA, isEggB, isExplosionA, isExplosionB, isBirdA, isBirdB, isPigA, isPigB, isMaterialA, isMaterialB;

    public MyContactListener(LevelManager level) {
        StartTime = TimeUtils.nanoTime() / 1000000000f;
        this.level = level;
        isSilverA = false;
        isSilverB = false;
        isEggA = false;
        isEggB = false;
        isExplosionA = false;
        isExplosionB = false;
        isBirdA = false;
        isBirdB = false;
        isPigA = false;
        isPigB = false;
        isMaterialA = false;
        isMaterialB = false;
    }

    @Override
    public void beginContact(Contact contact) {
        if (TimeUtils.nanoTime() / 1000000000f - StartTime < 5f) {
            return;
        }
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        try{
            isSilverA = bodyA.getUserData() instanceof Silver;
            isSilverB = bodyB.getUserData() instanceof Silver;
        } catch (Exception ignored){}

        if (isSilverA || isSilverB) {
            Body otherBody = isSilverA ? bodyB : bodyA;
            Body birdBody = isSilverA ? bodyA : bodyB;

            Object otherUserData = otherBody.getUserData();

            Silver bird = (Silver) birdBody.getUserData();

            if (!bird.getAbilityUsed()) {
                return;
            }

            if (otherUserData instanceof Pig) {
                Pig pig = (Pig) otherUserData;
                pig.instantKill();
                level.getBodiesToDestroy().add(otherBody);
            } else if (otherUserData instanceof Material) {
                Material material = (Material) otherUserData;
                material.destroy();
                level.getBodiesToDestroy().add(otherBody);
            }
        }

        try {
            isEggA = bodyA.getUserData() instanceof Matilda.Egg;
            isEggB = bodyB.getUserData() instanceof Matilda.Egg;
        } catch (Exception ignored){}

        if (isEggA || isEggB) {
            Body eggBody = isEggA ? bodyA : bodyB;
            Body otherBody = isEggA ? bodyB : bodyA;

            Object otherUserData = otherBody.getUserData();

            if (otherUserData instanceof Pig) {
                Pig pig = (Pig) otherUserData;
                pig.instantKill();
                level.getBodiesToDestroy().add(otherBody);
            } else if (otherUserData instanceof Material) {
                Material material = (Material) otherUserData;
                material.destroy();
                level.getBodiesToDestroy().add(otherBody);
            } else if(otherUserData instanceof String){
                String string = (String) otherUserData;
                if(string.equals("Ground")){
                    level.getBodiesToDestroy().add(eggBody);
                }
            }
        }

        try {
            isExplosionA = bodyA.getUserData() instanceof Integer && (Integer) bodyA.getUserData() == 1;
            isExplosionB = bodyB.getUserData() instanceof Integer && (Integer) bodyB.getUserData() == 1;
        } catch (Exception ignored){}

        if (isExplosionA || isExplosionB) {
            Body otherBody = isExplosionA ? bodyB : bodyA;

            if (otherBody.getUserData() instanceof Pig) {
                Pig pig = (Pig) otherBody.getUserData();
                pig.die();
                level.getBodiesToDestroy().add(otherBody);
                level.decrementPigs();
            }

            if (otherBody.getUserData() instanceof Material) {
                Material material = (Material) otherBody.getUserData();
                material.destroy();
                level.getBodiesToDestroy().add(otherBody);
            }
        }

    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        if (TimeUtils.nanoTime() / 1000000000f - StartTime < 5f) {
            return;
        }

        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        try{
            isPigA = bodyA.getUserData() instanceof  Pig;
            isPigB = bodyB.getUserData() instanceof  Pig;
        } catch (Exception ignored){}

        if (isPigA || isPigB) {
            Pig pig = (bodyA.getUserData() instanceof Pig) ? (Pig) bodyA.getUserData() : (Pig) bodyB.getUserData();

            float damage = impulse.getNormalImpulses()[0] * 2;

            pig.applyDamage(damage);
        }

        try{
            isBirdA = bodyA.getUserData() instanceof BasicBird;
            isBirdB = bodyB.getUserData() instanceof BasicBird;
        } catch (Exception ignored){}

        if(isBirdA || isBirdB){
            Body otherBody = isBirdA ? bodyB : bodyA;

            float damage = impulse.getNormalImpulses()[0];

            if (otherBody.getUserData() instanceof Material) {
                Material material = (Material) otherBody.getUserData();
                material.hit(damage);
                if(material.destroyed){
                    level.getBodiesToDestroy().add(otherBody);
                }
            }
        }

        try{
            isMaterialA = bodyA.getUserData() instanceof Material;
            isMaterialB = bodyB.getUserData() instanceof Material;
        } catch (Exception ignored){}

        if(isMaterialB && isMaterialA){
            Material materialA = (Material) bodyA.getUserData();
            Material materialB = (Material) bodyB.getUserData();

            materialA.hit(1);
            materialB.hit(1);

            if(materialA.destroyed){
                level.getBodiesToDestroy().add(bodyA);
            } if(materialB.destroyed){
                level.getBodiesToDestroy().add(bodyB);
            }
        }
    }
}
