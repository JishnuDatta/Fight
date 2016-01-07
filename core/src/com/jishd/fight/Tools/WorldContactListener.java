package com.jishd.fight.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.jishd.fight.FightGame;
import com.jishd.fight.Sprites.Classes.MercenaryModel;
import com.jishd.fight.Sprites.Items.Projectile;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        //Adding the bits together to make it faster?
        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        //Switch statement controls all collisions
        switch (cDef) {
            //For collision between bullet and a head
            case FightGame.HEAD_BIT | FightGame.PROJECTILE_BIT:
                if (fixA.getFilterData().categoryBits == FightGame.HEAD_BIT) {
                    ((MercenaryModel) fixA.getUserData()).calculateDamage(((Projectile) fixB.getUserData()).getMercenary(), ((Projectile) fixB.getUserData()), "head");
                } else {
                    ((MercenaryModel) fixB.getUserData()).calculateDamage(((Projectile) fixA.getUserData()).getMercenary(), ((Projectile) fixA.getUserData()), "head");
                }
                break;
            case FightGame.BODY_BIT | FightGame.PROJECTILE_BIT:
                if (fixA.getFilterData().categoryBits == FightGame.BODY_BIT) {
                    ((MercenaryModel) fixA.getUserData()).calculateDamage(((Projectile) fixB.getUserData()).getMercenary(), ((Projectile) fixB.getUserData()), "body");
                } else {
                    ((MercenaryModel) fixB.getUserData()).calculateDamage(((Projectile) fixA.getUserData()).getMercenary(), ((Projectile) fixA.getUserData()), "body");
                }
                break;
            case FightGame.PROJECTILE_BIT | FightGame.TILE_BIT:
                if (fixA.getFilterData().categoryBits == FightGame.PROJECTILE_BIT) {
                    ((Projectile) (fixA.getUserData())).setDeleteEntity();
                } else {
                    ((Projectile) (fixB.getUserData())).setDeleteEntity();
                }
                break;
            case FightGame.HEAD_BIT | FightGame.TILE_BIT:
            case FightGame.BODY_BIT | FightGame.TILE_BIT:
            case FightGame.PROJECTILE_BIT | FightGame.PROJECTILE_BIT:
            default:
                break;

        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
