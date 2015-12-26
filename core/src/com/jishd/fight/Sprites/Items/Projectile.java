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

public class Projectile extends Sprite {
    private PlayScreen playScreen;
    private World world;
    private Mercenary mercenary;
    private Body mercenaryBody;

    private boolean killProjectile;
    private boolean deadProjectile;
    private boolean hitEnvironment;
    private int deathTimer;
    
    private Body arrowBody;

    private int damage;
    
    private float previousAngle;
    
//Float x and y become projectile speed
    public Projectile(PlayScreen playScreen, Body mercenaryBody, Mercenary mercenary, Item item) {
        this.playScreen = playScreen;
        world = playScreen.getWorld();
        this.mercenary = mercenary;
        this.mercenaryBody = mercenaryBody;
        
        killProjectile = false;
        deadProjectile = false;
        hitEnvironment = false;

        damage = mercenary.getRangedDamage();

        previousAngle = 0;

        TextureRegion arrow = new TextureRegion(playScreen.getAtlas().findRegion("Arrow"), 0, 0, 40, 12);
        setBounds(mercenaryBody.getPosition().x, mercenaryBody.getPosition().y, 40 / FightGame.PPM, 12 / FightGame.PPM);
        setRegion(arrow);
        setOrigin(getWidth(), getHeight() / 2);

        defineProj();

    }

    public void update(float dt) {
        if (killProjectile && !deadProjectile) {
            world.destroyBody(arrowBody);
            deadProjectile = true;
        } else if(deadProjectile && hitEnvironment){
            deathTimer += dt;
            System.out.println(deathTimer);
            if(deathTimer > 1)
            setAlpha(0.5f);
        }
        else if (!deadProjectile) {
            float angle = (float) (MathUtils.radiansToDegrees * Math.tan(arrowBody.getLinearVelocity().y / arrowBody.getLinearVelocity().x));
            rotate(angle - previousAngle);

            setPosition(arrowBody.getPosition().x - getWidth(), arrowBody.getPosition().y - getHeight() / 2);
            previousAngle = angle;
        }
    }

    public void defineProj() {

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(mercenaryBody.getPosition().x + 20 / FightGame.PPM, mercenaryBody.getPosition().y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        arrowBody = world.createBody(bodyDef);
        arrowBody.setBullet(true);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(3 / FightGame.PPM);

        fixtureDef.shape = shape;

        fixtureDef.filter.categoryBits = FightGame.PROJECTILE_BIT;
        arrowBody.createFixture(fixtureDef).setUserData(this);
    }

    public void shoot() {
        arrowBody.applyLinearImpulse(new Vector2(20f, 10f), arrowBody.getWorldCenter(), true);
    }

    public int getDamage() {
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
        if (!deadProjectile || (hitEnvironment && deathTimer < 5)) {
            super.draw(batch);
        }
    }

    public Mercenary getMercenary() {
        return mercenary;
    }
}
