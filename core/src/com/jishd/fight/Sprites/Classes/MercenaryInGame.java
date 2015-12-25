package com.jishd.fight.Sprites.Classes;

import com.jishd.fight.FightGame;
import com.jishd.fight.Mercenaries.Mercenary;
import com.jishd.fight.Sprites.Items.Projectile;

//Does damage/ stats calculations for in game and states
public class MercenaryInGame{
    private MercenaryModel mercenaryModel;
    private Mercenary mercenary;

    private int jumpCounter;

    public FightGame.MercenaryInGameState currentState;
    public FightGame.MercenaryInGameState previousState;

    private boolean charDirRight;
    private float stateTimer = 0;

    private int currentHealth, currentMana;

    private boolean killModel;

    public MercenaryInGame(MercenaryModel mercenaryModel){
        this.mercenaryModel = mercenaryModel;
        this.mercenary = mercenaryModel.getMercenary();

        currentHealth = mercenary.getStats().getHealth();
        currentMana = mercenary.getStats().getMana();

        charDirRight = true;
        jumpCounter = 0;

        currentState = FightGame.MercenaryInGameState.STANDING;
        previousState = FightGame.MercenaryInGameState.STANDING;
        stateTimer = 0;

        killModel = false;
    }

    public FightGame.MercenaryInGameState getState() {
        if (mercenaryModel.getMercenaryBody().getLinearVelocity().y > 0) {
            return FightGame.MercenaryInGameState.JUMPING;
        } else if (mercenaryModel.getMercenaryBody().getLinearVelocity().y < 0) {
            return FightGame.MercenaryInGameState.FALLING;
        } else if (mercenaryModel.getMercenaryBody().getLinearVelocity().x != 0 && mercenaryModel.getMercenaryBody().getLinearVelocity().y == 0 ) {
            return FightGame.MercenaryInGameState.RUNNING;
        } else {
            return FightGame.MercenaryInGameState.STANDING;
        }
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    //Should I use an enu instead of a string for this?
    public void calculateDamage(Projectile p, String s) {
        if (s == "head") {
            System.out.println("headshot!");
            currentHealth -= p.getDamage();
            p.destroy(false);

        } else if (s == "body") {
            System.out.println("bodyshot!");
            currentHealth -= p.getDamage();
            p.destroy(false);
        }
        //Check if dead
        if (currentHealth <= 0) {
            currentHealth = 0;
            killModel = true;
        }
    }

    public boolean isKillModel() {
        return killModel;
    }

    public boolean isCharDirRight() {
        return charDirRight;
    }

    public void setCharDirRight(boolean charDirRight) {
        this.charDirRight = charDirRight;
    }

    public void update(float dt) {
        if (!mercenaryModel.isDead()) {
            if (currentState == FightGame.MercenaryInGameState.STANDING || currentState == FightGame.MercenaryInGameState.RUNNING) {
                jumpCounter = 0;
            }
        }
    }



}
