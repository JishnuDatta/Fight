package com.jishd.fight.PlayerData;
// Costs money to heal / revive, gain gold per kill (glory), money for winning etc

import com.jishd.fight.FightGame;
import com.jishd.fight.Mercenaries.Assassin;
import com.jishd.fight.Mercenaries.Human;
import com.jishd.fight.Mercenaries.Knight;
import com.jishd.fight.Mercenaries.Mercenary;
import com.jishd.fight.Mercenaries.Ranger;
import com.jishd.fight.Mercenaries.Tech;
import com.jishd.fight.Mercenaries.Wizard;

import java.util.ArrayList;

public class Player {

    private FightGame fightGame;
    private String name;
    private Inventory inventory;
    private int[] controls;
    private ArrayList<Mercenary> fighterList;

    public Player(FightGame fightGame, String name) {
        this.fightGame = fightGame;
        this.name = name;
        inventory = new Inventory(this);
        controls = new int[8];
        fighterList = new ArrayList<Mercenary>();
        //Adding a new human (symbolising yourself)
        fighterList.add(new Human(this));
    }

    //Buys a new mercenary and their gear, enum is just a placeholder for gui
    public void buyMercenary(FightGame.Mercenaries m, Player player) {
        switch (m) {
            case RANGER:
                Mercenary ranger = new Ranger(this);
                fighterList.add(ranger);
                break;
            case WIZARD:
                Mercenary wizard = new Wizard(this);
                fighterList.add(wizard);
                break;
            case ASSASSIN:
                Mercenary assassin = new Assassin(this);
                fighterList.add(assassin);
                break;
            case KNIGHT:
                Mercenary knight = new Knight(this);
                fighterList.add(knight);
                break;
            case TECH:
                Mercenary tech = new Tech(this);
                fighterList.add(tech);
                break;
            default:
                break;
        }
    }

    public String getName() {
        return name;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public ArrayList<Mercenary> getMercenaryList() {
        return fighterList;
    }

    public FightGame getFightGame() {
        return fightGame;
    }
}
