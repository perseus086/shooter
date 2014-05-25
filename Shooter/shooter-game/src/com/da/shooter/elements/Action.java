package com.da.shooter.elements;

import java.io.Serializable;

import com.da.shooter.communication.Player;

public class Action implements Serializable {
	
	private static final long serialVersionUID = 5439287403890312005L;
	
	private int avatarId;
	private int id;
	private String username;
	private String name;
	
	public Action(Player player, int id, String name) {
		super();
		this.avatarId = player.getAvatarId();
		this.id = id;
		this.name = name;
		this.username = player.getUsername();
	}
	public int getAvatarId() {
		return avatarId;
	}
	public void setAvatarId(int avatarId) {
		this.avatarId = avatarId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
