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
import com.jishd.fight.Items.Weapons.Bow;
import com.jishd.fight.Mercenaries.Mercenary;
import com.jishd.fight.Screens.PlayScreen;
import com.jishd.fight.Sprites.Entity;
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


    public MercenaryModel(PlayScreen playScreen, float spawnPointX, float spawnPointY, TextureRegion textureRegion, Mercenary mercenary) {
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

    public void createEntityBody(float spawnPointX, float spawnPointY) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(spawnPointX / FightGame.PPM, spawnPointY / FightGame.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        entityBody = world.createBody(bodyDef);

        //Create the head hitbox
        FixtureDef headFixture = new FixtureDef();
        CircleShape head = new CircleShape();
        head.setRadius(7 / FightGame.PPM);
        head.setPosition(new Vector2(0, 48 / FightGame.PPM));
        headFixture.shape = head;
        headFixture.filter.categoryBits = FightGame.HEAD_BIT;
        entityBody.createFixture(headFixture).setUserData(this);

        //Create torso hitbox - this will need to be split up later
        FixtureDef bodyFixture = new FixtureDef();
        PolygonShape torso = new PolygonShape();
        torso.setAsBox(11 / FightGame.PPM, 14 / FightGame.PPM, new Vector2(0 / FightGame.PPM, 28 / FightGame.PPM), 0);
        bodyFixture.shape = torso;
        // bodyFixture.friction = 10;
        bodyFixture.filter.categoryBits = FightGame.BODY_BIT;
        entityBody.createFixture(bodyFixture).setUserData(this);

        //Create lowerbody hitbox
        FixtureDef legFixture = new FixtureDef();
        PolygonShape legs = new PolygonShape();
        legs.setAsBox(11 / FightGame.PPM, 14 / FightGame.PPM, new Vector2(0 / FightGame.PPM, 0 / FightGame.PPM), 0);
        legFixture.shape = legs;
        //this should be changed
        legFixture.filter.categoryBits = FightGame.BODY_BIT;
        entityBody.createFixture(legFixture).setUserData(this);
    }

    public void update(float dt) {
        setPosition(entityBody.getPosition().x - getWidth() / 2, entityBody.getPosition().y - getWidth() / 2);
        //Check if dead
        if (currentHealth <= 0) {
            currentHealth = 0;
            setDeleteEntity();
        }
        if (currentState == State.STANDING || currentState == State.RUNNING) {
            jumpCounter = 0;
        }
        healthAndManaBarCreator.update();

        for (DamageOnHitGenerator damageOnHitGenerator : damageOnHitGeneratorArray) {
            damageOnHitGenerator.update(dt);
        }
        setRegion(completeEntityRegion);
        setDirection();
    }

    public TextureRegion getTextureRegion(TextureRegion textureRegion) {
        //For now only has 1 region so the entire texture region is the same.
        TextureRegion region = textureRegion;
        //Will contain animations and different states for running jumping etc here
        return region;
    }

    public void setDirection() {
        if ((!charDirRight) && !getRegion().isFlipX()) {
           getRegion().flip(true, false);
            charDirRight = false;
        } else if ((charDirRight) && getRegion().isFlipX()) {
            getRegion().flip(true, false);
            charDirRight = true;
        }
    }

    public State getState() {
        if (entityBody.getLinearVelocity().y > 0) {
            return State.JUMPING;
        } else if (entityBody.getLinearVelocity().y < 0) {
            return State.FALLING;
        } else if (entityBody.getLinearVelocity().x != 0) {
            return State.RUNNING;
        } else {
            return State.STANDING;
        }
    }

    public void handleInput() {
        //Jump
        if (Gdx.input.isKeyJustPressed(mercenary.getPlayer().controls[0])) {
            entityBody.applyLinearImpulse(new Vector2(0, 8f), entityBody.getWorldCenter(), true);
        }
        //Left
        if (Gdx.input.isKeyPressed(mercenary.getPlayer().controls[1]) && entityBody.getLinearVelocity().x >= -5) {
            entityBody.applyLinearImpulse(new Vector2(-1f, 0), entityBody.getWorldCenter(), true);
            charDirRight = false;
        }
        //Right
        if (Gdx.input.isKeyPressed(mercenary.getPlayer().controls[2]) && entityBody.getLinearVelocity().x <= 5) {
            entityBody.applyLinearImpulse(new Vector2(1f, 0), entityBody.getWorldCenter(), true);
            charDirRight = true;
        }
        //Shoot
        if (Gdx.input.isKeyJustPressed(mercenary.getPlayer().controls[4])) {
            if (mercenary.getLoadout().getWeapon1().getWeaponType() == FightGame.Weapons.Bow) {
                float degrees = getDegrees(Gdx.input.getX(),Math.abs(720 - Gdx.input.getY()));

                System.out.println(degrees);
                Vector2 projectileAdditionPosition = degreesConversion(degrees);
                new Projectile(playScreen,( getEntityBody().getPosition().x + projectileAdditionPosition.x), (getEntityBody().getPosition().y + projectileAdditionPosition.y + 19 /FightGame.PPM),((Bow)mercenary.getLoadout().getWeapon1()).getProjectileTextureRegion(playScreen.getAtlas()),mercenary,mercenary.getLoadout().getWeapon1(), degrees);
//            } else if (mercenary.getLoadout().getWeapon1().getWeaponType() == FightGame.Weapons.Dagger) {
//                DaggerAttack daggerAttack = new DaggerAttack(playScreen, super.entityBody, mercenary, mercenary.getLoadout().getWeapon1(), charDirRight ? 0 : 180);
            }
        }

    }

    public void calculateDamage(Mercenary mercenary, Projectile p, String s) {
        Damage damageHit = playScreen.getdCalc().calcDamage(this.getMercenary(), mercenary, p.getItem(), s);
        damageOnHitGeneratorArray.add(new DamageOnHitGenerator(this, damageHit));
        currentHealth -= damageHit.getTotalDamage();
        p.setDeleteEntity();
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

    public Vector2 degreesConversion(float degrees){
        //(38 / FightGame.PPM) is the tested hypotenuse value
        float xValue = (float) (Math.cos(Math.toRadians(degrees)) * (38 / FightGame.PPM)) ;
        float yValue = (float) (Math.sin(Math.toRadians(degrees)) * (38 / FightGame.PPM)) ;
        Vector2 v2 = new Vector2(  xValue,yValue);
        System.out.println(v2.toString());
        return v2;
    }

    public float getDegrees(float x, float y){
        float bodyX = getEntityBody().getPosition().x;
        float bodyY =  getEntityBody().getPosition().y + (19 / FightGame.PPM);
        return new Vector2(x/ FightGame.PPM - bodyX,  y/ FightGame.PPM - bodyY).angle();
    }
}
