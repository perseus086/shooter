package com.da.shooter.elements;

import java.io.Serializable;

public class Action implements Serializable {
	
	private static final long serialVersionUID = 5439287403890312005L;
	
	private int avatarId;
	private int id;
	private String name;
	
	public Action(int avatarId, int id, String name) {
		super();
		this.avatarId = avatarId;
		this.id = id;
		this.name = name;
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
	
}
