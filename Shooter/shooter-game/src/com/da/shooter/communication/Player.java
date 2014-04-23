package com.da.shooter.communication;

import java.beans.Transient;
import java.io.Serializable;
import java.net.InetAddress;

public class Player implements Serializable {
	private static final long serialVersionUID = 6048579983904069488L;
	
	transient private InetAddress address;
	private String avatarId;
	
	private float[] bodyPos;
	private float[] swordPos;
	
	@Transient
	public InetAddress getAddress() {
		return address;
	}
	
	@Transient
	public void setAddress(InetAddress address) {
		this.address = address;
	}
	public String getAvatarId() {
		return avatarId;
	}
	public void setAvatarId(String avatarId) {
		this.avatarId = avatarId;
	}

	public float[] getBodyPos() {
		return bodyPos;
	}

	public void setBodyPos(float[] bodyPos) {
		this.bodyPos = bodyPos;
	}

	public float[] getSwordPos() {
		return swordPos;
	}

	public void setSwordPos(float[] swordPos) {
		this.swordPos = swordPos;
	}
	
}
