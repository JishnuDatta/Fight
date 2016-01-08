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
                if (fixA.getFilterData().categoryBits == FightGame.HEAD_BIT && !((Projectile) fixB.getUserData()).deleteEntity) {
                    ((MercenaryModel) fixA.getUserData()).calculateDamage(((Projectile) fixB.getUserData()).getMercenary(), ((Projectile) fixB.getUserData()), "head");
                } else if (fixB.getFilterData().categoryBits == FightGame.HEAD_BIT && !((Projectile) fixA.getUserData()).deleteEntity) {
                    ((MercenaryModel) fixB.getUserData()).calculateDamage(((Projectile) fixA.getUserData()).getMercenary(), ((Projectile) fixA.getUserData()), "head");
                }
                break;
            case FightGame.BODY_BIT | FightGame.PROJECTILE_BIT:
                if (fixA.getFilterData().categoryBits == FightGame.BODY_BIT && !((Projectile) fixB.getUserData()).deleteEntity) {
                    ((MercenaryModel) fixA.getUserData()).calculateDamage(((Projectile) fixB.getUserData()).getMercenary(), ((Projectile) fixB.getUserData()), "body");
                } else if(fixB.getFilterData().categoryBits == FightGame.BODY_BIT && !((Projectile) fixA.getUserData()).deleteEntity){
                    ((MercenaryModel) fixB.getUserData()).calculateDamage(((Projectile) fixA.getUserData()).getMercenary(), ((Projectile) fixA.getUserData()), "body");
                }
                break;
            case FightGame.LEG_BIT | FightGame.PROJECTILE_BIT:
                if (fixA.getFilterData().categoryBits == FightGame.LEG_BIT && !((Projectile) fixB.getUserData()).deleteEntity) {
                    ((MercenaryModel) fixA.getUserData()).calculateDamage(((Projectile) fixB.getUserData()).getMercenary(), ((Projectile) fixB.getUserData()), "leg");
                } else if (fixB.getFilterData().categoryBits == FightGame.LEG_BIT && !((Projectile) fixA.getUserData()).deleteEntity){
                    ((MercenaryModel) fixB.getUserData()).calculateDamage(((Projectile) fixA.getUserData()).getMercenary(), ((Projectile) fixA.getUserData()), "leg");
                }
                break;
            case FightGame.PROJECTILE_BIT | FightGame.TILE_BIT:
                if (fixA.getFilterData().categoryBits == FightGame.PROJECTILE_BIT) {
                    ((Projectile) (fixA.getUserData())).setDeleteEntity();
                } else {
                    ((Projectile) (fixB.getUserData())).setDeleteEntity();
                }
                break;
            case FightGame.JUMP_BIT | FightGame.TILE_BIT:
                if (fixA.getFilterData().categoryBits == FightGame.JUMP_BIT) {
                    ((MercenaryModel) (fixA.getUserData())).resetJumps();

                } else {
                    ((MercenaryModel) (fixB.getUserData())).resetJumps();
                }
                break;
            case FightGame.JUMP_BIT | FightGame.HEAD_BIT:
                if (fixA.getFilterData().categoryBits == FightGame.JUMP_BIT) {
                    ((MercenaryModel) (fixA.getUserData())).resetJumps();

                } else {
                    ((MercenaryModel) (fixB.getUserData())).resetJumps();
                }
                break;
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
