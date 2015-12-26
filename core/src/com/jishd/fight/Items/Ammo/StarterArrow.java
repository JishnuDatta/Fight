package com.jishd.fight.Items.Ammo;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jishd.fight.FightGame;
import com.jishd.fight.Items.Item;

public class StarterArrow extends Item{
    public StarterArrow(int rangedAttributes, int magicAttributes, int shadowAttributes, int physicalAttributes, int techAttributes) {
        super(rangedAttributes, magicAttributes, shadowAttributes, physicalAttributes, techAttributes);
    }

    @Override
    public FightGame.ItemType getItemType() {
        return FightGame.ItemType.AMMO;
    }

    @Override
    public String toString() {
        return "Starter Arrow";
    }

    public TextureRegion getTexture(TextureAtlas atlas){
        return new TextureRegion(atlas.findRegion("Arrow"), 0, 0, 40, 12);
    }

}
