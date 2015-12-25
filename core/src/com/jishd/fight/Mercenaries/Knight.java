package com.jishd.fight.Mercenaries;

import com.jishd.fight.FightGame;
import com.jishd.fight.PlayerData.Player;

public class Knight extends Mercenary {
    public Knight(Player player) {
        super(player);
    }

    public void addAndEquipStarterItems() {

    }

    public FightGame.Mercenaries getType() {
        return FightGame.Mercenaries.KNIGHT;
    }

    @Override
    public String toString() {
        return "Knight";
    }
}
