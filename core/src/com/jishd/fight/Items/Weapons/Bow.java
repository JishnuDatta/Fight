package com.jishd.fight.Items.Weapons;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jishd.fight.FightGame;
import com.jishd.fight.Items.Item;

//List of bows to be added, laser bow(arrows fire without gravity, instantly??), ghost bow (arrows fire thru wall)

public class Bow extends Item {

    public Bow(int level, int rangedAttributes, int magicAttributes, int shadowAttributes, int physicalAttributes, int techAttributes, FightGame.WeaponDamageType damageType, FightGame.Effect effect) {
        super(level, rangedAttributes, magicAttributes, shadowAttributes, physicalAttributes, techAttributes, damageType, effect);
    }

    public FightGame.ItemType getItemType() {
        return FightGame.ItemType.WEAPON;
    }

    @Override
    public FightGame.DamageForms getItemAffinity() {
        return FightGame.DamageForms.Ranged;
    }

    @Override
    public String getCombinedName() {
        return super.getCombinedName() + "Bow";
    }

    public FightGame.Weapons getWeaponType() {
        return FightGame.Weapons.Bow;
    }

    public TextureRegion getProjectileTextureRegion(TextureAtlas atlas){
        return new TextureRegion(atlas.findRegion("Arrow"), 0, 0, 60, 12);
    }
}
