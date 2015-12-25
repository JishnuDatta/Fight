package com.jishd.fight.Sprites.Classes;


import com.jishd.fight.Screens.PlayScreen;

public abstract class Character {


    public int[] controlList = new int[8];

    public abstract String toString();

    public abstract int getHealth();

    public abstract int getMana();

    public abstract CharacterModel spawnCharacterModel(PlayScreen screen);

    public abstract int numberOfJumps();
}
