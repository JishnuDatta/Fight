package com.jishd.fight.Items.Weapons;

import com.jishd.fight.FightGame;
import com.jishd.fight.Items.Item;

public class Bow extends Item {

    public Bow(int rangedAttributes, int magicAttributes, int shadowAttributes, int physicalAttributes, int techAttributes) {
        super(rangedAttributes, magicAttributes, shadowAttributes, physicalAttributes, techAttributes);
    }
    public FightGame.ItemType getItemType(){
        return FightGame.ItemType.WEAPON;
    }

    @Override
    public String getCombinedName() {
        return super.getCombinedName() + "Bow";
    }


}
