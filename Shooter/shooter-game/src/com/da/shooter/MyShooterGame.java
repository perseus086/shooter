package com.da.shooter;

import com.badlogic.gdx.Game;
import com.da.shooter.Splash;

public class MyShooterGame extends Game {
	
	@Override
	public void create() {		
		this.setScreen(new Splash(this));
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {		
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}
