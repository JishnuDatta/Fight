package com.jishd.fight.Sprites.Classes;

import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.utils.Array;
import com.jishd.fight.FightGame;
import com.jishd.fight.Mercenaries.Mercenary;
import com.jishd.fight.Screens.PlayScreen;
import com.jishd.fight.Sprites.Items.Projectile;
import com.jishd.fight.Tools.DamageOnHitGenerator;
import com.jishd.fight.Tools.HealthAndManaBarCreator;

public class MercenaryModel extends Sprite {

    private Mercenary mercenary;
    private PlayScreen playScreen;
    private World world;

    private float currentHealth, currentMana;

    private boolean charDirRight;
    private int jumpCounter;

    private boolean killModel, isDead;
    private float deathTimer;

    private enum State {STANDING, RUNNING, JUMPING, FALLING}
    private State currentState;
    private State previousState;
    private float stateTimer;

    private TextureRegion charStand;
    private Animation charRun;
    private Animation charJump;
    
    private Body mercenaryBody;

    private HealthAndManaBarCreator healthAndManaBarCreator;
    private Array<DamageOnHitGenerator> damageOnHitGeneratorArray;

    public MercenaryModel(PlayScreen playScreen, Mercenary mercenary, int spawnPointX, int spawnPointY){
        this.mercenary = mercenary;
        this.playScreen = playScreen;
        this.world = playScreen.getWorld();

        currentHealth = mercenary.getHealthMult();
        currentMana = mercenary.getManaMult();

        charDirRight = true;
        jumpCounter = 0;

        killModel = false;
        isDead = false;
        deathTimer = 0;

        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;

        healthAndManaBarCreator = new HealthAndManaBarCreator(this);
        damageOnHitGeneratorArray = new Array<DamageOnHitGenerator>();

        //Setting up Run
        // Array<TextureRegion> frames = new Array<TextureRegion>();
        //   for(int i = 0; i < 1; i++){
        //   frames.add(new TextureRegion(getTexture()));
        // }
        //  charRun = new Animation(0.1f, frames);
        //frames.clear();
        //Setting up Jump
        //Copy from run
        //Standing

        charStand = mercenary.getTextureRegion(playScreen.getAtlas());
        setBounds(spawnPointX, spawnPointY, 32 / FightGame.PPM, 72 / FightGame.PPM);
        setRegion(charStand);
        //setSize(48 / FightGame.PPM, 64 / FightGame.PPM);
        //setAlpha(0.3f);

        //Create the body
        defineChar(spawnPointX, spawnPointY);
    }

    public void defineChar(int spawnPointX, int spawnPointY) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(spawnPointX / FightGame.PPM, spawnPointY / FightGame.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        mercenaryBody = world.createBody(bodyDef);
        
        //Create the head hitbox
        FixtureDef headFixture = new FixtureDef();
        CircleShape head = new CircleShape();
        head.setRadius(7 / FightGame.PPM);
        head.setPosition(new Vector2(0, 48 / FightGame.PPM));
        headFixture.shape = head;
        headFixture.filter.categoryBits = FightGame.HEAD_BIT;
        mercenaryBody.createFixture(headFixture).setUserData(this);

        //Create torso hitbox - this will need to be split up later
        FixtureDef bodyFixture = new FixtureDef();
        PolygonShape torso = new PolygonShape();
        torso.setAsBox(11 / FightGame.PPM, 14 / FightGame.PPM, new Vector2(0 / FightGame.PPM, 28 / FightGame.PPM), 0);
        bodyFixture.shape = torso;
        // bodyFixture.friction = 10;
        bodyFixture.filter.categoryBits = FightGame.BODY_BIT;
        mercenaryBody.createFixture(bodyFixture).setUserData(this);

        //Create lowerbody hitbox
        FixtureDef legFixture = new FixtureDef();
        PolygonShape legs = new PolygonShape();
        legs.setAsBox(11 / FightGame.PPM, 14 / FightGame.PPM, new Vector2(0 / FightGame.PPM, 0 / FightGame.PPM), 0);
        legFixture.shape = legs;
        // bodyFixture.friction = 10;
        //this should be changed
        legFixture.filter.categoryBits = FightGame.BODY_BIT;
        mercenaryBody.createFixture(legFixture).setUserData(this);
    }

    public void update(float dt) {
        if (killModel && !isDead) {
            world.destroyBody(mercenaryBody);
            isDead = true;
        }
        else if(isDead && deathTimer < 1){
                deathTimer += dt;
                setAlpha(1.0f - (deathTimer));
        }
        if (!isDead) {
            //Check if dead
            if (currentHealth <= 0) {
                currentHealth = 0;
                destroy();
            }
            if (currentState == State.STANDING || currentState == State.RUNNING) {
                jumpCounter = 0;
            }
            setPosition(mercenaryBody.getPosition().x - getWidth() / 2, mercenaryBody.getPosition().y - getWidth() / 2);
            setRegion(getFrame(dt));
            healthAndManaBarCreator.update();

        }
        for(DamageOnHitGenerator damageOnHitGenerator: damageOnHitGeneratorArray){
            damageOnHitGenerator.update(dt);
        }
    }

    public TextureRegion getFrame(float dt) {
        TextureRegion region;
        currentState = getState();
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
        if ((!charDirRight) && !region.isFlipX()) {
            region.flip(true, false);
            charDirRight = false;
        } else if ((charDirRight) && region.isFlipX()) {
            region.flip(true, false);
            charDirRight = true;

        }
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    public State getState() {
        if (mercenaryBody.getLinearVelocity().y > 0) {
            return State.JUMPING;
        } else if (mercenaryBody.getLinearVelocity().y < 0) {
            return State.FALLING;
        } else if (mercenaryBody.getLinearVelocity().x != 0){
            return State.RUNNING;
        } else {
            return State.STANDING;
        }
    }

    public void handleInput() {
        //Jump
        if (Gdx.input.isKeyJustPressed(mercenary.getPlayer().controls[0])) {
            mercenaryBody.applyLinearImpulse(new Vector2(0, 8f), mercenaryBody.getWorldCenter(), true);
        }
        //Left
        if (Gdx.input.isKeyPressed(mercenary.getPlayer().controls[1]) && mercenaryBody.getLinearVelocity().x >= -5) {
            mercenaryBody.applyLinearImpulse(new Vector2(-1f, 0), mercenaryBody.getWorldCenter(), true);
            charDirRight = false;
        }
        //Right
        if (Gdx.input.isKeyPressed(mercenary.getPlayer().controls[2]) && mercenaryBody.getLinearVelocity().x <= 5) {
            mercenaryBody.applyLinearImpulse(new Vector2(1f, 0), mercenaryBody.getWorldCenter(), true);
            charDirRight = true;
        }
        //Shoot
        if (Gdx.input.isKeyJustPressed( mercenary.getPlayer().controls[4])) {
                Projectile projectile = new Projectile(playScreen, mercenaryBody, mercenary, mercenary.getLoadout().getWeapon1(), charDirRight ? 0 : 180);
                 playScreen.getProjectiles().add(projectile);
        }
    }

    public void calculateDamage(Mercenary mercenary, Projectile p, String s) {
        if (s.equals("head")) {
            System.out.println("headshot!");
            currentHealth -= p.getDamage();
            p.destroy(false);

        } else if (s.equals("body")) {
            System.out.println("bodyshot!");
            currentHealth -= p.getDamage();
            p.destroy(false);
        }
        damageOnHitGeneratorArray.add(new DamageOnHitGenerator(this, p.getDamage()));
        System.out.println("added new " + this + " " + p.getDamage());
    }

    public Mercenary getMercenary() {
        return mercenary;
    }

    public Body getMercenaryBody() {
        return mercenaryBody;
    }

    public int getJumpCounter() {
        return jumpCounter;
    }

    public PlayScreen getPlayScreen() {
        return playScreen;
    }

    public float getCurrentHealth() {
        return currentHealth;
    }

    public float getCurrentMana() {
            return currentMana;
        }

    public void destroy() {
        killModel = true;
    }

    @Override
    public void draw(Batch batch) {
        if (!isDead || (deathTimer < 1)) {
            super.draw(batch);
            healthAndManaBarCreator.draw(batch);
        }
    }

    public Array<DamageOnHitGenerator> getDamageOnHitGeneratorArray() {
        return damageOnHitGeneratorArray;
    }
}
