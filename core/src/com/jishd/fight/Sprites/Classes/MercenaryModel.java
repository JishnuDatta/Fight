package com.jishd.fight.Sprites.Classes;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.jishd.fight.FightGame;
import com.jishd.fight.Mercenaries.Mercenary;
import com.jishd.fight.Screens.PlayScreen;
import com.jishd.fight.Sprites.Items.Projectile;

//Does texture/ hitboxes for in game
public class MercenaryModel extends Sprite{
    private PlayScreen playScreen;
    private World world;
    private Mercenary mercenary;
    private MercenaryInGame mercenaryInGame;

    private Body mercenaryBody;
    //State and animation stuff

    private Animation charRun;
    private Animation charJump;
    private TextureRegion charStand;

    private Projectile projectile;

    private boolean isDead;//change over

    public MercenaryModel(PlayScreen playScreen, Mercenary mercenary) {

        this.playScreen = playScreen;
        this.world = playScreen.getWorld();
        this.mercenary = mercenary;
        mercenaryInGame = new MercenaryInGame(this);


        //setColor(0.3f);

        //Setting up Run
        // Array<TextureRegion> frames = new Array<TextureRegion>();
        //   for(int i = 0; i < 1; i++){
        //   frames.add(new TextureRegion(getTexture()));
        // }
        //  charRun = new Animation(0.1f, frames);
        //frames.clear();
        //Setting up Jump

        //Standing
        //Figure out size stuff
        charStand = new TextureRegion(playScreen.getAtlas().findRegion(mercenary.toString()), 0, 0, 48, 64);
       // setBounds(100, 100, sizeX / FightGame.PPM, sizeY / FightGame.PPM);
        setRegion(charStand);

        setSize(64 / FightGame.PPM, 64 / FightGame.PPM);

        defineChar();
    }

    public void defineChar() {
        BodyDef bdef = new BodyDef();

        //Set this at spawn points later on
        bdef.position.set(100 / FightGame.PPM, 100 / FightGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        mercenaryBody = world.createBody(bdef);

        //Standardize this
        FixtureDef headFixture = new FixtureDef();
        //Create the head hitbox
        CircleShape head = new CircleShape();
        head.setRadius(6 / FightGame.PPM);
        head.setPosition(new Vector2(-7 / FightGame.PPM, 21 / FightGame.PPM));
        headFixture.shape = head;
        headFixture.filter.categoryBits = FightGame.HEAD_BIT;
        mercenaryBody.createFixture(headFixture).setUserData(this);

        FixtureDef bodyFixture = new FixtureDef();
        //Create lowerbody hitbox
        PolygonShape torso = new PolygonShape();
        torso.setAsBox(10 / FightGame.PPM, 21 / FightGame.PPM, new Vector2(-5 / FightGame.PPM, -7 / FightGame.PPM), 0);
        bodyFixture.shape = torso;
        // bodyFixture.friction = 10;
        bodyFixture.filter.categoryBits = FightGame.BODY_BIT;
        mercenaryBody.createFixture(bodyFixture).setUserData(this);
    }

    public void update(float dt) {
        //If Mercenary now dead, kill the body, etc
        if (mercenaryInGame.isKillModel() && !isDead) {
            world.destroyBody(mercenaryBody);
            isDead = true;
        }
        //If Mercenary alive
        else if(!isDead){
        mercenaryInGame.update(dt);
            setPosition(mercenaryBody.getPosition().x - getWidth() / 2, mercenaryBody.getPosition().y - getWidth() / 2);
            setRegion(getFrame(dt));
        }
    }

    public TextureRegion getFrame(float dt) {
        FightGame.MercenaryInGameState state = mercenaryInGame.getState();

        TextureRegion region;
        switch (state) {
            case JUMPING:
                // System.out.println("Jumping");
                // region = charJump.getKeyFrame(stateTimer);
                //break;
            case RUNNING:
                //System.out.println("Running");
                //region = charRun.getKeyFrame(stateTimer, true);
                //break;
            case FALLING:
                //System.out.println("Falling");
                //  break;
            case STANDING:
            default:
                // System.out.println("Standing");
                region = charStand;
                break;

        }
        if ((mercenaryBody.getLinearVelocity().x < 0 || !mercenaryInGame.isCharDirRight()) && !region.isFlipX()) {
            region.flip(true, false);
            mercenaryInGame.setCharDirRight(false);
        } else if ((mercenaryBody.getLinearVelocity().x > 0 || mercenaryInGame.isCharDirRight()) && region.isFlipX()) {
            region.flip(true, false);
            mercenaryInGame.setCharDirRight(true);
        }

       // stateTimer = currentState == previousState ? stateTimer + dt : 0;
       // previousState = currentState;
        return region;
    }

    public void shoot() {
    //    projectile = new Projectile(0, 0, 3, playScreen, mercenaryBody, );
        projectile.shoot();
        playScreen.getProjectiles().add(projectile);
    }

    public Mercenary getMercenary() {
        return mercenary;
    }

    @Override
    public void draw(Batch batch) {
        if (!isDead) {
            super.draw(batch);
        }
    }

    public Body getMercenaryBody(){
        return mercenaryBody;
    }

    public boolean isDead() {
        return isDead;
    }
}
