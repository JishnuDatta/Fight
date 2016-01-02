package com.jishd.fight.Mercenaries;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jishd.fight.FightGame;
import com.jishd.fight.PlayerData.Loadout;
import com.jishd.fight.PlayerData.Player;
import com.jishd.fight.PlayerData.Stats;
import com.jishd.fight.Tools.Damage;

public abstract class Mercenary {
    //Should i make this public??
    protected Player player;
    protected Stats stats;
    protected Loadout loadout;

    public Mercenary(Player player) {
        this.player = player;
        this.stats = new Stats(this);
        this.loadout = new Loadout(this);
        addAndEquipStarterItems();
    }

    public abstract void addAndEquipStarterItems();

    public abstract FightGame.Mercenaries getType();

    public Player getPlayer() {
        return player;
    }

    public Loadout getLoadout() {
        return loadout;
    }

    public Stats getStats(){
        return stats;
    }

    //Just a placeholder for when I create real sprites, convert this to abstract afterwards
    public TextureRegion getTextureRegion(TextureAtlas atlas){
        return new TextureRegion(atlas.findRegion("Human"), 0, 0, 32, 72);
    }

    public Damage getBaseDamage(){
        return new Damage(getRangedDamage(), getMagicDamage(), getShadowDamage(), getPhysicalDamage(), getTechDamage());
    }

    public float getRangedDamage() {
        return stats.getRangedDamage() + loadout.getRangedDamage();
    }

    public float getHeadshotDamage() {
        return stats.getHeadshotDamage() + loadout.getHeadshotDamage();
    }

    public float getProjectileSpeed() {
        return stats.getProjectileSpeed() + loadout.getProjectileSpeed();
    }

    public float getAttackSpeed() {
        return stats.getAttackSpeed() + loadout.getAttackSpeed();
    }

    public float getMagicResistance() {
        return stats.getMagicResistance() + loadout.getMagicResistance();
    }

    public float getTechResistance() {
        return stats.getTechResistance() + loadout.getTechResistance();
    }

    public float getMagicDamage() {
        return stats.getMagicDamage() + loadout.getMagicDamage();
    }

    public float getManaMult() {
        return stats.getManaMult() + loadout.getManaMult();
    }

    public float getManaRegen() {
        return stats.getManaMult() + loadout.getManaRegen();
    }

    public float getSpellPen() {
        return stats.getSpellPen() + loadout.getSpellPen();
    }

    public float getPhysicalResistance() {
        return stats.getPhysicalResistance() + loadout.getPhysicalResistance();
    }

    public float getShadowDamage() {
        return stats.getShadowDamage() + loadout.getShadowDamage();
    }

    public float getBackstabDamage() {
        return stats.getBackstabDamage() + loadout.getBackstabDamage();
    }

    public float getMovementSpeed() {
        return stats.getMovementSpeed() + loadout.getMovementSpeed();
    }

    public float getInvisibility() {
        return stats.getInvisibility() + loadout.getInvisibility();
    }

    public float getPhysicalDamage() {
        return stats.getPhysicalDamage() + loadout.getPhysicalDamage();
    }

    //Hardcoding in 100 health for now.
    public float getHealthMult() {
        return stats.getHealthMult() + loadout.getHealthMult() + 100;
    }

    public float getHealthRegen() {
        return stats.getHealthRegen() + loadout.getHealthRegen();
    }

    public float getFrontDamage() {
        return stats.getFrontDamage() + loadout.getFrontDamage();
    }

    public float getRangedResistance() {
        return stats.getRangedResistance() + loadout.getRangedResistance();
    }

    public float getTechDamage() {
        return stats.getTechDamage() + loadout.getTechDamage();
    }

    public float getExplosionRange() {
        return stats.getExplosionRange() + loadout.getExplosionRange();
    }

    public float getLegAndFootDamage() {
        return stats.getLegAndFootDamage() + loadout.getLegAndFootDamage();
    }

    public float getPassiveResistance() {
        return stats.getPassiveResistance() + loadout.getPassiveResistance();
    }

    public float getShadowResistance() {
        return stats.getShadowResistance() + loadout.getShadowResistance();
    }

}
