package com.jishd.fight.Items.Weapons;

import com.jishd.fight.FightGame;
import com.jishd.fight.Items.Item;

public class Dagger extends Item {
    public Dagger(int level, int rangedAttributes, int magicAttributes, int shadowAttributes, int physicalAttributes, int techAttributes, FightGame.WeaponDamageType damageType, FightGame.Effect effect) {
        super(level, rangedAttributes, magicAttributes, shadowAttributes, physicalAttributes, techAttributes, damageType, effect);
    }

    @Override
    public FightGame.ItemType getItemType() {
         return FightGame.ItemType.WEAPON;
    }

    @Override
    public FightGame.DamageForms getItemAffinity() {
        return FightGame.DamageForms.Shadow;
    }

    @Override
    public String getCombinedName() {
        return super.getCombinedName() + "Dagger";
    }

    public FightGame.Weapons getWeaponType(){
        return FightGame.Weapons.Dagger;
    }
}
