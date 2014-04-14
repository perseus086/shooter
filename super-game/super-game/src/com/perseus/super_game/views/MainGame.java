package com.perseus.super_game.views;

import com.badlogic.gdx.Game;

public class MainGame extends Game {

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
		super.resize(width,height);
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
