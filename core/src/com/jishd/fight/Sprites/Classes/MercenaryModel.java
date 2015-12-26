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

public class MercenaryModel extends Sprite {

    private Mercenary mercenary;
    private PlayScreen playScreen;
    private World world;

    private int currentHealth, currentMana;

    private boolean charDirRight;
    private int jumpCounter;

    private boolean killModel, isDead;

    private enum State {STANDING, RUNNING, JUMPING, FALLING}
    private State currentState;
    private State previousState;
    private float stateTimer;

    private TextureRegion charStand;
    private Animation charRun;
    private Animation charJump;
    
    private Body mercenaryBody;

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

        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;

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
        setBounds(spawnPointX, spawnPointY, 48 / FightGame.PPM, 64 / FightGame.PPM);
        setRegion(charStand);
        //setSize(48 / FightGame.PPM, 64 / FightGame.PPM);
        //setColor(0.3f);

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
        head.setRadius(6 / FightGame.PPM);
        head.setPosition(new Vector2(-7 / FightGame.PPM, 21 / FightGame.PPM));
        headFixture.shape = head;
        headFixture.filter.categoryBits = FightGame.HEAD_BIT;
        mercenaryBody.createFixture(headFixture).setUserData(this);

        //Create lowerbody hitbox
        FixtureDef bodyFixture = new FixtureDef();
        PolygonShape torso = new PolygonShape();
        torso.setAsBox(10 / FightGame.PPM, 21 / FightGame.PPM, new Vector2(-5 / FightGame.PPM, -7 / FightGame.PPM), 0);
        bodyFixture.shape = torso;
        // bodyFixture.friction = 10;
        bodyFixture.filter.categoryBits = FightGame.BODY_BIT;
        mercenaryBody.createFixture(bodyFixture).setUserData(this);
    }

    public void update(float dt) {
        if (killModel && !isDead) {
            world.destroyBody(mercenaryBody);
            isDead = true;
        }
        if (!isDead) {
            if (currentState == State.STANDING || currentState == State.RUNNING) {
                jumpCounter = 0;
            }
            setPosition(mercenaryBody.getPosition().x - getWidth() / 2, mercenaryBody.getPosition().y - getWidth() / 2);
            setRegion(getFrame(dt));
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
        if ((mercenaryBody.getLinearVelocity().x < 0 || !charDirRight) && !region.isFlipX()) {
            region.flip(true, false);
            charDirRight = false;
        } else if ((mercenaryBody.getLinearVelocity().x > 0 || charDirRight) && region.isFlipX()) {
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

    public void handleInput(FightGame.Controls input) {



        switch(input) {
            case WEAPON1:

                Projectile projectile = new Projectile(playScreen, mercenaryBody, mercenary, mercenary.getLoadout().getWeapon1());
                projectile.shoot();
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
        //Check if dead
        if (currentHealth <= 0) {
            currentHealth = 0;
            destroy();
        }
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

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getCurrentMana() {
            return currentMana;
        }

    public void destroy() {
        killModel = true;
    }

    @Override
    public void draw(Batch batch) {
        if (!isDead) {
            super.draw(batch);
        }
    }

}
