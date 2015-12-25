package com.jishd.fight.PlayerData;

import com.jishd.fight.FightGame;
import com.jishd.fight.Items.Item;
import com.jishd.fight.Mercenaries.Mercenary;

public class Loadout {

    private Item weapon1, weapon2, misc1, misc2, rune, ammo, head, chest, leg, foot;
    private Mercenary mercenary;

    public Loadout(Mercenary mercenary) {
        this.mercenary = mercenary;

        //Do I have to set them as null?
        weapon1 = null;
        weapon2 = null;
        misc1 = null;
        misc2 = null;
        rune = null;
        ammo = null;
        head = null;
        chest = null;
        leg = null;
        foot = null;
    }

    public void equip(Item item, FightGame.SLOT slot) {
        if(mercenary.getPlayer().getInventory().getAllItems().contains(item)){
        if (slot == FightGame.SLOT.weapon1Slot) {
            weapon1 = item;
        } else if (slot == FightGame.SLOT.weapon2Slot) {
            weapon2 = item;
        } else if (slot == FightGame.SLOT.misc1Slot) {
            misc1 = item;
        } else if (slot == FightGame.SLOT.misc2Slot) {
            misc2 = item;
        } else if (slot == FightGame.SLOT.runeSlot) {
            rune = item;
        } else if (slot == FightGame.SLOT.ammoSlot) {
            ammo = item;
        } else if (slot == FightGame.SLOT.headSlot) {
            head = item;
        } else if (slot == FightGame.SLOT.chestSlot) {
            chest = item;
        } else if (slot == FightGame.SLOT.legSlot) {
            leg = item;
        } else if (slot == FightGame.SLOT.footSlot) {
            foot = item;
        }
        }
    }

    public Item getWeapon1() {
        return weapon1;
    }

    public Item getWeapon2() {
        return weapon2;
    }

    public Item getMisc1() {
        return misc1;
    }

    public Item getMisc2() {
        return misc2;
    }

    public Item getRune() {
        return rune;
    }

    public Item getAmmo() {
        return ammo;
    }

    public Item getHead() {
        return head;
    }

    public Item getChest() {
        return chest;
    }

    public Item getLeg() {
        return leg;
    }

    public Item getFoot() {
        return foot;
    }

    public Mercenary getMercenary() {
        return mercenary;
    }
}
