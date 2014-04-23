package com.da.shooter;

import com.badlogic.gdx.Game;

public class MyShooterGame extends Game {
	
	@Override
	public void create() {		
		GameScreen.createInstance(this,false,"localhost");
		this.setScreen(GameScreen.getInstance());// Splash(this));
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
