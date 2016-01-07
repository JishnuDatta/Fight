package com.jishd.fight.Sprites.Items;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.jishd.fight.FightGame;
import com.jishd.fight.Items.Item;
import com.jishd.fight.Mercenaries.Mercenary;
import com.jishd.fight.Screens.PlayScreen;
import com.jishd.fight.Sprites.Entity;

public class Projectile extends Entity{
private Mercenary mercenary;
    private Item item;
    private float previousAngle;
    public Projectile(PlayScreen playScreen, float spawnPointX, float spawnPointY, TextureRegion textureRegion, Mercenary mercenary, Item item, float degrees) {
        super(playScreen, spawnPointX, spawnPointY, textureRegion);
        this.mercenary = mercenary;
        this.item = item;
        setOrigin(getWidth(), getHeight()/2);
        rotate(degrees);
        previousAngle = degrees;
        //Degrees dont comply with the system?? something weird going on
        entityBody.applyLinearImpulse(new Vector2(40f * ((float) Math.cos(Math.toRadians(degrees))), 40f* ((float) Math.sin(Math.toRadians(degrees)))), super.entityBody.getWorldCenter(), true);
    }

    @Override
    public void createEntityBody(float spawnPointX, float spawnPointY) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(spawnPointX, spawnPointY);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        entityBody = world.createBody(bodyDef);
        entityBody.setBullet(true);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(2 / FightGame.PPM);

        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = FightGame.PROJECTILE_BIT;
        entityBody.createFixture(fixtureDef).setUserData(this);
    }

    //I think casting is causing this issue, will have to work out a fix for this.
    public TextureRegion getTextureRegion(TextureRegion textureRegion) {
        return new TextureRegion(playScreen.getAtlas().findRegion("Arrow"), 0, 0, 60, 12);
        //return (((Bow)item).getProjectileTextureRegion(playScreen.getAtlas()));
    }

    public Item getItem(){
        return item;
    }

    public Mercenary getMercenary() {
        return mercenary;
    }

    public void update(float dt){
        float angle = (float) (MathUtils.radiansToDegrees * Math.atan2(getEntityBody().getLinearVelocity().y, getEntityBody().getLinearVelocity().x));
        rotate(angle - previousAngle);
        previousAngle = angle;
        setPosition(entityBody.getPosition().x - getWidth(),entityBody.getPosition().y - getHeight()/2);
    }
}

