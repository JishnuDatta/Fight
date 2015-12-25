package com.jishd.fight.Mercenaries;

import com.jishd.fight.FightGame;
import com.jishd.fight.PlayerData.Player;

public class Tech extends Mercenary {
    public Tech(Player player) {
        super(player);
    }

    public void addAndEquipStarterItems() {

    }

    public FightGame.Mercenaries getType() {
        return FightGame.Mercenaries.TECH;
    }

    @Override
    public String toString() {
        return "Tech";
    }
}
