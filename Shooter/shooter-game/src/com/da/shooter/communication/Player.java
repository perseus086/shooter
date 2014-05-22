package com.da.shooter.communication;

import java.io.Serializable;

public class Player implements Serializable {
	private static final long serialVersionUID = 6048579983904069488L;
	
//	transient private InetAddress address;
	private int avatarId;

	private int status;
	
//	private float[] bodyPos;
//	private float[] swordPos;
	
//	@Transient
//	public InetAddress getAddress() {
//		return address;
//	}
//	
//	@Transient
//	public void setAddress(InetAddress address) {
//		this.address = address;
//	}
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

//	public float[] getBodyPos() {
//		return bodyPos;
//	}
//
//	public void setBodyPos(float[] bodyPos) {
//		this.bodyPos = bodyPos;
//	}
//
//	public float[] getSwordPos() {
//		return swordPos;
//	}
//
//	public void setSwordPos(float[] swordPos) {
//		this.swordPos = swordPos;
//	}
	
	public interface Status{
		int GAME_OVER = 0;
	}
	
}
