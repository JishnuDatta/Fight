package com.jishd.fight.Tools;

import com.jishd.fight.Items.Item;
import com.jishd.fight.Mercenaries.Mercenary;

public class DamageCalculator {

    public DamageCalculator() {
    }

    public Damage calcDamage(Mercenary mercenaryVictim, Mercenary mercenaryAttacker, Item damagingItem, String bodyPart) {
        Damage totalDamage = mercenaryAttacker.getBaseDamage();
        totalDamage.rangedDamage += damagingItem.getDamage().rangedDamage;
        totalDamage.magicDamage += damagingItem.getDamage().magicDamage;
        totalDamage.shadowDamage += damagingItem.getDamage().shadowDamage;
        totalDamage.physicalDamage += damagingItem.getDamage().physicalDamage;
        totalDamage.techDamage += damagingItem.getDamage().techDamage;

        //Calculate resistances - max (at level 100 is 50 % reduced damage) (at level 200 is ) - having trouble with floats and doubles, for now it is flat scaling
        // totalDamage.rangedDamage *=  100 - mercenaryVictim.getRangedResistance();
        totalDamage.magicDamage *= 100 - mercenaryVictim.getMagicResistance();
        totalDamage.shadowDamage *= 100 - mercenaryVictim.getShadowResistance();
        totalDamage.physicalDamage *= 100 - mercenaryVictim.getPhysicalResistance();
        totalDamage.techDamage *= 100 - mercenaryVictim.getTechResistance();

        //Do bodypart damage buffs / decreases here
        return totalDamage;
    }
}
