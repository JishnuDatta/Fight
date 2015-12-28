package com.jishd.fight;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jishd.fight.PlayerData.Player;
import com.jishd.fight.Screens.PlayScreen;

import java.util.ArrayList;

public class FightGame extends Game {
    //Pixels per Meter Scaling, Used for Box2D physics
    public static final float PPM = 32;
    //Width and Height of Game Resolution
    public static final int V_WIDTH = 1280;
    public static final int V_HEIGHT = 720;
    //Bits used for collision detection
    public static final short NOTHING_BIT = 0;
    public static final short HEAD_BIT = 1;
    public static final short BODY_BIT = 2;
    public static final short PROJECTILE_BIT = 4;
    public static final short TILE_BIT = 8;
    //public static final short DESTROYED_BIT = 16;
    //public static final short OBJECT_BIT = 32;
    //public static final short ENEMY_BIT = 64;
    //public static final short ENEMY_HEAD_BIT = 128;
    //public static final short ITEM_BIT = 256;
    //public static final short MARIO_HEAD_BIT = 512;
    //public static final short FIREBALL_BIT = 1024;

    //Gameplay wise
    public static final int BASE_MAX_ATTRIBUTE_LEVEL = 500;

    //Sprite Batch For Entire Game
    public SpriteBatch batch;
    //Storing the players, maybe need to do after a menu? hardcoded for now
    private ArrayList<Player> playerList;

    //Keeps track of the screen, might not need to.
    private Screen currentScreen;

    public enum Mercenaries {RANGER, WIZARD, ASSASSIN, KNIGHT, TECH, HUMAN}
    public enum SLOT {weapon1Slot, weapon2Slot, misc1Slot, misc2Slot, runeSlot, ammoSlot, headSlot, chestSlot, legSlot, footSlot}
    public enum ItemType{WEAPON, MISC, RUNE, AMMO, HEAD, CHEST, LEG, FOOT}
    public enum Controls {UP, LEFT, RIGHT, DOWN, JUMP, WEAPON1, WEAPON2, SPELL, MISC1, MISC2}
    public enum MercenaryInGameState {STANDING, RUNNING, JUMPING, FALLING}
    public enum WeaponDamageType{SelfReliant, Specialist, Dualist, Default, Confused, Random, AllRounder, HeadHunter, LegBreaker, ChestCarver, BackStabber, Comboer, Diversified}//Used for damage calculation
    //Showing maximum ranges for these weapons:
    //Self-reliant, gives 200% on highest attribute it provides.
    //Specialist, gives 500% of a certain stat, if the mercenary only has stats in that category
    //Dualist, gives 100% on top two stats of the mercenary
    //Default, gives 200% on top stat mercenary has.
    //Confused, gives 300% on smallest stat mercenary has
    //Random, gives 400% on a random stat every hit.
    //All rounder, gives 100% on all stats.
    //HeadHunter, gives 1000% of ranged on all headshots.
    //Legbreaker, gives 400% of tech on all leg
    //chestcarver, gives 400% of physical on all front hits
    //backstabber, gives 400% of shadow on all backstabs.
    //comboer, gives 100% -> 300% of best attribute, for each continuous hit in a short amount of time (maybe base it on attack speed?)
    //Diversity, give 300% split evenly between each attribute it gives.
    //Jujutsu, does damage 300% based on enemies max attribute.
    //more to come
    public enum Stages{STAGE1}

    //Appears to be the main function of the class
    @Override
    public void create() {
        Gdx.graphics.setDisplayMode(Gdx.graphics.getDesktopDisplayMode().width, Gdx.graphics.getDesktopDisplayMode().height,true);
        //Add as many players as who join in //this can be done later, for now hardcoded as 4 players
        playerList = new ArrayList<Player>();
        for (int i = 0; i < 4; i++) {
            playerList.add(new Player(this, "Player " + Integer.toString(i)));
            //Their current mercenaries are humans, so I am adding and setting new rangers for them
            playerList.get(i).buyMercenary(Mercenaries.RANGER);
        }
        //Hardcoding control values, also find out why printscreen is causing other players to act
        playerList.get(0).controls[0] = Input.Keys.UP;
        playerList.get(0).controls[1] = Input.Keys.LEFT;
        playerList.get(0).controls[2] = Input.Keys.RIGHT;
        playerList.get(0).controls[3] = Input.Keys.DOWN;
        playerList.get(0).controls[4] = Input.Keys.SPACE;

        //Creates the sprite batch and the screen and sets the current screen to the PlayScreen
        batch = new SpriteBatch();
        //This will be replaced by menuscreen gui
        currentScreen = new PlayScreen(this, Stages.STAGE1);
        setScreen(currentScreen);
    }

    public Screen getScreen() {
        return currentScreen;
    }

    @Override
    public void render() {
        super.render();
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }


}
