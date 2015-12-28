package com.jishd.fight.Mercenaries;

import com.jishd.fight.FightGame;
import com.jishd.fight.Items.Ammo.StarterArrow;
import com.jishd.fight.Items.Item;
import com.jishd.fight.Items.Weapons.StarterBow;
import com.jishd.fight.PlayerData.Player;

public class Ranger extends Mercenary {

    public Ranger(Player player) {
        super(player);
    }

    public void addAndEquipStarterItems() {
        Item starterItem1 = new StarterBow(1, 5, 0, 0, 0, 0);
        player.getInventory().addItem(starterItem1);
        loadout.equip(starterItem1, FightGame.SLOT.weapon1Slot);
        Item starterItem2 = new StarterArrow(1, 5, 0, 0, 0, 0);
        player.getInventory().addItem(starterItem2);
        loadout.equip(starterItem2, FightGame.SLOT.ammoSlot);
    }

    public FightGame.Mercenaries getType() {
        return FightGame.Mercenaries.RANGER;
    }

    @Override
    public String toString() {
        return "Ranger";
    }
}
