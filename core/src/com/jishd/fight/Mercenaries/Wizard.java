package com.jishd.fight.Mercenaries;

import com.jishd.fight.FightGame;
import com.jishd.fight.PlayerData.Player;

public class Wizard extends Mercenary {
    public Wizard(Player player) {
        super(player);
    }

    public void addAndEquipStarterItems() {

    }

    public FightGame.Mercenaries getType() {
        return FightGame.Mercenaries.WIZARD;
    }

    @Override
    public String toString() {
        return "Wizard";
    }
}
