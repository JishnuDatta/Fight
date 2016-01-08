package com.jishd.fight.Sprites.Items;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.jishd.fight.FightGame;
import com.jishd.fight.Items.Item;
import com.jishd.fight.Screens.PlayScreen;
import com.jishd.fight.Sprites.Entity;

public class DaggerAttack extends Entity{

    private Body mercenaryBody;
    private Item item;
    boolean rightDirection;

    public DaggerAttack(PlayScreen playScreen, float spawnPointX, float spawnPointY, TextureRegion textureRegion, Body mercenaryBody, Item item) {
    super(playScreen, spawnPointX, spawnPointY, textureRegion);
    this.mercenaryBody = mercenaryBody;
    this.item = item;

        //Attaching the dagger to the body - stuff gets weird - Slowing down my models, maybe not even worth it
//        DistanceJointDef distanceJointDef = new DistanceJointDef();
//        distanceJointDef.initialize(entityBody, mercenaryBody, new Vector2(0,0), new Vector2(0,0));
//        distanceJointDef.collideConnected = false;
//        world.createJoint(distanceJointDef);
    }

    @Override
    public void createEntityBody(float spawnPointX, float spawnPointY) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(spawnPointX, spawnPointY + 19/FightGame.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        entityBody = world.createBody(bodyDef);
        entityBody.setBullet(true);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(10/FightGame.PPM,3/FightGame.PPM,new Vector2(0,0), 0);

        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        //fixtureDef.filter.categoryBits = FightGame.ATTACK_BIT;
        fixtureDef.density =0;
        entityBody.createFixture(fixtureDef).setUserData(this);
        entityBody.setGravityScale(0);
    }

    //I think casting is causing this issue, will have to work out a fix for this.
    public TextureRegion getTextureRegion(TextureRegion textureRegion) {
        return new TextureRegion(playScreen.getAtlas().findRegion("Dagger"), 0, 0, 20, 6);
    }

    public Item getItem(){
        return item;
    }

    public void update(float dt) {
            setPosition(entityBody.getPosition().x - getWidth()/2, entityBody.getPosition().y - getHeight() / 2);
        }
    }

