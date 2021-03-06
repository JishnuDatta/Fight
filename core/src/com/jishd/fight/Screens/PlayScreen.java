package com.jishd.fight.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jishd.fight.FightGame;
import com.jishd.fight.PlayerData.Player;
import com.jishd.fight.Scenes.Hud;
import com.jishd.fight.Sprites.Classes.MercenaryModel;
import com.jishd.fight.Sprites.Entity;
import com.jishd.fight.Tools.B2WorldCreator;
import com.jishd.fight.Tools.DamageCalculator;
import com.jishd.fight.Tools.DamageOnHitGenerator;
import com.jishd.fight.Tools.WorldContactListener;

public class PlayScreen implements Screen {
    public FightGame game;
    private TextureAtlas atlas;

    //Camera stuff, orthographic camera is the camera, viewport is the "fit" of the camera, hud is a separate layer on top
    private OrthographicCamera gamecam, textcam;
    private Viewport gamePort, textPort;
    private Hud hud;

    //Tiled Map Stuff, loads the first map
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2dStuff, world is where all the collisions happen, 2ddebug puts lines on all the colliding objects
    private World world;
    private Box2DDebugRenderer b2dr;

    //Storing all the mercenary models on this level
    private Array<Entity> entities;
    private Array<Body> destroyEntities;

    //New DamageCalculator
    DamageCalculator dCalc;

    //May need to also send an array of players entering in the game, if some dont want to play
    public PlayScreen(FightGame game, FightGame.Stages stage) {
        this.game = game;

        //This may need to go to game instead, as sprites will also be used in the menu screens etc, maybe have 2 diff spritesheets?
        atlas = new TextureAtlas("FightGame.atlas");

        //Camera fitting
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(FightGame.V_WIDTH / FightGame.PPM, FightGame.V_HEIGHT / FightGame.PPM, gamecam);

        mapLoader = new TmxMapLoader();
        switch (stage) {
            case STAGE1:
                map = mapLoader.load("Stage1.tmx");
                break;
            case STAGE2:
            map = mapLoader.load("Stage2.tmx");
            break;
            default:
                break;
        }
        renderer = new OrthogonalTiledMapRenderer(map, 1 / FightGame.PPM);

        //Move Camera to middle of map
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        //more box2d stuff (collisions), also set gravity to 10m/s
        world = new World(new Vector2(0, -9.813f), true);
        b2dr = new Box2DDebugRenderer();

        //Combines box 2 with the map I think
        new B2WorldCreator(world, map);
        world.setContactListener(new WorldContactListener());

//        //Entity conversion
        entities = new Array<Entity>();
        //Destroy Bodies List
        destroyEntities = new Array<Body>();

//        //Convert this to gui stuff after done - pass through an array if players (so not all players are chosen)
        for (Player player : game.getPlayerList()) {
            new MercenaryModel(this, 500, 500, player.getCurrentMercenary().getTextureRegion(atlas), player.getCurrentMercenary());
        }

        dCalc = new DamageCalculator();

        //hud = new Hud(game.batch, models);

    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float dt) {
        update(dt);

        //Clear game screen with black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render game map
        renderer.render();

        //render box2dDebugLines
        b2dr.render(world, gamecam.combined);

        //not sure
        game.batch.setProjectionMatrix(gamecam.combined);

        game.batch.begin();
        for (Entity entity : entities) {
            entity.draw(game.batch);
        }


        Matrix4 matrix = game.batch.getProjectionMatrix().cpy(); // cpy() needed to properly set afterwards because calling set() seems to modify kept matrix, not replaces it
        game.batch.setProjectionMatrix(matrix.setToScaling(new Vector3(matrix.getScaleX() / FightGame.PPM, matrix.getScaleY() / FightGame.PPM, 1)));

        for (Entity entity : entities) {
            if (entity instanceof MercenaryModel) {
                for (DamageOnHitGenerator damageOnHitGenerator : ((MercenaryModel) entity).getDamageOnHitGeneratorArray()) {
                    damageOnHitGenerator.draw(game.batch);
                }
            }

        }
        game.batch.end();
        //Set batch to draw hud camera
        // game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        // hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    public void handleInput(float dt) {
        for (Entity entity : entities) {
            if (entity instanceof MercenaryModel) {
                ((MercenaryModel) entity).handleInput();
            }
        }
    }

    public void update(float dt) {
        world.step(1 / 60f, 6, 2);
        handleInput(dt);


        for (int i = 0; i < entities.size;) {
            entities.get(i).update(dt);
            if(entities.get(i).deleteEntity) {
                destroyEntities.add(entities.get(i).getEntityBody());
                entities.removeIndex(i);
            }
            i++;
        }
        for(int i =0; i< destroyEntities.size; i++){
            world.destroyBody(destroyEntities.get(i));
        }
        destroyEntities.clear();


        // hud.update();
        gamecam.update();

        renderer.setView(gamecam);
    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    public World getWorld() {
        return world;
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }

    public Array<Entity> getEntities() {
        return entities;
    }

    public DamageCalculator getdCalc() {
        return dCalc;
    }
}
