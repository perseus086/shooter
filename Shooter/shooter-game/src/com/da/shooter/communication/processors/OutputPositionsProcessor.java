package com.da.shooter.communication.processors;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.da.shooter.GameScreen;
import com.da.shooter.communication.CommunicationManager;
import com.da.shooter.communication.Message;
import com.da.shooter.communication.Player;
import com.da.shooter.elements.Avatar;

public class OutputPositionsProcessor extends OutputMessageProcessor {

	
	private Map<String,Player> lastPositions;
	
	public OutputPositionsProcessor() {
		super();
		this.lastPositions = null;
	}

	@Override
	Message process() {
//		try {
//			sleep(10);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		// Positions
		Map<String,Avatar> avatars = GameScreen.getInstance().getAvatars();
		Map<String,Player> positions = new HashMap<String,Player>();
		for (String avatarId : avatars.keySet()) {
			Player player = new Player();
			player.setAvatarId(avatarId);
			Vector2 pos = avatars.get(avatarId).getBody().getPosition().cpy();
			player.setBodyPos(new float[]{pos.x,pos.y});
			pos = avatars.get(avatarId).getBody(Avatar.Constants.BODY_SWORD).getPosition();
			player.setSwordPos(new float[]{pos.x,pos.y,avatars.get(avatarId).getBody(Avatar.Constants.BODY_SWORD).getAngle()});
			positions.put(avatarId,player);
		}
		
		// Check position changes
		if(!checkPositions(positions)){
			return null;
		}
		this.lastPositions = positions;
		
		// Message
		Message msg = new Message(Message.Type.POSITIONS);
		msg.setData((Serializable) positions);
		msg.setAvatarId(GameScreen.getInstance().getAvatarId());
		msg.setNumber(this.msgCounter++);
		msg.setPort(CommunicationManager.getInstance().getPlayerPort());
		List<InetAddress> addresses = new ArrayList<InetAddress>();
		for (Player player : CommunicationManager.getInstance().getPlayers().values()) {
			addresses.add(player.getAddress());
		}
		msg.setAddresses(addresses);
		return msg;
	}
	
	private boolean checkPositions(Map<String,Player> positions){
		if(this.lastPositions == null) return true;
		for (String avatarId : positions.keySet()) {
			if(!this.lastPositions.containsKey(avatarId)) return true;
			if(!comparePlayers(positions.get(avatarId),lastPositions.get(avatarId))){
				return true;
			}
		}
		return false;
	}
	
	private boolean comparePlayers(Player p1, Player p2){
		if(!Arrays.equals(p1.getBodyPos(), p2.getBodyPos())){
			return false;
		}
		if(!Arrays.equals(p1.getSwordPos(), p2.getSwordPos())){
			return false;
		}
		return true;
	}

}
