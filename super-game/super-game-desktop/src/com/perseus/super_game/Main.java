package com.perseus.super_game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.perseus.super_game.views.MainGame;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "super-game";
		cfg.useGL30 = false;
		cfg.width = 720;
		cfg.height = 1280;
		
		new LwjglApplication(new MainGame(), cfg);
	}
}
