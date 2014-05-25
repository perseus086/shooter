package com.da.shooter.communication;

import java.io.Serializable;

public class Player implements Serializable {
	private static final long serialVersionUID = 6048579983904069488L;
	
//	transient private InetAddress address;
	private int avatarId;

	private int status;
	private String username;
	
	public Player() {
		this.status = Status.PLAYING;
	}

	public int getAvatarId() {
		return avatarId;
	}
	public void setAvatarId(int avatarId) {
		this.avatarId = avatarId;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	public int getStatus() {
		return status;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public interface Status{
		int PLAYING = 0;
		int GAME_OVER = 1;
	}
	
}
