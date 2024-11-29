    package com.actors;

    import com.badlogic.gdx.graphics.Texture;
    import com.badlogic.gdx.graphics.g2d.SpriteBatch;
    import com.badlogic.gdx.math.Vector2;
    import com.badlogic.gdx.physics.box2d.*;
    import com.levels.LevelManager;

    import java.io.Serializable;

    public class Pig extends Beings implements Serializable {

        protected transient Body body;
        public static final float PPM = 32f;
        public float health;
        private boolean isAlive;
        private LevelManager level;
        private transient Texture ScaredFace;
        private float shrinkFactor = 1f;
        private boolean isShrinkingCompleted = false;

        public void initializeNonSerializableFields(World world, LevelManager level){
            super.initializeNonSerializableFields(world);
            this.Face = new Texture("Birds/Piggy.png");
            ScaredFace = new Texture("Birds/ScaredPig.png");
            this.level = level;
            if(isAlive) {
                BodyDef def = new BodyDef();
                def.type = BodyDef.BodyType.DynamicBody;
                def.position.set(posX, posY);
                def.fixedRotation = true;

                body = world.createBody(def);
                body.setUserData(this);

                PolygonShape shape = new PolygonShape();
                shape.setAsBox(width / 2f / PPM, height / 2f / PPM);

                FixtureDef fixtureDef = new FixtureDef();
                fixtureDef.shape = shape;
                fixtureDef.density = 1.0f;
                fixtureDef.friction = 0.5f;
                fixtureDef.restitution = 0.5f;

                body.createFixture(fixtureDef);
                shape.dispose();
            }
        }

        public Pig(World world, int posX, int posY, LevelManager level){
            super(world, posX, posY, 75, 75);
            Face = new Texture("Birds/Piggy.png");
            ScaredFace = new Texture("Birds/ScaredPig.png");
            this.level = level;

            BodyDef def = new BodyDef();
            def.type = BodyDef.BodyType.DynamicBody;
            def.position.set(posX/PPM, posY/PPM);
            def.fixedRotation = true;

            body = world.createBody(def);
            body.setUserData(this);
            health = 1800;
            isAlive = true;

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(width / 2f / PPM, height / 2f / PPM);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.density = 1.0f;
            fixtureDef.friction = 0.5f;
            fixtureDef.restitution = 0.5f;

            body.createFixture(fixtureDef);
            shape.dispose();
        }

        @Override
        public void render(SpriteBatch batch) {
            if(body==null){
                return;
            }
            Vector2 position = body.getPosition();
            this.posX = position.x;
            this.posY = position.y;

            float renderX = (position.x * PPM) - (width / 2f);
            float renderY = (position.y * PPM) - (height / 2f);

            float rotation = (float) Math.toDegrees(body.getAngle());
            if(isAlive){
                batch.draw(Face, renderX, renderY, width / 2f, height / 2f, width, height,
                    1, 1, rotation, 0, 0, Face.getWidth(), Face.getHeight(),
                    false, false);
            } else {
                if(!isShrinkingCompleted){
                    batch.draw(Face, renderX, renderY, width / 2f, height / 2f, width * shrinkFactor, height * shrinkFactor,
                        1, 1, rotation, 0, 0, Face.getWidth(), Face.getHeight(),
                        false, false);
                }
            }
        }

        public void applyDamage(float damage) {
            if (!isAlive) return;

            health -= damage;
            if(health<=900 && !(this instanceof ForemanPig) && !(this instanceof KingPig)){
                Face = ScaredFace;
            }
            if (health <= 0) {
                die();
            }
        }

        public void die() {
            isAlive = false;
            level.getBodiesToDestroy().add(this.body);
//            level.CurrentPigs.remove(this);
        }

        public Body getBody(){
            return this.body;
        }

        public void update(float deltaTime) {
            if (!isAlive && !isShrinkingCompleted) {
                float shrinkSpeed = 2f;
                shrinkFactor -= shrinkSpeed * deltaTime;

                if (shrinkFactor <= 0) {
                    shrinkFactor = 0;
                    isShrinkingCompleted = true;
                }
            }
        }

        public void instantKill(){
            isAlive = false;
            isShrinkingCompleted = true;
        }
    }
