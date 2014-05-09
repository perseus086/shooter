package com.da.shooter.communication;

import java.io.Serializable;

public class Player implements Serializable {
	private static final long serialVersionUID = 6048579983904069488L;
	
//	transient private InetAddress address;
	private int avatarId;
	
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
	
}
