package com.jishd.fight.Sprites.Classes;

import com.jishd.fight.FightGame;
import com.jishd.fight.Screens.PlayScreen;


public class Archer extends Character {
    private FightGame game;

    public Archer(FightGame game) {

    }

    @Override
    public String toString() {
        return "Archer";
    }

    @Override
    public int getHealth() {
        return 200;
    }

    @Override
    public int getMana() {
        return 100;
    }

    @Override
    public CharacterModel spawnCharacterModel(PlayScreen screen) {
        return new CharacterModel(screen, this, 48, 64);
    }

    @Override
    public int numberOfJumps() {
        return 1;
    }
}
