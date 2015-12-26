package com.jishd.fight.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jishd.fight.FightGame;
import com.jishd.fight.PlayerData.Player;
import com.jishd.fight.Scenes.Hud;
import com.jishd.fight.Sprites.Classes.MercenaryModel;
import com.jishd.fight.Sprites.Items.Projectile;
import com.jishd.fight.Tools.B2WorldCreator;
import com.jishd.fight.Tools.WorldContactListener;

import java.util.ArrayList;

public class PlayScreen implements Screen {
    public FightGame game;
    private TextureAtlas atlas;

    //Camera stuff, orthographic camera is the camera, viewport is the "fit" of the camera, hud is a separate layer on top
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;

    //Tiled Map Stuff, loads the first map
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2dStuff, world is where all the collisions happen, 2ddebug puts lines on all the colliding objects
    private World world;
    private Box2DDebugRenderer b2dr;

    //Storing all the mercenary models on this level
    private ArrayList<MercenaryModel> models;

    //All projectiles on this map
    private ArrayList<Projectile> projectiles;
    
    //May need to also send an array of players entering in the game, if some dont want to play
    public PlayScreen(FightGame game, FightGame.Stages stage) {
        this.game = game;
        
        //This may need to go to game instead, as sprites will also be used in the menu screens etc, maybe have 2 diff spritesheets?
        atlas = new TextureAtlas("FightGame.pack");

        //Camera fitting
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(FightGame.V_WIDTH / FightGame.PPM, FightGame.V_HEIGHT / FightGame.PPM, gamecam);

        mapLoader = new TmxMapLoader();
        switch(stage) {
            case STAGE1:
                map = mapLoader.load("Stage1.tmx");
                break;
            default:
                break;
        }
        renderer = new OrthogonalTiledMapRenderer(map, 1 / FightGame.PPM);

        //Move Camera to middle of map
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        //more box2d stuff (collisions), also set gravity to 10m/s
        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();

        //Combines box 2 with the map I think
        new B2WorldCreator(world, map);
        world.setContactListener(new WorldContactListener());

        //Create the new Mercenary models for this level, and spawn them in
        models = new ArrayList<MercenaryModel>();
        for (Player player : game.getPlayerList()) {
            //Setting 100, 100 as spawnpoint for now
            models.add(new MercenaryModel(this, player.getCurrentMercenary(), 100, 100));
        }
        projectiles = new ArrayList<Projectile>();

       // hud = new Hud(game.batch, models);
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);

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
        for (MercenaryModel model : models) {
            model.draw(game.batch);
        }
        for (Projectile projectile : projectiles) {
            projectile.draw(game.batch);

        }
        //Set batch to draw hud camera

        game.batch.end();
        //game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
       // hud.stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    public void handleInput(float dt) {
        for (MercenaryModel model : models) {
            //Jump
            if (Gdx.input.isKeyJustPressed(model.getMercenary().getPlayer().controls[0])) {
                model.getMercenaryBody().applyLinearImpulse(new Vector2(0, 8f), model.getMercenaryBody().getWorldCenter(), true);
            }
            //Left
            if (Gdx.input.isKeyPressed(model.getMercenary().getPlayer().controls[1]) && model.getMercenaryBody().getLinearVelocity().x >= -3) {
                model.getMercenaryBody().applyLinearImpulse(new Vector2(-3, 0), model.getMercenaryBody().getWorldCenter(), true);
            }
            //Right
            if (Gdx.input.isKeyPressed(model.getMercenary().getPlayer().controls[2]) && model.getMercenaryBody().getLinearVelocity().x <= 3) {
                model.getMercenaryBody().applyLinearImpulse(new Vector2(3, 0), model.getMercenaryBody().getWorldCenter(), true);
            }
            //Shoot
            if (Gdx.input.isKeyJustPressed(model.getMercenary().getPlayer().controls[4])) {
                model.handleInput(FightGame.Controls.WEAPON1);
            }
        }
    }

    public void update(float dt) {
        handleInput(dt);
        world.step(1 / 60f, 6, 2);

        for (MercenaryModel model : models) {
            model.update(dt);
        }

       // hud.update();
        for (Projectile projectile : projectiles) {
            projectile.update(dt);
        }
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

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }


}
