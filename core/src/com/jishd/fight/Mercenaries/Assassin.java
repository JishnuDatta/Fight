package com.jishd.fight.Mercenaries;

import com.jishd.fight.FightGame;
import com.jishd.fight.PlayerData.Player;

public class Assassin extends Mercenary {
    public Assassin(Player player) {
        super(player);
    }

    public void addAndEquipStarterItems() {

    }

    public FightGame.Mercenaries getType() {
        return FightGame.Mercenaries.ASSASSIN;
    }

    @Override
    public String toString() {
        return "Assassin";
    }
}
