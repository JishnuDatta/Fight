package com.jishd.fight.Mercenaries;

import com.jishd.fight.FightGame;
import com.jishd.fight.PlayerData.Player;

public class Human extends Mercenary {
    public Human(Player player) {
        super(player);
    }

    //Nothing for humans
    public void addAndEquipStarterItems() {

    }

    public FightGame.Mercenaries getType() {
        return FightGame.Mercenaries.HUMAN;
    }

    @Override
    public String toString() {
        return "Human";
    }
}
