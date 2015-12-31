package com.jishd.fight.Mercenaries;

import com.jishd.fight.FightGame;
import com.jishd.fight.Items.Item;
import com.jishd.fight.Items.Weapons.Dagger;
import com.jishd.fight.PlayerData.Player;

public class Assassin extends Mercenary {
    public Assassin(Player player) {
        super(player);
    }

    public void addAndEquipStarterItems() {
        Item starterItem1 = new Dagger(1, 5, 0, 0, 0, 0, FightGame.WeaponDamageType.Boring, FightGame.Effect.NONE);
        player.getInventory().addItem(starterItem1);
        loadout.equip(starterItem1, FightGame.SLOT.weapon1Slot);
    }

    public FightGame.Mercenaries getType() {
        return FightGame.Mercenaries.ASSASSIN;
    }

    @Override
    public String toString() {
        return "Assassin";
    }
}
