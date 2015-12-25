package com.jishd.fight.Sprites.Classes;


import com.jishd.fight.Screens.PlayScreen;
import com.jishd.fight.Sprites.Spells.Spell;

import java.util.ArrayList;

public abstract class Character {


    public int[] controlList = new int[8];

    public abstract String toString();

    public abstract Integer getCode();

    public abstract int getHealth();

    public abstract int getMana();

    public abstract ArrayList<Spell> getSpells();

    public abstract ArrayList<Integer> getStats();

    public abstract CharacterModel spawnCharacterModel(PlayScreen screen);

    public abstract int numberOfJumps();
}
