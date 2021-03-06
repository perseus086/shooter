package com.da.shooter;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.da.desktop.input.DesktopInputProcessor;

public class Main {
	
	public static LwjglApplication app;
	
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "shooter-game";
		cfg.useGL30 = false;
		cfg.width = 1280;
		cfg.height = 720;
		
		app = new LwjglApplication(new MyShooterGame(){
			public void initInputListeners(){
				//app.getInput().setInputProcessor(new DesktopInputProcessor());
			}
		}, cfg);
		
	}
}
