package com.jishd.fight.Mercenaries;

import com.jishd.fight.FightGame;
import com.jishd.fight.PlayerData.Loadout;
import com.jishd.fight.PlayerData.Player;
import com.jishd.fight.PlayerData.Stats;

public abstract class Mercenary {
    public FightGame.Mercenaries type;
    //Should i make this public?
    protected Player player;
    protected Stats stats;
    protected Loadout loadout;

    public Mercenary(Player player) {
        this.player = player;
        this.stats = new Stats(this);
        this.loadout = new Loadout(this);
        addAndEquipStarterItems();
        type = getType();
    }

    public abstract void addAndEquipStarterItems();

    public abstract FightGame.Mercenaries getType();

    public Player getPlayer() {
        return player;
    }

    public Stats getStats(){
        return stats;
    }
}
