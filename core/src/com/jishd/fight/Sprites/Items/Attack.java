package com.jishd.fight.Sprites.Items;

import com.jishd.fight.Screens.PlayScreen;
import com.jishd.fight.Sprites.Entity;

public abstract class Attack extends Entity{
    public Attack(PlayScreen playScreen, int positionX, int positionY) {
        super(playScreen, positionX, positionY);
        super.getEntityBody().setBullet(true);
    }
}
