package com.jishd.fight.Tools;

import com.badlogic.gdx.math.Vector2;
import com.jishd.fight.Sprites.Classes.MercenaryModel;

public class DamageOnHitGenerator {
    public DamageOnHitGenerator(MercenaryModel mercenaryModel, float x, float y, float damage){
        Vector2 generateRandomMomentum = new Vector2(((float) Math.random() - 0.5f) * 4,3);
    }
}
