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
    //Need to make private at one point
    public int[] controls;
    private ArrayList<Mercenary> fighterList;

    private Mercenary currentMercenary;

    public Player(FightGame fightGame, String name) {
        this.fightGame = fightGame;
        this.name = name;
        inventory = new Inventory(this);
        controls = new int[8];
        fighterList = new ArrayList<Mercenary>();
        //Adding a new human (symbolising yourself)
        fighterList.add(new Human(this));
        currentMercenary = fighterList.get(0);
    }

    //Buys a new mercenary and their gear and sets them as the current mercenary, enum is just a placeholder for gui
    public void buyMercenary(FightGame.Mercenaries m) {
        Mercenary newMercenary;
        switch (m) {
            case RANGER:
                newMercenary = new Ranger(this);
                break;
            case WIZARD:
                newMercenary = new Wizard(this);
                break;
            case ASSASSIN:
                newMercenary = new Assassin(this);
                break;
            case KNIGHT:
                newMercenary = new Knight(this);
                break;
            case TECH:
                newMercenary = new Tech(this);

                break;
            default:
                newMercenary = new Human(this);
                break;
        }
        fighterList.add(newMercenary);
        currentMercenary = newMercenary;
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

    public Mercenary getCurrentMercenary() {
        return currentMercenary;
    }
}
