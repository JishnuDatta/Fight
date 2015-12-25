package com.jishd.fight.Mercenaries;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jishd.fight.FightGame;
import com.jishd.fight.PlayerData.Loadout;
import com.jishd.fight.PlayerData.Player;
import com.jishd.fight.PlayerData.Stats;
import com.jishd.fight.Screens.PlayScreen;

public abstract class Mercenary {
    public FightGame.Mercenaries type;
    //Should i make this public??
    protected Player player;
    protected Stats stats;
    protected Loadout loadout;

    public Mercenary(Player player) {
        this.player = player;
        this.stats = new Stats(this);
        this.loadout = new Loadout(this);
        addAndEquipStarterItems();
        type = getType();
    }

    public abstract void addAndEquipStarterItems();

    public abstract FightGame.Mercenaries getType();

    public Player getPlayer() {
        return player;
    }

    public Stats getStats(){
        return stats;
    }

    //Just a placeholder for when I create real sprites, convert this to abstract afterwards
    public TextureRegion getTextureRegion(){
        return new TextureRegion(((PlayScreen)player.getFightGame().getScreen()).getAtlas().findRegion("Archer"), 0, 0, 48, 64);
    }

    public int getRangedDamage() {
        return stats.getRangedDamage() + loadout.getRangedDamage();
    }

    public int getHeadshotDamage() {
        return stats.getHeadshotDamage() + loadout.getHeadshotDamage();
    }

    public int getProjectileSpeed() {
        return stats.getProjectileSpeed() + loadout.getProjectileSpeed();
    }

    public int getAttackSpeed() {
        return stats.getAttackSpeed() + loadout.getAttackSpeed();
    }

    public int getMagicResistance() {
        return stats.getMagicResistance() + loadout.getMagicResistance();
    }

    public int getTechResistance() {
        return stats.getTechResistance() + loadout.getTechResistance();
    }

    public int getMagicDamage() {
        return stats.getMagicDamage() + loadout.getMagicDamage();
    }

    public int getManaMult() {
        return stats.getManaMult() + loadout.getManaMult();
    }

    public int getManaRegen() {
        return stats.getManaMult() + loadout.getManaRegen();
    }

    public int getSpellPen() {
        return stats.getSpellPen() + loadout.getSpellPen();
    }

    public int getPhysicalResistance() {
        return stats.getPhysicalResistance() + loadout.getPhysicalResistance();
    }

    public int getShadowDamage() {
        return stats.getShadowDamage() + loadout.getShadowDamage();
    }

    public int getBackstabDamage() {
        return stats.getBackstabDamage() + loadout.getBackstabDamage();
    }

    public int getMovementSpeed() {
        return stats.getMovementSpeed() + loadout.getMovementSpeed();
    }

    public int getInvisibility() {
        return stats.getInvisibility() + loadout.getInvisibility();
    }

    public int getPhysicalDamage() {
        return stats.getPhysicalDamage() + loadout.getPhysicalDamage();
    }

    public int getHealthMult() {
        return stats.getHealthMult() + loadout.getHealthMult();
    }

    public int getHealthRegen() {
        return stats.getHealthRegen() + loadout.getHealthRegen();
    }

    public int getFrontDamage() {
        return stats.getFrontDamage() + loadout.getFrontDamage();
    }

    public int getRangedResistance() {
        return stats.getRangedResistance() + loadout.getRangedResistance();
    }

    public int getTechDamage() {
        return stats.getTechDamage() + loadout.getTechDamage();
    }

    public int getExplosionRange() {
        return stats.getExplosionRange() + loadout.getExplosionRange();
    }

    public int getLegAndFootDamage() {
        return stats.getLegAndFootDamage() + loadout.getLegAndFootDamage();
    }

    public int getPassiveResistance() {
        return stats.getPassiveResistance() + loadout.getPassiveResistance();
    }

    public int getShadowResistance() {
        return stats.getShadowResistance() + loadout.getShadowResistance();
    }

}
