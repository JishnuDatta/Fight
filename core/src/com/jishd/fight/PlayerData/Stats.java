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
    private int rangedDamage, headshotDamage, projectileSpeed, attackSpeed, magicResistanceR, techResistanceR;
    //Magic attributes
    private int magicDamage, manaMult, physicalResistanceM, techResistanceM;
    //Shadow
    private int shadowDamage, backstabDamage, movementSpeed, invisibility, rangedResistanceS, magicResistanceS;
    //Physical
    private int physicalDamage, healthMult, rangedResistanceP, shadowResistanceP;
    //Tech
    private int techDamage, shadowResistanceT, physicalResistanceP;


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

    //Sort this
    public int getHealth(){
        return BASE_HEALTH ;//+ 5*(healthMult);
    }

    public int getMana(){
        return BASE_MANA ;//+ 5*(manaMult);
    }

}


