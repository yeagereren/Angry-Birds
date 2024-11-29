package com.levels;

import com.actors.*;
import com.angrybirds.game.CustomButton;
import com.angrybirds.game.GameStates;
import com.angrybirds.game.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.utils.*;

import java.io.Serializable;
import java.util.*;

public abstract class LevelManager implements Serializable {

    protected Building CurrentBuilding;
    protected ArrayList<Pig> CurrentPigs = null;
    protected int choice, PigsNo, BirdsNo;
    protected ArrayList<AbstractMap.Entry<String, ? extends BasicBird>> AllButtons;
    protected ArrayList<BasicBird> birds;
    protected BasicBird chosenBird;
    protected Slingshot slingshot;
    protected boolean paused;
    protected Main game;
    private ArrayList<Integer> chooseBirds;


    protected transient Stage stage;
    protected transient World world;
    protected transient Body Ground;
    protected transient CustomButton circle1, circle2, circle3, PauseButton, ResumeButton, BackButton, ForwardButton, SaveGameButton;
    private transient ArrayList<Body> BodiesToDestroy; //Doubtful
    protected transient MyContactListener contactListener;
    protected transient Box2DDebugRenderer debugRenderer;

    public void initializeNonSerializableFields(Main game){
        BodiesToDestroy = new ArrayList<>();
        this.game = game;
        world = new World(new Vector2(0, -9.8f), true);
        contactListener = new MyContactListener(this);
        world.setContactListener(contactListener);
        debugRenderer = new Box2DDebugRenderer();

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Image background = new Image(new Texture("1Assets/Waterfall.jpg"));
        background.setFillParent(true);

        if(CurrentBuilding!=null){
            CurrentBuilding.initializeNonSerializableFields(world);
        }
        PrepareButtons(chooseBirds);
        if(CurrentPigs!=null){
            for(Pig pig: CurrentPigs){
                pig.initializeNonSerializableFields(world, this);
            }
        }

        for(AbstractMap.Entry<String, ? extends BasicBird> entry : AllButtons){
            BasicBird bird = entry.getValue();
            entry.getValue().initializeNonSerializableFields(world, stage, bird == chosenBird);
        }

        slingshot.initializeNonSerializableFields(world, stage, this);
        createGround(world);

        Table mainTable = new Table();
        mainTable.setFillParent(true);

        mainTable.top();
        mainTable.add(circle1).width(circle1.getWidth()).height(circle1.getHeight()).padTop(70).pad(30);
        mainTable.add(circle2).width(circle2.getWidth()).height(circle2.getHeight()).padTop(70).pad(30);
        mainTable.add(circle3).width(circle3.getWidth()).height(circle3.getHeight()).padTop(70).pad(30);

        stage.addActor(background);
        stage.addActor(mainTable);
        stage.addActor(PauseButton);
        stage.addActor(ResumeButton);
        stage.addActor(ForwardButton);
        stage.addActor(SaveGameButton);
        stage.addActor(BackButton);
        stage.addActor(slingshot);

        if(paused){
            pause();
        }
    }

    LevelManager(int choice){
        AllButtons = new ArrayList<>();
        paused = false;
        this.choice = choice;
        BodiesToDestroy = new ArrayList<>();
        contactListener = new MyContactListener(this);

        world = new World(new Vector2(0, -9.8f), true);
        debugRenderer = new Box2DDebugRenderer();

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }

    ArrayList<Integer> generateThreeDistinctValues() {
        ArrayList<Integer> values = new ArrayList<>();
        for (int i = 0; i <= 6; i++) {
            values.add(i);
        }

        Collections.shuffle(values);

        return new ArrayList<>(values.subList(0, 3));
    }

    public void LoadLevelUtils(Main game){
        world.setContactListener(contactListener);
        this.game = game;
        Image background = new Image(new Texture("1Assets/Waterfall.jpg"));
        background.setFillParent(true);

        createGround(world);

        AllButtons.add(new AbstractMap.SimpleEntry<>("red_bird.png", new RedBird(world, -150, -250, stage)));
        AllButtons.add(new AbstractMap.SimpleEntry<>("yellow_bird.png", new Chuck(world, -150, -250, stage)));
        AllButtons.add(new AbstractMap.SimpleEntry<>("bomb.png", new Bomb(world, -150, -250, stage, this)));
        AllButtons.add(new AbstractMap.SimpleEntry<>("matilda_egg.png", new Matilda(world, -150, -250, stage)));
        AllButtons.add(new AbstractMap.SimpleEntry<>("blue_bird.png", new BlueBird(world, -150, -250, stage)));
        AllButtons.add(new AbstractMap.SimpleEntry<>("Silver.png", new Silver(world, -150, -250, stage)));
        AllButtons.add(new AbstractMap.SimpleEntry<>("Terance.png", new Terance(world, -150, -250, stage)));

        chooseBirds = generateThreeDistinctValues();

        birds = new ArrayList<>(3);

        for(int i=0; i<3; i++){
            birds.add(AllButtons.get(chooseBirds.get(i)).getValue());
        }

        PrepareButtons(chooseBirds);

        chosenBird = birds.get(0);
        chosenBird.load();
        slingshot = new Slingshot(chosenBird, new Vector2(120, 260), new Vector2(230, 350), 400, 600, this);

        PrepareBuildings(world, choice);
        this.PigsNo = CurrentPigs.size();
        this.BirdsNo = 3;
        Table mainTable = new Table();
        mainTable.setFillParent(true);

        mainTable.top();
        mainTable.add(circle1).width(circle1.getWidth()).height(circle1.getHeight()).padTop(70).pad(30);
        mainTable.add(circle2).width(circle2.getWidth()).height(circle2.getHeight()).padTop(70).pad(30);
        mainTable.add(circle3).width(circle3.getWidth()).height(circle3.getHeight()).padTop(70).pad(30);

        stage.addActor(background);
        stage.addActor(mainTable);
        stage.addActor(PauseButton);
        stage.addActor(ResumeButton);
        stage.addActor(ForwardButton);
        stage.addActor(SaveGameButton);
        stage.addActor(BackButton);
        stage.addActor(slingshot);
    }

    private void PrepareButtons(List<Integer> chooseBirds){
        BackButton = new CustomButton("GoBack.png", 90);
        BackButton.setPosition(11, 865);

        circle1 = new CustomButton("Birds/" + AllButtons.get(chooseBirds.get(0)).getKey(), 50);
        circle2 = new CustomButton("Birds/" + AllButtons.get(chooseBirds.get(1)).getKey(), 50);
        circle3 = new CustomButton("Birds/" + AllButtons.get(chooseBirds.get(2)).getKey(), 50);

        circle1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if(!birds.get(0).getLaunched()){
                    if(chosenBird!=null){
                        if(chosenBird.getLaunched()){
                            chosenBird.dispose();
                        } else {
                            chosenBird.setPosition(-150, -250);
                        }
                    }
                    chosenBird = birds.get(0);
                    chosenBird.load();
                    slingshot.updateBird(chosenBird);
                }
            }
        });
        circle2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if(!birds.get(1).getLaunched()){
                    if(chosenBird!=null){
                        if(chosenBird.getLaunched()){
                            chosenBird.dispose();
                        } else {
                            chosenBird.setPosition(-150, -250);
                        }
                    }
                    chosenBird = birds.get(1);
                    chosenBird.load();
                    slingshot.updateBird(chosenBird);
                }
            }
        });
        circle3.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if(!birds.get(2).getLaunched()){
                    if(chosenBird!=null){
                        if(chosenBird.getLaunched()){
                            chosenBird.dispose();
                        } else {
                            chosenBird.setPosition(-150, -250);
                        }
                    }
                    chosenBird = birds.get(2);
                    chosenBird.load();
                    slingshot.updateBird(chosenBird);
                }
            }
        });

        PauseButton = new CustomButton("1Assets/Pause.png", 90);
        PauseButton.setPosition(1730, 870);
        PauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                pause();
                updateButtonVisibility();
            }
        });

        ResumeButton = new CustomButton("1Assets/Resume.png", 83);
        ResumeButton.setPosition(1742.5f, 873);
        ResumeButton.setVisible(false);
        ResumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                resume();
                updateButtonVisibility();
            }
        });

        SaveGameButton = new CustomButton("SaveGame.png", 68);
        SaveGameButton.setPosition(1750, 730);
        SaveGameButton.setVisible(false);


        ForwardButton = new CustomButton("ForwardGame.png", 180, 120);
        ForwardButton.setPosition(1700, 50);
        ForwardButton.setVisible(false);
    }

    private void updateButtonVisibility() {
        if (paused) {
            PauseButton.setVisible(false);
            ResumeButton.setVisible(true);
        } else {
            PauseButton.setVisible(true);
            ResumeButton.setVisible(false);
        }
    }

    private void createGround(World world){
        int ScreenWidth = Gdx.graphics.getWidth();

        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.StaticBody;
        float PPM = 32f;
        def.position.set(ScreenWidth/2f/ PPM, 50/ PPM);

        Ground = world.createBody(def);
        Ground.setUserData("Ground");

        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox(ScreenWidth, 139/ PPM);

        FixtureDef fixture = new FixtureDef();
        fixture.shape = groundShape;
        fixture.density = 0.0f;
        fixture.friction = 0.6f;
        fixture.restitution = 0.0f;

        Ground.createFixture(fixture);

        groundShape.dispose();
    }

    public void render(){
        game.getBatch().begin();
        if(paused){
            ResumeButton.setVisible(true);
            SaveGameButton.setVisible(true);
            PauseButton.setVisible(false);
        } else {
            ResumeButton.setVisible(false);
            PauseButton.setVisible(true);
            SaveGameButton.setVisible(false);
            killOutOfBoundPigs();
        }
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        if(chosenBird!=null) {
            chosenBird.update(Gdx.graphics.getDeltaTime());
            chosenBird.render(game.getBatch());
        }
        for (Pig currentPig : CurrentPigs) {
            currentPig.update(Gdx.graphics.getDeltaTime());
            currentPig.render(game.getBatch());
        }
        for(Material material: CurrentBuilding.getMaterials()){
            material.update(Gdx.graphics.getDeltaTime(), this);
        }
        CurrentBuilding.render(game.getBatch());
        if(isGameOver()){
            ForwardButton.setVisible(true);
        }
        game.getBatch().end();

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            if(chosenBird!=null && !chosenBird.getAbilityUsed()){
                chosenBird.SpecialFunctionality();
            }
        }
    }

    private boolean isGameOver(){
        return PigsNo==0 || BirdsNo==0;
    }

    private void killOutOfBoundPigs() {
        Iterator<Pig> iterator = CurrentPigs.iterator();
        while (iterator.hasNext()) {
            Pig pig = iterator.next();
            if(pig.getBody()==null){
                continue;
            }
            Vector2 position = pig.getBody().getPosition().scl(Pig.PPM);

            if (position.y < 0 || position.x > Gdx.graphics.getWidth() || position.x < 0) {
                pig.die();
                iterator.remove();
            }
        }
    }



    private void PrepareBuildings(World world, int choice) {
        CurrentPigs = new ArrayList<>();
        CurrentBuilding = new Building();
        switch (choice) {
            case 0:
                Building0(world);
                break;
            case 1:
                Building1(world);
                break;
            case 2:
                Building2(world);
                break;
            case 3:
                Building3(world);
                break;
            case 4:
                Building4(world);
                break;
            case 5:
                Building5(world);
                break;
            case 6:
                Building6(world);
                break;
            case 7:
                Building7(world);
                break;
            case 8:
                Building8(world);
                break;
            case 9:
                Building9(world);
                break;
            case 10:
                Building10(world);
                break;
            default:
                System.out.println("Invalid index: " + choice + ". No building prepared.");
                break;
        }
    }

    private void Building6(World world){

        CurrentBuilding.Add(new Stone(world, 1620, 900, 200, 60));
        CurrentBuilding.Add(new Stone(world, 1620, 900, 250, 60));
        CurrentBuilding.Add(new Stone(world, 900, 700, 60, 180));
        CurrentBuilding.Add(new Stone(world, 1100, 700, 60, 180));
        CurrentBuilding.Add(new Stone(world, 1020, 235, 300, 60));
        CurrentBuilding.Add(new Stone(world, 1600, 235, 300, 60));



        CurrentBuilding.Add(new Glass(world, 1530, 333, 60, 135));
        CurrentBuilding.Add(new Glass(world, 1700, 333, 60, 135));
        CurrentBuilding.Add(new Glass(world, 1000, 430, 250, 60));

        CurrentBuilding.Add(new Wood(world, 1310, 435, 315, 30));
        CurrentBuilding.Add(new Wood(world, 1000, 900, 200, 60));
        CurrentBuilding.Add(new Wood(world, 1000, 900, 250, 60));
        CurrentBuilding.Add(new Wood(world, 1530, 700, 60, 180));
        CurrentBuilding.Add(new Wood(world, 1700, 700, 60, 180));
        CurrentBuilding.Add(new Wood(world, 900, 333, 60, 135));
        CurrentBuilding.Add(new Wood(world, 1100, 333, 60, 135));
        CurrentBuilding.Add(new Wood(world, 1620, 430, 250, 60));

        CurrentPigs.add(new ForemanPig(world,1280, 630, this));

        CurrentPigs.add(new KingPig(world,1000, 630, this));
        CurrentPigs.add(new KingPig(world,1560, 630, this));


        CurrentPigs.add(new Pig(world,1000, 1500, this));
        CurrentPigs.add(new Pig(world,1580, 1500, this));
        CurrentPigs.add(new Pig(world,1000, 300, this));
        CurrentPigs.add(new Pig(world,1580, 300, this));


    }

    private void Building7(World world){

            CurrentBuilding.Add(new Stone(world, 1430, 1100, 150, 50));
            CurrentBuilding.Add(new Stone(world, 1600, 235, 300, 60));


            CurrentBuilding.Add(new Glass(world, 1410, 1200, 50, 180));
            CurrentBuilding.Add(new Glass(world, 1430, 1200, 50, 180));

            CurrentBuilding.Add(new Glass(world, 1380, 700, 60, 180));
            CurrentBuilding.Add(new Glass(world, 1200, 333, 60, 135));
            CurrentBuilding.Add(new Glass(world, 1380, 333, 60, 135));
            CurrentBuilding.Add(new Glass(world, 1530, 333, 60, 135));
            CurrentBuilding.Add(new Glass(world, 1700, 333, 60, 135));
            CurrentBuilding.Add(new Glass(world, 1540, 700, 60, 180));




            CurrentBuilding.Add(new Wood(world, 1290, 430, 250, 60));
            CurrentBuilding.Add(new Wood(world, 1620, 430, 250, 60));
            CurrentBuilding.Add(new Wood(world, 1310, 235, 300, 60));

            CurrentPigs.add(new ForemanPig(world,1280, 630, this));


            CurrentPigs.add(new KingPig(world,1560, 630, this));
            CurrentPigs.add(new KingPig(world,1420, 1500, this));



            CurrentPigs.add(new Pig(world,1280, 300, this));
            CurrentPigs.add(new Pig(world,1580, 300, this));
    }

    private void Building8(World world){
        CurrentBuilding.Add(new Wood(world, 1530, 700, 500, 30));
        CurrentBuilding.Add(new Stone(world, 1750, 900, 40, 300));
        CurrentBuilding.Add(new Stone(world, 1300, 900, 40, 300));
        CurrentBuilding.Add(new Stone(world, 1530, 1200, 500, 30));
        CurrentBuilding.Add(new Stone(world, 1530, 1200, 500, 30));
        CurrentBuilding.Add(new Glass(world, 1450, 900, 40, 200));
        CurrentBuilding.Add(new Glass(world, 1600, 900, 40, 200));
        CurrentBuilding.Add(new Wood(world, 1530, 1000, 200, 30));
        CurrentBuilding.Add(new Glass(world, 1450, 1500, 40, 200));
        CurrentBuilding.Add(new Glass(world, 1600, 1500, 40, 200));
        CurrentBuilding.Add(new Wood(world, 1530, 1600, 200, 30));
        CurrentBuilding.Add(new Glass(world, 1525, 1800, 50, 100));
        CurrentBuilding.Add(new Glass(world, 1525, 1800, 50, 100));
        CurrentPigs.add(new KingPig(world,1560, 830, this));
        CurrentPigs.add(new KingPig(world,1550, 1870, this));
        CurrentPigs.add(new ForemanPig(world,1540, 1285, this));
    }

    private void Building9(World world){
        CurrentBuilding.Add(new Stone(world, 1620, 900, 250, 60));
        CurrentBuilding.Add(new Stone(world, 1600, 235, 300, 60));


        CurrentBuilding.Add(new Glass(world, 1300, 900, 250, 60));
        CurrentBuilding.Add(new Glass(world, 1200, 700, 60, 180));
        CurrentBuilding.Add(new Glass(world, 1380, 700, 60, 180));
        CurrentBuilding.Add(new Glass(world, 1200, 333, 60, 135));
        CurrentBuilding.Add(new Glass(world, 1380, 333, 60, 135));
        CurrentBuilding.Add(new Glass(world, 1530, 333, 60, 135));
        CurrentBuilding.Add(new Glass(world, 1700, 333, 60, 135));


        CurrentBuilding.Add(new Wood(world, 1530, 700, 60, 180));
        CurrentBuilding.Add(new Wood(world, 1700, 700, 60, 180));
        CurrentBuilding.Add(new Wood(world, 1290, 430, 250, 60));
        CurrentBuilding.Add(new Wood(world, 1620, 430, 250, 60));
        CurrentBuilding.Add(new Wood(world, 1310, 235, 300, 60));

        CurrentPigs.add(new ForemanPig(world,1280, 630, this));


        CurrentPigs.add(new KingPig(world,1560, 630, this));
        CurrentPigs.add(new KingPig(world,1280, 1500, this));


        CurrentPigs.add(new Pig(world,1580, 1500, this));
        CurrentPigs.add(new Pig(world,1280, 300, this));
        CurrentPigs.add(new Pig(world,1580, 300, this));

    }

    private void Building10(World world){
        int hiddenDestroyerIndex = (int) (Math.random()*22);
        CurrentBuilding.Add(new Stone(world, 1620, 900, 200, 60));
        CurrentBuilding.Add(new Stone(world, 1620, 900, 250, 60));
        CurrentBuilding.Add(new Stone(world, 900, 700, 60, 180));
        CurrentBuilding.Add(new Stone(world, 1100, 700, 60, 180));
        CurrentBuilding.Add(new Stone(world, 1020, 235, 300, 60));
        CurrentBuilding.Add(new Stone(world, 1600, 235, 300, 60));

        CurrentBuilding.Add(new Glass(world, 1300, 900, 200, 60));
        CurrentBuilding.Add(new Glass(world, 1300, 900, 250, 60));
        CurrentBuilding.Add(new Glass(world, 1200, 700, 60, 180));
        CurrentBuilding.Add(new Glass(world, 1380, 700, 60, 180));
        CurrentBuilding.Add(new Glass(world, 1200, 333, 60, 135));
        CurrentBuilding.Add(new Glass(world, 1380, 333, 60, 135));
        CurrentBuilding.Add(new Glass(world, 1530, 333, 60, 135));
        CurrentBuilding.Add(new Glass(world, 1700, 333, 60, 135));
        CurrentBuilding.Add(new Glass(world, 1000, 430, 250, 60));

        CurrentBuilding.Add(new Wood(world, 1000, 900, 200, 60));
        CurrentBuilding.Add(new Wood(world, 1000, 900, 250, 60));
        CurrentBuilding.Add(new Wood(world, 1530, 700, 60, 180));
        CurrentBuilding.Add(new Wood(world, 1700, 700, 60, 180));
        CurrentBuilding.Add(new Wood(world, 900, 333, 60, 135));
        CurrentBuilding.Add(new Wood(world, 1100, 333, 60, 135));
        CurrentBuilding.Add(new Wood(world, 1290, 430, 250, 60));
        CurrentBuilding.Add(new Wood(world, 1620, 430, 250, 60));
        CurrentBuilding.Add(new Wood(world, 1310, 235, 300, 60));
        CurrentBuilding.getMaterials().get(hiddenDestroyerIndex).setAsHiddenDestroyer();

        CurrentPigs.add(new ForemanPig(world,1280, 630, this));
        CurrentPigs.add(new KingPig(world,1000, 630, this));
        CurrentPigs.add(new KingPig(world,1560, 630, this));
        CurrentPigs.add(new KingPig(world,1280, 1500, this));
        CurrentPigs.add(new Pig(world,1000, 1500, this));
        CurrentPigs.add(new Pig(world,1580, 1500, this));
        CurrentPigs.add(new Pig(world,1000, 300, this));
        CurrentPigs.add(new Pig(world,1280, 300, this));
        CurrentPigs.add(new Pig(world,1580, 300,this));
    }


    private void Building5(World world){
        CurrentBuilding.Add(new Stone(world, 1310, 235, 300, 60));
        CurrentBuilding.Add(new Stone(world, 1600, 235, 300, 60));

        CurrentBuilding.Add(new Glass(world, 1200, 333, 60, 135));
        CurrentBuilding.Add(new Glass(world, 1380, 333, 60, 135));
        CurrentBuilding.Add(new Glass(world, 1530, 333, 60, 135));
        CurrentBuilding.Add(new Glass(world, 1700, 333, 60, 135));

        CurrentBuilding.Add(new Wood(world, 1290, 430, 250, 60));
        CurrentBuilding.Add(new Wood(world, 1620, 430, 250, 60));

        CurrentBuilding.Add(new Stone(world, 1450, 500, 300, 60));

        CurrentPigs.add(new Pig(world, 1200, 500, this));
        CurrentPigs.add(new Pig(world,1650, 500, this));
        CurrentPigs.add(new Pig(world,1285, 320, this));
        CurrentPigs.add(new KingPig(world, 1430, 600, this));
    }

    private void Building4(World world){
        CurrentBuilding.Add(new Wood(world, 1320, 257, 50, 100));
        CurrentBuilding.Add(new Glass(world, 1480, 257, 50, 100));

        CurrentBuilding.Add(new Wood(world, 1600, 257, 50, 100));
        CurrentBuilding.Add(new Glass(world, 1750, 257, 50, 100));

        CurrentBuilding.Add(new Stone(world, 1010, 257, 50, 100));
        CurrentBuilding.Add(new Wood(world, 1180, 257, 50, 100));

        CurrentBuilding.Add(new Stone(world, 1400, 400, 225, 50));
        CurrentBuilding.Add(new Glass(world, 1670, 400, 225, 50));
        CurrentBuilding.Add(new Wood(world, 1100, 400, 225, 50));

        CurrentPigs.add(new Pig(world, 1010, 500, this));
        CurrentPigs.add(new Pig(world, 1180, 500, this));
        CurrentPigs.add(new Pig(world, 1320, 500, this));
        CurrentPigs.add(new Pig(world, 1450, 500, this));
        CurrentPigs.add(new Pig(world, 1600, 500, this));
        CurrentPigs.add(new Pig(world,1750, 500, this));
    }

    private void Building3(World world){
        CurrentBuilding.Add(new Wood(world, 1260, 257, 50, 120));
        CurrentBuilding.Add(new Wood(world, 1420, 257, 50, 120));
        CurrentBuilding.Add(new Wood(world, 1600, 257, 50, 120));
        CurrentBuilding.Add(new Wood(world, 1750, 257, 50, 120));

        CurrentBuilding.Add(new Glass(world, 1335, 400, 225, 50));
        CurrentBuilding.Add(new Glass(world, 1670, 400, 225, 50));

        CurrentPigs.add(new Pig(world, 1320, 500, this));
        CurrentPigs.add(new Pig(world,1670, 500, this));
        CurrentPigs.add(new Pig(world,1670, 235, this));
        CurrentPigs.add(new ForemanPig(world,1510, 250, this));
    }

    private void Building2(World world){
        CurrentBuilding.Add(new Stone(world, 1310, 215, 300, 50));
        CurrentBuilding.Add(new Stone(world, 1600, 215, 300, 50));

        CurrentBuilding.Add(new Wood(world, 1200, 310, 50, 135));
        CurrentBuilding.Add(new Wood(world, 1350, 310, 50, 135));
        CurrentBuilding.Add(new Wood(world, 1550, 310, 50, 135));
        CurrentBuilding.Add(new Wood(world, 1700, 310, 50, 135));

        CurrentBuilding.Add(new Wood(world, 1275, 410, 250, 50));
        CurrentBuilding.Add(new Wood(world, 1625, 410, 250, 50));

        CurrentBuilding.Add(new Wood(world, 1300, 500, 50, 120));
        CurrentBuilding.Add(new Wood(world, 1580, 500, 50, 120));

        CurrentBuilding.Add(new Glass(world, 1430, 600, 400, 50));

        CurrentPigs.add(new Pig(world, 1200, 500, this));
        CurrentPigs.add(new Pig(world,1680, 500, this));
        CurrentPigs.add(new KingPig(world,1450, 330, this));
        CurrentPigs.add(new ForemanPig(world, 1430, 690, this));
    }


    private void Building1(World world){
        CurrentBuilding.Add(new Wood(world, 1400, 257, 50, 200));
        CurrentBuilding.Add(new Glass(world, 1500, 257, 50, 200));
        CurrentBuilding.Add(new Stone(world, 1300, 257, 50, 200));

        CurrentPigs.add(new Pig(world, 1650, 200, this));
        CurrentPigs.add(new Pig(world, 1680, 200, this));
        CurrentPigs.add(new Pig(world, 1720, 200, this));
    }

    private void Building0(World world){
        CurrentBuilding.Add(new Stone(world, 1450,255,45.5f,126.5f));
        CurrentBuilding.Add(new Stone(world,1650, 255, 45.5f, 126.5f));
        CurrentBuilding.Add(new Stone(world,1550,342,270f,45.5f));

        CurrentBuilding.Add(new Wood(world,1475,400,43f,61.5f));
        CurrentBuilding.Add(new Wood(world,1475,460,43,61.5f));
        CurrentBuilding.Add(new Wood(world,1630,400,43,61.5f));
        CurrentBuilding.Add(new Wood(world,1630,460,43,61.5f));

        CurrentBuilding.Add(new Glass(world, 1550,520,250,50));

        CurrentPigs.add(new Pig(world, 1550, 425, this));
        CurrentPigs.add(new ForemanPig(world, 1545, 255, this));
        CurrentPigs.add(new KingPig(world,1550, 610, this));
    }

    public void pause(){
        paused = true;
        slingshot.togglePaused(true);
        circle1.disableButton();
        circle2.disableButton();
        circle3.disableButton();
    }

    public void resume(){
        paused = false;
        slingshot.togglePaused(false);
        circle3.enableButton();
        circle1.enableButton();
        circle2.enableButton();
    }

    public Stage getStage() {
        return stage;
    }

    public void decrementBirds(){
        this.BirdsNo--;
    }

    public void decrementPigs(){
        this.PigsNo--;
    }

    public SpriteBatch getBatch(){
        return this.game.getBatch();
    }

    public ArrayList<Body> getBodiesToDestroy(){
        return this.BodiesToDestroy;
    }
    public void EndLevel() {
        game.incrementLevel();
        game.reloadLevel3();
        game.ChangeState(GameStates.WINNING_SCREEN);
        this.PigsNo = 0;
    }
}
