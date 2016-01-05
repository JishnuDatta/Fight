package com.jishd.fight.Sprites.Items;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jishd.fight.Screens.PlayScreen;
import com.jishd.fight.Sprites.Entity;

public abstract class Attack extends Entity {
    public Attack(PlayScreen playScreen, int positionX, int positionY, TextureRegion textureRegion) {
        super(playScreen, positionX, positionY, textureRegion);
        super.getEntityBody().setBullet(true);
    }
}
