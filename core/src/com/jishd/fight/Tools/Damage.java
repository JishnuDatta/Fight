package com.jishd.fight.Tools;

public class Damage {
    public float rangedDamage, magicDamage, shadowDamage, physicalDamage, techDamage;

    public Damage(float rangedDamage, float magicDamage, float shadowDamage, float physicalDamage, float techDamage) {
        this.rangedDamage = rangedDamage;
        this.magicDamage = magicDamage;
        this.shadowDamage = shadowDamage;
        this.physicalDamage = physicalDamage;
        this.techDamage = techDamage;
    }

    public int getTotalDamage() {
        return (int) (rangedDamage + magicDamage + shadowDamage + physicalDamage + techDamage);
    }
}
