package com.jishd.fight.Mercenaries;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jishd.fight.FightGame;
import com.jishd.fight.Items.Item;
import com.jishd.fight.Items.Weapons.Bow;
import com.jishd.fight.PlayerData.Player;

public class Ranger extends Mercenary {

    public Ranger(Player player) {
        super(player);
    }

    public void addAndEquipStarterItems() {
        Item starterItem1 = new Bow(1, 5, 0, 0, 0, 0, FightGame.WeaponDamageType.Boring, FightGame.Effect.NONE);
        player.getInventory().addItem(starterItem1);
        loadout.equip(starterItem1, FightGame.SLOT.weapon1Slot);
    }

    public FightGame.Mercenaries getType() {
        return FightGame.Mercenaries.RANGER;
    }

    @Override
    public String toString() {
        return "Ranger";
    }

    @Override
    public TextureRegion getTextureRegion(TextureAtlas atlas) {
        return new TextureRegion(atlas.findRegion("Archer"), 0, 0, 32, 72);
    }
}
