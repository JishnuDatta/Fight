package com.jishd.fight.Items;

import com.jishd.fight.FightGame;
import com.jishd.fight.Tools.Damage;

public abstract class Item {
    private int rangedAttributes, magicAttributes, shadowAttributes, physicalAttributes, techAttributes, totalAttributes;

    private FightGame.WeaponDamageType damageType;
    private FightGame.Effect effect;
    //Number of different attributes the item provides
    private int diversity;

    private String tier, name, diversityDescription;

    //Level needed to equip item (also gives indication of the power of the item)
    private int level;

    //Can turn this into an array of effects later on.
    public Item(int level, int rangedAttributes, int magicAttributes, int shadowAttributes, int physicalAttributes, int techAttributes, FightGame.WeaponDamageType damageType, FightGame.Effect effect) {
        this.level = level;
        this.rangedAttributes = rangedAttributes;
        this.magicAttributes = magicAttributes;
        this.shadowAttributes = shadowAttributes;
        this.physicalAttributes = physicalAttributes;
        this.techAttributes = techAttributes;

        this.damageType = damageType;
        this.effect = effect;

        totalAttributes = rangedAttributes + magicAttributes + shadowAttributes + physicalAttributes + techAttributes;
        getDiversity();
        getTier();
        getDiversityDescription();
    }

    public int getRangedAttributes() {
        return rangedAttributes;
    }

    public int getShadowAttributes() {
        return shadowAttributes;
    }

    public int getMagicAttributes() {
        return magicAttributes;
    }

    public int getPhysicalAttributes() {
        return physicalAttributes;
    }

    public int getTechAttributes() {
        return techAttributes;
    }

    private void getDiversity() {
        if (rangedAttributes > 0) {
            diversity++;
        }
        if (magicAttributes > 0) {
            diversity++;
        }
        if (shadowAttributes > 0) {
            diversity++;
        }
        if (physicalAttributes > 0) {
            diversity++;
        }
        if (techAttributes > 0) {
            diversity++;
        }
    }

        //Get Diversity description
private void getDiversityDescription(){
        if (diversity == 5) {
            diversityDescription = "Master's";
        } else if (diversity == 4) {
            diversityDescription = "Jack of all Trades";
        } else if (diversity == 3) {
            diversityDescription = "Triple Threat";
        } else if (diversity == 2) {
            if (rangedAttributes > 0 && magicAttributes > 0) {
                diversityDescription = "Elven";
            } else if (rangedAttributes > 0 && shadowAttributes > 0) {
                diversityDescription = "Hitman's";
            } else if (rangedAttributes > 0 && physicalAttributes > 0) {
                diversityDescription = "Hunter's";
            } else if (rangedAttributes > 0 && techAttributes > 0) {
                diversityDescription = "Sniper's";
            } else if (magicAttributes > 0 && shadowAttributes > 0) {
                diversityDescription = "Spectre's";
            } else if (magicAttributes > 0 && physicalAttributes > 0) {
                diversityDescription = "Druid's";
            }
            //https://en.wikipedia.org/wiki/Clarke%27s_three_laws
            else if (magicAttributes > 0 && techAttributes > 0) {
                diversityDescription = "Clarke's";
            } else if (shadowAttributes > 0 && physicalAttributes > 0) {
                diversityDescription = "Slayer's";
            } else if (shadowAttributes > 0 && techAttributes > 0) {
                diversityDescription = "Terminator's";
            } else if (physicalAttributes > 0 && techAttributes > 0) {
                diversityDescription = "Destroyer's";
            }
        } else if (diversity == 1) {
            if (rangedAttributes > 0) {
                diversityDescription = "Archer's";
            } else if (magicAttributes > 0) {
                diversityDescription = "Warlock's";
            } else if (shadowAttributes > 0) {
                diversityDescription = "Reaper's";
            } else if (physicalAttributes > 0) {
                diversityDescription = "Warrior's";
            } else if (techAttributes > 0) {
                diversityDescription = "Cyborg's";
            }
        } else if (diversity == 0) {
            diversityDescription = "Piece Of";
        }
    }

    private void getTier() {
        if (totalAttributes / diversity >= 50) {
            tier = "Nemesis";
        } else if (totalAttributes / diversity >= 40) {
            tier = "Godly";
        } else if (totalAttributes / diversity >= 30) {
            tier = "Legendary";
        } else if (totalAttributes / diversity >= 20) {
            tier = "Supreme";
        } else if (totalAttributes / diversity >= 10) {
            tier = "Decent";
        } else if (totalAttributes / diversity > 0) {
            tier = "Basic";
        } else if (totalAttributes == 0) {
            tier = "Crap";
        }
    }

    public String getCombinedName() {
        return diversityDescription + " " + tier + " ";
    }

    public abstract FightGame.ItemType getItemType();

    public abstract FightGame.DamageForms getItemAffinity();

    public int getLevel(){
        return level;
    }

    public Damage getDamage(){
        Damage damageToBeReturned = new Damage(0,0,0,0,0);
        switch(damageType){
            case Boring: // 30 Affinity Damage + 100% * Mercenary Affinity Attributes
                if(getItemAffinity() == FightGame.DamageForms.Ranged){
                    float damageBoring = 30 + rangedAttributes;
                    damageToBeReturned.rangedDamage += damageBoring;
                }
                else if(getItemAffinity() == FightGame.DamageForms.Shadow){
                    float damageBoring = 30 + shadowAttributes;
                    damageToBeReturned.shadowDamage += damageBoring;
                }
                break;
            default:
                break;
        }
        return damageToBeReturned;
    }

    public abstract FightGame.Weapons getWeaponType();

}
