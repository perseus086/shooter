package com.perseus.super_game.model;

public class Hero {
	
	private int id;
	private float x;
	private float y;
	private float speed;
	private int orientation;
	private int won;
	private int played;
	
	public Hero(float x,float y){
		this.x = x;
		this.y = y;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public int getOrientation() {
		return orientation;
	}
	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}
	public int getWon() {
		return won;
	}
	public void setWon(int won) {
		this.won = won;
	}
	public int getPlayed() {
		return played;
	}
	public void setPlayed(int played) {
		this.played = played;
	}
	
	public void update(){
		this.x = x + speed;
		if (speed == 0) this.x = x;
	}
	
}
