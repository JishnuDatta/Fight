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
import com.jishd.fight.Screens.PlayScreen;
import com.jishd.fight.Sprites.Classes.Character;

public class Projectile extends Sprite {
    private Body body;
    private Body arrowBody;
    private Vector2[] arrowVertices;
    private Projectile projectile;
    private int damage = 30;

    private Character character;


    private float lifeTime, lifeTimer, x, y, radians, dx, dy, previousAngle;

    private boolean remove;
    private World world;
    private PlayScreen screen;
    private BodyDef bdef;

    private boolean killProjectile;
    private boolean deadProjectile;
    private boolean hitEnvironment;


    public Projectile(float x, float y, float radians, PlayScreen screen, Body body, Character character) {
        this.screen = screen;
        world = screen.getWorld();
        killProjectile = false;
        deadProjectile = false;
        hitEnvironment = false;
        this.x = x;
        this.y = y;
        this.radians = radians;
        this.body = body;
        this.projectile = this;
        this.character = character;

        float speed = 64 / FightGame.PPM;

        dx = MathUtils.cos(radians) * speed;
        dy = MathUtils.sin(radians) * speed;
        lifeTimer = 0;
        lifeTime = 1;

        TextureRegion arrow = new TextureRegion(screen.getAtlas().findRegion("Arrow"), 0, 0, 40, 12);
        setBounds(body.getPosition().x, body.getPosition().y, 40 / FightGame.PPM, 12 / FightGame.PPM);
        setRegion(arrow);
        setOrigin(getWidth(), getHeight() / 2);
        previousAngle = 0;
        defineProj();

    }

    public void update(float dt) {

        if (killProjectile && !deadProjectile) {
            world.destroyBody(arrowBody);
            deadProjectile = true;
        } else if (!deadProjectile) {
            float angle = (float) (MathUtils.radiansToDegrees * Math.tan(arrowBody.getLinearVelocity().y / arrowBody.getLinearVelocity().x));
            rotate(angle - previousAngle);

            setPosition(arrowBody.getPosition().x - getWidth(), arrowBody.getPosition().y - getHeight() / 2);
            previousAngle = angle;
        }
    }

    public void defineProj() {

        bdef = new BodyDef();
        bdef.position.set(body.getPosition().x + 20 / FightGame.PPM, body.getPosition().y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        arrowBody = world.createBody(bdef);
        arrowBody.setBullet(true);

        FixtureDef fdef = new FixtureDef();


//        PolygonShape polygonShape = new PolygonShape();
//        arrowVertices[0].set(0, 0);
//        arrowVertices[1].set(     8/32, 40/ 32 );
//        arrowVertices[2].set(8/32, 40/ 32);
//        arrowVertices[3].set(12/32, 0);
//        polygonShape.set(arrowVertices);
//          fdef.shape = polygonShape;


        CircleShape shape = new CircleShape();
        shape.setRadius(3 / FightGame.PPM);

        fdef.shape = shape;

        fdef.filter.categoryBits = FightGame.PROJECTILE_BIT;
        arrowBody.createFixture(fdef).setUserData(this);


    }

    public void shoot() {
        arrowBody.applyLinearImpulse(new Vector2(20f, 10f), arrowBody.getWorldCenter(), true);
    }

    public Character getCharacter() {
        return character;
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
        if (!deadProjectile || hitEnvironment) {
            super.draw(batch);
        }

    }

}
