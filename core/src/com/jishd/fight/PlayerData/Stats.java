package com.jishd.fight.PlayerData;

import com.jishd.fight.FightGame;
import com.jishd.fight.Mercenaries.Mercenary;

public class Stats {
    private Mercenary mercenary;

    private static final int BASE_HEALTH = 200;
    private static final int BASE_MANA = 100;

    private int ranged, magic, shadow, physical, tech;
    private int maxRanged, maxMagic, maxShadow, maxPhysical, maxTech;
    private int level, experience;

    private int passiveLevel;
    private String passive;

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

    public Stats(Mercenary mercenary) {
        this.mercenary = mercenary;

        level = 1;
        experience = 0;

        ranged = 0;
        magic = 0;
        shadow = 0;
        physical = 0;
        tech = 0;

        maxRanged = FightGame.BASE_MAX_ATTRIBUTE_LEVEL;
        maxMagic = FightGame.BASE_MAX_ATTRIBUTE_LEVEL;
        maxShadow = FightGame.BASE_MAX_ATTRIBUTE_LEVEL;
        maxPhysical = FightGame.BASE_MAX_ATTRIBUTE_LEVEL;
        maxTech = FightGame.BASE_MAX_ATTRIBUTE_LEVEL;

        switch (mercenary.type) {
            case RANGER:
                maxRanged *= 2;
                passive = "knockback";
                break;
            case WIZARD:
                maxMagic *= 2;
                passive = "curse";
                break;
            case ASSASSIN:
                maxShadow *= 2;
                passive = "crit";
                break;
            case KNIGHT:
                maxPhysical *= 2;
                passive = "stun";
                break;
            case TECH:
                maxTech *= 2;
                passive = "burn";
                break;
            default:
                break;
        }
    }

    public void levelUp() {
        switch (mercenary.type) {
            case RANGER:
                ranged += FightGame.BASE_MAX_ATTRIBUTE_LEVEL / 100;
                break;
            case WIZARD:
                magic += FightGame.BASE_MAX_ATTRIBUTE_LEVEL / 100;
                break;
            case ASSASSIN:
                shadow += FightGame.BASE_MAX_ATTRIBUTE_LEVEL / 100;
                break;
            case KNIGHT:
                physical += FightGame.BASE_MAX_ATTRIBUTE_LEVEL / 100;
                break;
            case TECH:
                tech += FightGame.BASE_MAX_ATTRIBUTE_LEVEL / 100;
                break;
            default:
                break;
        }
        passiveLevel++;
    }

    public void addAttributePoint() {
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


