package com.da.shooter.communication;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.da.shooter.elements.Action;
import com.da.shooter.elements.Avatar;


public class Message implements Serializable {

	private static final long serialVersionUID = 7881482968420820365L;
	
	protected int type;
	
//	private int gameId;
	
//	private int port;
	
	private int avatarId;
	
	private int number;
	
	/** The data. */
	private Serializable data;
	
	/** The time. */
//	private long time;
	
	/** The address. */
//	private transient List<InetAddress> addresses;

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

//	public long getTime() {
//		return time;
//	}
//
//	public void setTime(long time) {
//		this.time = time;
//	}

//	public List<InetAddress> getAddresses() {
//		return addresses;
//	}
//
//	public void setAddresses(List<InetAddress> addresses) {
//		this.addresses = addresses;
//	}
//	
//	public void setAddress(InetAddress address){
//		this.addresses = new ArrayList<InetAddress>(Arrays.asList(new InetAddress[]{address}));
//	}

//	public int getGameId() {
//		return gameId;
//	}
//
//	public void setGameId(int gameId) {
//		this.gameId = gameId;
//	}
//	
//	public int getPort() {
//		return port;
//	}
//
//	public void setPort(int port) {
//		this.port = port;
//	}
//
	public int getAvatarId() {
		return avatarId;
	}

	public void setAvatarId(int avatarId) {
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
		int REQUEST_MESSAGE_ID = 4;
		int NEW_PLAYER = 5;
		int LIFE_REDUCE = 6;
		int GAME_OVER = 7;
	}
	
	public String getMessageTypeName(){
		switch (this.type) {
			case Type.ACTION: return "ACTION";
			case Type.REQUEST_ID: return "REQUEST_ID";
			case Type.REQUEST_ID_RESPONSE: return "REQUEST_ID_RESPONSE";
			case Type.POSITIONS: return "POSITIONS";
			case Type.REQUEST_MESSAGE_ID: return "REQUEST_MESSAGE_ID";
			case Type.NEW_PLAYER: return "NEW_PLAYER";
			case Type.LIFE_REDUCE: return "LIFE_REDUCE";
			case Type.GAME_OVER: return "GAME_OVER";
			default: return "NO_TYPE";
		}
	}
	
	public String getMessageDetails(){
		switch (this.type) {
			case Type.ACTION:
				Action action = (Action) this.getData();
				return "player_id:"+this.getAvatarId()+" | action:"+action.getName();
			default:
				return "";
		}
	}
}
