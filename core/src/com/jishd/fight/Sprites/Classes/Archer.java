package com.jishd.fight.Sprites.Classes;

import com.jishd.fight.FightGame;
import com.jishd.fight.Screens.PlayScreen;
import com.jishd.fight.Sprites.Spells.Spell;

import java.util.ArrayList;


public class Archer extends Character {
    private FightGame game;

    public Archer(FightGame game) {

    }

    @Override
    public String toString() {
        return "Archer";
    }

    @Override
    public Integer getCode() {
        return 0;
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
    public ArrayList<Spell> getSpells() {
        return null;
    }

    @Override
    public ArrayList<Integer> getStats() {
        return null;
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
