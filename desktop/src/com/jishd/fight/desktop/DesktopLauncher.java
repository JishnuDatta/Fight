package com.jishd.fight.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.jishd.fight.FightGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//config.fullscreen = true;
		//Gdx.graphics.setDisplayMode(1280, 720, true);
		config.width = 1280;
		config.height = 720;
		TexturePacker.process("../assets/classes/images", "../assets", "FightGame");
		new LwjglApplication(new FightGame(), config);
	}
}
