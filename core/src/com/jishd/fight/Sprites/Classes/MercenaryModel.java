package com.jishd.fight.Sprites.Classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.jishd.fight.FightGame;
import com.jishd.fight.Mercenaries.Mercenary;
import com.jishd.fight.Screens.PlayScreen;
import com.jishd.fight.Sprites.Entity;
import com.jishd.fight.Sprites.Items.DaggerAttack;
import com.jishd.fight.Sprites.Items.Projectile;
import com.jishd.fight.Tools.Damage;
import com.jishd.fight.Tools.DamageOnHitGenerator;
import com.jishd.fight.Tools.HealthAndManaBarCreator;

public class MercenaryModel extends Entity {

    private Mercenary mercenary;

    private float currentHealth, currentMana;

    private boolean charDirRight;
    private int jumpCounter;

    private float deathTimer;

    private enum State {STANDING, RUNNING, JUMPING, FALLING}

    private State currentState;
    private State previousState;
    private float stateTimer;

    private TextureRegion charStand;
    private Animation charRun;
    private Animation charJump;

    private HealthAndManaBarCreator healthAndManaBarCreator;
    private Array<DamageOnHitGenerator> damageOnHitGeneratorArray;


    public MercenaryModel(PlayScreen playScreen, int spawnPointX, int spawnPointY, TextureRegion textureRegion, Mercenary mercenary) {
        super(playScreen, spawnPointX, spawnPointY, textureRegion);
        this.mercenary = mercenary;

        currentHealth = mercenary.getHealthMult();
        currentMana = mercenary.getManaMult();

        charDirRight = true;
        jumpCounter = 0;

        deathTimer = 0;

        healthAndManaBarCreator = new HealthAndManaBarCreator(this);
        damageOnHitGeneratorArray = new Array<DamageOnHitGenerator>();
    }

    public void createEntityBody(int spawnPointX, int spawnPointY) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(spawnPointX / FightGame.PPM, spawnPointY / FightGame.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        super.entityBody = world.createBody(bodyDef);

        //Create the head hitbox
        FixtureDef headFixture = new FixtureDef();
        CircleShape head = new CircleShape();
        head.setRadius(7 / FightGame.PPM);
        head.setPosition(new Vector2(0, 48 / FightGame.PPM));
        headFixture.shape = head;
        headFixture.filter.categoryBits = FightGame.HEAD_BIT;
        super.entityBody.createFixture(headFixture).setUserData(this);

        //Create torso hitbox - this will need to be split up later
        FixtureDef bodyFixture = new FixtureDef();
        PolygonShape torso = new PolygonShape();
        torso.setAsBox(11 / FightGame.PPM, 14 / FightGame.PPM, new Vector2(0 / FightGame.PPM, 28 / FightGame.PPM), 0);
        bodyFixture.shape = torso;
        // bodyFixture.friction = 10;
        bodyFixture.filter.categoryBits = FightGame.BODY_BIT;
        super.entityBody.createFixture(bodyFixture).setUserData(this);

        //Create lowerbody hitbox
        FixtureDef legFixture = new FixtureDef();
        PolygonShape legs = new PolygonShape();
        legs.setAsBox(11 / FightGame.PPM, 14 / FightGame.PPM, new Vector2(0 / FightGame.PPM, 0 / FightGame.PPM), 0);
        legFixture.shape = legs;
        // bodyFixture.friction = 10;
        //this should be changed
        legFixture.filter.categoryBits = FightGame.BODY_BIT;
        super.entityBody.createFixture(legFixture).setUserData(this);
    }

    public void update(float dt) {
        //Check if dead
        if (currentHealth <= 0) {
            currentHealth = 0;
            super.setDeleteEntity();
        }
        if (currentState == State.STANDING || currentState == State.RUNNING) {
            jumpCounter = 0;
        }
        setPosition(super.entityBody.getPosition().x - getWidth() / 2, super.entityBody.getPosition().y - getWidth() / 2);
        healthAndManaBarCreator.update();

        for (DamageOnHitGenerator damageOnHitGenerator : damageOnHitGeneratorArray) {
            damageOnHitGenerator.update(dt);
        }
        super.setRegion(completeEntityRegion);
        setDirection();
    }

    public TextureRegion getTextureRegion(TextureRegion textureRegion) {
        //For now only has 1 region so the entire texture region is the same.
        TextureRegion region = textureRegion;
        //Will contain animations and different states for running jumping etc here
        return region;
    }

    public void setDirection() {
        if ((!charDirRight) && !super.getRegion().isFlipX()) {
            super.getRegion().flip(true, false);
            charDirRight = false;
        } else if ((charDirRight) && super.getRegion().isFlipX()) {
            super.getRegion().flip(true, false);
            charDirRight = true;
        }
    }

    public State getState() {
        if (super.entityBody.getLinearVelocity().y > 0) {
            return State.JUMPING;
        } else if (super.entityBody.getLinearVelocity().y < 0) {
            return State.FALLING;
        } else if (super.entityBody.getLinearVelocity().x != 0) {
            return State.RUNNING;
        } else {
            return State.STANDING;
        }
    }

    public void handleInput() {
        //Jump
        if (Gdx.input.isKeyJustPressed(mercenary.getPlayer().controls[0])) {
            super.entityBody.applyLinearImpulse(new Vector2(0, 8f), super.entityBody.getWorldCenter(), true);
        }
        //Left
        if (Gdx.input.isKeyPressed(mercenary.getPlayer().controls[1]) && super.entityBody.getLinearVelocity().x >= -5) {
            super.entityBody.applyLinearImpulse(new Vector2(-1f, 0), super.entityBody.getWorldCenter(), true);
            charDirRight = false;
        }
        //Right
        if (Gdx.input.isKeyPressed(mercenary.getPlayer().controls[2]) && super.entityBody.getLinearVelocity().x <= 5) {
            super.entityBody.applyLinearImpulse(new Vector2(1f, 0), super.entityBody.getWorldCenter(), true);
            charDirRight = true;
        }
        //Shoot
        if (Gdx.input.isKeyJustPressed(mercenary.getPlayer().controls[4])) {
            if (mercenary.getLoadout().getWeapon1().getWeaponType() == FightGame.Weapons.Bow) {
                Projectile projectile = new Projectile(playScreen, super.entityBody, mercenary, mercenary.getLoadout().getWeapon1(), charDirRight ? 0 : 180);
            } else if (mercenary.getLoadout().getWeapon1().getWeaponType() == FightGame.Weapons.Dagger) {
                DaggerAttack daggerAttack = new DaggerAttack(playScreen, super.entityBody, mercenary, mercenary.getLoadout().getWeapon1(), charDirRight ? 0 : 180);
            }
        }

    }

    public void calculateDamage(Mercenary mercenary, Projectile p, String s) {
        if (s.equals("head")) {
            currentHealth -= p.getDamage();
            p.destroy(false);

        } else if (s.equals("body")) {
            currentHealth -= p.getDamage();
            p.destroy(false);
        }
        Damage damageHit = playScreen.getdCalc().calcDamage(this.getMercenary(), mercenary, p.getItem(), s);
        damageOnHitGeneratorArray.add(new DamageOnHitGenerator(this, damageHit));

        currentHealth -= damageHit.getTotalDamage();
    }

    public Mercenary getMercenary() {
        return mercenary;
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

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
        healthAndManaBarCreator.draw(batch);
    }

    public Array<DamageOnHitGenerator> getDamageOnHitGeneratorArray() {
        return damageOnHitGeneratorArray;
    }
}
