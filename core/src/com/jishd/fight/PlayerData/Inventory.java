package com.jishd.fight.PlayerData;

import com.jishd.fight.FightGame;
import com.jishd.fight.Items.Item;

import java.util.ArrayList;


public class Inventory {
    private Player player;

    private ArrayList<Item> allItems;
    private ArrayList<Item> weaponItems;
    private ArrayList<Item> miscItems;
    private ArrayList<Item> runeItems;
    private ArrayList<Item> ammoItems;
    private ArrayList<Item> headItems;
    private ArrayList<Item> chestItems;
    private ArrayList<Item> legItems;
    private ArrayList<Item> footItems;

    public Inventory(Player player) {
        this.player = player;
    }

    public void addItem(Item item) {
        allItems.add(item);
        if (item.getItemType() == FightGame.ItemType.WEAPON) {
            weaponItems.add(item);
        } else if (item.getItemType() == FightGame.ItemType.MISC) {
            miscItems.add(item);
        } else if (item.getItemType() == FightGame.ItemType.RUNE) {
            runeItems.add(item);
        } else if (item.getItemType() == FightGame.ItemType.AMMO) {
           ammoItems.add(item);
        } else if (item.getItemType() == FightGame.ItemType.HEAD) {
            headItems.add(item);
        } else if (item.getItemType() == FightGame.ItemType.CHEST) {
            chestItems.add(item);
        } else if (item.getItemType() == FightGame.ItemType.LEG) {
            legItems.add(item);
        } else if (item.getItemType() == FightGame.ItemType.FOOT) {
            footItems.add(item);
        }
    }

    public ArrayList<Item> getAllItems() {
        return allItems;
    }
}


