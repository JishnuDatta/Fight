package com.jishd.fight;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jishd.fight.Screens.PlayScreen;
import com.jishd.fight.Sprites.Classes.Archer;
import com.jishd.fight.Sprites.Classes.Character;

import java.util.ArrayList;

public class FightGame extends Game {
    //Pixels per Meter Scaling, Used for Box2D physics
    public static final float PPM = 32;
    //Width and Height of Game Resolution
    public static final int V_WIDTH = 1280;
    public static final int V_HEIGHT = 720;
    //Bits
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
    private ArrayList<Character> playerList;

    //Keeps track of the screen, might not need to.
    private Screen currentScreen;

    public enum Mercenaries {RANGER, WIZARD, ASSASSIN, KNIGHT, TECH, HUMAN};
    public enum SLOT {weapon1Slot, weapon2Slot, misc1Slot, misc2Slot, runeSlot, ammoSlot, headSlot, chestSlot, legSlot, footSlot};
    public enum ItemType{WEAPON, MISC, RUNE, AMMO, HEAD, CHEST, LEG, FOOT};
    public enum Controls {UP, LEFT, RIGHT, DOWN, JUMP, WEAPON1, WEAPON2, SPELL, MISC1, MISC2};
    public enum MercenaryInGameState {STANDING, RUNNING, JUMPING, FALLING}

    //Appears to be the main function of the class
    @Override
    public void create() {
        playerList = new ArrayList<Character>();
        for (int i = 0; i < 4; i++) {
            playerList.add(new Archer(this));
        }
        playerList.get(0).controlList[0] = Input.Keys.UP;
        playerList.get(0).controlList[1] = Input.Keys.LEFT;
        playerList.get(0).controlList[2] = Input.Keys.RIGHT;
        playerList.get(0).controlList[3] = Input.Keys.DOWN;
        playerList.get(0).controlList[4] = Input.Keys.SPACE;


        //Creates the sprite batch and the screen and sets the current screen to the PlayScreen
        batch = new SpriteBatch();
        currentScreen = new PlayScreen(this);
        setScreen(currentScreen);
        //Hardcoding in four archers into the game for now.

    }

    ;

    public Screen getScreen() {
        return currentScreen;
    }

    ;

    @Override
    public void render() {
        super.render();
    }

    public ArrayList<Character> getPlayerList() {
        return playerList;
    }


}
