package com.da.shooter.communication;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Message implements Serializable {

	private static final long serialVersionUID = 7881482968420820365L;
	
	protected int type;
	
	private int gameId;
	
	private int port;
	
	private String avatarId;
	
	private int number;
	
	/** The data. */
	private Serializable data;
	
	/** The time. */
	private long time;
	
	/** The address. */
	private transient List<InetAddress> addresses;

	public Message(int type) {
		super();
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Serializable getData() {
		return data;
	}

	public void setData(Serializable data) {
		this.data = data;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public List<InetAddress> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<InetAddress> addresses) {
		this.addresses = addresses;
	}
	
	public void setAddress(InetAddress address){
		this.addresses = new ArrayList<InetAddress>(Arrays.asList(new InetAddress[]{address}));
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getAvatarId() {
		return avatarId;
	}

	public void setAvatarId(String avatarId) {
		this.avatarId = avatarId;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public interface Type{
		int ACTION = 0;
		int REQUEST_ID = 1;
		int REQUEST_ID_RESPONSE = 2;
		int POSITIONS = 3;
	}
}
