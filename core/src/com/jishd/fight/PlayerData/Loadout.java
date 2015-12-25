package com.jishd.fight.PlayerData;

import com.jishd.fight.FightGame;
import com.jishd.fight.Items.Item;
import com.jishd.fight.Mercenaries.Mercenary;

public class Loadout {

    private Item weapon1, weapon2, misc1, misc2, rune, ammo, head, chest, leg, foot;
    private Mercenary mercenary;

    //Ranged attributes
    private int rangedDamage, headshotDamage, projectileSpeed, attackSpeed, techResistance;
    //Magic attributes
    private int magicDamage, manaMult, manaRegen, spellPen, physicalResistance;
    //Shadow
    private int shadowDamage, backstabDamage, movementSpeed, invisibility, magicResistance;
    //Physical
    private int physicalDamage, healthMult, healthRegen, frontDamage, rangedResistance;
    //Tech
    private int techDamage, explosionRange, legAndFootDamage, passiveResistance, shadowResistance;

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
            //Turn into switch statement? useful for doubles etc
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

    public int getRangedDamage() {
        return rangedDamage;
    }

    public void setRangedDamage(int rangedDamage) {
        this.rangedDamage = rangedDamage;
    }

    public int getHeadshotDamage() {
        return headshotDamage;
    }

    public void setHeadshotDamage(int headshotDamage) {
        this.headshotDamage = headshotDamage;
    }

    public int getProjectileSpeed() {
        return projectileSpeed;
    }

    public void setProjectileSpeed(int projectileSpeed) {
        this.projectileSpeed = projectileSpeed;
    }

    public int getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(int attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public int getTechResistance() {
        return techResistance;
    }

    public void setTechResistance(int techResistance) {
        this.techResistance = techResistance;
    }

    public int getMagicDamage() {
        return magicDamage;
    }

    public void setMagicDamage(int magicDamage) {
        this.magicDamage = magicDamage;
    }

    public int getManaMult() {
        return manaMult;
    }

    public void setManaMult(int manaMult) {
        this.manaMult = manaMult;
    }

    public int getManaRegen() {
        return manaRegen;
    }

    public void setManaRegen(int manaRegen) {
        this.manaRegen = manaRegen;
    }

    public int getSpellPen() {
        return spellPen;
    }

    public void setSpellPen(int spellPen) {
        this.spellPen = spellPen;
    }

    public int getPhysicalResistance() {
        return physicalResistance;
    }

    public void setPhysicalResistance(int physicalResistance) {
        this.physicalResistance = physicalResistance;
    }

    public int getShadowDamage() {
        return shadowDamage;
    }

    public void setShadowDamage(int shadowDamage) {
        this.shadowDamage = shadowDamage;
    }

    public int getBackstabDamage() {
        return backstabDamage;
    }

    public void setBackstabDamage(int backstabDamage) {
        this.backstabDamage = backstabDamage;
    }

    public int getMovementSpeed() {
        return movementSpeed;
    }

    public void setMovementSpeed(int movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public int getInvisibility() {
        return invisibility;
    }

    public void setInvisibility(int invisibility) {
        this.invisibility = invisibility;
    }

    public int getMagicResistance() {
        return magicResistance;
    }

    public void setMagicResistance(int magicResistance) {
        this.magicResistance = magicResistance;
    }

    public int getPhysicalDamage() {
        return physicalDamage;
    }

    public void setPhysicalDamage(int physicalDamage) {
        this.physicalDamage = physicalDamage;
    }

    public int getHealthMult() {
        return healthMult;
    }

    public void setHealthMult(int healthMult) {
        this.healthMult = healthMult;
    }

    public int getHealthRegen() {
        return healthRegen;
    }

    public void setHealthRegen(int healthRegen) {
        this.healthRegen = healthRegen;
    }

    public int getFrontDamage() {
        return frontDamage;
    }

    public void setFrontDamage(int frontDamage) {
        this.frontDamage = frontDamage;
    }

    public int getRangedResistance() {
        return rangedResistance;
    }

    public void setRangedResistance(int rangedResistance) {
        this.rangedResistance = rangedResistance;
    }

    public int getTechDamage() {
        return techDamage;
    }

    public void setTechDamage(int techDamage) {
        this.techDamage = techDamage;
    }

    public int getExplosionRange() {
        return explosionRange;
    }

    public void setExplosionRange(int explosionRange) {
        this.explosionRange = explosionRange;
    }

    public int getLegAndFootDamage() {
        return legAndFootDamage;
    }

    public void setLegAndFootDamage(int legAndFootDamage) {
        this.legAndFootDamage = legAndFootDamage;
    }

    public int getPassiveResistance() {
        return passiveResistance;
    }

    public void setPassiveResistance(int passiveResistance) {
        this.passiveResistance = passiveResistance;
    }

    public int getShadowResistance() {
        return shadowResistance;
    }

    public void setShadowResistance(int shadowResistance) {
        this.shadowResistance = shadowResistance;
    }


}
