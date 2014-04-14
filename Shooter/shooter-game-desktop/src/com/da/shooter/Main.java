package com.da.shooter;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "shooter-game";
		cfg.useGL30 = false;
		cfg.width = 720;
		cfg.height = 1280;
		
		new LwjglApplication(new MyShooterGame(), cfg);
	}
}
