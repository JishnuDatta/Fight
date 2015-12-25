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
import com.jishd.fight.Screens.PlayScreen;
import com.jishd.fight.Sprites.Items.Projectile;

public class CharacterModel extends Sprite {

    public Body b2body;
    public State currentState;
    public State previousState;
    public int jumpCounter;

    private World world;
    private PlayScreen screen;
    private Animation charRun;
    private Animation charJump;
    private boolean charDirRight;
    private float stateTimer;
    private Projectile projectile;
    private Character character;
    private TextureRegion charStand;
    private int currentHealth, currentMana;
    private boolean killModel, isDead;

    public CharacterModel(PlayScreen screen, Character character, int sizeX, int sizeY) {
        //Find sprites for certain characters

        this.character = character;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        charDirRight = true;

        killModel = false;
        isDead = false;
        //Setting up Run
        // Array<TextureRegion> frames = new Array<TextureRegion>();
        //   for(int i = 0; i < 1; i++){
        //   frames.add(new TextureRegion(getTexture()));
        // }
        //  charRun = new Animation(0.1f, frames);
        //frames.clear();
        //Setting up Jump

        //Standing
        charStand = new TextureRegion(screen.getAtlas().findRegion("Archer"), 0, 0, 48, 64);
        setBounds(100, 100, sizeX / FightGame.PPM, sizeY / FightGame.PPM);
        setRegion(charStand);
        setSize(64 / FightGame.PPM, 64 / FightGame.PPM);
        //setColor(0.3f);

        //Set world and screen
        this.screen = screen;
        this.world = screen.getWorld();
        currentHealth = character.getHealth();
        currentMana = character.getMana();

        jumpCounter = 0;


        defineChar();
    }

    public void defineChar() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(100 / FightGame.PPM, 100 / FightGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);


        FixtureDef headFixture = new FixtureDef();

        //Create the head hitbox
        CircleShape head = new CircleShape();
        head.setRadius(6 / FightGame.PPM);
        head.setPosition(new Vector2(-7 / FightGame.PPM, 21 / FightGame.PPM));
        headFixture.shape = head;
        headFixture.filter.categoryBits = FightGame.HEAD_BIT;
        b2body.createFixture(headFixture).setUserData(this);
        //        EdgeShape lower = new EdgeShape();
//        head.set(new Vector2(-2 / FightGame.PPM, 7/ FightGame.PPM), new Vector2(2 /FightGame.PPM, 7 / FightGame.PPM));
//        fdef.shape = shape;

        FixtureDef bodyFixture = new FixtureDef();

        //Create lowerbody hitbox
        PolygonShape torso = new PolygonShape();
        torso.setAsBox(10 / FightGame.PPM, 21 / FightGame.PPM, new Vector2(-5 / FightGame.PPM, -7 / FightGame.PPM), 0);
        bodyFixture.shape = torso;
        // bodyFixture.friction = 10;
        bodyFixture.filter.categoryBits = FightGame.BODY_BIT;
        b2body.createFixture(bodyFixture).setUserData(this);
    }

    public void update(float dt) {

        if (killModel && !isDead) {
            world.destroyBody(b2body);
            isDead = true;
        }
        if (!isDead) {
            if (currentState == State.STANDING || currentState == State.RUNNING) {
                jumpCounter = 0;

            }
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getWidth() / 2);
            setRegion(getFrame(dt));
        }

    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();

        TextureRegion region;
        switch (currentState) {
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
        if ((b2body.getLinearVelocity().x < 0 || !charDirRight) && !region.isFlipX()) {
            region.flip(true, false);
            charDirRight = false;
        } else if ((b2body.getLinearVelocity().x > 0 || charDirRight) && region.isFlipX()) {
            region.flip(true, false);
            charDirRight = true;

        }
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    public State getState() {
        if (b2body.getLinearVelocity().y > 0) {
            return State.JUMPING;
        } else if (b2body.getLinearVelocity().y < 0) {
            return State.FALLING;
        } else if (b2body.getLinearVelocity().x != 0) {
            return State.RUNNING;
        } else {
            return State.STANDING;
        }
    }

    public void shoot() {
        projectile = new Projectile(0, 0, 3, screen, b2body, character);
        projectile.shoot();
        screen.getProjectiles().add(projectile);
    }

    public Character getCharacter() {
        return character;
    }

    public void calculateDamage(Character character, Projectile p, String s) {
        if (s.equals("head")) {
            System.out.println("headshot!");
            currentHealth -= p.getDamage();
            p.destroy(false);

        } else if (s.equals("body")) {
            System.out.println("bodyshot!");
            currentHealth -= p.getDamage();
            p.destroy(false);
        }
        //Check if dead
        if (currentHealth <= 0) {
            currentHealth = 0;
            destroy();
        }
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public void destroy() {
        killModel = true;
    }

    public void draw(Batch batch) {
        if (!isDead) {
            super.draw(batch);
        }

    }

    public enum State {FALLING, JUMPING, STANDING, RUNNING}


}
