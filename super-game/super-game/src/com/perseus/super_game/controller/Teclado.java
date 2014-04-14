package com.perseus.super_game.controller;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.perseus.super_game.model.Hero;


public class Teclado extends InputAdapter implements InputProcessor{

	private Hero hero;
	
	public Teclado(Hero hero){
		
		this.hero = hero;
	}
	
	
	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.RIGHT){
			hero.setSpeed(3);;
		}
		
		if(keycode == Keys.LEFT){
			hero.setSpeed(-3);;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Keys.RIGHT){
			hero.setSpeed(0);;
		}
		
		if(keycode == Keys.LEFT){
			hero.setSpeed(-0);;
		}
		
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		System.out.println("Mouse aplasto en:" + screenX + "," + screenY);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
