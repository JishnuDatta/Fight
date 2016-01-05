package com.jishd.fight.Sprites.Items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.jishd.fight.FightGame;
import com.jishd.fight.Items.Item;
import com.jishd.fight.Mercenaries.Mercenary;
import com.jishd.fight.Screens.PlayScreen;

//Tried to add animation to the projectiles but it looks crap, remove when possible and revert back to singular image.

public class Projectile extends Sprite {
    private PlayScreen playScreen;
    private World world;
    private Mercenary mercenary;
    private Body mercenaryBody;

    private Item item;

    private boolean killProjectile;
    private boolean deadProjectile;
    private boolean hitEnvironment;
    private float deathTimer;

    private float previousAngle;

    private boolean projDirRight;

    private Body arrowBody;

    private float damage;

    //Float x and y become projectile speed
    public Projectile(PlayScreen playScreen, Body mercenaryBody, Mercenary mercenary, Item item, float degrees) {
        this.playScreen = playScreen;
        world = playScreen.getWorld();
        this.mercenary = mercenary;
        this.mercenaryBody = mercenaryBody;

        this.item = item;

        killProjectile = false;
        deadProjectile = false;
        hitEnvironment = false;

        damage = mercenary.getRangedDamage();

        previousAngle = 0;
        int arrowSpeed = 40;

        projDirRight = (degrees < 180) ? true : false;

        setBounds(mercenaryBody.getPosition().x, mercenaryBody.getPosition().y, 30 / FightGame.PPM, 6 / FightGame.PPM);
        setOrigin(projDirRight ? getWidth() : 0, getHeight() / 2);
        setRegion(new TextureRegion(playScreen.getAtlas().findRegion("Arrow"), 0, 0, 60, 12));
        flip(!projDirRight, false);
        defineProj();
        float velocityX = new Float(arrowSpeed * Math.cos(MathUtils.degreesToRadians * degrees));
        float velocityY = new Float(arrowSpeed * Math.sin(MathUtils.degreesToRadians * degrees));
        arrowBody.applyLinearImpulse(new Vector2(velocityX, velocityY), arrowBody.getWorldCenter(), true);
    }

    public void update(float dt) {
        if (killProjectile && !deadProjectile) {
            world.destroyBody(arrowBody);
            deadProjectile = true;
        } else if (deadProjectile && hitEnvironment && deathTimer < 1) {
            deathTimer += dt;
            setAlpha(1.0f - (deathTimer));
        } else if (!deadProjectile) {
            float angle = (float) (MathUtils.radiansToDegrees * Math.tan(arrowBody.getLinearVelocity().y / arrowBody.getLinearVelocity().x));
            rotate(angle - previousAngle);

            setPosition(arrowBody.getPosition().x + (projDirRight ? -getWidth() : 0), arrowBody.getPosition().y - getHeight() / 2);
            previousAngle = angle;
        }
    }

    public void defineProj() {

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(projDirRight ? mercenaryBody.getPosition().x + 20 / FightGame.PPM : mercenaryBody.getPosition().x - 20 / FightGame.PPM, mercenaryBody.getPosition().y + 30 / FightGame.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        arrowBody = world.createBody(bodyDef);
        arrowBody.setBullet(true);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(2 / FightGame.PPM);
        shape.setPosition(new Vector2(projDirRight ? -5 / FightGame.PPM : 5 / FightGame.PPM, 0));

        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = FightGame.PROJECTILE_BIT;
        arrowBody.createFixture(fixtureDef).setUserData(this);
    }

    public float getDamage() {
        return damage;
    }

    public void destroy(boolean hitEnvironment) {
        killProjectile = true;
        this.hitEnvironment = hitEnvironment;
    }

    public Body getBody() {
        return arrowBody;
    }

    public void draw(Batch batch) {
        if (!deadProjectile || (hitEnvironment && deathTimer < 1f)) {
            super.draw(batch);
        }
    }

    public Mercenary getMercenary() {
        return mercenary;
    }

    public Item getItem() {
        return item;
    }
}
